package me.ford.srt.locations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Location;

import me.ford.srt.ISpecRandomTeleport;

public class SimplyActivationLocationProvider extends AbstractLocationProvider implements ActivationLocationProvider {
    private static final String NAME = "activators.yml";
    private static final double TOL_SQUARED = 1.0D;
    private final Collection<NamedLocation> locations;
    private final ActivationListener listener;

    public SimplyActivationLocationProvider(ISpecRandomTeleport srt, ActivationListener listener) {
        super(srt, NAME);
        locations = super.locations.values();
        this.listener = listener;
    }

    @Override
    public boolean isActivationLocation(Location loc) {
        for (NamedLocation aLoc : locations) {
            Location cLoc = aLoc.getLocation();
            if (cLoc.getWorld() != loc.getWorld())
                continue;
            if (cLoc.distanceSquared(loc) < TOL_SQUARED) {
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
    public void removeAsActivationLocation(Location loc) {
        String name = null;
        for (Entry<String, NamedLocation> entry : super.locations.entrySet()) {
            if (entry.getValue().getLocation().distanceSquared(loc) < TOL_SQUARED) {
                name = entry.getKey();
                break;
            }
        }
        if (name == null) {
            throw new IllegalArgumentException(
                    "Could not find a matching acvitation location to remove for location: " + loc);
        }
        removeLocation(name);
    }

    @Override
    public ActivationListener getListener() {
        return listener;
    }

    @Override
    public List<Location> getAllLocations() {
        List<Location> list = new ArrayList<>();
        for (NamedLocation loc : locations) {
            list.add(loc.getLocation());
        }
        return list;
    }

}