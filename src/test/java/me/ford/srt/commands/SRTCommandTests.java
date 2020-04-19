package me.ford.srt.commands;

import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ford.srt.config.Messages;
import me.ford.srt.locations.LocationProvider;
import me.ford.srt.locations.NamedLocation;
import me.ford.srt.mock.MockCommandSender;
import me.ford.srt.mock.MockPlayer;
import me.ford.srt.mock.MockSpecRandomTeleport;
import me.ford.srt.mock.MockWorld;

public class SRTCommandTests {
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
        String nonExistentSubCommand = "RANDOMTEXT";
        assertCommand(sender, nonExistentSubCommand, messages.getUnrecognizedCommandMessage(nonExistentSubCommand));
    }

    @Test
    public void srtAddLocSenderTests() {
        assertCommand(sender, "addloc", command.getSubCommand("addloc").getUsage(sender, new String[] {}));
        assertCommand(sender, "addloc SomeName", messages.getNeedAPlayerMessage());
    }

    @Test
    public void srtAddLocPlayerTests() {
        MockPlayer player = new MockPlayer("playerName4321");
        assertCommand(player, "addloc", command.getSubCommand("addloc").getUsage(player, new String[] {}));

        String locName = "randomLocN4me";
        String worldName = "rand0mW0rlnam3";
        Location loc = new Location(new MockWorld(worldName), 33.1, 55.1, -555.11);
        player.setLocation(loc);
        assertCommand(player, "addloc " + locName, messages.getAddedLocationMessage(locName, loc));

        LocationProvider locProv = srt.getLocationProvider();

        Assert.assertEquals(locProv.getLocation(locName), loc);
        Assert.assertEquals(locProv.getLocations().get(locName), new NamedLocation(locName, loc));
        Assert.assertTrue(locProv.getLocations().containsValue(new NamedLocation(locName, loc)));
        Assert.assertTrue(locProv.getLocations().size() == 1);
        Assert.assertEquals(locProv.getRandomLocationName(), locName);
        Assert.assertEquals(locProv.getLocation(locName), loc);
        Assert.assertEquals(locProv.getLocation(locProv.getRandomLocationName()), loc);
    }

    @Test
    public void srtRemoveLocSenderTests() {
        assertCommand(sender, "removeloc", command.getSubCommand("removeloc").getUsage(sender, new String[] {}));
        String nonExistent = "nonExistentLoc";
        assertCommand(sender, "removeloc " + nonExistent, messages.getLocationDoesNotExistMessage(nonExistent));

        String locName = "randomLocN4me";
        String worldName = "rand0mW0rlnam3";
        Location loc = new Location(new MockWorld(worldName), 33.1, 55.1, -555.11);
        LocationProvider locProv = srt.getLocationProvider();
        locProv.setLocation(locName, loc);

        assertCommand(sender, "removeloc " + nonExistent, messages.getLocationDoesNotExistMessage(nonExistent));

        Assert.assertTrue(locProv.getLocations().size() != 0);
        // real deal
        assertCommand(sender, "removeloc " + locName, messages.getRemovedLocationMessage(locName, loc));

        Assert.assertTrue(locProv.getLocations().size() == 0);
    }

    private void assertCommand(MockCommandSender sender, String args, String expected) {
        sender.setExpectedMessage(expected);
        String[] argArray = args.split(" ");
        // fix "" providing an argument
        if (argArray.length == 1 && argArray[0].isEmpty()) argArray = new String[] {};
        command.onCommand(sender, null, "srt", argArray); // assertion within

    }

}