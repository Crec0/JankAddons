package jankaddons.Commands;

import carpet.settings.SettingsManager;
import com.mojang.brigadier.CommandDispatcher;
import jankaddons.JankAddonsSettings;
import net.minecraft.server.command.ServerCommandSource;


import static net.minecraft.server.command.CommandManager.literal;

public class Command_FungusRandomTickGrow{

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("fungusRandomTickGrow")
                        .requires((player) -> SettingsManager.canUseCommand(player, JankAddonsSettings.fungusRandomTickGrow))
        );
    }
}
