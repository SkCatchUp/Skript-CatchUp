package jp.nlaocs.skriptcatchup.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EvtInventoryMoveItem extends SkriptEvent {
    static {
        Skript.registerEvent("Inventory Item Move", SimpleEvent.class, InventoryMoveItemEvent.class,
                        "[catch[ ]up] inventory item (move|transport)",
                        "[catch[ ]up] inventory (mov(e|ing)|transport[ing]) [an] item")
                .description(
                        "Called when an entity or block (e.g. hopper) tries to move items directly from one inventory to another.",
                        "When this event is called, the initiator may have already removed the item from the source inventory and is ready to move it into the destination inventory.",
                        "If this event is cancelled, the items will be returned to the source inventory."
                )
                .examples(
                        "on inventory item move:",
                        "\tbroadcast \"%holder of past event-inventory% is transporting %event-item% to %holder of event-inventory%!\""
                )
                .since("2.8.0");
        EventValues.registerEventValue(InventoryMoveItemEvent.class, Inventory.class, new Getter<Inventory, InventoryMoveItemEvent>() {
            @Override
            public Inventory get(InventoryMoveItemEvent event) {
                return event.getSource();
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(InventoryMoveItemEvent.class, Inventory.class, new Getter<Inventory, InventoryMoveItemEvent>() {
            @Override
            public Inventory get(InventoryMoveItemEvent event) {
                return event.getDestination();
            }
        }, EventValues.TIME_FUTURE);
        EventValues.registerEventValue(InventoryMoveItemEvent.class, Block.class, new Getter<Block, InventoryMoveItemEvent>() {
            @Override
            public Block get(InventoryMoveItemEvent event) {
                return event.getSource().getLocation().getBlock();
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(InventoryMoveItemEvent.class, Block.class, new Getter<Block, InventoryMoveItemEvent>() {
            @Override
            public Block get(InventoryMoveItemEvent event) {
                return event.getDestination().getLocation().getBlock();
            }
        }, EventValues.TIME_FUTURE);
        EventValues.registerEventValue(InventoryMoveItemEvent.class, ItemStack.class, new Getter<ItemStack, InventoryMoveItemEvent>() {
            @Override
            public ItemStack get(InventoryMoveItemEvent event) {
                return event.getItem();
            }
        }, EventValues.TIME_NOW);
    }

    @Override
    public boolean init(final Literal<?>[] args, final int matchedPattern, final SkriptParser.ParseResult parser) {
        return true;
    }

    @Override
    @SuppressWarnings("null")
    public boolean check(Event event) {
        final ItemStack itemStack;
        itemStack = ((InventoryMoveItemEvent) event).getItem();
        return itemStack != null;

    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "inventory item move";
    }
}
