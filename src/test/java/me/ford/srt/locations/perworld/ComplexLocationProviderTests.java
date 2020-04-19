package me.ford.srt.locations.perworld;

import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ford.srt.config.CustomConfigHandler;
import me.ford.srt.locations.NamedLocation;
import me.ford.srt.mock.MockSpecRandomTeleport;
import me.ford.srt.mock.MockWorld;

public class ComplexLocationProviderTests extends PerWorldLocationTestsBase {
    private MockSpecRandomTeleport srt;
    private ComplexLocationProvider provider;

    @Override
    public PerWorldNamedLocationProvider getProvider() {
        return provider;
    }

    @Before
    @Override
    public void setUp() {
        srt = new MockSpecRandomTeleport();
        provider = srt.getLocationProvider();
        super.setUp();
    }

    @After
    public void tearDown() {
        srt.disable();
    }

    @Test
    public void isPerWorld() {
        Assert.assertTrue(provider.isPerWorld());
    }

    private void createNotPerWorld() {
        CustomConfigHandler handler = new CustomConfigHandler(srt, "config.yml");
        tearDown();
        handler.getConfig().set("use-per-world-locations", false);
        handler.saveConfig();
        srt = new MockSpecRandomTeleport();
        provider = srt.getLocationProvider();
    }

    @Test
    public void notPerWorld() {
        createNotPerWorld();
        Assert.assertFalse(provider.isPerWorld());
    }

    @Test
    public void notPerWorldReturnsCorrect() {
        createNotPerWorld();
        String worldName = getRandomName("world");
        MockWorld world = new MockWorld(worldName);

        String locName = getRandomName("loc");
        Location loc = getRandomLocation(world);
        NamedLocation named = new NamedLocation(locName, loc);

        provider.setLocation(locName, loc);
        Assert.assertEquals(named, provider.getRandomLocation(world));
        Assert.assertEquals(loc, provider.getRandomLocation(world).getLocation());
        Assert.assertEquals(locName, provider.getRandomLocation(world).getName());
    }

    @Test
    public void notPerWorldWorksNullWorld() {
        createNotPerWorld();
        String worldName = getRandomName("world");
        MockWorld world = new MockWorld(worldName);

        String locName = getRandomName("loc");
        Location loc = getRandomLocation(world);
        NamedLocation named = new NamedLocation(locName, loc);

        provider.setLocation(locName, loc);
        Assert.assertEquals(named, provider.getRandomLocation(null));
        Assert.assertEquals(loc, provider.getRandomLocation(null).getLocation());
        Assert.assertEquals(locName, provider.getRandomLocation(null).getName());
    }

    @Test
    public void notPerWorldWorksRegardlessOfWorld() {
        createNotPerWorld();
        String worldName1 = getRandomName("world");
        MockWorld world1 = new MockWorld(worldName1);

        String locName = getRandomName("loc");
        Location loc = getRandomLocation(world1);
        NamedLocation named = new NamedLocation(locName, loc);

        String worldName2 = getRandomName("world");
        MockWorld world2 = new MockWorld(worldName2);

        provider.setLocation(locName, loc);

        Assert.assertEquals(named, provider.getRandomLocation(world2));
        Assert.assertEquals(loc, provider.getRandomLocation(world2).getLocation());
        Assert.assertEquals(locName, provider.getRandomLocation(world2).getName());
    }

}