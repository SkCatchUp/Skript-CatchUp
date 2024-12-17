package jp.nlaocs.skriptcatchup.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.Nullable;

public class InventoryUtils {
    public static @Nullable Inventory getInventory(InventoryView view, int rawSlot) {
        return BukkitInventoryUtils.getInventory(view, rawSlot);
    }

    public static @Nullable Integer convertSlot(InventoryView view, int rawSlot) {
        return view.convertSlot(rawSlot);
    }
}
