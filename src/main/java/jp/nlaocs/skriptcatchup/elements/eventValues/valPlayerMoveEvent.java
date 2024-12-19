package jp.nlaocs.skriptcatchup.elements.eventValues;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerMoveEvent;

public class valPlayerMoveEvent {
    static {
        EventValues.registerEventValue(PlayerMoveEvent.class, Chunk.class, new Getter<Chunk, PlayerMoveEvent>() {
            @Override
            public Chunk get(PlayerMoveEvent event) {
                return event.getTo().getChunk();
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(PlayerMoveEvent.class, Chunk.class, new Getter<Chunk, PlayerMoveEvent>() {
            @Override
            public Chunk get(PlayerMoveEvent event) {
                return event.getFrom().getChunk();
            }
        }, EventValues.TIME_PAST);
        EventValues.registerEventValue(PlayerMoveEvent.class, Location.class, new Getter<Location, PlayerMoveEvent>() {
            @Override
            public Location get(PlayerMoveEvent event) {
                return event.getFrom();
            }
        }, EventValues.TIME_PAST);
        EventValues.registerEventValue(PlayerMoveEvent.class, Block.class, new Getter<Block, PlayerMoveEvent>() {
            @Override
            public Block get(PlayerMoveEvent event) {
                return event.getTo().clone().subtract(0, 0.5, 0).getBlock();
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(PlayerMoveEvent.class, Location.class, new Getter<Location, PlayerMoveEvent>() {
            @Override
            public Location get(PlayerMoveEvent event) {
                return event.getTo();
            }
        }, EventValues.TIME_NOW);
    }
}
