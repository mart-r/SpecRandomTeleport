package me.ford.srt;

import org.bstats.bukkit.Metrics;

import me.ford.srt.commands.SRTCommand;
import me.ford.srt.config.Messages;
import me.ford.srt.config.Settings;
import me.ford.srt.locations.ActivationListener;
import me.ford.srt.locations.ActivationLocationProvider;
import me.ford.srt.locations.SimplyActivationLocationProvider;
import me.ford.srt.locations.LocationProvider;

public class SpecRandomTeleport extends SchedulingSpecRandomTeleport {
    private Messages messages;
    private Settings settings;
    private LocationProvider locationProvider;
    private ActivationLocationProvider activationProvider;

    @Override
    public void onEnable() {
        messages = new Messages(this);
        settings = new Settings(this);

        saveDefaultConfig();
        messages.saveDefaultConfig();

        ActivationListener activationListener = new ActivationListener(this);
        getServer().getPluginManager().registerEvents(activationListener, this);

        locationProvider = new LocationProvider(this);
        activationProvider = new SimplyActivationLocationProvider(this, activationListener);

        getCommand("specrandomteleport").setExecutor(new SRTCommand(this));

        if (settings.useMetrics()) {
            new Metrics(this, 7216);
        }
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public Messages getMessages() {
        return messages;
    }

    @Override
    public LocationProvider getLocationProvider() {
        return locationProvider;
    }

    @Override
    public ActivationLocationProvider getActivationLocationProvider() {
        return activationProvider;
    }

}