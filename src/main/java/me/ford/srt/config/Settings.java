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

    public boolean useMetrics() {
        return srt.getConfig().getBoolean("use-metrics", true);
    }

    public boolean usePerWorld() {
        return srt.getConfig().getBoolean("use-per-world-locations", true);
    }

}