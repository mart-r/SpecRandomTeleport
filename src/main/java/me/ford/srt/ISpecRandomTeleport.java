package me.ford.srt;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitTask;

import me.ford.srt.config.Messages;
import me.ford.srt.config.Settings;
import me.ford.srt.locations.ActivationLocationProvider;
import me.ford.srt.locations.LocationProvider;

public interface ISpecRandomTeleport {

    // implemented by JavaPlugin

    public Logger getLogger();

    public FileConfiguration getConfig();

    public File getDataFolder();

    public void saveResource(String name, boolean replace);

    public InputStream getResource(String name);

    // scheduling

    public BukkitTask runTask(Runnable runnable);

    public BukkitTask runTaskLater(Runnable runnable, long delay);

    public BukkitTask runTaskTimer(Runnable runnable, long delay, long period);

    public BukkitTask runTaskAsynchronously(Runnable runnable);

    public BukkitTask runTaskLaterAsynchronously(Runnable runnable, long delay);

    public BukkitTask runTaskTimerAsynchronously(Runnable runnable, long delay, long period);

    // custom

    public Settings getSettings();

    public Messages getMessages();

    public LocationProvider getLocationProvider();

    public ActivationLocationProvider getActivationLocationProvider();

}