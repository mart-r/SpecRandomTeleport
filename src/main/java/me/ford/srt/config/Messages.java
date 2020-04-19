package me.ford.srt.config;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import me.ford.srt.ISpecRandomTeleport;
import me.ford.srt.locations.NamedLocation;
import me.ford.srt.utils.FormatUtils;

public class Messages extends CustomConfigHandler {
    private static final String NAME = "messages.yml";

    public Messages(ISpecRandomTeleport srt) {
        super(srt, NAME);
    }

    protected Messages(ISpecRandomTeleport srt, String name) { // for testing purposes
        super(srt, name);
    }

    // general

    public String getUnrecognizedCommandMessage(String sub) {
        return getMessage("unrecognized-subcommand", "{sub} is not a recognized subcommand!").replace("{sub}", sub);
    }

    public String getNeedAPlayerMessage() {
        return getMessage("need-a-player", "Only a player can do this!");
    }

    public String getNoLocationsSetMessage() {
        return getMessage("no-locations-set",
                "There are no locations provided by the plugin! Set some locations or contact an admin for them to do so!");
    }

    // srt add

    public String getLocationExistsMessage(String name) {
        return getMessage("location-exists",
                "Location {name} already exists, use '/srp moveloc <name>' to move it here").replace("{name}", name);
    }

    public String getAddedLocationMessage(String name, Location loc) {
        return getLocationMessage("added-location",
                "Added location {name} in {world} at {x}, {y}, {z} (with {yaw}, {pitch})", loc).replace("{name}", name);
    }

    // srt remove

    public String getLocationDoesNotExistMessage(String name) {
        return getMessage("location-does-not-exist", "Location {name} does not exist").replace("{name}", name);
    }

    public String getRemovedLocationMessage(String name, Location loc) {
        return getLocationMessage("removed-location",
                "Removed location {name} from {world} at {x}, {y}, {z} (with {yaw}, {pitch})", loc).replace("{name}",
                        name);
    }

    // srt move

    public String getMovedLocationMessage(String name, Location loc) {
        return getLocationMessage("moved-location",
                "Moved location {name} to {world} at {x}, {y}, {z} (with {yaw}, {pitch})", loc).replace("{name}", name);
    }

    // srt list

    public String getListMessage(List<NamedLocation> locs) {
        StringBuilder builder = new StringBuilder(getListHeaderMessage(locs.size()));
        for (NamedLocation loc : locs) {
            builder.append("\n");
            builder.append(getListItemMessage(loc));
        }
        return builder.toString();
    }

    public String getListHeaderMessage(int amount) {
        return getMessage("list-header", "All locations ({amount}):").replace("{amount}", String.valueOf(amount));
    }

    public String getListItemMessage(String name, Location loc) {
        return getLocationMessage("list-item", "{name} in {world} at {x}, {y}, {z} (with {yaw}, {pitch})", loc)
                .replace("{name}", name);
    }

    public String getListItemMessage(NamedLocation loc) {
        return getListItemMessage(loc.getName(), loc.getLocation());
    }

    // srt use

    public String getTeleportingMessage(NamedLocation loc) {
        return getTeleportingMessage(loc.getName(), loc.getLocation());
    }

    public String getTeleportingMessage(String name, Location loc) {
        return getLocationMessage("teleporting",
                "Teleporting to random location {name} in {world} at {x}, {y}, {z} (with {yaw}, {pitch})", loc)
                        .replace("{name}", name);
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

    public String getStartedWaitingDelActivateMessage() {
        return getMessage("need-to-click-remove-activation", "Click on a block to remove it as an activation block");
    }

    public String getNotAnActivationBlockMessage() {
        return getMessage("not-an-activation-block", "This is not an activation block");
    }

    public String getDeletedActivationBlockMessage() {
        return getMessage("deleted-activation-block", "This block was removed as an activation block!");
    }

    public String getActivationListMessage(List<Location> locations) {
        StringBuilder builder = new StringBuilder(getListHeaderMessage(locations.size()));
        for (Location loc : locations) {
            builder.append("\n");
            builder.append(getActivationListItemMessage(loc));
        }
        return builder.toString();
    }

    public String getActivationListHeaderMessage(int amount) {
        return getMessage("activation-list-header", "All activations ({amount}):").replace("{amount}",
                String.valueOf(amount));
    }

    public String getActivationListItemMessage(Location loc) {
        return getLocationMessage("activation-list-item", "{world} at {x}, {y}, {z} (with {yaw}, {pitch})", loc);
    }

    public String getLocationMessage(String path, String def, Location loc) {
        return getMessage(path, def).replace("{world}", loc.getWorld().getName())
                .replace("{x}", FormatUtils.formatDouble(loc.getX()))
                .replace("{y}", FormatUtils.formatDouble(loc.getY()))
                .replace("{z}", FormatUtils.formatDouble(loc.getZ()))
                .replace("{yaw}", FormatUtils.formatDouble(loc.getYaw()))
                .replace("{pitch}", FormatUtils.formatDouble(loc.getPitch()));
    }

    public String getMessage(String path, String def) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString(path, def));
    }

}