package com.kryp.itemframesplus.util;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ItemFramesPlusPlayerPreferences {
    private static final Map<UUID, Boolean> playerPreferences = new ConcurrentHashMap<>();

    public static void addPlayer(UUID uuid, Boolean preference) {
        playerPreferences.put(uuid, preference);
    }

    public static Boolean getPreference(UUID uuid) {
        return playerPreferences.getOrDefault(uuid, true);
    }

    public static void removePlayer(UUID uuid) {
        playerPreferences.remove(uuid);
    }
}
