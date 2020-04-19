package me.ford.srt.locations;

import java.io.File;

import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ford.srt.mock.MockSpecRandomTeleport;
import me.ford.srt.mock.MockWorld;

public class PerWorldLocationProviderTests {
    private MockSpecRandomTeleport srt;
    private PerWorldLocationProvider provider;

    @Before
    public void setUp() {
        srt = new MockSpecRandomTeleport();
        provider = new PerWorldLocationProvider(srt); // TODO - perhaps in the future, the plugin will provide it?
    }

    @After
    public void tearDown() {
        srt.disable();
        for (File file : provider.getFiles()) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
    
    @Test
    public void methods() {
        Assert.assertNotNull(provider.getFiles());
        Assert.assertNotNull(provider.getNames(null));
        Assert.assertNotNull(provider.getLocations(null));
        Assert.assertNull(provider.getLocation(null, "worldNameDoesNotExist"));
        Assert.assertNull(provider.getRandomLocation(null));
        Assert.assertNull(provider.removeLocation(null, "noName"));
    }

    @Test
    public void multiWorld() {
        String worldName1 = "mw_w0rl1";
        MockWorld world1 = new MockWorld(worldName1);
        String worldName2 = "mw_w0rl2";
        MockWorld world2 = new MockWorld(worldName2);

        Assert.assertNull(provider.getRandomLocation(world1));
        Assert.assertNull(provider.getRandomLocation(world2));

        String locName1 = "l0c_in_world_1";
        Location loc1 = new Location(world1, -444.441, 412654.21, 9901412.11);
        provider.setLocation(locName1, loc1);

        Assert.assertNotNull(provider.getRandomLocation(world1));
        Assert.assertNull(provider.getRandomLocation(world2));
        Assert.assertEquals(new NamedLocation(locName1, loc1), provider.getRandomLocation(world1));
        Assert.assertEquals(loc1, provider.getRandomLocation(world1).getLocation()); 
        Assert.assertEquals(locName1, provider.getRandomLocation(world1).getName());
        
        String locName2 = "world_2_l0c";
        Location loc2 = new Location(world2, 9990081, 104.2, -11924124.33);
        provider.setLocation(locName2, loc2);

        Assert.assertNotNull(provider.getRandomLocation(world1));
        Assert.assertNotNull(provider.getRandomLocation(world2));
        Assert.assertEquals(new NamedLocation(locName2, loc2), provider.getRandomLocation(world2));
        Assert.assertEquals(loc2, provider.getRandomLocation(world2).getLocation());
    }

    @Test
    public void multiWorldMultiLoc() {
        String worldName1 = "mw_w2220rl1";
        MockWorld world1 = new MockWorld(worldName1);

        Assert.assertNull(provider.getRandomLocation(world1));

        String locName1 = "l0c_1n_w0rld";
        Location loc1 = new Location(world1, -624.441, 867.21, -886211.11);
        NamedLocation named1 = new NamedLocation(locName1, loc1);
        
        String locName2 = "world_1_l0c";
        Location loc2 = new Location(world1, 552.2, 34.2, 34124.99);
        NamedLocation named2 = new NamedLocation(locName2, loc2);

        provider.setLocation(locName1, loc1);
        provider.setLocation(locName2, loc2);

        Assert.assertEquals(2, provider.getLocations(world1).size());

        NamedLocation rndw1 = provider.getRandomLocation(world1);
        Assert.assertTrue(rndw1.equals(named1) || rndw1.equals(named2));
        Assert.assertTrue(rndw1.getName().equals(locName1) || rndw1.getName().equals(locName2));
        Location rndLoc1 = rndw1.getLocation();
        Assert.assertTrue(rndLoc1.equals(loc1) || rndLoc1.equals(loc2));


        String worldName2 = "mw_wwwvirus1";
        MockWorld world2 = new MockWorld(worldName2);

        Assert.assertNull(provider.getRandomLocation(world2));

        String locName3 = "some_loc_world_2";
        Location loc3 = new Location(world2, -324.441, 367.21, -3211.11);
        NamedLocation named3 = new NamedLocation(locName3, loc3);
        
        String locName4 = "w00rld_2_l0c";
        Location loc4 = new Location(world2, 352.2, 64.2, 34124.99);
        NamedLocation named4 = new NamedLocation(locName4, loc4);

        provider.setLocation(locName3, loc3);
        provider.setLocation(locName4, loc4);

        Assert.assertEquals(2, provider.getLocations(world2).size());

        NamedLocation rndw2 = provider.getRandomLocation(world2);
        Assert.assertTrue(rndw2.equals(named3) || rndw2.equals(named4));
        Assert.assertTrue(rndw2.getName().equals(locName3) || rndw2.getName().equals(locName4));
        Location rndLoc2 = rndw2.getLocation();
        Assert.assertTrue(rndLoc2.equals(loc3) || rndLoc2.equals(loc4));
    }

}