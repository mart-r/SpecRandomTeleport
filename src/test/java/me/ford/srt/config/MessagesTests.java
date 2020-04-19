package me.ford.srt.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ford.srt.locations.NamedLocation;
import me.ford.srt.mock.MockSpecRandomTeleport;
import me.ford.srt.mock.MockWorld;

public class MessagesTests extends MessageTestsBase {
    private MockSpecRandomTeleport srt;
    private Messages messages;

    @Before
    public void setUp() {
        srt = new MockSpecRandomTeleport();
        messages = srt.getMessages();
    }

    @After
    public void tearDown() {
        srt.disable();
    }

    @Test
    public void fileNameNotNull() {
        Assert.assertNotNull(messages.getFileName());
    }

    @Test
    public void fileNameCorrect() {
        Assert.assertEquals(messages.getFileName(), "messages.yml");
    }

    @Test
    public void fileNotNull() {
        Assert.assertNotNull(messages.getFile());
    }

    @Test
    public void fileCorrect() {
        Assert.assertEquals(new File(srt.getDataFolder(), "messages.yml"), messages.getFile());
    }

    @Test
    public void activationListHeaderUsesPlaceholder() {
        int nr = 4;
        String msg = messages.getActivationListHeaderMessage(nr);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, nr));
    }

    @Test
    public void activationListeItemUsesPlaceholder() {
        String worldName = getRandomName("world");
        MockWorld world = new MockWorld(worldName);
        Location loc = getRandomLocation(world);

        String msg = messages.getActivationListItemMessage(loc);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, loc));
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

        String msg = messages.getActivationListMessage(list);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, loc, loc2));

    }

    @Test
    public void addedLocationUsesPlaceholder() {
        String worldName = getRandomName("world");
        String locName = getRandomName("loc");
        Location loc = getRandomLocation(new MockWorld(worldName));
        
        String msg = messages.getAddedLocationMessage(locName, loc);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, loc, locName));
    }

    @Test
    public void deletedActivationBlockHasNoPlaceholder() {
        String msg = messages.getDeletedActivationBlockMessage();
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
    }

    @Test
    public void listHeaderUsesPlaceholder() {
        int nr = -440;
        String msg = messages.getListHeaderMessage(nr);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, nr));
    }

    @Test
    public void listItemUsesPlaceholder() {
        String locName = getRandomName("loc");
        String worldName = getRandomName("world");
        Location loc = getRandomLocation(new MockWorld(worldName));

        String msg = messages.getListItemMessage(locName, loc);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, loc, locName));
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

        String msg = messages.getListMessage(locs);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, locs.keySet(), locs.values()));

    }

    @Test
    public void locationDoesNotExistUsesPlaceholder() {
        String locName = getRandomName("loc");
        String msg = messages.getLocationDoesNotExistMessage(locName);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, locName));
    }

    @Test
    public void locationExistsUsesPlaceholder() {
        String locName = getRandomName("loc");
        String msg = messages.getLocationExistsMessage(locName);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, locName));
    }

    @Test
    public void markesAsActivationDoesntHavePlaveholder() {
        String msg = messages.getMarkedAsActivationMessage();
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
    }

    @Test
    public void movedLocationUesPlaceholder() {
        String locName = getRandomName("loc");
        Location loc = getRandomLocation(new MockWorld(getRandomName("world")));

        String msg = messages.getMovedLocationMessage(locName, loc);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, locName, loc));
    }

    @Test
    public void needAPlayerDoesntHavePlaceholder() {
        String msg = messages.getNeedAPlayerMessage();
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
    }

    @Test
    public void notAnActivationBlockDoesntHavePlaceholder() {
        String msg = messages.getNotAnActivationBlockMessage();
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
    }

    @Test
    public void removedLocationUsesPlaceholder() {
        String locName = getRandomName("loc");
        Location loc = getRandomLocation(new MockWorld(getRandomName("world")));
        String msg = messages.getRemovedLocationMessage(locName, loc);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, locName, loc));
    }

    @Test
    public void startedWiringDelDoesntHavePlaceholder() {
        String msg = messages.getStartedWaitingDelActivateMessage();
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
    }

    @Test
    public void startedWaitingDoesntHavePlaceholder() {
        String msg = messages.getStartedWaitingMessage();
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));;
    }

    @Test
    public void teleportingUesesPlaceholder() {
        String locName = getRandomName("loc");
        Location loc = getRandomLocation(new MockWorld(getRandomName("world")));
        String msg = messages.getTeleportingMessage(locName, loc);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, locName, loc));
    }

    @Test
    public void unrecognizedCommandUsesPlaceholder() {
        String noCommand = getRandomName("commandNoExist");
        String msg = messages.getUnrecognizedCommandMessage(noCommand);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, noCommand));
    }

    @Test
    public void unsuitableBlockDoesntHavePlaceholder() {
        String msg = messages.getUnsuitableBlockMessage();
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
    }

}