param(
    [string]$Token = $env:MODRINTH_TOKEN,
    [string]$Version = "1.2.0",
    [ValidateSet("fabric", "forge", "neoforge")]
    [string]$Loader = "fabric",
    [string]$ArtifactsDir = "",
    [string]$Changelog = "",
    [switch]$IncludeSources,
    [switch]$ReplaceOldVersions,
    [switch]$AddQuiltLoaders,
    [switch]$DryRun
)

$ErrorActionPreference = "Stop"
$api = "https://api.modrinth.com/v2"
$projectSlug = "itemframesplus"
$fabricApiProjectId = "P7dR8mSH"

if (-not $ArtifactsDir) {
    $ArtifactsDir = Join-Path (Split-Path $PSScriptRoot -Parent) "build\libs\$Version"
}
$ArtifactsDir = (Resolve-Path $ArtifactsDir).Path

$defaultChangelog = @"
- Fixed item frame hitboxes not being shrunk on 1.20.5/1.20.6
- Fixed invisibility toggle double-firing in singleplayer
- Fixed per-player preference desync on servers (now synced on join)
- F3+B now shows hitboxes for force-visible frames when the feature is off
- Corrected supported-version ranges; Java requirement now accurate per version
"@
if (-not $Changelog) { $Changelog = $defaultChangelog }

$fabricTargets = @(
    @{ Mc = "1.16.5";  Games = @("1.16.5", "1.17", "1.17.1", "1.18", "1.18.1", "1.18.2") },
    @{ Mc = "1.19";    Games = @("1.19", "1.19.1", "1.19.2", "1.19.3", "1.19.4", "1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4") },
    @{ Mc = "1.20.5";  Games = @("1.20.5", "1.20.6") },
    @{ Mc = "1.21";    Games = @("1.21", "1.21.1", "1.21.2", "1.21.3") },
    @{ Mc = "1.21.4";  Games = @("1.21.4", "1.21.5", "1.21.6", "1.21.7", "1.21.8") },
    @{ Mc = "1.21.11"; Games = @("1.21.9", "1.21.10", "1.21.11") },
    @{ Mc = "26.1";    Games = @("26.1", "26.1.1", "26.1.2") },
    @{ Mc = "26.2";    Games = @("26.2", "26.3") }
)

$forgeTargets = @(
    @{ Mc = "1.18";    Games = @("1.18", "1.18.1", "1.18.2") },
    @{ Mc = "1.19";    Games = @("1.19", "1.19.1", "1.19.2", "1.19.3", "1.19.4", "1.20", "1.20.1") },
    @{ Mc = "1.20.2";  Games = @("1.20.2", "1.20.3", "1.20.4", "1.20.6") },
    @{ Mc = "1.21";    Games = @("1.21", "1.21.1") },
    @{ Mc = "1.21.3";  Games = @("1.21.3", "1.21.4", "1.21.5") },
    @{ Mc = "1.21.6";  Games = @("1.21.6", "1.21.7", "1.21.8", "1.21.9", "1.21.10") },
    @{ Mc = "1.21.11"; Games = @("1.21.11") },
    @{ Mc = "26.1";    Games = @("26.1", "26.1.1", "26.1.2", "26.2", "26.3") }
)

$neoforgeTargets = @(
    @{ Mc = "1.20.4";  Games = @("1.20.4") },
    @{ Mc = "1.20.6";  Games = @("1.20.6") },
    @{ Mc = "1.21";    Games = @("1.21", "1.21.1") },
    @{ Mc = "1.21.2";  Games = @("1.21.2", "1.21.3", "1.21.4", "1.21.5") },
    @{ Mc = "1.21.6";  Games = @("1.21.6") },
    @{ Mc = "1.21.7";  Games = @("1.21.7", "1.21.8", "1.21.9", "1.21.10") },
    @{ Mc = "1.21.11"; Games = @("1.21.11") },
    @{ Mc = "26.1";    Games = @("26.1", "26.1.1", "26.1.2", "26.2", "26.3") }
)

