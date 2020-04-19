package me.ford.srt.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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

    private Location getRandomLocation(MockWorld world) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        return new Location(world, r.nextDouble()*1000, r.nextDouble() * 1000, r.nextDouble() * 1000, r.nextFloat() * 360, r.nextFloat()*180 - 90);
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
        String worldName = "someName";
        MockWorld world = new MockWorld(worldName);
        double x = 1.4;
        double y = 4.4;
        double z = -102.4;
        Location loc = new Location(world, x, y, z);

        String msg = messages.getActivationListItemMessage(loc);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, loc));
    }

    @Test
    public void activationListUsesPlaceholder() {
        String worldName = "someN4me";
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
        String worldName = "S0m3W00r3ld";
        String locName = "someRandomLocation4";
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
        String locName = "theNam3OfTH3WLOCATI0n";
        String worldName = "somew0nd3rfullWORLD";
        Location loc = getRandomLocation(new MockWorld(worldName));

        String msg = messages.getListItemMessage(locName, loc);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, loc, locName));
    }

    @Test
    public void listUsesPlaceholder() {
        String locName = "the12321Nam3OfTH3WLOCATI0n";
        String worldName = "somew0nd3rf4412ullWORLD";

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
        String locName = "SomeRandomString";
        String msg = messages.getLocationDoesNotExistMessage(locName);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, locName));
    }

    @Test
    public void locationExistsUsesPlaceholder() {
        String locName = "Yet4n0th3rLocationNAME";
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
        String locName = "woo_A_name";
        Location loc = getRandomLocation(new MockWorld("SomeWorldNameNEW123"));

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
        String locName = "yay-more_names";
        Location loc = getRandomLocation(new MockWorld("TheworldISABOUTTOend"));
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
        String locName = "holdyourbreath";
        Location loc = getRandomLocation(new MockWorld("thisISTHEend"));
        String msg = messages.getTeleportingMessage(locName, loc);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, locName, loc));
    }

    @Test
    public void unrecognizedCommandUsesPlaceholder() {
        String noCommand = "ThisCommandDoesNotExist";
        String msg = messages.getUnrecognizedCommandMessage(noCommand);
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
        Assert.assertTrue(assertContains(msg, noCommand));
    }

    @Test
    public void unsuitableBlockDoesntHavePlaceholder() {
        String msg = messages.getUnsuitableBlockMessage();
        Assert.assertFalse("Has placeholder:" + msg, hasPlaceHolder(msg));
    }

    private boolean hasPlaceHolder(String msg) {
        return msg.contains("{") || msg.contains("}");
    }

    private boolean assertContains(String msg, Object... parts) {
        for (Object o : parts) {
            String expected;
            if (o instanceof Double) {
                expected = FormatUtils.formatDouble((double) o);
            } else if (o instanceof Float) {
                    expected = FormatUtils.formatDouble((float) o);
            } else if (o instanceof Collection) { // in case of collection, check recursively
                if (!assertContains(msg, ((Collection<?>) o).toArray())) return false;
                continue; // next
            } else if (o instanceof Location) {
                Location loc = (Location) o;
                if (!assertContains(msg, loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch())) return false;
                continue;
            } else if (o instanceof NamedLocation) {
                NamedLocation loc = (NamedLocation) o;
                if (!assertContains(msg, loc.getName() ,loc.getLocation())) removedLocationUsesPlaceholder();
                continue;
            } else {
                expected = String.valueOf(o);
            }
            Assert.assertTrue(msg + " should contain " + o, msg.contains(expected));
        }
        return true;
    }

}