package me.ford.srt.config;

import me.ford.srt.ISpecRandomTeleport;

public class Settings {
    private final ISpecRandomTeleport srt;

    public Settings(ISpecRandomTeleport srt) {
        this.srt = srt;
    }

    public boolean isDebugEnabled() {
        return srt.getConfig().getBoolean("debug", false);
    }

}