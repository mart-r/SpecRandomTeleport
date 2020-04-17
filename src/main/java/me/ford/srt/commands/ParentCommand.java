package me.ford.srt.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;

import me.ford.srt.config.Messages;

/**
 * ParentCommand
 */
public abstract class ParentCommand implements TabExecutor {
    private final Map<String, SubCommand> subCommands = new LinkedHashMap<>();
    private final Messages messages;

    public ParentCommand(Messages messages) {
        this.messages = messages;
    }

    protected void addSubCommand(String name, SubCommand subCommand) {
        subCommands.put(name.toLowerCase(), subCommand);
    }

    protected boolean removeSubCommand(String name) {
        return subCommands.remove(name) != null;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            for (Entry<String, SubCommand> entry : subCommands.entrySet()) {
                if (entry.getValue().hasPermission(sender)) {
                    list.add(entry.getKey());
                }
            }
            return StringUtil.copyPartialMatches(args[0], list, new ArrayList<>());
        } else {
            SubCommand sub = subCommands.get(args[0]);
            if (sub == null || !sub.hasPermission(sender)) {
                return list;
            } else {
                return sub.onTabComplete(sender, command, alias, args);
            }
        }
    }

    private String getUsage(CommandSender sender) {
        List<String> msgs = new ArrayList<>();
        String header = getUsage();
        for (SubCommand cmd : subCommands.values()) {
            if (cmd.hasPermission(sender)) {
                for (String part : cmd.getUsage(sender, new String[] {}).split("\n")) {
                    msgs.add(part);
                }
            }
        }
        return header + "\n" + String.join("\n", msgs);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return noArgs(sender);
        }
        SubCommand cmd = subCommands.get(args[0]);
        if (cmd == null || !cmd.hasPermission(sender)) {
            if (!args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(messages.getUnrecognizedCommandMessage(args[0]));
                return true;
            }
            showUsage(sender);
            return true;
        }

        if (!cmd.onCommand(sender, command, label, args)) {
            sender.sendMessage(cmd.getUsage(sender, args));
        }
        return true;
    }

    private void showUsage(CommandSender sender) {
        sender.sendMessage(getUsage(sender));
    }

    protected boolean noArgs(CommandSender sender) { // can be overwritten
        showUsage(sender);
        return true;
    }

    protected abstract String getUsage();

}