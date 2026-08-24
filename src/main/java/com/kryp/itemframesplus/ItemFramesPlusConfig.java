package com.kryp.itemframesplus;

import com.kryp.itemframesplus.platform.Platform;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class ItemFramesPlusConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Options options;
    private static File configFile;

    public static class Options {
        private Boolean invisibleItemFrames = true;

        public Boolean getInvisibleItemFrames() {
            return invisibleItemFrames;
        }

        public void setInvisibleItemFrames(Boolean invisibleItemFrames) {
            this.invisibleItemFrames = invisibleItemFrames;
            Platform.INSTANCE.sendPreferenceToServer(invisibleItemFrames);
            saveConfig();
        }
    }

    public static void registerConfig() {
        Path path = Platform.INSTANCE.getConfigDir().resolve(ItemFramesPlus.MOD_ID + ".json");
        configFile = path.toFile();

        if (!configFile.exists()) {
            createConfig();
        }
        loadConfig(path);
    }

    private static void createConfig() {
        options = new Options();
        saveConfig();
    }

    private static void loadConfig(Path configFilePath) {
        try {
            byte[] bytes = Files.readAllBytes(configFilePath);
            String jsonString = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
            if (jsonString.isEmpty()) {
                options = new Options();
                return;
            }
            Options loaded = GSON.fromJson(jsonString, Options.class);
            if (loaded == null) {
                options = new Options();
                return;
            }
            options = loaded;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Options getOptions() {
        return options;
    }

    private static void saveConfig() {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(configFile), java.nio.charset.StandardCharsets.UTF_8))) {
            writer.write(GSON.toJson(options));
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
