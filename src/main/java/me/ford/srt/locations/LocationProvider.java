package me.ford.srt.locations;

import me.ford.srt.ISpecRandomTeleport;

public class LocationProvider extends AbstractLocationProvider {
    private static final String NAME = "locations.yml";

    public LocationProvider(ISpecRandomTeleport srt) {
        super(srt, NAME);
    }

}