package me.ford.srt.locations.perworld;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;

import me.ford.srt.locations.NamedLocation;

public interface PerWorldNamedLocationProvider {

    public boolean hasLocation(String name);

    public void setLocation(String name, Location loc);

    public Location removeLocation(World world, String name);

    public Location getLocation(World world, String name);

    public Map<String, NamedLocation> getLocations(World world);

    public Set<String> getNames(World world);

    public NamedLocation getRandomLocation(World world);

    public Set<File> getFiles();

}