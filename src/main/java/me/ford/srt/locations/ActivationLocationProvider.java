package me.ford.srt.locations;

import org.bukkit.Location;

import me.ford.srt.ActivationListener;

public interface ActivationLocationProvider {

    public boolean isActivationLocation(Location loc);

    public void markAsActivationLocation(Location loc);

    public void removeAsActivationLocation(Location loc);

    public ActivationListener getListener();

}