$targets = if ($Loader -eq "forge") { $forgeTargets } elseif ($Loader -eq "neoforge") { $neoforgeTargets } else { $fabricTargets }

# Fabric versions that also run on Quilt Loader (needs Quilted Fabric API, which
# only exists for these Minecraft versions).
$quiltSupportedMc = @("1.19", "1.21")

$headers = @{ "User-Agent" = "itemframesplus-publish/$Version (Kryp/itemframesplus)" }
if ($Token) { $headers["Authorization"] = $Token }

function Get-Json([string]$path, [bool]$auth = $false) {
    $r = Invoke-WebRequest "$api$path" -Headers $headers -UseBasicParsing
    return $r.Content | ConvertFrom-Json
}

"== Validating project and game version tags =="
try {
    $project = Get-Json "/project/$projectSlug"
    "Project: $($project.title) ($($project.id))"
} catch {
    throw "Project '$projectSlug' not found or not accessible: $_"
}

$tags = @((Get-Json "/tag/game_version") | ForEach-Object { $_.version })
foreach ($t in $targets) {
    foreach ($g in $t.Games) {
        if ($tags -notcontains $g) { Write-Warning "Game version tag '$g' does not exist on Modrinth" }
    }
}

if (-not $Token) {
    Write-Warning "No token provided - running in dry-run mode. Set `$env:MODRINTH_TOKEN or pass -Token."
    $DryRun = $true
}

$allVersions = @(Get-Json "/project/$projectSlug/version")
$existing = $allVersions.version_number

if ($AddQuiltLoaders) {
    "== Adding Quilt loader tag to supported Fabric versions =="
    foreach ($mc in $quiltSupportedMc) {
        $vn = "$Version+$mc"
        $hits = @($allVersions | Where-Object { $_.version_number -eq $vn -and $_.loaders -contains "fabric" })
        if ($hits.Count -eq 0) { Write-Warning "No Fabric version found for $vn"; continue }
        foreach ($v in $hits) {
            if ($v.loaders -contains "quilt") { "SKIP $vn (quilt already present)"; continue }
            $newLoaders = @($v.loaders + "quilt" | Select-Object -Unique)
            if ($DryRun) {
                "[DRY-RUN] would PATCH $vn ($($v.id)) loaders=[$($newLoaders -join ',')]"
                continue
            }
            try {
                # Modrinth moved version edits to the v3 route.
                $body = @{ loaders = $newLoaders } | ConvertTo-Json
                Invoke-WebRequest "https://api.modrinth.com/v3/version/$($v.id)" -Method Patch -Headers $headers -ContentType "application/json" -Body $body -UseBasicParsing | Out-Null
                "PATCHED $vn ($($v.id)) loaders=[$($newLoaders -join ',')]"
            } catch {
                Write-Warning "Failed to patch $vn : $_"
            }
        }
    }
    return
}

if ($ReplaceOldVersions) {
    $old = @($allVersions | Where-Object { $_.version_number -eq $Version })
    if ($old.Count -eq 0) { "No plain '$Version' versions to replace." }
    foreach ($v in $old) {
        $files = $v.files.filename -join ","
        if ($DryRun) {
            "[DRY-RUN] would DELETE old version $($v.version_number) ($($v.id)): $files"
            continue
        }
        try {
            Invoke-WebRequest "$api/version/$($v.id)" -Method Delete -Headers $headers -UseBasicParsing | Out-Null
            "DELETED old version $($v.version_number) ($($v.id)): $files"
        } catch {
            Write-Warning "Failed to delete $($v.id): $_"
        }
    }
}

