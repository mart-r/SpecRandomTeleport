package me.ford.srt.config;

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

    public String getMessage(String path, String def) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString(path, def));
    }

}