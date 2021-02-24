package me.swipez.farmingop;

import me.swipez.farmingop.bstats.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class Farmingop extends JavaPlugin {

    boolean gamestarted = false;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new FarmListener(this), this);
        getServer().getPluginManager().registerEvents(new AnvilListener(), this);
        getCommand("farmop").setExecutor(new FarmCommand(this));
        getCommand("farmop").setTabCompleter(new CommandComplete());
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        new Metrics(this, 10448);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
