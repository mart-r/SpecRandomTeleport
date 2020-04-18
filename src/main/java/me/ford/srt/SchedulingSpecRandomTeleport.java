package me.ford.srt;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class SchedulingSpecRandomTeleport extends JavaPlugin implements ISpecRandomTeleport {

    public void runTask(Runnable runnable) {
        getServer().getScheduler().runTask(this, runnable);
    }

    public void runTaskLater(Runnable runnable, long delay) {
        getServer().getScheduler().runTaskLater(this, runnable, delay);
    }

    public void runTaskTimer(Runnable runnable, long delay, long period) {
        getServer().getScheduler().runTaskTimer(this, runnable, delay, period);
    }

    public void runTaskAsynchronously(Runnable runnable) {
        getServer().getScheduler().runTaskAsynchronously(this, runnable);
    }

    public void runTaskLaterAsynchronously(Runnable runnable, long delay) {
        getServer().getScheduler().runTaskLaterAsynchronously(this, runnable, delay);
    }

    public void runTaskTimerAsynchronously(Runnable runnable, long delay, long period) {
        getServer().getScheduler().runTaskTimerAsynchronously(this, runnable, delay, period);
    }

}