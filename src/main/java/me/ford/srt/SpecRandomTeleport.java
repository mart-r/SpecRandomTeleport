package me.ford.srt;

import org.bukkit.plugin.java.JavaPlugin;

import me.ford.srt.commands.SRTCommand;
import me.ford.srt.config.Messages;
import me.ford.srt.config.Settings;
import me.ford.srt.locations.LocationProvider;

public class SpecRandomTeleport extends JavaPlugin {
    private Messages messages;
    private Settings settings;
    private LocationProvider locationProvider;

    @Override
    public void onEnable() {
        messages = new Messages(this);
        settings = new Settings(this);
        
        saveDefaultConfig();
        messages.saveDefaultConfig();

        locationProvider = new LocationProvider(this);

        getCommand("specrandomteleport").setExecutor(new SRTCommand(this));

    }

    public Settings getSettings() {
        return settings;
    }

    public Messages getMessages() {
        return messages;
    }

    public LocationProvider getLocationProvider() {
        return locationProvider;
    }

}