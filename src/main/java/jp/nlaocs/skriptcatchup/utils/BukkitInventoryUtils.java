package jp.nlaocs.skriptcatchup.utils;

import com.google.common.base.Preconditions;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.Nullable;

public class BukkitInventoryUtils {
    @Nullable
    public static Inventory getInventory(InventoryView view, int rawSlot) {
        if (rawSlot == -999 || rawSlot == -1) {
            return null;
        }
        Preconditions.checkArgument(rawSlot >= 0, "Negative, non outside slot %s", rawSlot);
        Preconditions.checkArgument(rawSlot < countSlots(view), "Slot %s greater than inventory slot count", rawSlot);

        if (rawSlot < view.getTopInventory().getSize()) {
            return view.getTopInventory();
        } else {
            return view.getBottomInventory();
        }
    }

    public static int countSlots(InventoryView view) {
        return view.getTopInventory().getSize() + view.getBottomInventory().getSize();
    }
}
