package me.ford.srt;

import org.bukkit.scheduler.BukkitTask;

public interface CustomScheduler {

    public BukkitTask runTask(Runnable runnable);

    public BukkitTask runTaskLater(Runnable runnable, long delay);

    public BukkitTask runTaskTimer(Runnable runnable, long delay, long period);

    public BukkitTask runTaskAsynchronously(Runnable runnable);

    public BukkitTask runTaskLaterAsynchronously(Runnable runnable, long delay);

    public BukkitTask runTaskTimerAsynchronously(Runnable runnable, long delay, long period);

}