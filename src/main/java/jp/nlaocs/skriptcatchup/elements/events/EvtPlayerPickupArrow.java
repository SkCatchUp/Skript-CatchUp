package jp.nlaocs.skriptcatchup.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.entity.Projectile;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EvtPlayerPickupArrow {
    static {
        Skript.registerEvent("Player Pickup Arrow", SimpleEvent.class, PlayerPickupArrowEvent.class, "[catch[ ]up] [player] (pick[ing| ]up [an] arrow|arrow pick[ing| ]up)")
                .description("Called when a player picks up an arrow from the ground.")
                .examples(
                        "on arrow pickup:",
                        "\tcancel the event",
                        "\tteleport event-projectile to block 5 above event-projectile"
                )
                .since("2.8.0")
                .requiredPlugins("Minecraft 1.14+ (event-projectile)");
        EventValues.registerEventValue(PlayerPickupArrowEvent.class, Projectile.class, new Getter<Projectile, PlayerPickupArrowEvent>() {
            @Override
            public Projectile get(PlayerPickupArrowEvent event) {
                return event.getArrow();
            }
        }, EventValues.TIME_NOW);

        EventValues.registerEventValue(PlayerPickupArrowEvent.class, ItemStack.class, new Getter<ItemStack, PlayerPickupArrowEvent>() {
            @Override
            @Nullable
            public ItemStack get(PlayerPickupArrowEvent event) {
                return event.getItem().getItemStack();
            }
        }, EventValues.TIME_NOW);
    }
}
