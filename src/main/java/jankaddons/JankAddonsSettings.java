package jankaddons;

import carpet.settings.Rule;

public class JankAddonsSettings {

    public static final String CREC0 = "crec0";

    @Rule(
            desc = "Makes crimson fungus and nyliem fungus grow based on random ticks",
            category = {"CREC0", "SURVIVAL", "FEATURE"},
            options = {"false", "true"}
    )
    public static boolean fungusRandomTickGrow = false;
}
