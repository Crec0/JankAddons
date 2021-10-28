package jankaddons.commands;

import carpet.settings.SettingsManager;
import carpet.utils.Messenger;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import jankaddons.JankAddons;
import jankaddons.JankAddonsSettings;
import jankaddons.helpers.PortalMonitorUtil;
import net.minecraft.command.argument.EntitySummonArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import static jankaddons.constants.StringConstants.GRAY_DASHED_LINE;
import static net.minecraft.command.CommandSource.suggestMatching;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class PortalMonitorCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("portalmonitor")
                        .requires((player) -> SettingsManager.canUseCommand(player, JankAddonsSettings.commandPortalMonitor))
                        .then(literal("addLoader")
                                .then(argument("name",StringArgumentType.word())
                                        .executes(PortalMonitorCommand::addChunk)))
                        .then(literal("removeLoader")
                                .then(argument("name", StringArgumentType.word())
                                        .suggests((c, b) -> suggestMatching(PortalMonitorUtil.getCustomNamedPosStream(),b))
                                        .executes(PortalMonitorCommand::removeChunk)))
                        .then(literal("listLoaders")
                                .executes(PortalMonitorCommand::listChunks))
                        .then(literal("addEntity")
                                .then(argument("entity", EntitySummonArgumentType.entitySummon())
                                        .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                        .executes(PortalMonitorCommand::addEntity)))
                        .then(literal("removeEntity")
                                .then(argument("entityName", StringArgumentType.string())
                                .suggests((c, b) -> suggestMatching(PortalMonitorUtil.getTrackedEntities(),b))
                                .executes(PortalMonitorCommand::removeEntity)))
                        .then(literal("listEntities")
                                .executes(PortalMonitorCommand::listEntities)));
    }

    private static int addChunk(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        PlayerEntity player = source.getPlayer();
        if (player.world.getRegistryKey() != World.OVERWORLD) {
            Messenger.m(source, "r You must be in overworld to add chunk.");
            return 0;
        }
        String name = context.getArgument("name", String.class);
        if (PortalMonitorUtil.add(name, player.chunkX, player.chunkZ)) {
            Messenger.m(source, "d Added " + name + "@[x: " + player.chunkX + ", z: " + player.chunkZ + "] to monitor list");
        } else {
            Messenger.m(source, "r Couldn't add " + name + ". Name Already exists.");
        }
        return 1;
    }

    private static int removeChunk(CommandContext<ServerCommandSource> context) {
        String name = context.getArgument("name", String.class);
        JankAddons.PORTAL_DATA.removePortal(name);
        Messenger.m(context.getSource(), "d Removed chunk " + name + " from the monitor list");
        return 1;
    }

    private static int listChunks(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        Messenger.m(source, GRAY_DASHED_LINE, " \nLoaders\n  ", "c name ", "y [x:cx,z:cz] ", "l (triggers per minute) ", "m (last trigger)\n", GRAY_DASHED_LINE);
        PortalMonitorUtil.getSortedPortalsAsString().forEach(s -> Messenger.m(source, s));
        Messenger.m(source, GRAY_DASHED_LINE);
        return 1;
    }

    private static int addEntity(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Identifier entity = EntitySummonArgumentType.getEntitySummon(context, "entity");
        EntityType<? extends Entity> entityType = EntityType.get(entity.toString())
                                                            .orElseThrow(PortalMonitorCommand::invalidEntityException);
        PortalMonitorUtil.addToTracked(entityType);
        Messenger.m(context.getSource(), "d Added entity " + entity + " from the tracked entities");
        return 1;
    }

    private static int removeEntity(CommandContext<ServerCommandSource> context) {
        String entity = context.getArgument("entityName", String.class);
        PortalMonitorUtil.removeTracked(entity);
        Messenger.m(context.getSource(), "d Removed entity " + entity + " from the tracked entities");
        return 1;
    }

    private static int listEntities(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        Messenger.m(source, GRAY_DASHED_LINE, " \nCurrently tracked entities");
        PortalMonitorUtil.getTrackedEntities().forEach(s -> Messenger.m(source, "c  " + s));
        Messenger.m(source, GRAY_DASHED_LINE);
        return 1;
    }

    private static CommandSyntaxException invalidEntityException(){
        return new SimpleCommandExceptionType(new LiteralText("Invalid entity. Entity not added.")).create();
    }
}