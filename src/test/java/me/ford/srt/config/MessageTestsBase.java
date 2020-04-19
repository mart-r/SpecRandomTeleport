package me.ford.srt.config;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.junit.Assert;

import me.ford.srt.locations.NamedLocation;
import me.ford.srt.mock.MockWorld;
import me.ford.srt.utils.FormatUtils;

public abstract class MessageTestsBase {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    protected Location getRandomLocation(MockWorld world) {
        return new Location(world, random.nextDouble()*1000, random.nextDouble() * 1000, random.nextDouble() * 1000, random.nextFloat() * 360, random.nextFloat()*180 - 90);
    }

    protected boolean hasPlaceHolder(String msg) {
        return msg.contains("{") || msg.contains("}");
    }

    protected boolean assertContains(String msg, Object... parts) {
        for (Object o : parts) {
            String expected;
            if (o instanceof Double) {
                expected = FormatUtils.formatDouble((double) o);
            } else if (o instanceof Float) {
                    expected = FormatUtils.formatDouble((float) o);
            } else if (o instanceof Collection) { // in case of collection, check recursively
                if (!assertContains(msg, ((Collection<?>) o).toArray())) return false;
                continue; // next
            } else if (o instanceof Location) {
                Location loc = (Location) o;
                if (!assertContains(msg, loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch())) return false;
                continue;
            } else if (o instanceof NamedLocation) {
                NamedLocation loc = (NamedLocation) o;
                if (!assertContains(msg, loc.getName() ,loc.getLocation())) return false;
                continue;
            } else {
                expected = String.valueOf(o);
            }
            Assert.assertTrue(msg + " should contain " + o, msg.contains(expected));
        }
        return true;
    }

}