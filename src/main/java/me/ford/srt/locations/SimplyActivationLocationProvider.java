package me.ford.srt.locations;

import java.util.Collection;

import org.bukkit.Location;

import me.ford.srt.ActivationListener;
import me.ford.srt.SpecRandomTeleport;

public class SimplyActivationLocationProvider extends AbstractLocationProvider implements ActivationLocationProvider {
    private static final String NAME = "activators.yml";
    private static final double TOL_SQUARED = 1.0D;
    private final Collection<Location> locations;
    private final ActivationListener listener;

    public SimplyActivationLocationProvider(SpecRandomTeleport srt, ActivationListener listener) {
        super(srt, NAME);
        locations = super.locations.values();
        this.listener = listener;
    }

    @Override
    public boolean isActivationLocation(Location loc) {
        for (Location aLoc : locations) {
            if (aLoc.getWorld() != loc.getWorld()) continue;
            if (aLoc.distanceSquared(loc) < TOL_SQUARED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void markAsActivationLocation(Location loc) {
        setLocation(String.valueOf(System.currentTimeMillis()), loc);
    }

    @Override
    public ActivationListener getListener() {
        return listener;
    }

}