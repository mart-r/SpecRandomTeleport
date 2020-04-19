package me.ford.srt.locations;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import me.ford.srt.ISpecRandomTeleport;

public class ActivationListener implements Listener {
    private final Set<UUID> activateNext = new HashSet<>();
    private final Set<UUID> deleteNext = new HashSet<>();
    private final ISpecRandomTeleport srt;
    private ActivationLocationProvider activationProvider = null;
    private LocationProvider locationProvider = null;

    public ActivationListener(ISpecRandomTeleport srt) {
        this.srt = srt;
        srt.runTask(()-> lazyInit());
    }

    public void addWaitingToActivate(Player player) {
        activateNext.add(player.getUniqueId());
    }

    public void addWaitingToDelActivate(Player player) {
        deleteNext.add(player.getUniqueId());
    }
    
    private void lazyInit() {
        activationProvider = srt.getActivationLocationProvider();
        locationProvider = srt.getLocationProvider();
    }
    
    @EventHandler
    public void onSetActivation(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        Block block = event.getClickedBlock();
        if (event.getClickedBlock() == null) return;
        Player player = event.getPlayer();
        if (!activateNext.contains(player.getUniqueId())) return;
        activateNext.remove(player.getUniqueId()); // removing here in case there's nothing to activate nearby
        try {
            activationProvider.markAsActivationLocation(block.getLocation());
        } catch (IllegalArgumentException e) {
            player.sendMessage(srt.getMessages().getUnsuitableBlockMessage());
            return;
        }
        player.sendMessage(srt.getMessages().getMarkedAsActivationMessage());
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onDelActivation(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        Block block = event.getClickedBlock();
        if (event.getClickedBlock() == null) return;
        Player player = event.getPlayer();
        if (!deleteNext.contains(player.getUniqueId())) return;
        deleteNext.remove(player.getUniqueId()); // remove before in case there's nothing to actually delete
        if (!activationProvider.isActivationLocation(block.getLocation())) {
            player.sendMessage(srt.getMessages().getNotAnActivationBlockMessage());
            return;
        }
        activationProvider.removeAsActivationLocation(block.getLocation());
        player.sendMessage(srt.getMessages().getDeletedActivationBlockMessage());
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onAcitvate(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        Block block = event.getClickedBlock();
        if (event.getClickedBlock() == null) return;
        if (!activationProvider.isActivationLocation(block.getLocation())) return;
        NamedLocation nLoc = locationProvider.getRandomLocation();
        Location loc = nLoc.getLocation();
        Player player = event.getPlayer();
        String msg = srt.getMessages().getTeleportingMessage(nLoc);
        if (!msg.isEmpty()) player.sendMessage(msg);
        player.teleport(loc);
    }

}