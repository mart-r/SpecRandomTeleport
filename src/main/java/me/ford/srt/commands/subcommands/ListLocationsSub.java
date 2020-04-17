package me.ford.srt.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import me.ford.srt.commands.SubCommand;
import me.ford.srt.config.Messages;
import me.ford.srt.locations.AbstractLocationProvider;

public class ListLocationsSub extends SubCommand {
    private static final String USAGE = "/srt list>";
    private static final String PERMS = "srt.commands.list";
    private final AbstractLocationProvider provider;
    private final Messages messages;

    public ListLocationsSub(AbstractLocationProvider provider, Messages messages) {
        this.provider = provider;
        this.messages = messages;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        sender.sendMessage(messages.getListMessage(provider.getLocations()));
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