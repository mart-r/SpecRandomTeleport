package me.ford.srt.mock;

import org.bukkit.scheduler.BukkitTask;

import me.ford.srt.CustomScheduler;

public class MockScheduler implements CustomScheduler {

    @Override
    public BukkitTask runTask(Runnable runnable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BukkitTask runTaskLater(Runnable runnable, long delay) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BukkitTask runTaskTimer(Runnable runnable, long delay, long period) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BukkitTask runTaskAsynchronously(Runnable runnable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BukkitTask runTaskLaterAsynchronously(Runnable runnable, long delay) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BukkitTask runTaskTimerAsynchronously(Runnable runnable, long delay, long period) {
        // TODO Auto-generated method stub
        return null;
    }

}