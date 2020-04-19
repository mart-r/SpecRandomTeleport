package me.ford.srt.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import me.ford.srt.commands.SubCommand;
import me.ford.srt.config.Messages;
import me.ford.srt.locations.perworld.ComplexLocationProvider;

public class RemoveLocationSub extends SubCommand {
    private static final String USAGE = "/srt removeloc <name>";
    private static final String PERMS = "srt.commands.removeloc";
    private final ComplexLocationProvider provider;
    private final Messages messages;

    public RemoveLocationSub(ComplexLocationProvider provider, Messages messages) {
        this.provider = provider;
        this.messages = messages;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> list = new ArrayList<>();
        World world = null;
        if (sender instanceof Player) {
            world = ((Player) sender).getWorld();
        }
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], provider.getNames(world), list);
        }
        return list;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length < 1) {
            return false;
        }

        String locName = args[0];
        // check if name already exists
        if (!provider.hasLocation(locName)) {
            sender.sendMessage(messages.getLocationDoesNotExistMessage(locName));
            return true;
        }

        World world = null;
        if (sender instanceof Player) {
            world = ((Player) sender).getWorld();
        }

        // remove
        Location loc = provider.removeLocation(world, locName);
        sender.sendMessage(messages.getRemovedLocationMessage(locName, loc));
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