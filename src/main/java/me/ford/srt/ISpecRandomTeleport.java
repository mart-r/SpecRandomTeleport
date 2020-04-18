package me.ford.srt;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

import me.ford.srt.config.Messages;
import me.ford.srt.config.Settings;
import me.ford.srt.locations.ActivationLocationProvider;
import me.ford.srt.locations.LocationProvider;

public interface ISpecRandomTeleport {

    // implemented by JavaPlugin

    public Logger getLogger();

    public File getDataFolder();

    public void saveResource(String name, boolean replace);

    public InputStream getResource(String name);

    // custom

    public Settings getSettings();

    public Messages getMessages();

    public LocationProvider getLocationProvider();

    public ActivationLocationProvider getActivationLocationProvider();

}