package me.ford.srt.locations.perworld;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ford.srt.mock.MockSpecRandomTeleport;

public class PerWorldLocationProviderTests extends PerWorldLocationTestsBase {
    private MockSpecRandomTeleport srt;
    private PerWorldLocationProvider provider;

    @Override
    public PerWorldLocationProvider getProvider() {
        return provider;
    }

    @Before
    @Override
    public void setUp() {
        srt = new MockSpecRandomTeleport();
        provider = new PerWorldLocationProvider(srt); // TODO - perhaps in the future, the plugin will provide it?
        super.setUp();
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
    public void filesNotNull() {
        Assert.assertNotNull(provider.getFiles());
    }
    
    @Test
    public void namesNotNull() {
        Assert.assertNotNull(provider.getNames(null));
    }
    
    @Test
    public void locationsNotNull() {
        Assert.assertNotNull(provider.getLocations(null));
    }
    
    @Test
    public void nonExistentLocationNull() {
        Assert.assertNull(provider.getLocation(null, "worldNameDoesNotExist"));
    }
    
    @Test
    public void nullIfNoLocations() {
        Assert.assertNull(provider.getRandomLocation(null));
    }
    
    @Test
    public void unableToRemoveIfNoneSet() {
        Assert.assertNull(provider.removeLocation(null, "noName"));
    }

}