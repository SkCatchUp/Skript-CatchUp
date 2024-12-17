package jp.nlaocs.skriptcatchup.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.entity.Player;

import com.destroystokyo.paper.event.entity.EndermanAttackPlayerEvent;

public class EvtEndermanEnrage {
    static {
        if (Skript.classExists("com.destroystokyo.paper.event.entity.EndermanAttackPlayerEvent")) {
            Skript.registerEvent("Enderman Enrage", SimpleEvent.class, EndermanAttackPlayerEvent.class, "[catch[ ]up] enderman (enrage|anger)")
                    .description(
                            "Called when an enderman gets mad because a player looked at them.",
                            "Note: This does not stop enderman from targeting the player as a result of getting damaged."
                    )
                    .examples(
                            "# Stops endermen from getting angry players with the permission \"safeFrom.enderman\"",
                            "on enderman enrage:",
                            "\tif player has permission \"safeFrom.enderman\":",
                            "\t\tcancel event"
                    )
                    .since("2.9.0")
                    .requiredPlugins("Paper");
            EventValues.registerEventValue(EndermanAttackPlayerEvent.class, Player.class, new Getter<Player, EndermanAttackPlayerEvent>() {
                @Override
                public Player get(EndermanAttackPlayerEvent event) {
                    return event.getPlayer();
                }
            }, EventValues.TIME_NOW);
        }
    }
}
