package jp.nlaocs.skriptcatchup.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.jetbrains.annotations.Nullable;

public class EvtAnvilPrepare {
    static {
        Skript.registerEvent("Anvil Prepare", SimpleEvent.class, PrepareAnvilEvent.class, "[catch[ ]up] anvil prepar(e|ing)")
                .description("Called when an item is put in a slot for repair by an anvil. Please note that this event is called multiple times in a single item slot move.")
                .examples("on anvil prepare:",
                        "\tevent-item is set # result item",
                        "\tchance of 5%:",
                        "\t\tset repair cost to repair cost * 50%",
                        "\t\tsend \"You're LUCKY! You got 50% discount.\" to player")
                .since("2.7");
        EventValues.registerEventValue(PrepareAnvilEvent.class, ItemStack.class, new Getter<ItemStack, PrepareAnvilEvent>() {
            @Override
            @Nullable
            public ItemStack get(PrepareAnvilEvent e) {
                AnvilInventory i = e.getInventory();
                return i.getItem(2);
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(PrepareAnvilEvent.class, Inventory.class, new Getter<Inventory, PrepareAnvilEvent>() {
            @Override
            @Nullable
            public Inventory get(PrepareAnvilEvent e) {
                return e.getInventory();
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(PrepareAnvilEvent.class, Player.class, new Getter<Player, PrepareAnvilEvent>() {
            @Override
            @Nullable
            public Player get(PrepareAnvilEvent e) {
                return (Player) e.getView().getPlayer();
            }
        }, EventValues.TIME_NOW);

    }
}
