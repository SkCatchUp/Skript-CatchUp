package jp.nlaocs.skriptcatchup.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.skript.util.slot.InventorySlot;
import ch.njol.skript.util.slot.Slot;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.DragType;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.Nullable;

import jp.nlaocs.skriptcatchup.utils.InventoryUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EvtInventoryDrag {
    static {
        Skript.registerEvent("Inventory Drag", SimpleEvent.class, InventoryDragEvent.class, "[catch[ ]up] inventory drag[ging]")
                .description("Called when a player drags an item in their cursor across the inventory.")
                .examples(
                        "on inventory drag:",
                        "\tif player's current inventory is {_gui}:",
                        "\t\tsend \"You can't drag your items here!\" to player",
                        "\t\tcancel event"
                )
                .since("2.7");
        EventValues.registerEventValue(InventoryDragEvent.class, Player.class, new Getter<Player, InventoryDragEvent>() {
            @Override
            @Nullable
            public Player get(InventoryDragEvent event) {
                return event.getWhoClicked() instanceof Player ? (Player) event.getWhoClicked() : null;
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(InventoryDragEvent.class, World.class, new Getter<World, InventoryDragEvent>() {
            @Override
            @Nullable
            public World get(InventoryDragEvent event) {
                return event.getWhoClicked().getWorld();
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(InventoryDragEvent.class, ItemStack.class, new Getter<ItemStack, InventoryDragEvent>() {
            @Override
            @Nullable
            public ItemStack get(InventoryDragEvent event) {
                return event.getOldCursor();
            }
        }, EventValues.TIME_PAST);
        EventValues.registerEventValue(InventoryDragEvent.class, ItemStack.class, new Getter<ItemStack, InventoryDragEvent>() {
            @Override
            @Nullable
            public ItemStack get(InventoryDragEvent event) {
                return event.getCursor();
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(InventoryDragEvent.class, ItemStack[].class, new Getter<ItemStack[], InventoryDragEvent>() {
            @Override
            @Nullable
            public ItemStack[] get(InventoryDragEvent event) {
                return event.getNewItems().values().toArray(new ItemStack[0]);
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(InventoryDragEvent.class, Slot[].class, new Getter<Slot[], InventoryDragEvent>() {
            @Override
            @Nullable
            public Slot[] get(InventoryDragEvent event) {
                List<Slot> slots = new ArrayList<>(event.getRawSlots().size());
                InventoryView view = event.getView();
                for (Integer rawSlot : event.getRawSlots()) {
                    Inventory inventory = InventoryUtils.getInventory(view, rawSlot);
                    Integer slot = InventoryUtils.convertSlot(view, rawSlot);
                    if (inventory == null || slot == null)
                        continue;
                    // Not all indices point to inventory slots. Equipment, for example
                    if (inventory instanceof PlayerInventory && slot >= 36) {
                        slots.add(new ch.njol.skript.util.slot.EquipmentSlot(((PlayerInventory) view.getBottomInventory()).getHolder(), slot));
                    } else {
                        slots.add(new InventorySlot(inventory, slot));
                    }
                }
                return slots.toArray(new Slot[0]);
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(InventoryDragEvent.class, ClickType.class, new Getter<ClickType, InventoryDragEvent>() {
            @Override
            @Nullable
            public ClickType get(InventoryDragEvent event) {
                return event.getType() == DragType.EVEN ? ClickType.LEFT : ClickType.RIGHT;
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(InventoryDragEvent.class, Inventory[].class, new Getter<Inventory[], InventoryDragEvent>() {
            @Override
            @Nullable
            public Inventory[] get(InventoryDragEvent event) {
                Set<Inventory> inventories = new HashSet<>();
                InventoryView view = event.getView();
                for (Integer rawSlot : event.getRawSlots()) {
                    Inventory inventory = InventoryUtils.getInventory(view, rawSlot);
                    if (inventory != null)
                        inventories.add(inventory);
                }
                return inventories.toArray(new Inventory[0]);
            }
        }, EventValues.TIME_NOW);
    }
}
