package me.ford.srt.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
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
import me.ford.srt.utils.FormatUtils;

public class MessagesTests {
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
    public void methods() {
        Assert.assertNotNull(messages.getFileName());
        Assert.assertEquals(messages.getFileName(), "messages.yml");
        Assert.assertNotNull(messages.getFile());
        Assert.assertEquals(new File(srt.getDataFolder(), "messages.yml"), messages.getFile());
    }

    @Test
    public void placeholdersUsed() {
        int nr = 4;
        String msg = messages.getActivationListHeaderMessage(nr);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(contains(msg, nr));

        String worldName = "someName";
        MockWorld world = new MockWorld(worldName);
        double x = 1.4;
        double y = 4.4;
        double z = -102.4;
        Location loc = new Location(world, x, y, z);

        msg = messages.getActivationListItemMessage(loc);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(contains(msg, worldName, x, y, z));

        double x2 = x -100;
        List<Location> list = new ArrayList<>();
        list.add(loc);
        list.add(new Location(world, x2, y, z));

        msg = messages.getActivationListMessage(list);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(contains(msg, worldName, x, y, z, x2));

        String locName = "someRandomLocation";
        
        msg = messages.getAddedLocationMessage(locName, loc);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(contains(msg, worldName, x, y, z, locName));

        msg = messages.getDeletedActivationBlockMessage();
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));

        nr = nr - 440;
        msg = messages.getListHeaderMessage(nr);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(contains(msg, nr));

        msg = messages.getListItemMessage(locName, loc);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(contains(msg, worldName, x, y, z, locName));

        Map<String, NamedLocation> locs = new HashMap<>();
        for (Location cloc : list) {
            String name = locName + cloc.getWorld().getName() + cloc.getX() + cloc.getY() + cloc.getZ(); // unique names
            locs.put(name, new NamedLocation(name, cloc));
        }

        msg = messages.getListMessage(locs);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(contains(msg, worldName, x, y, z, x2, locName, locs.keySet()));

        msg = messages.getLocationDoesNotExistMessage(locName);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(contains(msg, locName));

        msg = messages.getLocationExistsMessage(locName);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(contains(msg, locName));

        msg = messages.getMarkedAsActivationMessage();
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));

        msg = messages.getMovedLocationMessage(locName, loc);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(contains(msg, locName, worldName, x, y, z));

        msg = messages.getNeedAPlayerMessage();
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));

        msg = messages.getNotAnActivationBlockMessage();
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));

        msg = messages.getRemovedLocationMessage(locName, loc);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(contains(msg, locName, worldName, x, y, z));

        msg = messages.getStartedWaitingDelActivateMessage();
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));

        msg = messages.getStartedWaitingMessage();
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));

        msg = messages.getTeleportingMessage(locName, loc);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(contains(msg, locName, worldName, x, y, z));

        msg = messages.getUnrecognizedCommandMessage(locName);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(contains(msg, locName));

        msg = messages.getUnsuitableBlockMessage();
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));

    }

    private boolean hasPlaceHolder(String msg) {
        return msg.contains("{") || msg.contains("}");
    }

    private boolean contains(String msg, Object... parts) {
        for (Object o : parts) {
            String expected;
            if (o instanceof Double) {
                expected = FormatUtils.formatDouble((double) o);
            } else if (o instanceof Collection) { // in case of collection, check recursively
                if (!contains(msg, ((Collection<?>) o).toArray())) return false;
                continue; // next
            } else {
                expected = String.valueOf(o);
            }
            if (!msg.contains(expected)) return false;
        }
        return true;
    }

}