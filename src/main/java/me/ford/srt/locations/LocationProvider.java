package me.ford.srt.locations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;

import me.ford.srt.SpecRandomTeleport;
import me.ford.srt.config.CustomConfigHandler;

public class LocationProvider extends CustomConfigHandler {
    private static final String NAME = "locations.yml";
    private final Map<String, Location> locations = new HashMap<>();
    private final SpecRandomTeleport srt;
    private final Random random = ThreadLocalRandom.current();

    public LocationProvider(SpecRandomTeleport srt) {
        super(srt, NAME);
        this.srt = srt;
        loadCache();
    }

    private void loadCache() {
        for (String key : getConfig().getKeys(false)) {
            Location loc;
            try {
                loc = getConfig().getLocation(key);
            } catch (IllegalArgumentException e) { // for unknown world
                srt.getLogger().warning("The location stored at " + key + " conatins an invalid world and is thus not loadded!");
                continue;
            }
            locations.put(key, loc);
        }
        if (locations.isEmpty()) {
            srt.getLogger().warning("No locations stored in " + NAME + " - the plugin will not work properly!");
        }
    }

    public void reload() {
        locations.clear();
        loadCache();
    }

    public boolean hasLocation(String name) {
        return locations.get(name) != null;
    }

    public void addLocation(String name, Location loc) {
        getConfig().set(name, loc);
        saveConfig();
        locations.put(name, loc);
    }

    public Location removeLocation(String name) {
        Location loc = locations.remove(name);
        if (loc != null) {
            getConfig().set(name, null);
            saveConfig();
        }
        return loc;
    }

    public Set<String> getNames() {
        return new HashSet<>(locations.keySet());
    }

    public Location getRandomLocation() {
        if (locations.isEmpty()) {
            throw new RuntimeException("No locations set!");
        }
        int amount = locations.size();
        int nr = random.nextInt(amount);
        for (Location loc : locations.values()) { // iterating because the Collection is not ordered
            if (nr == 0) return loc;
            nr--;
        }
        throw new RuntimeException("Got random " + nr + "/" + amount + " and was unable to find the corresponding location - this really shouldn't be happening!");
    }

}