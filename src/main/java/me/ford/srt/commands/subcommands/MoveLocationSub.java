package me.ford.srt.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ford.srt.commands.SubCommand;
import me.ford.srt.config.Messages;
import me.ford.srt.locations.LocationProvider;

public class MoveLocationSub extends SubCommand {
    private static final String USAGE = "/srt addloc <name>";
    private static final String PERMS = "srt.commands.addloc";
    private final LocationProvider provider;
    private final Messages messages;

    public MoveLocationSub(LocationProvider provider, Messages messages) {
        this.provider = provider;
        this.messages = messages;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length < 1) {
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(messages.getNeedAPlayerMessage());
            return true;
        }
        Player player = (Player) sender;

        String locName = args[0];
        // check if name already exists
        if (!provider.hasLocation(locName)) {
            player.sendMessage(messages.getLocationDoesNotExistMessage(locName));
            return true;
        }

        // move
        Location loc = player.getLocation();
        provider.addLocation(locName, loc);
        sender.sendMessage(messages.getMovedLocationMessage(locName, loc));
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(PERMS);
    }

    @Override
    public String getUsage(CommandSender sender, String[] args) {
        return USAGE;
    }

}