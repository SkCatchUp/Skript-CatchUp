package jp.nlaocs.skriptcatchup.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.entity.EntityType;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Experience;
import ch.njol.skript.util.Getter;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.Nullable;

public class EvtTeleport extends SkriptEvent {

    static {
        Skript.registerEvent("Teleport", EvtTeleport.class, CollectionUtils.array(EntityTeleportEvent.class, PlayerTeleportEvent.class), "[catch[ ]up] [%entitytypes%] teleport[ing]")
                .description(
                        "This event can be used to listen to teleports from non-players or player entities respectively.",
                        "When teleporting entities, the event may also be called due to a result of natural causes, such as an enderman or shulker teleporting, or wolves teleporting to players.",
                        "When teleporting players, the event can be called by teleporting through a nether/end portal, or by other means (e.g. plugins).")
                .examples(
                        "on teleport:",
                        "on player teleport:",
                        "on creeper teleport:"
                )
                .since("1.0, 2.9.0 (entity teleport)");
        EventValues.registerEventValue(PlayerTeleportEvent.class, Location.class, new Getter<Location, PlayerTeleportEvent>() {
            @Override
            @Nullable
            public Location get(PlayerTeleportEvent event) {
                return event.getTo();
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(PlayerTeleportEvent.class, Block.class, new Getter<Block, PlayerTeleportEvent>() {
            @Override
            @Nullable
            public Block get(PlayerTeleportEvent event) {
                return event.getTo().clone().subtract(0, 0.5, 0).getBlock();
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(EntityTeleportEvent.class, Location.class, new Getter<Location, EntityTeleportEvent>() {
            @Override
            public @Nullable Location get(EntityTeleportEvent event) {
                return event.getFrom();
            }
        }, EventValues.TIME_PAST);
        EventValues.registerEventValue(EntityTeleportEvent.class, Location.class, new Getter<Location, EntityTeleportEvent>() {
            @Override
            public @Nullable Location get(EntityTeleportEvent event) {
                return event.getTo();
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(EntityTeleportEvent.class, Block.class, new Getter<Block, EntityTeleportEvent>() {
            @Override
            public @Nullable Block get(EntityTeleportEvent event) {
                return event.getTo().clone().subtract(0, 0.5, 0).getBlock();
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(EntityTeleportEvent.class, Chunk.class, new Getter<Chunk, EntityTeleportEvent>() {
            @Override
            public @Nullable Chunk get(EntityTeleportEvent event) {
                return event.getTo().getChunk();
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(EntityTeleportEvent.class, Chunk.class, new Getter<Chunk, EntityTeleportEvent>() {
            @Override
            public @Nullable Chunk get(EntityTeleportEvent event) {
                return event.getFrom().getChunk();
            }
        }, EventValues.TIME_PAST);
    }

    @Nullable
    private Literal<EntityType> entitiesLiteral;
    private EntityType @Nullable [] entities;

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        if (args[0] != null) {
            entitiesLiteral = ((Literal<EntityType>) args[0]); // evaluate only once
            entities = entitiesLiteral.getAll();
        }
        return true;
    }


    @Override
    public boolean check(Event event) {
        if (event instanceof EntityTeleportEvent) {
            Entity entity = ((EntityTeleportEvent) event).getEntity();
            return checkEntity(entity);
        } else if (event instanceof PlayerTeleportEvent) {
            Entity entity = ((PlayerTeleportEvent) event).getPlayer();
            return checkEntity(entity);
        } else {
            return false;
        }
    }

    private boolean checkEntity(Entity entity) {
        if (entities != null) {
            for (EntityType entType : entities) {
                if (entType.isInstance(entity))
                    return true;
            }
            return false;
        }
        return true;
    }

    public String toString(@Nullable Event event, boolean debug) {
        if (entitiesLiteral != null)
            return "on " + entitiesLiteral.toString(event, debug) + " teleport";
        return "on teleport";
    }

}
