package jankaddons.helpers;

import jankaddons.helpers.ExpirableNamedPosition;
import jankaddons.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class PortalMonitorData {
    private final Map<String, ExpirableNamedPosition> namedPositions;
    private final Map<String, EntityType<? extends Entity>> entityTypes;

    public PortalMonitorData() {
        namedPositions = new HashMap<>();
        entityTypes = new HashMap<>();

        addEntity(EntityType.ITEM);
        addEntity(EntityType.MINECART);
    }

    public Map<String, ExpirableNamedPosition> getNamedPositions() {
        return namedPositions;
    }

    public Map<String, EntityType<? extends Entity>> getEntityTypes() {
        return entityTypes;
    }

    public void addPortal(String name, ExpirableNamedPosition position) {
        namedPositions.put(name, position);
    }

    public void removePortal(String name) {
        namedPositions.remove(name);
    }

    public void addEntity(EntityType<? extends Entity> type) {
        entityTypes.put(Utils.getNiceName(type), type);
    }

    public void removeEntity(String entity) {
        entityTypes.remove(entity);
    }

    public boolean isTrackedPortal(String name){
        return namedPositions.containsKey(name);
    }

    public boolean isTrackedEntity(EntityType<? extends Entity> type) {
        return entityTypes.containsValue(type);
    }

    public void clear(){
        namedPositions.clear();
        entityTypes.clear();
    }
}
