package me.ford.srt;

import java.io.File;

import me.ford.srt.config.Messages;
import me.ford.srt.config.Settings;
import me.ford.srt.locations.ActivationLocationProvider;
import me.ford.srt.locations.LocationProvider;

public interface ISpecRandomTeleport {

    // implemented by JavaPlugin

    public File getDataFolder();

    // custom

    public Settings getSettings();

    public Messages getMessages();

    public LocationProvider getLocationProvider();

    public ActivationLocationProvider getActivationLocationProvider();

}