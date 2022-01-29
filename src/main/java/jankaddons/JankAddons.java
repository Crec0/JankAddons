package jankaddons;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.script.CarpetContext;
import carpet.script.annotation.AnnotationParser;
import com.mojang.brigadier.CommandDispatcher;
import jankaddons.commands.PortalMonitorCommand;
import jankaddons.helpers.PortalMonitorData;
import jankaddons.helpers.PortalMonitorUtil;
import jankaddons.script.Scarpet;
import net.fabricmc.api.ModInitializer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JankAddons implements CarpetExtension, ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("JankAddons");
    public static MinecraftServer MINECRAFT_SERVER;
    public static PortalMonitorData PORTAL_DATA = new PortalMonitorData();

    @Override
    public void onInitialize() {
        CarpetServer.manageExtension(new JankAddons());
    }

    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(JankAddonsSettings.class);
        AnnotationParser.parseFunctionClass(Scarpet.class);
    }

    @Override
    public void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        PortalMonitorCommand.register(dispatcher);
    }

    @Override
    public void onServerLoaded(MinecraftServer server) {
        CarpetExtension.super.onServerLoaded(server);
        MINECRAFT_SERVER = server;
        if (!JankAddonsSettings.commandPortalMonitor.equals("false")) {
            if (PortalMonitorUtil.readSaveFile()) {
                LOGGER.info("Successfully read save file");
            } else {
                LOGGER.error("Failed to read save file");
            }
        }
    }

    @Override
    public void onServerClosed(MinecraftServer server) {
        CarpetExtension.super.onServerClosed(server);
        if (!JankAddonsSettings.commandPortalMonitor.equals("false")) {
            if (PortalMonitorUtil.writeSaveFile()) {
                LOGGER.info("Successfully wrote save file");
            } else {
                LOGGER.error("Failed to write save file");
            }
        }
    }
}
