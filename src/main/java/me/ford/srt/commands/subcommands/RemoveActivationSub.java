package me.ford.srt.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ford.srt.commands.SubCommand;
import me.ford.srt.config.Messages;
import me.ford.srt.locations.ActivationLocationProvider;

public class RemoveActivationSub extends SubCommand {
    private static final String USAGE = "/srt removeactivation";
    private static final String PERMS = "srt.commands.removeactivation";
    private final ActivationLocationProvider provider;
    private final Messages messages;

    public RemoveActivationSub(ActivationLocationProvider provider, Messages messages) {
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

        // start waiting
        provider.getListener().addWaitingToDelActivate(player);
        sender.sendMessage(messages.getStartedWaitingDelActivateMessage());
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