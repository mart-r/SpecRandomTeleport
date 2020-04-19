package me.ford.srt.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ford.srt.ISpecRandomTeleport;
import me.ford.srt.locations.NamedLocation;
import me.ford.srt.mock.MockSpecRandomTeleport;
import me.ford.srt.mock.MockWorld;

public class MessageCodeDefaultTests extends MessageTestsBase {
    private MockSpecRandomTeleport srt;
    private Messages noDefaults;
    private DefMessages defaults;

    @Before
    public void setUp() { 
        srt = new MockSpecRandomTeleport();
        noDefaults = srt.getMessages(); // use in code defaults
        defaults = new DefMessages(srt);
    }

    @After
    public void tearDown() {
        srt.disable();
    }

    @Test
    public void activationListHeaderUsesPlaceholder() {
        int nr = 4;
        String msg1 = defaults.getActivationListHeaderMessage(nr);
        String msg2 = noDefaults.getActivationListHeaderMessage(nr);
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void activationListeItemUsesPlaceholder() {
        String worldName = getRandomName("world");
        MockWorld world = new MockWorld(worldName);
        Location loc = getRandomLocation(world);

        String msg1 = defaults.getActivationListItemMessage(loc);
        String msg2 = noDefaults.getActivationListItemMessage(loc);
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void activationListUsesPlaceholder() {
        String worldName = getRandomName("world");
        MockWorld world = new MockWorld(worldName);
        Location loc = getRandomLocation(world);

        List<Location> list = new ArrayList<>();
        list.add(loc);
        Location loc2 = getRandomLocation(world);
        list.add(loc2);

        String msg1 = defaults.getActivationListMessage(list);
        String msg2 = noDefaults.getActivationListMessage(list);
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void addedLocationUsesPlaceholder() {
        String worldName = getRandomName("world");
        String locName = getRandomName("loc");
        Location loc = getRandomLocation(new MockWorld(worldName));
        
        String msg1 = defaults.getAddedLocationMessage(locName, loc);
        String msg2 = noDefaults.getAddedLocationMessage(locName, loc);
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void deletedActivationBlockHasNoPlaceholder() {
        String msg1 = defaults.getDeletedActivationBlockMessage();
        String msg2 = noDefaults.getDeletedActivationBlockMessage();
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void listHeaderUsesPlaceholder() {
        int nr = 42;
        String msg1 = defaults.getListHeaderMessage(nr);
        String msg2 = noDefaults.getListHeaderMessage(nr);
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void listItemUsesPlaceholder() {
        String locName = getRandomName("loc");
        String worldName = getRandomName("world");
        Location loc = getRandomLocation(new MockWorld(worldName));

        String msg1 = defaults.getListItemMessage(locName, loc);
        String msg2 = noDefaults.getListItemMessage(locName, loc);
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void listUsesPlaceholder() {
        String locName = getRandomName("loc");
        String worldName = getRandomName("world");

        MockWorld world = new MockWorld(worldName);

        Map<String, NamedLocation> locs = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            Location loc = getRandomLocation(world);
            String name = locName + "_" + loc.getWorld().getName() + "_" + loc.getX() + "_" + loc.getY() + "_" + loc.getZ();
            locs.put(name, new NamedLocation(name, loc));
        }

        String msg1 = defaults.getListMessage(locs);
        String msg2 = noDefaults.getListMessage(locs);
        Assert.assertEquals(msg1, msg2);

    }

    @Test
    public void locationDoesNotExistUsesPlaceholder() {
        String locName = getRandomName("loc");
        String msg1 = defaults.getLocationDoesNotExistMessage(locName);
        String msg2 = noDefaults.getLocationDoesNotExistMessage(locName);
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void locationExistsUsesPlaceholder() {
        String locName = getRandomName("loc");
        String msg1 = defaults.getLocationExistsMessage(locName);
        String msg2 = noDefaults.getLocationExistsMessage(locName);
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void markesAsActivationDoesntHavePlaveholder() {
        String msg1 = defaults.getMarkedAsActivationMessage();
        String msg2 = noDefaults.getMarkedAsActivationMessage();
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void movedLocationUesPlaceholder() {
        String locName = getRandomName("loc");
        Location loc = getRandomLocation(new MockWorld(getRandomName("world")));

        String msg1 = defaults.getMovedLocationMessage(locName, loc);
        String msg2 = noDefaults.getMovedLocationMessage(locName, loc);
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void needAPlayerDoesntHavePlaceholder() {
        String msg1 = defaults.getNeedAPlayerMessage();
        String msg2 = noDefaults.getNeedAPlayerMessage();
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void notAnActivationBlockDoesntHavePlaceholder() {
        String msg1 = defaults.getNotAnActivationBlockMessage();
        String msg2 = noDefaults.getNotAnActivationBlockMessage();
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void removedLocationUsesPlaceholder() {
        String locName = getRandomName("loc");
        Location loc = getRandomLocation(new MockWorld(getRandomName("world")));
        String msg1 = defaults.getRemovedLocationMessage(locName, loc);
        String msg2 = noDefaults.getRemovedLocationMessage(locName, loc);
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void startedWiringDelDoesntHavePlaceholder() {
        String msg1 = defaults.getStartedWaitingDelActivateMessage();
        String msg2 = noDefaults.getStartedWaitingDelActivateMessage();
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void startedWaitingDoesntHavePlaceholder() {
        String msg1 = defaults.getStartedWaitingMessage();
        String msg2 = noDefaults.getStartedWaitingMessage();
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void teleportingUesesPlaceholder() {
        String locName = getRandomName("loc");
        Location loc = getRandomLocation(new MockWorld(getRandomName("world")));
        String msg1 = defaults.getTeleportingMessage(locName, loc);
        String msg2 = noDefaults.getTeleportingMessage(locName, loc);
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void unrecognizedCommandUsesPlaceholder() {
        String noCommand = getRandomName("commandNoExist");
        String msg1 = defaults.getUnrecognizedCommandMessage(noCommand);
        String msg2 = noDefaults.getUnrecognizedCommandMessage(noCommand);
        Assert.assertEquals(msg1, msg2);
    }

    @Test
    public void unsuitableBlockDoesntHavePlaceholder() {
        String msg1 = defaults.getUnsuitableBlockMessage();
        String msg2 = noDefaults.getUnsuitableBlockMessage();
        Assert.assertEquals(msg1, msg2);
    }

    private class DefMessages extends Messages {
        private static final String FILE_NAME = "../../../src/main/resources/messages.yml";

        protected DefMessages(ISpecRandomTeleport srt) {
            super(srt, FILE_NAME);
        }

    }

}