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
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntitySummonArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import static jankaddons.constants.StringConstants.GRAY_DASHED_LINE;
import static net.minecraft.commands.SharedSuggestionProvider.suggest;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class PortalMonitorCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                literal("portalmonitor")
                        .requires((player) -> SettingsManager.canUseCommand(player, JankAddonsSettings.commandPortalMonitor))
                        .then(literal("addLoader")
                                .then(argument("name",StringArgumentType.word())
                                        .executes(PortalMonitorCommand::addChunk)))
                        .then(literal("removeLoader")
                                .then(argument("name", StringArgumentType.word())
                                        .suggests((c, b) -> suggest(PortalMonitorUtil.getCustomNamedPosStream(),b))
                                        .executes(PortalMonitorCommand::removeChunk)))
                        .then(literal("listLoaders")
                                .executes(PortalMonitorCommand::listChunks))
                        .then(literal("addEntity")
                                .then(argument("entity", EntitySummonArgument.id())
                                        .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                        .executes(PortalMonitorCommand::addEntity)))
                        .then(literal("removeEntity")
                                .then(argument("entityName", StringArgumentType.string())
                                .suggests((c, b) -> suggest(PortalMonitorUtil.getTrackedEntities(),b))
                                .executes(PortalMonitorCommand::removeEntity)))
                        .then(literal("listEntities")
                                .executes(PortalMonitorCommand::listEntities)));
    }

    private static int addChunk(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        Player player = source.getPlayerOrException();
        if (player.level.dimension() != Level.OVERWORLD) {
            Messenger.m(source, "r You must be in overworld to add chunk.");
            return 0;
        }
        String name = context.getArgument("name", String.class);
        ChunkPos playerChunkPos = player.chunkPosition();
        if (PortalMonitorUtil.add(name, playerChunkPos.x, playerChunkPos.z)) {
            Messenger.m(source, "d Added " + name + "@[x: " + playerChunkPos.x + ", z: " + playerChunkPos.z + "] to monitor list");
        } else {
            Messenger.m(source, "r Couldn't add " + name + ". Name Already exists.");
        }
        return 1;
    }

    private static int removeChunk(CommandContext<CommandSourceStack> context) {
        String name = context.getArgument("name", String.class);
        JankAddons.PORTAL_DATA.removePortal(name);
        Messenger.m(context.getSource(), "d Removed chunk " + name + " from the monitor list");
        return 1;
    }

    private static int listChunks(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        Messenger.m(source, GRAY_DASHED_LINE, " \nLoaders\n  ", "c name ", "y [x:cx,z:cz] ", "l (triggers per minute) ", "m (last trigger)\n", GRAY_DASHED_LINE);
        PortalMonitorUtil.getSortedPortalsAsString().forEach(s -> Messenger.m(source, s));
        Messenger.m(source, GRAY_DASHED_LINE);
        return 1;
    }

    private static int addEntity(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ResourceLocation entity = EntitySummonArgument.getSummonableEntity(context, "entity");
        EntityType<? extends Entity> entityType = EntityType.byString(entity.toString())
                                                            .orElseThrow(PortalMonitorCommand::invalidEntityException);
        PortalMonitorUtil.addToTracked(entityType);
        Messenger.m(context.getSource(), "d Added entity " + entity + " from the tracked entities");
        return 1;
    }

    private static int removeEntity(CommandContext<CommandSourceStack> context) {
        String entity = context.getArgument("entityName", String.class);
        PortalMonitorUtil.removeTracked(entity);
        Messenger.m(context.getSource(), "d Removed entity " + entity + " from the tracked entities");
        return 1;
    }

    private static int listEntities(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        Messenger.m(source, GRAY_DASHED_LINE, " \nCurrently tracked entities");
        PortalMonitorUtil.getTrackedEntities().forEach(s -> Messenger.m(source, "c  " + s));
        Messenger.m(source, GRAY_DASHED_LINE);
        return 1;
    }

    private static CommandSyntaxException invalidEntityException(){
        return new SimpleCommandExceptionType(new TextComponent("Invalid entity. Entity not added.")).create();
    }
}
