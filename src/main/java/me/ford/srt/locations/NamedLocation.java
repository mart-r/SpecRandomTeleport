package me.ford.srt.locations;

import org.bukkit.Location;

public class NamedLocation {
    private final String name;
    private final Location loc;

    public NamedLocation(String name, Location loc) {
        this.name = name;
        this.loc = loc;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return loc;
    }

    @Override
    public String toString() {
        return name + ":" + loc;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof NamedLocation))
            return false;
        NamedLocation o = (NamedLocation) other;
        return o.name.equals(name) && o.loc.equals(loc);
    }

}