package me.ford.srt.locations;

import me.ford.srt.SpecRandomTeleport;

public class LocationProvider extends AbstractLocationProvider {
    private static final String NAME = "locations.yml";

    public LocationProvider(SpecRandomTeleport srt) {
		super(srt, NAME);
	}

}