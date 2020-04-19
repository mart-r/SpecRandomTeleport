package me.ford.srt.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ford.srt.config.MessageTestsBase;
import me.ford.srt.config.Messages;
import me.ford.srt.locations.NamedLocation;
import me.ford.srt.locations.perworld.ComplexLocationProvider;
import me.ford.srt.mock.MockCommandSender;
import me.ford.srt.mock.MockPlayer;
import me.ford.srt.mock.MockSpecRandomTeleport;
import me.ford.srt.mock.MockWorld;

public class SRTCommandTests extends MessageTestsBase {
    private MockSpecRandomTeleport srt;
    private MockCommandSender sender;
    private Messages messages;
    private SRTCommand command;

    @Before
    public void setUp() {
        srt = new MockSpecRandomTeleport();
        messages = srt.getMessages();
        command = new SRTCommand(srt);
        sender = new MockCommandSender("MockCommandSenderName");
    }

    @After
    public void tearDown() {
        srt.disable();
    }

    @Test
    public void methods() {
        Assert.assertNotNull(command.getUsage());
    }

    @Test
    public void srtBaseTests() {
        assertCommand(sender, "", command.getUsage(sender));
        String nonExistentSubCommand = getRandomName("NOEXIST");
        assertCommand(sender, nonExistentSubCommand, messages.getUnrecognizedCommandMessage(nonExistentSubCommand));
    }

    @Test
    public void srtAddLocSenderTests() {
        assertCommand(sender, "addloc", command.getSubCommand("addloc").getUsage(sender, new String[] {}));
        assertCommand(sender, "addloc SomeName", messages.getNeedAPlayerMessage());
    }

    @Test
    public void srtAddLocPlayerTests() {
        MockPlayer player = new MockPlayer(getRandomName("player"));
        assertCommand(player, "addloc", command.getSubCommand("addloc").getUsage(player, new String[] {}));

        String locName = getRandomName("loc");
        String worldName = getRandomName("world");
        Location loc = getRandomLocation(new MockWorld(worldName));
        player.setLocation(loc);
        assertCommand(player, "addloc " + locName, messages.getAddedLocationMessage(locName, loc));

        ComplexLocationProvider locProv = srt.getLocationProvider();

        Assert.assertEquals(locProv.getLocation(loc.getWorld(), locName), loc);
        Assert.assertTrue(locProv.getLocations(loc.getWorld()).contains(new NamedLocation(locName, loc)));
        Assert.assertTrue(locProv.getLocations(loc.getWorld()).size() == 1);
        Assert.assertEquals(locProv.getLocations(loc.getWorld()).get(0), new NamedLocation(locName, loc));
        Assert.assertEquals(locProv.getRandomLocation(loc.getWorld()), new NamedLocation(locName, loc));
        Assert.assertEquals(locProv.getLocation(loc.getWorld(), locName), loc);
        Assert.assertEquals(locProv.getRandomLocation(loc.getWorld()).getLocation(), loc);
    }

    @Test
    public void srtRemoveLocSenderTests() {
        assertCommand(sender, "removeloc", command.getSubCommand("removeloc").getUsage(sender, new String[] {}));
        String nonExistent = getRandomName("noexists");
        assertCommand(sender, "removeloc " + nonExistent, messages.getLocationDoesNotExistMessage(nonExistent));

        String locName = getRandomName("loc");
        String worldName = getRandomName("world");
        Location loc = getRandomLocation(new MockWorld(worldName));
        ComplexLocationProvider locProv = srt.getLocationProvider();
        locProv.setLocation(locName, loc);

        assertCommand(sender, "removeloc " + nonExistent, messages.getLocationDoesNotExistMessage(nonExistent));

        Assert.assertTrue(locProv.getLocations(loc.getWorld()).size() != 0);
        // real deal
        assertCommand(sender, "removeloc " + locName, messages.getRemovedLocationMessage(locName, loc));

        Assert.assertTrue(locProv.getLocations(loc.getWorld()).size() == 0);
    }

    @Test
    public void useTests() {
        MockPlayer player = new MockPlayer(getRandomName("player"));

        String worldName = getRandomName("world");
        MockWorld world = new MockWorld(worldName);
        Location loc1 = getRandomLocation(world);
        Location loc2 = getRandomLocation(world);

        assertCommand(player, "use", messages.getNoLocationsSetMessage());

        String locName = getRandomName("loc");
        player.setLocation(loc1);
        srt.getLocationProvider().setLocation(locName, loc2);

        assertCommand(player, "use", messages.getTeleportingMessage(locName, loc2));
        Assert.assertEquals(loc2, player.getLocation());
    }

    @Test
    public void listAllConsole() {
        String worldName = getRandomName("world");
        MockWorld world = new MockWorld(worldName);
        Location loc1 = getRandomLocation(world);
        Location loc2 = getRandomLocation(world);
        List<NamedLocation> locs = new ArrayList<>();
        // need to preserver order
        locs.add(new NamedLocation("loc2", loc1));
        locs.add(new NamedLocation("loc1", loc2));
        for (NamedLocation loc : locs) {
            srt.getLocationProvider().setLocation(loc.getName(), loc.getLocation());
        }
        String msg = messages.getListMessage(locs);
        assertCommand(sender, "list", msg);
    }

