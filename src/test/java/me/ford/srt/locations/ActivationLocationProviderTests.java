package me.ford.srt.locations;

import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ford.srt.mock.MockSpecRandomTeleport;
import me.ford.srt.mock.MockWorld;

public class ActivationLocationProviderTests {
    private MockSpecRandomTeleport srt;
    private ActivationLocationProvider provider;

    @Before
    public void setUp() {
        srt = new MockSpecRandomTeleport();
        provider = srt.getActivationLocationProvider();
    }

    @After
    public void tearDown() {
        srt.disable();
    }

    @Test
    public void methods() {
        Assert.assertNotNull(provider.getAllLocations());
    }

    @Test
    public void saveLoad() {
        Assert.assertTrue(provider.getAllLocations().size() == 0);

        String worldName = "RandomWorldName313";
        MockWorld world = new MockWorld(worldName);
        double x = 1099444.22;
        double y = -44111212;
        double z = Double.MIN_VALUE;
        Location loc = new Location(world, x, y, z);

        provider.markAsActivationLocation(loc);

        Assert.assertTrue(provider.getAllLocations().size() == 1);
        Assert.assertEquals(loc, provider.getAllLocations().get(0));
        Assert.assertTrue(provider.isActivationLocation(loc));

        // new instance
        provider = new SimplyActivationLocationProvider(srt, provider.getListener());

        Assert.assertTrue(provider.getAllLocations().size() == 1);
        Assert.assertEquals(loc, provider.getAllLocations().get(0));
        Assert.assertTrue(provider.isActivationLocation(loc));
    }

}