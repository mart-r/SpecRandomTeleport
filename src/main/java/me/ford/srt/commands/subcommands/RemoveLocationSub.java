package me.ford.srt.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import me.ford.srt.commands.SubCommand;
import me.ford.srt.config.Messages;
import me.ford.srt.locations.AbstractLocationProvider;

public class RemoveLocationSub extends SubCommand {
    private static final String USAGE = "/srt removeloc <name>";
    private static final String PERMS = "srt.commands.removeloc";
    private final AbstractLocationProvider provider;
    private final Messages messages;

    public RemoveLocationSub(AbstractLocationProvider provider, Messages messages) {
        this.provider = provider;
        this.messages = messages;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], provider.getNames(), list);
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

        // remove
        Location loc = provider.removeLocation(locName);
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