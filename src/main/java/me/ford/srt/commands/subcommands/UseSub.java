package me.ford.srt.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ford.srt.commands.SubCommand;
import me.ford.srt.config.Messages;
import me.ford.srt.locations.NamedLocation;
import me.ford.srt.locations.perworld.ComplexLocationProvider;

public class UseSub extends SubCommand {
    private static final String USAGE = "/srt use";
    private static final String PERMS = "srt.commands.use";
    private final ComplexLocationProvider provider;
    private final Messages messages;

    public UseSub(ComplexLocationProvider provider, Messages messages) {
        this.provider = provider;
        this.messages = messages;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messages.getNeedAPlayerMessage());
            return true;
        }
        Player player = (Player) sender;
        NamedLocation nLoc = provider.getRandomLocation(player.getWorld());
        if (nLoc == null) {
            sender.sendMessage(messages.getNoLocationsSetMessage());
            return true;
        }
        Location loc = nLoc.getLocation();
        sender.sendMessage(messages.getTeleportingMessage(nLoc));
        player.teleport(loc);
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