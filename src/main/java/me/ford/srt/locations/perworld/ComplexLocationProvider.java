package me.ford.srt.locations.perworld;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;

import me.ford.srt.ISpecRandomTeleport;
import me.ford.srt.locations.LocationProvider;
import me.ford.srt.locations.NamedLocation;

public class ComplexLocationProvider implements PerWorldNamedLocationProvider {
    private final boolean usePerWorld;
    private final LocationProvider locProvider;
    private final PerWorldLocationProvider perWorldProvider;

    public ComplexLocationProvider(ISpecRandomTeleport srt) {
        this.usePerWorld = srt.getSettings().usePerWorld();
        if (usePerWorld) {
            locProvider = null;
            perWorldProvider = new PerWorldLocationProvider(srt);
        } else {
            locProvider = new LocationProvider(srt);
            perWorldProvider = null;
        }
    }

    @Override
    public boolean hasLocation(String name) {
        if (usePerWorld) {
            return perWorldProvider.hasLocation(name);
        } else {
            return locProvider.hasLocation(name);
        }
    }

    @Override
    public void setLocation(String name, Location loc) {
        if (usePerWorld) {
            perWorldProvider.setLocation(name, loc);
        } else {
            locProvider.setLocation(name, loc);
        }
    }

    @Override
    public Location removeLocation(World world, String name) {
        if (usePerWorld) {
            return perWorldProvider.removeLocation(world, name);
        } else {
            return locProvider.removeLocation(name);
        }
    }

    @Override
    public Location getLocation(World world, String name) {
        if (usePerWorld) {
            return perWorldProvider.getLocation(world, name);
        } else {
            return locProvider.getLocation(name);
        }
    }

    @Override
    public List<NamedLocation> getLocations(World world) {
        if (usePerWorld) {
            return perWorldProvider.getLocations(world);
        } else {
            return locProvider.getLocations();
        }
    }

    @Override
    public Set<String> getNames(World world) {
        if (usePerWorld) {
            return perWorldProvider.getNames(world);
        } else {
            return locProvider.getNames();
        }
    }

    @Override
    public NamedLocation getRandomLocation(World world) {
        if (usePerWorld) {
            return perWorldProvider.getRandomLocation(world);
        } else {
            return locProvider.getRandomLocation();
        }
    }

    @Override
    public Set<File> getFiles() {
        if (usePerWorld) {
            return perWorldProvider.getFiles();
        } else {
            Set<File> files = new HashSet<>();
            files.add(locProvider.getFile());
            return files;
        }
    }

    public boolean isPerWorld() {
        return usePerWorld;
    }

}