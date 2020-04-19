package me.ford.srt.locations;

import java.io.File;

import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ford.srt.mock.MockSpecRandomTeleport;
import me.ford.srt.mock.MockWorld;

public class LocationProviderTests {
    private MockSpecRandomTeleport srt;
    private LocationProvider provider;

    @Before
    public void setUp() {
        srt = new MockSpecRandomTeleport();
        provider = srt.getLocationProvider();
    }

    @After
    public void tearDown() {
        srt.disable();
    }

    @Test
    public void methods() {
        Assert.assertEquals("locations.yml", provider.getFileName());
        Assert.assertEquals(new File(srt.getDataFolder(), "locations.yml"), provider.getFile());
        Assert.assertNotNull(provider.getConfig());
    }

    @Test
    public void saveLoad() {
        Assert.assertTrue(provider.getLocations().isEmpty());
        Assert.assertNull(provider.getRandomLocation());
        
        String locName = "ThisSpawnLocation";
        String worldName = "RandomWorldName123";
        MockWorld world = new MockWorld(worldName);
        double x = 109944.22;
        double y = -441212;
        double z = Double.MAX_VALUE;
        Location loc = new Location(world, x, y, z);

        provider.setLocation(locName, loc);

        Assert.assertTrue(provider.getLocations().size() == 1);
        Assert.assertTrue(provider.getNames().size() == 1);
        Assert.assertTrue(provider.getNames().contains(locName));
        Assert.assertEquals(provider.getRandomLocation(), new NamedLocation(locName, loc));
        Assert.assertEquals(provider.getRandomLocation().getLocation(), loc);

        // new instance
        provider = new LocationProvider(srt);

        Assert.assertTrue(provider.getLocations().size() == 1);
        Assert.assertTrue(provider.getNames().size() == 1);
        Assert.assertTrue(provider.getNames().contains(locName));
        Assert.assertEquals(provider.getRandomLocation(), new NamedLocation(locName, loc));
        Assert.assertEquals(provider.getRandomLocation().getLocation(), loc);
        
        String locName2 = "AnotherLocation";
        Location loc2 = new Location(world, x*y, x - z, y*y + x*z);
        provider.setLocation(locName2, loc2);

        Assert.assertTrue(provider.getLocations().size() == 2);
        Assert.assertTrue(provider.getNames().size() == 2);
        Assert.assertTrue(provider.getNames().contains(locName));
        Assert.assertTrue(provider.getNames().contains(locName2));
        Assert.assertTrue(provider.getLocations().values().contains(new NamedLocation(locName, loc)));
        Assert.assertTrue(provider.getLocations().values().contains(new NamedLocation(locName2, loc2)));
        NamedLocation rndLoc = provider.getRandomLocation();
        Assert.assertNotNull(rndLoc);
        Assert.assertTrue(rndLoc.getName().equals(locName) || rndLoc.getName().equals(locName2));
        Assert.assertTrue(rndLoc.getLocation().equals(loc) || rndLoc.getLocation().equals(loc2));
        Assert.assertTrue(rndLoc.equals(new NamedLocation(locName, loc)) || rndLoc.equals(new NamedLocation(locName2, loc2)));
    }

}