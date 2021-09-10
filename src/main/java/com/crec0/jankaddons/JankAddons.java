package com.crec0.jankaddons;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.ServerCommandSource;

public class JankAddons implements CarpetExtension, ModInitializer {

    @Override
    public void onInitialize() {CarpetServer.manageExtension(new JankAddons());}

    @Override
    public void onGameStarted() {CarpetServer.settingsManager.parseSettingsClass(JankAddonsSettings.class);}

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {}
}