foreach ($t in $targets) {
    $mc = $t.Mc
    $jarName = "itemframesplus-$Loader-$Version+$mc.jar"
    $jarPath = Join-Path $ArtifactsDir $jarName
    $srcName = "itemframesplus-$Loader-$Version+$mc-sources.jar"
    $srcPath = Join-Path $ArtifactsDir $srcName
    $versionNumber = "$Version+$mc"

    if (-not (Test-Path $jarPath)) { throw "Missing artifact: $jarPath" }
    $jarBytes = [System.IO.File]::ReadAllBytes($jarPath)
    $sha1 = [System.BitConverter]::ToString([System.Security.Cryptography.SHA1]::HashData($jarBytes)).Replace("-", "").ToLower()

    $deps = @()
    if ($Loader -eq "fabric") {
        $fapiVersions = @(Get-Json "/project/$fabricApiProjectId/version?game_versions=%5B%22$mc%22%5D&loaders=%5B%22fabric%22%5D")
        if ($fapiVersions.Count -eq 0) { throw "No Fabric API version on Modrinth for $mc" }
        $deps = @(@{ project_id = $fabricApiProjectId; dependency_type = "required" })
    }

    $name = "ItemFrames+ $Version for $mc ($Loader)"

    if (@($allVersions | Where-Object { $_.version_number -eq $versionNumber -and $_.loaders -contains $Loader }).Count -gt 0) {
        "SKIP $versionNumber ($Loader already exists)"
        continue
    }

    $data = @{
        name             = $name
        version_number   = $versionNumber
        changelog        = $Changelog
        dependencies     = $deps
        game_versions    = $t.Games
        version_type     = "release"
        loaders          = @($Loader)
        featured         = $false
        status           = "listed"
        requested_status = "listed"
        project_id       = $project.id
        file_parts       = @("file1")
        primary_file     = "file1"
        environment      = "server_only_client_optional"
        file_types       = @{}
    }
    if ($IncludeSources -and (Test-Path $srcPath)) {
        $data.file_parts = @("file1", "sources1")
        $data.file_types = @{ sources1 = "sources-jar" }
    }

    $dataJson = $data | ConvertTo-Json -Depth 6

    if ($DryRun) {
        "[DRY-RUN] would publish $versionNumber ($jarName, sha1=$sha1) games=[$($t.Games -join ',')] loaders=[$Loader] deps=$($deps.Count)"
        continue
    }

    try {
        $client = [System.Net.Http.HttpClient]::new()
        $client.DefaultRequestHeaders.TryAddWithoutValidation("User-Agent", "itemframesplus-publish/$Version (Kryp/itemframesplus)") | Out-Null
        $client.DefaultRequestHeaders.TryAddWithoutValidation("Authorization", $Token) | Out-Null

        $form = [System.Net.Http.MultipartFormDataContent]::new()
        $form.Add([System.Net.Http.StringContent]::new($dataJson, [System.Text.Encoding]::UTF8, "application/json"), "data")
        $jarPart = [System.Net.Http.ByteArrayContent]::new($jarBytes)
        $jarPart.Headers.ContentType = [System.Net.Http.Headers.MediaTypeHeaderValue]::Parse("application/java-archive")
        $form.Add($jarPart, "file1", $jarName)
        if ($IncludeSources -and (Test-Path $srcPath)) {
            $srcBytes = [System.IO.File]::ReadAllBytes($srcPath)
            $srcPart = [System.Net.Http.ByteArrayContent]::new($srcBytes)
            $srcPart.Headers.ContentType = [System.Net.Http.Headers.MediaTypeHeaderValue]::Parse("application/java-archive")
            $form.Add($srcPart, "sources1", $srcName)
        }

        $resp = $client.PostAsync("$api/version", $form).Result
        $body = $resp.Content.ReadAsStringAsync().Result
        if ($resp.IsSuccessStatusCode) {
            $created = $body | ConvertFrom-Json
            "PUBLISHED $versionNumber -> https://modrinth.com/mod/$projectSlug/version/$($created.id)"
        } else {
            Write-Warning "FAILED $versionNumber ($($resp.StatusCode)): $body"
        }
        $client.Dispose()
    } catch {
        Write-Warning "FAILED $versionNumber : $_"
    }
}
