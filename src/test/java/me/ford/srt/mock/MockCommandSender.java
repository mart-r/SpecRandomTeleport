package me.ford.srt.mock;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.junit.Assert;

public class MockCommandSender implements CommandSender {
    private final Set<String> permissions;
    private final String name;
    private String expectedMessage;

    public MockCommandSender(String name) {
        this(name, null);
    }

    public MockCommandSender(String name, Set<String> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public void setExpectedMessage(String msg) {
        expectedMessage = msg;
    }

    @Override
    public boolean isPermissionSet(String name) {
        return true;
    }

    @Override
    public boolean isPermissionSet(Permission perm) {
        return true;
    }

    @Override
    public boolean hasPermission(String name) {
        if (permissions == null) return true;
        return permissions.contains(name);
    }

    @Override
    public boolean hasPermission(Permission perm) {
        return true;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value,
            int ticks) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        // TODO Auto-generated method stub

    }

    @Override
    public void recalculatePermissions() {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public void setOp(boolean value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendMessage(String message) {
        Assert.assertEquals(expectedMessage, message);
    }

    @Override
    public void sendMessage(String[] messages) {
        for (String msg : messages) {
            sendMessage(msg);
        }
    }

    @Override
    public Server getServer() {
        return Bukkit.getServer(); // assuming mocked server
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Spigot spigot() {
        // TODO Auto-generated method stub
        return null;
    }

}