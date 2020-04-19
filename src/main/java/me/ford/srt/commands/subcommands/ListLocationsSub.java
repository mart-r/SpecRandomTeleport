package me.ford.srt.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ford.srt.commands.SubCommand;
import me.ford.srt.config.Messages;
import me.ford.srt.config.Settings;
import me.ford.srt.locations.perworld.ComplexLocationProvider;

public class ListLocationsSub extends SubCommand {
    private static final String USAGE = "/srt list";
    private static final String PERMS = "srt.commands.list";
    private final Settings settings;
    private final ComplexLocationProvider provider;
    private final Messages messages;

    public ListLocationsSub(Settings settings, ComplexLocationProvider provider, Messages messages) {
        this.settings = settings;
        this.provider = provider;
        this.messages = messages;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        World world = null;
        if (sender instanceof Player) {
            world = ((Player) sender).getWorld();
        }
        if (world == null && settings.usePerWorld()) {
            sender.sendMessage(messages.getNoWorldMessage());
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