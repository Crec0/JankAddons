package jankaddons.helpers;

import java.util.ArrayList;
import java.util.List;

import static jankaddons.constants.SharedConstants.PORTAL_ACTIVITY_EXPIRY;

public class ExpirableNamedPosition {

    private final String name;
    private final int x;
    private final int z;
    private final boolean isCustomNamed;
    private int expiry;
    private int timesTriggered;
    private long age;

    public ExpirableNamedPosition(String name, int x, int z, boolean isCustomNamed) {
        this.name = name;
        this.x = x;
        this.z = z;
        this.isCustomNamed = isCustomNamed;
        this.expiry = PORTAL_ACTIVITY_EXPIRY;
        this.timesTriggered = 1;
        this.age = 0;
    }

    public ExpirableNamedPosition(String name, int x, int z) {
        this(name, x, z, true);
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public boolean isCustomNamed() {
        return isCustomNamed;
    }

    public float getAverageTriggersPerMinute() {
        return ((float) (this.timesTriggered * 20 * 60) / this.age);
    }

    public boolean withinEntityTickingRange(int x, int z) {
        return this.getChebyshevDistance(x, z) <= 1;
    }

    private int getChebyshevDistance(int x, int z) {
        return Math.max(Math.abs(this.x - x), Math.abs(this.z - z));
    }

    public boolean hasExpired() {
        return this.expiry <= 0;
    }

    public boolean hasExpiredAndNotPersistent() {
        return this.hasExpired() && !this.isCustomNamed();
    }

    public void resetExpiry() {
        this.expiry = PORTAL_ACTIVITY_EXPIRY;
        this.timesTriggered++;
    }

    public void tick() {
        this.age++;
        this.expiry--;
    }

    public int lastTriggered() {
        return PORTAL_ACTIVITY_EXPIRY - this.expiry;
    }

    @Override
    public String toString() {
        return this.name + " [x: " + this.x + ", z: " + this.z + "] (" + (int) getAverageTriggersPerMinute() + " triggers/min) (-" + lastTriggered() + " ticks)";
    }

    public Object[] formatedOutputString() {
        List<String> stringBuilder = new ArrayList<>();
        stringBuilder.add("   ");
        stringBuilder.add("c " + this.name.trim() + " ");
        stringBuilder.add("y [x: " + this.x + ", z: " + this.z + "] ");
        stringBuilder.add("l (" + (int) getAverageTriggersPerMinute() + " triggers/min) ");
        stringBuilder.add("m (-" + lastTriggered() + " ticks)");
        return stringBuilder.toArray(new Object[0]);
    }
}
