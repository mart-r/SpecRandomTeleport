package me.ford.srt.config;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import me.ford.srt.SpecRandomTeleport;

public class Messages extends CustomConfigHandler {
    private static final String NAME = "messages.yml";

    public Messages(SpecRandomTeleport srt) {
        super(srt, NAME);
    }

    // general

	public String getUnrecognizedCommandMessage(String sub) {
        return getMessage("unrecognized-subcommand", "{sub} is not a recognized subcommand!")
                    .replace("{sub}", sub);
	}

    public String getNeedAPlayerMessage() {
        return getMessage("need-a-player", "Only a player can do this!");
    }

    // srt add

    public String getLocationExistsMessage(String name) {
        return getMessage("location-exists", "Location {name} already exists, use '/srp moveloc <name>' to move it here")
                    .replace("{name}", name);
    }

    public String getAddedLocationMessage(String name, Location loc) {
        return getMessage("added-location", "Added location {name} in {world} at {x}, {y}, {z} (with {yaw}, {pitch})")
                    .replace("{name}", name).replace("{world}", loc.getWorld().getName())
                    .replace("{x}", String.format("%5.2f", loc.getX())).replace("{y}", String.format("%5.2f", loc.getY()))
                    .replace("{z}", String.format("%5.2f", loc.getZ()))
                    .replace("{yaw}", String.format("%5.2f", loc.getYaw())).replace("{pitch}", String.format("%5.2f", loc.getPitch()));
    }

    // srt remove

    public String getLocationDoesNotExistMessage(String name) {
        return getMessage("location-does-not-exist", "Location {name} does not exist")
                    .replace("{name}", name);
    }

    public String getRemovedLocationMessage(String name, Location loc) {
        return getMessage("removed-location", "Removed location {name} from {world} at {x}, {y}, {z} (with {yaw}, {pitch})")
                    .replace("{name}", name).replace("{world}", loc.getWorld().getName())
                    .replace("{x}", String.format("%5.2f", loc.getX())).replace("{y}", String.format("%5.2f", loc.getY()))
                    .replace("{z}", String.format("%5.2f", loc.getZ()))
                    .replace("{yaw}", String.format("%5.2f", loc.getYaw())).replace("{pitch}", String.format("%5.2f", loc.getPitch()));
    }

    // srt move

    public String getMovedLocationMessage(String name, Location loc) {
        return getMessage("moved-location", "Moved location {name} to {world} at {x}, {y}, {z} (with {yaw}, {pitch})")
                    .replace("{name}", name).replace("{world}", loc.getWorld().getName())
                    .replace("{x}", String.format("%5.2f", loc.getX())).replace("{y}", String.format("%5.2f", loc.getY()))
                    .replace("{z}", String.format("%5.2f", loc.getZ()))
                    .replace("{yaw}", String.format("%5.2f", loc.getYaw())).replace("{pitch}", String.format("%5.2f", loc.getPitch()));
    }

    // srt list

    public String getListMessage(Map<String, Location> locs) {
        StringBuilder builder = new StringBuilder(getListHeaderMessage(locs.size()));
        for (Entry<String, Location> entry : locs.entrySet()) {
            builder.append("\n");
            builder.append(getListItemMessage(entry.getKey(), entry.getValue()));
        }
        return builder.toString();
    }

    public String getListHeaderMessage(int amount) {
        return getMessage("list-header", "All locations ({amount}):").replace("{amount}", String.valueOf(amount));
    }

    public String getListItemMessage(String name, Location loc) {
        return getMessage("list-item", "{name}  in {world} at {x}, {y}, {z} (with {yaw}, {pitch})")
                    .replace("{name}", name).replace("{world}", loc.getWorld().getName())
                    .replace("{x}", String.format("%5.2f", loc.getX())).replace("{y}", String.format("%5.2f", loc.getY()))
                    .replace("{z}", String.format("%5.2f", loc.getZ()))
                    .replace("{yaw}", String.format("%5.2f", loc.getYaw())).replace("{pitch}", String.format("%5.2f", loc.getPitch()));
    }

    // srt use

    public String getTeleportingMessage(String name, Location loc) {
        return getMessage("teleporting", "Teleporting to random location {name} in {world} at {x}, {y}, {z} (with {yaw}, {pitch})")
                    .replace("{name}", name).replace("{world}", loc.getWorld().getName())
                    .replace("{x}", String.format("%5.2f", loc.getX())).replace("{y}", String.format("%5.2f", loc.getY()))
                    .replace("{z}", String.format("%5.2f", loc.getZ()))
                    .replace("{yaw}", String.format("%5.2f", loc.getYaw())).replace("{pitch}", String.format("%5.2f", loc.getPitch()));
    }

    // actvation

    public String getStartedWaitingMessage() {
        return getMessage("need-to-click-activation", "Click on a block to mark it as an activation block");
    }

    public String getUnsuitableBlockMessage() {
        return getMessage("unsuitable-block", "This block is unsuitable for activation");
    }

    public String getMarkedAsActivationMessage() {
        return getMessage("marked-as-activation-block", "This block was marked as an activation block!");
    }

    public String getMessage(String path, String def) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString(path, def));
    }

    public String getStartedWaitingDelActivateMessage() {
        return getMessage("need-to-click-remove-activation", "Click on a block to remove it as an activation block");
    }

    public String getNotAnActivationBlockMessage() {
        return getMessage("not-an-activation-block", "This is not an activation block");
    }

    public String getDeletedActivationBlockMessage() {
        return getMessage("deleted-activation-block", "This block was removed as an activation block!");
    }

}