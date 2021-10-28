package jankaddons;

import carpet.settings.Rule;

import static carpet.settings.RuleCategory.*;
import static jankaddons.constants.StringConstants.JANK;

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
            desc = "Enables /portalactivity command to monitor portal usages within last x ticks",
            extra = "Time it monitors is set by /carpet portalActivityMoniterTime <duration>",
            category = {JANK, SURVIVAL, COMMAND},
            options = {"ops", "true", "false"}
    )
    public static String commandPortalMonitor = "false";

    @Rule(
            desc = "Makes tall grass and large fern harvestable with shears",
            options = {"true", "false"},
            category = {JANK, SURVIVAL, FEATURE}
    )
    public static boolean harvestableTallGrassFern = false;

    @Rule(
            desc = "Make deepslate instaminable",
            category = {JANK, SURVIVAL, FEATURE},
            options = {"true", "false"}
    )
    public static boolean instamineDeepslate = false;

    @Rule(
            desc = "Make enderchests movable",
            category = {JANK, SURVIVAL, EXPERIMENTAL},
            options = {"true", "false"}
    )
    public static boolean movableEnderChest = false;
}
