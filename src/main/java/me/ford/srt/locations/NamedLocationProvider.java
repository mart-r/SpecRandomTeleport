package me.ford.srt.locations;

import java.util.Map;
import java.util.Set;

import org.bukkit.Location;

public interface NamedLocationProvider {

    public boolean hasLocation(String name);

    public void setLocation(String name, Location loc);

    public Location removeLocation(String name);

    public Location getLocation(String name);

    public Map<String, NamedLocation> getLocations();

    public Set<String> getNames();

    public NamedLocation getRandomLocation();

}