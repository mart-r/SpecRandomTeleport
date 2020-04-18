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

    // scheduling

    public void runTask(Runnable runnable);

    public void runTaskLater(Runnable runnable, long delay);

    public void runTaskTimer(Runnable runnable, long delay, long period);

    public void runTaskAsynchronously(Runnable runnable);

    public void runTaskLaterAsynchronously(Runnable runnable, long delay);

    public void runTaskTimerAsynchronously(Runnable runnable, long delay, long period);

    // custom

    public Settings getSettings();

    public Messages getMessages();

    public LocationProvider getLocationProvider();

    public ActivationLocationProvider getActivationLocationProvider();

}