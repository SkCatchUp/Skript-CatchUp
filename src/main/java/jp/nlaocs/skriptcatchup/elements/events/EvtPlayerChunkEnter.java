package jp.nlaocs.skriptcatchup.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.Nullable;

public class EvtPlayerChunkEnter extends SkriptEvent {

    static {
        Skript.registerEvent("Player Chunk Enter", EvtPlayerChunkEnter.class, PlayerMoveEvent.class, "[catch[ ]up] [player] (enter[s] [a] chunk|chunk enter[ing])")
                .description("Called when a player enters a chunk. Note that this event is based on 'player move' event, and may be called frequent internally.")
                .examples(
                        "on player enters a chunk:",
                        "\tsend \"You entered a chunk: %past event-chunk% -> %event-chunk%!\" to player"
                ).since("2.7");
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
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        PlayerMoveEvent moveEvent = ((PlayerMoveEvent) event);
        return !moveEvent.getFrom().getChunk().equals(moveEvent.getTo().getChunk());
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "player enter chunk";
    }

}