    @Test
    public void listAllConsoleMultiworld() {
        String worldName = "world1";
        MockWorld world1 = new MockWorld(worldName);

        MockWorld world2 = new MockWorld("world2");
        Location loc1 = getRandomLocation(world1);
        Location loc2 = getRandomLocation(world2);
        List<NamedLocation> locs = new ArrayList<>();
        // need to preserver order
        locs.add(new NamedLocation("loc1", loc1));
        locs.add(new NamedLocation("loc2", loc2));
        // since in the implementation, the worlds get in the middle and the order can
        // change
        // with the current setup, will need to iterate backwards over the locations to
        // add them in such a way where the order in both lists stays the same
        // iterating backwards so as to preserver order
        for (int i = locs.size() - 1; i >= 0; i--) {
            NamedLocation loc = locs.get(i);
            srt.getLocationProvider().setLocation(loc.getName(), loc.getLocation());
        }
        String msg = messages.getListMessage(locs);
        assertCommand(sender, "list", msg);
    }

    @Test
    public void listSpecifcConsoleMultiworld() {
        String worldName = getRandomName("wrold");
        MockWorld world1 = new MockWorld(worldName);

        MockWorld world2 = new MockWorld(getRandomName("world"));
        Location loc1 = getRandomLocation(world1);
        NamedLocation named1 = new NamedLocation(getRandomName("named"), loc1);
        Location loc2 = getRandomLocation(world2);
        NamedLocation named2 = new NamedLocation(getRandomName("named"), loc2);

        srt.getLocationProvider().setLocation(named1.getName(), loc1);
        srt.getLocationProvider().setLocation(named2.getName(), loc2);

        List<NamedLocation> locs = new ArrayList<>();
        locs.add(named1);

        // world1
        String msg = messages.getListMessage(locs);
        assertCommand(sender, "list " + worldName, msg);

        locs.clear();;
        locs.add(named2);

        // world 2
        msg = messages.getListMessage(locs);
        assertCommand(sender, "list " + world2.getName(), msg);
    }

    @Test
    public void listPlayerWorldTest() {
        String worldName = getRandomName("wrold");
        MockWorld world1 = new MockWorld(worldName);

        MockWorld world2 = new MockWorld(getRandomName("world"));
        Location loc1 = getRandomLocation(world1);
        NamedLocation named1 = new NamedLocation(getRandomName("named"), loc1);
        Location loc2 = getRandomLocation(world2);
        NamedLocation named2 = new NamedLocation(getRandomName("named"), loc2);

        srt.getLocationProvider().setLocation(named1.getName(), loc1);
        srt.getLocationProvider().setLocation(named2.getName(), loc2);

        List<NamedLocation> locs = new ArrayList<>();
        locs.add(named1);

        
        MockPlayer player = new MockPlayer(getRandomName("player"));
        player.setLocation(getRandomLocation(world1));

        // world1
        String msg = messages.getListMessage(locs);
        assertCommand(player, "list", msg);

        // world1
        msg = messages.getListMessage(locs);
        assertCommand(player, "list " + worldName, msg);
    }

    @Test
    public void playerTeleportList() {
        String worldName = getRandomName("wrold");
        MockWorld world1 = new MockWorld(worldName);

        MockWorld world2 = new MockWorld(getRandomName("world"));
        Location loc1 = getRandomLocation(world1);
        NamedLocation named1 = new NamedLocation(getRandomName("named"), loc1);
        Location loc2 = getRandomLocation(world2);
        NamedLocation named2 = new NamedLocation(getRandomName("named"), loc2);

        srt.getLocationProvider().setLocation(named1.getName(), loc1);
        srt.getLocationProvider().setLocation(named2.getName(), loc2);

        List<NamedLocation> locs = new ArrayList<>();
        locs.add(named1);

        
        MockPlayer player = new MockPlayer(getRandomName("player"));
        player.setLocation(getRandomLocation(world1));

        // world1
        String msg = messages.getListMessage(locs);
        assertCommand(player, "list", msg);

        // world1
        msg = messages.getListMessage(locs);
        assertCommand(player, "list " + worldName, msg);

        // teleport player
        player.setLocation(getRandomLocation(world2));
        locs.clear();;
        locs.add(named2);

        // world2
        msg = messages.getListMessage(locs);
        assertCommand(player, "list", msg);

        // world2
        msg = messages.getListMessage(locs);
        assertCommand(player, "list " + world2.getName(), msg);
    }

    private void assertCommand(MockCommandSender sender, String args, String expected) {
        sender.setExpectedMessage(expected);
        String[] argArray = args.split(" ");
        // fix "" providing an argument
        if (argArray.length == 1 && argArray[0].isEmpty())
            argArray = new String[] {};
        command.onCommand(sender, null, "srt", argArray); // assertion within

    }

}