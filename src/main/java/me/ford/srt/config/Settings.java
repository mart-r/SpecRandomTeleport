package me.ford.srt.config;

import me.ford.srt.SpecRandomTeleport;

public class Settings {
    private final SpecRandomTeleport srt;

    public Settings(SpecRandomTeleport srt) {
        this.srt = srt;
    }

    public boolean isDebugEnabled() {
        return srt.getConfig().getBoolean("debug", false);
    }

}