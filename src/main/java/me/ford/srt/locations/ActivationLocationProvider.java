package me.ford.srt.locations;

import java.util.List;

import org.bukkit.Location;

public interface ActivationLocationProvider {

    public boolean isActivationLocation(Location loc);

    public void markAsActivationLocation(Location loc);

    public void removeAsActivationLocation(Location loc);

    public List<Location> getAllLocations();

    public ActivationListener getListener();

}