package jankaddons.helpers;

import com.google.gson.*;
import jankaddons.JankAddons;
import jankaddons.JankAddonsSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static jankaddons.JankAddons.PORTAL_DATA;

public class PortalMonitorUtil {
    private static final AtomicInteger ENTITY_COUNTER = new AtomicInteger();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(PortalMonitorData.class, new PortalDataSerializer())
                                                      .registerTypeAdapter(PortalMonitorData.class, new PortalDataDeserializer())
                                                      .disableHtmlEscaping()
                                                      .setPrettyPrinting()
                                                      .create();

    public static boolean isTrackedEntity(EntityType<? extends Entity> entityType) {
        return PORTAL_DATA.isTrackedEntity(entityType);
    }

    public static void addToTracked(EntityType<? extends Entity> entityType) {
        PORTAL_DATA.addEntity(entityType);
    }

    public static void removeTracked(String entityType){
        PORTAL_DATA.removeEntity(entityType);
    }

    public static void resetOrInitialize(EntityType<? extends Entity> type, int x, int z) {
        String niceNameEntity = Utils.getNiceName(type) + ENTITY_COUNTER.incrementAndGet();
        for (ExpirableNamedPosition position : PORTAL_DATA.getNamedPositions().values()) {
            if (position.withinEntityTickingRange(x, z)) {
                position.resetExpiry();
                return;
            }
        }
        PORTAL_DATA.addPortal(niceNameEntity, new ExpirableNamedPosition(niceNameEntity, x, z, false));
    }

    public static boolean add(String name, int x, int z) {
        if (PORTAL_DATA.isTrackedPortal(name)) {
            return false;
        }
        List<String> keysToRemove = new ArrayList<>();
        for (Map.Entry<String, ExpirableNamedPosition> entry : PORTAL_DATA.getNamedPositions().entrySet()) {
            if (entry.getValue().withinEntityTickingRange(x, z)) {
                keysToRemove.add(entry.getKey());
            }
        }
        keysToRemove.forEach(p -> PORTAL_DATA.removePortal(p));
        PORTAL_DATA.addPortal(name, new ExpirableNamedPosition(name, x, z));
        return true;
    }

    public static void tick() {
        if (JankAddonsSettings.commandPortalMonitor.equals("false")) return;
        List<String> keysToRemove = new ArrayList<>();
        for (Map.Entry<String, ExpirableNamedPosition> entry : PORTAL_DATA.getNamedPositions().entrySet()) {
            entry.getValue().tick();
            if (entry.getValue().hasExpiredAndNotPersistent()) {
                keysToRemove.add(entry.getKey());
            }
        }
        keysToRemove.forEach(p -> PORTAL_DATA.removePortal(p));
    }

    public static Stream<String> getCustomNamedPosStream() {
        return PORTAL_DATA.getNamedPositions()
                          .values()
                          .stream()
                          .filter(ExpirableNamedPosition::isCustomNamed)
                          .map(ExpirableNamedPosition::getName);
    }

    public static Stream<Object[]> getSortedPortalsAsString() {
        return PORTAL_DATA.getNamedPositions()
                          .values()
                          .stream()
                          .sorted(Comparator.comparingDouble(ExpirableNamedPosition::lastTriggered))
                          .map(ExpirableNamedPosition::formatedOutputString);
    }

    public static Stream<String> getTrackedEntities() {
        return PORTAL_DATA.getEntityTypes().keySet().stream();
    }

    public static boolean readSaveFile() {
        Path saveFile = Utils.getSaveFile();
        if (saveFile == null) return false;
        try (BufferedReader reader = Files.newBufferedReader(saveFile)) {
            PORTAL_DATA = GSON.fromJson(reader, PortalMonitorData.class);
        } catch (IOException | JsonParseException e) {
            JankAddons.LOGGER.error("Failed to read save file");
            return false;
        }
        return true;
    }

    public static boolean writeSaveFile() {
        Path saveFile = Utils.getSaveFile();
        if (saveFile == null) return false;
        try (BufferedWriter writer = Files.newBufferedWriter(saveFile)) {
            GSON.toJson(PORTAL_DATA, writer);
        } catch (IOException | JsonIOException e) {
            JankAddons.LOGGER.error("Failed to write file");
            return false;
        }
        return true;
    }


    private static class PortalDataSerializer implements JsonSerializer<PortalMonitorData> {
        @Override
        public JsonElement serialize(PortalMonitorData src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject root = new JsonObject();
            JsonArray portalArray = new JsonArray();
            for (ExpirableNamedPosition position : src.getNamedPositions().values()) {
                if (!position.isCustomNamed()) continue;
                JsonObject portal = new JsonObject();
                portal.addProperty("name", position.getName());
                portal.addProperty("x", position.getX());
                portal.addProperty("z", position.getZ());
                portalArray.add(portal);
            }
            root.add("portals", portalArray);
            JsonArray entities = new JsonArray();
            src.getEntityTypes().values().forEach(e -> entities.add(Registry.ENTITY_TYPE.getId(e).toString()));
            root.add("tracked_entities", entities);
            return root;
        }
    }

    private static class PortalDataDeserializer implements JsonDeserializer<PortalMonitorData> {
        @Override
        public PortalMonitorData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            PortalMonitorData data = new PortalMonitorData();
            data.clear();
            JsonObject root = json.getAsJsonObject();
            JsonArray portals = root.getAsJsonArray("portals");
            portals.forEach(portalObject -> {
                JsonObject portal = portalObject.getAsJsonObject();
                String name = portal.get("name").getAsString();
                int x = portal.get("x").getAsInt();
                int z = portal.get("z").getAsInt();
                data.addPortal(name, new ExpirableNamedPosition(name, x, z));
            });
            JsonArray entities = root.getAsJsonArray("tracked_entities");
            entities.forEach(entityObj -> EntityType.get(entityObj.getAsString()).ifPresent(data::addEntity));
            return data;
        }
    }
}
