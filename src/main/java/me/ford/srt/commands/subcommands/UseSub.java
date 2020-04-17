package me.ford.srt.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ford.srt.commands.SubCommand;
import me.ford.srt.config.Messages;
import me.ford.srt.locations.AbstractLocationProvider;

public class UseSub extends SubCommand {
    private static final String USAGE = "/srt use";
    private static final String PERMS = "srt.commands.use";
    private final AbstractLocationProvider provider;
    private final Messages messages;

    public UseSub(AbstractLocationProvider provider, Messages messages) {
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
        String locName = provider.getRandomLocationName();
        if (locName == null) {
            String msg = "There are not locations provided by the plugin! Set some locations or contact an admin for them to do so!";
            sender.sendMessage(msg);
            return true;
        }
        Location loc = provider.getLocation(locName);
        sender.sendMessage(messages.getTeleportingMessage(locName, loc));
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