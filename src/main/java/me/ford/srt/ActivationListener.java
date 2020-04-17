package me.ford.srt;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import me.ford.srt.locations.ActivationLocationProvider;
import me.ford.srt.locations.LocationProvider;

public class ActivationListener implements Listener {
    private final Set<UUID> nextInteract = new HashSet<>();
    private final SpecRandomTeleport srt;
    private ActivationLocationProvider activationProvider = null;
    private LocationProvider locationProvider = null;

    public ActivationListener(SpecRandomTeleport srt) {
        this.srt = srt;
        srt.getServer().getScheduler().runTaskAsynchronously(srt, ()-> lazyInit());
    }

    public void addWaitingToActivate(Player player) {
        nextInteract.add(player.getUniqueId());
    }
    
    private void lazyInit() {
        activationProvider = srt.getActivationLocationProvider();
        locationProvider = srt.getLocationProvider();
    }
    
    @EventHandler
    public void onSetActivation(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (event.getClickedBlock() == null) return;
        Player player = event.getPlayer();
        if (!nextInteract.contains(player.getUniqueId())) return;
        try {
            activationProvider.markAsActivationLocation(block.getLocation());
        } catch (IllegalArgumentException e) {
            player.sendMessage(srt.getMessages().getUnsuitableBlockMessage());
            return;
        }
        nextInteract.remove(player.getUniqueId());
        player.sendMessage(srt.getMessages().getMarkedAsActivationMessage());
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onAcitvate(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (event.getClickedBlock() == null) return;
        if (!activationProvider.isActivationLocation(block.getLocation())) return;
        String locName = locationProvider.getRandomLocationName();
        Location loc = locationProvider.getLocation(locName);
        Player player = event.getPlayer();
        player.sendMessage(srt.getMessages().getTeleportingMessage(locName, loc));
        player.teleport(loc);
    }

}