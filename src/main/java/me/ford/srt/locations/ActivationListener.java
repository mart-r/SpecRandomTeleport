package me.ford.srt.locations;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import me.ford.srt.ISpecRandomTeleport;
import me.ford.srt.locations.perworld.ComplexLocationProvider;

public class ActivationListener implements Listener {
    private final Set<UUID> activateNext = new HashSet<>();
    private final Set<UUID> deleteNext = new HashSet<>();
    private final ISpecRandomTeleport srt;
    private ActivationLocationProvider activationProvider = null;
    private ComplexLocationProvider locationProvider = null;

    public ActivationListener(ISpecRandomTeleport srt) {
        this.srt = srt;
        srt.runTask(() -> lazyInit());
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

    @EventHandler(priority = EventPriority.LOW) // make this run before activation
    public void onSetActivation(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND)
            return;
        Block block = event.getClickedBlock();
        if (event.getClickedBlock() == null)
            return;
        Player player = event.getPlayer();
        if (!activateNext.contains(player.getUniqueId()))
            return;
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

    @EventHandler(priority = EventPriority.LOW) // make this run before activation
    public void onDelActivation(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND)
            return;
        Block block = event.getClickedBlock();
        if (event.getClickedBlock() == null)
            return;
        Player player = event.getPlayer();
        if (!deleteNext.contains(player.getUniqueId()))
            return;
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
        if (event.getHand() == EquipmentSlot.OFF_HAND)
            return;
        Block block = event.getClickedBlock();
        if (event.getClickedBlock() == null)
            return;
        if (!activationProvider.isActivationLocation(block.getLocation()))
            return;
        Player player = event.getPlayer();
        NamedLocation nLoc = locationProvider.getRandomLocation(player.getWorld());
        if (nLoc == null) {
            player.sendMessage(srt.getMessages().getNoLocationsSetMessage());
            return;
        }
        Location loc = nLoc.getLocation();
        String msg = srt.getMessages().getTeleportingMessage(nLoc);
        if (!msg.isEmpty())
            player.sendMessage(msg);
        player.teleport(loc);
        event.setCancelled(true);
    }

}