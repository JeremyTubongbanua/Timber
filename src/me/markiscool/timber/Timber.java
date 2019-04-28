package me.markiscool.timber;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class
 */
public class Timber extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new TreeBreakListener(), this);
        new Metrics(this);
    }
}
