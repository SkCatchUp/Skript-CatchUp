package jp.nlaocs.skriptcatchup;

import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

public final class Skript_CatchUp extends JavaPlugin {

    static Skript_CatchUp instance;
    SkriptAddon addon;

    @Override
    public void onEnable() {
        instance = this;
        addon = Skript.registerAddon(this);

        try {
            addon.loadClasses("jp.nlaocs.skriptcatchup", "elements");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Skript_CatchUp getInstance() {
        return instance;
    }
}
