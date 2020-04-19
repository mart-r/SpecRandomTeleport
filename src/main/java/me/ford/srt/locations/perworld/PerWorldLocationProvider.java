package me.ford.srt.locations.perworld;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;

import me.ford.srt.ISpecRandomTeleport;
import me.ford.srt.locations.LocationProvider;
import me.ford.srt.locations.NamedLocation;

public class PerWorldLocationProvider implements PerWorldNamedLocationProvider {
    private static final String FILE_NAME_FORMAT = "locations_%s.yml";
    private final ISpecRandomTeleport srt;
    private final Map<World, LocationProvider> worldProviders = new HashMap<>();

    public PerWorldLocationProvider(ISpecRandomTeleport srt) {
        this.srt = srt;
    }

    private LocationProvider getWorldProvider(World world) {
        return worldProviders.get(world);
    }

    private LocationProvider getOrCreateWorldProvider(World world) {
        LocationProvider provider = worldProviders.get(world);
        if (provider == null) {
            provider = new LocationProvider(srt, String.format(FILE_NAME_FORMAT, world.getName()));
            worldProviders.put(world, provider);
        }
        return provider;
    }

    @Override
    public boolean hasLocation(String name) {
        for (LocationProvider provider : worldProviders.values()) {
            if (provider.hasLocation(name))
                return true;
        }
        return false;
    }

    @Override
    public void setLocation(String name, Location loc) {
        LocationProvider provider = getOrCreateWorldProvider(loc.getWorld());
        provider.setLocation(name, loc);
    }

    /**
     * If world is null, then the location with the name from the first world
     * encountered that has a location with such a name will be removed
     */
    @Override
    public Location removeLocation(World world, String name) {
        if (world == null) {
            for (World cworld : worldProviders.keySet()) {
                Location loc = removeLocation(cworld, name);
                if (loc != null) {
                    return loc;
                }
            }
        }
        LocationProvider provider = getWorldProvider(world);
        if (provider == null)
            return null;
        return provider.removeLocation(name);
    }

    @Override
    public Location getLocation(World world, String name) {
        LocationProvider provider = getWorldProvider(world);
        if (provider == null)
            return null;
        return provider.getLocation(name);
    }

    @Override
    public List<NamedLocation> getLocations(World world) {
        if (world == null) {
            List<NamedLocation> map = new ArrayList<>();
            for (World cworld : worldProviders.keySet()) {
                map.addAll(getLocations(cworld));
            }
            return map;
        }
        LocationProvider provider = getWorldProvider(world);
        if (provider == null)
            return new ArrayList<>();
        return provider.getLocations();
    }

    @Override
    public Set<String> getNames(World world) {
        if (world == null) {
            Set<String> names = new HashSet<>();
            for (World cworld : worldProviders.keySet()) {
                names.addAll(getNames(cworld));
            }
            return names;
        }
        LocationProvider provider = getWorldProvider(world);
        if (provider == null)
            return new HashSet<>();
        return provider.getNames();
    }

    @Override
    public NamedLocation getRandomLocation(World world) {
        LocationProvider provider = getWorldProvider(world);
        if (provider == null)
            return null;
        return provider.getRandomLocation();
    }

    @Override
    public Set<File> getFiles() {
        Set<File> files = new HashSet<>();
        for (LocationProvider provider : worldProviders.values()) {
            files.add(provider.getFile());
        }
        return files;
    }

}