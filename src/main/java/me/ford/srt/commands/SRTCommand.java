package me.ford.srt.commands;

import me.ford.srt.ISpecRandomTeleport;
import me.ford.srt.commands.subcommands.AddActivationSub;
import me.ford.srt.commands.subcommands.AddLocationSub;
import me.ford.srt.commands.subcommands.ListActivationsSub;
import me.ford.srt.commands.subcommands.ListLocationsSub;
import me.ford.srt.commands.subcommands.MoveLocationSub;
import me.ford.srt.commands.subcommands.RemoveActivationSub;
import me.ford.srt.commands.subcommands.RemoveLocationSub;
import me.ford.srt.commands.subcommands.UseSub;

public class SRTCommand extends ParentCommand {
    private static final String USAGE = "/srt <subcommand> <args>";

    public SRTCommand(ISpecRandomTeleport srt) {
        super(srt.getMessages());
        addSubCommand("addloc", new AddLocationSub(srt.getLocationProvider(), srt.getMessages()));
        addSubCommand("removeloc", new RemoveLocationSub(srt.getLocationProvider(), srt.getMessages()));
        addSubCommand("moveloc", new MoveLocationSub(srt.getLocationProvider(), srt.getMessages()));
        addSubCommand("list", new ListLocationsSub(srt.getSettings(), srt.getLocationProvider(), srt.getMessages()));
        addSubCommand("use", new UseSub(srt.getLocationProvider(), srt.getMessages()));
        addSubCommand("addactviation", new AddActivationSub(srt.getActivationLocationProvider(), srt.getMessages()));
        addSubCommand("removeactivation",
                new RemoveActivationSub(srt.getActivationLocationProvider(), srt.getMessages()));
        addSubCommand("listactivators", new ListActivationsSub(srt.getActivationLocationProvider(), srt.getMessages()));
    }

    @Override
    protected String getUsage() {
        return USAGE;
    }

}