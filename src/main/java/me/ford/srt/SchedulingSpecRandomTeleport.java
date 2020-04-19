package me.ford.srt;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public abstract class SchedulingSpecRandomTeleport extends JavaPlugin implements ISpecRandomTeleport {

    @Override
    public BukkitTask runTask(Runnable runnable) {
        return getServer().getScheduler().runTask(this, runnable);
    }

    @Override
    public BukkitTask runTaskLater(Runnable runnable, long delay) {
        return getServer().getScheduler().runTaskLater(this, runnable, delay);
    }

    @Override
    public BukkitTask runTaskTimer(Runnable runnable, long delay, long period) {
        return getServer().getScheduler().runTaskTimer(this, runnable, delay, period);
    }

    @Override
    public BukkitTask runTaskAsynchronously(Runnable runnable) {
        return getServer().getScheduler().runTaskAsynchronously(this, runnable);
    }

    @Override
    public BukkitTask runTaskLaterAsynchronously(Runnable runnable, long delay) {
        return getServer().getScheduler().runTaskLaterAsynchronously(this, runnable, delay);
    }

    @Override
    public BukkitTask runTaskTimerAsynchronously(Runnable runnable, long delay, long period) {
        return getServer().getScheduler().runTaskTimerAsynchronously(this, runnable, delay, period);
    }

}