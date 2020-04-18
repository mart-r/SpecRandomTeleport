package me.ford.srt.mock;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitTask;

import me.ford.srt.ISpecRandomTeleport;
import me.ford.srt.config.Messages;
import me.ford.srt.config.Settings;
import me.ford.srt.locations.ActivationListener;
import me.ford.srt.locations.ActivationLocationProvider;
import me.ford.srt.locations.LocationProvider;
import me.ford.srt.locations.SimplyActivationLocationProvider;

public class MockSpecRandomTeleport implements ISpecRandomTeleport {
    private static final String CONFIG_NAME = "config.yml";
    private final File sourceFolder = new File("src");
    private final File testFolder = new File(sourceFolder, "test");
    private final File dataFolder = new File(testFolder, "resources");
    private final Logger logger = Logger.getLogger("Mock SRT");
    private final MockScheduler scheduler;
    private final Settings settings;
    private final Messages messages;
    private final FileConfiguration config;
    // private final MockPluginManager pluginManager;

    private final LocationProvider locationProvider;
    private final ActivationLocationProvider activationProvider;

    public MockSpecRandomTeleport() {
        scheduler = new MockScheduler();
        ;
        settings = new Settings(this);
        messages = new Messages(this);
        config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), CONFIG_NAME));

        // pluginManager = new MockPluginManager();

        ActivationListener activationListener = new ActivationListener(this);
        // pluginManager.registerEvents(activationListener, this);

        locationProvider = new LocationProvider(this);
        activationProvider = new SimplyActivationLocationProvider(this, activationListener);

        // reflection
        setupMockServer();
    }

    private void setupMockServer() {
        Class<Bukkit> bukkit = Bukkit.class;
        Field serverField;
        try {
            serverField = bukkit.getDeclaredField("server");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            return;
        }
        serverField.setAccessible(true);
        try {
            serverField.set(null, MockServer.getInstance());
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public File getDataFolder() {
        return dataFolder;
    }

    @Override
    public void saveResource(String name, boolean replace) {
        // TODO Auto-generated method stub

    }

    @Override
    public InputStream getResource(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BukkitTask runTask(Runnable runnable) {
        return scheduler.runTask(runnable);
    }

    @Override
    public BukkitTask runTaskLater(Runnable runnable, long delay) {
        return scheduler.runTaskLater(runnable, delay);
    }

    @Override
    public BukkitTask runTaskTimer(Runnable runnable, long delay, long period) {
        return scheduler.runTaskTimer(runnable, delay, period);
    }

    @Override
    public BukkitTask runTaskAsynchronously(Runnable runnable) {
        return scheduler.runTaskAsynchronously(runnable);
    }

    @Override
    public BukkitTask runTaskLaterAsynchronously(Runnable runnable, long delay) {
        return scheduler.runTaskLaterAsynchronously(runnable, delay);
    }

    @Override
    public BukkitTask runTaskTimerAsynchronously(Runnable runnable, long delay, long period) {
        return scheduler.runTaskTimerAsynchronously(runnable, delay, period);
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

    public void disable() {
        File file = locationProvider.getFile();
        if (file.exists()) file.delete();
        file = ((SimplyActivationLocationProvider) activationProvider).getFile();
        if (file.exists()) file.delete();
    }

}