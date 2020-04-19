package me.ford.srt.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import me.ford.srt.commands.SubCommand;
import me.ford.srt.config.Messages;
import me.ford.srt.locations.perworld.ComplexLocationProvider;

public class ListLocationsSub extends SubCommand {
    private static final String USAGE = "/srt list [world]";
    private static final String PERMS = "srt.commands.list";
    private final ComplexLocationProvider provider;
    private final Messages messages;

    public ListLocationsSub(ComplexLocationProvider provider, Messages messages) {
        this.provider = provider;
        this.messages = messages;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            for (World world : Bukkit.getWorlds()) {
                list.add(world.getName());
            }
            return StringUtil.copyPartialMatches(args[0], list, new ArrayList<>());
        }
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        World world = null;
        if (sender instanceof Player) {
            world = ((Player) sender).getWorld();
        }
        if (args.length > 0) {
            World reqWorld = Bukkit.getWorld(args[0]);
            if (reqWorld != null) {
                world = reqWorld;
            }
        }
        sender.sendMessage(messages.getListMessage(provider.getLocations(world)));
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