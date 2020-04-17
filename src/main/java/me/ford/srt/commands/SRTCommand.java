package me.ford.srt.commands;

import me.ford.srt.SpecRandomTeleport;
import me.ford.srt.commands.subcommands.AddLocationSub;
import me.ford.srt.commands.subcommands.MoveLocationSub;
import me.ford.srt.commands.subcommands.RemoveLocationSub;

public class SRTCommand extends ParentCommand {
    private static final String USAGE = "/srt <subcommand> <args>";

    public SRTCommand(SpecRandomTeleport srt) {
        super(srt.getMessages());
        addSubCommand("addloc", new AddLocationSub(srt.getLocationProvider(), srt.getMessages()));
        addSubCommand("removeloc", new RemoveLocationSub(srt.getLocationProvider(), srt.getMessages()));
        addSubCommand("moveloc", new MoveLocationSub(srt.getLocationProvider(), srt.getMessages()));
    }

    @Override
    protected String getUsage() {
        return USAGE;
    }

}