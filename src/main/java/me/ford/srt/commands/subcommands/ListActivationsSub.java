package me.ford.srt.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import me.ford.srt.commands.SubCommand;
import me.ford.srt.config.Messages;
import me.ford.srt.locations.ActivationLocationProvider;

public class ListActivationsSub extends SubCommand {
    private static final String USAGE = "/srt listactivators";
    private static final String PERMS = "srt.commands.listactivators";
    private final ActivationLocationProvider provider;
    private final Messages messages;

    public ListActivationsSub(ActivationLocationProvider provider, Messages messages) {
        this.provider = provider;
        this.messages = messages;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        sender.sendMessage(messages.getActivationListMessage(provider.getAllLocations()));
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