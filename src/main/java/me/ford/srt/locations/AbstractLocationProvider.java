package me.ford.srt.locations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import me.ford.srt.ISpecRandomTeleport;
import me.ford.srt.config.CustomConfigHandler;

public abstract class AbstractLocationProvider extends CustomConfigHandler {
    protected final Map<String, NamedLocation> locations = new HashMap<>();
    private final ISpecRandomTeleport srt;
    private final Random random = ThreadLocalRandom.current();

    public AbstractLocationProvider(ISpecRandomTeleport srt, String name) {
        super(srt, name);
        this.srt = srt;
        loadCache();
    }

    private void loadCache() {
        for (String key : getConfig().getKeys(false)) {
            Location loc;
            try {
                loc = getConfig().getLocation(key);
            } catch (IllegalArgumentException e) { // for unknown world
                srt.getLogger().warning("The location stored at " + key + "  in " + getFileName()
                        + " conatins an invalid world and is thus not loadded!");
                continue;
            }
            locations.put(key, new NamedLocation(key, loc));
        }
        if (locations.isEmpty() && srt instanceof JavaPlugin) { // skip message during testing
            srt.getLogger()
                    .warning("No locations stored in " + getFileName() + " - the plugin will not work properly!");
        }
    }

    public void reload() {
        locations.clear();
        loadCache();
    }

    public boolean hasLocation(String name) {
        return locations.get(name) != null;
    }

    public void setLocation(String name, Location loc) {
        getConfig().set(name, loc);
        saveConfig();
        locations.put(name, new NamedLocation(name, loc));
    }

    public Location removeLocation(String name) {
        NamedLocation loc = locations.remove(name);
        if (loc != null) {
            getConfig().set(name, null);
            saveConfig();
        }
        return loc == null ? null : loc.getLocation();
    }

    public Location getLocation(String name) {
        NamedLocation loc = locations.get(name);
        return loc == null ? null : loc.getLocation();
    }

    public Map<String, NamedLocation> getLocations() {
        return new HashMap<>(locations);
    }

    public Set<String> getNames() {
        return new HashSet<>(locations.keySet());
    }

    public NamedLocation getRandomLocation() {
        if (locations.isEmpty()) {
            return null;
        }
        int amount = locations.size();
        int nr = random.nextInt(amount);
        for (NamedLocation loc : locations.values()) { // iterating because the Collection is not ordered
            if (nr == 0)
                return loc;
            nr--;
        }
        throw new RuntimeException("Got random " + nr + "/" + amount
                + " and was unable to find the corresponding location - this really shouldn't be happening!");
    }

}