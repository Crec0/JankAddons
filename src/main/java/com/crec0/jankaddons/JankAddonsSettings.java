package com.crec0.jankaddons;

import carpet.settings.Rule;

import static carpet.settings.RuleCategory.FEATURE;
import static carpet.settings.RuleCategory.SURVIVAL;
import static carpet.settings.RuleCategory.BUGFIX;
import static com.crec0.jankaddons.StringConstants.JANK;

public class JankAddonsSettings {
    @Rule(
            desc = "Makes crimson fungus and nyliem fungus grow based on random ticks",
            category = {JANK, SURVIVAL, FEATURE},
            options = {"false", "true"}
    )
    public static boolean fungusRandomTickGrow = false;

    @Rule(
            desc = "Make fresh bows stack to 64",
            category = {JANK, SURVIVAL, FEATURE},
            options = {"true", "false"}
    )
    public static boolean stackableFreshBows = false;

    @Rule(
            desc = "Fix polished blackstone breaking speed. Fixes MC-199752",
            category = {JANK, SURVIVAL, BUGFIX},
            options = {"true", "false"}
    )
    public static boolean polishedBlackstoneButtonBreakingSpeedFix = false;
}
