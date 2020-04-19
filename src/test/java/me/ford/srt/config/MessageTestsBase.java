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
    private final String[] randomWords = new String[] { "word", "world", "location", "random", "who", "end", "of",
            "the", "1337", "l33t", "working", "for", "test", "4test", "MC", "minecraft", "Spigot", "SpigotMC", "when",
            "life", "gives", "you", "lemons", "make", "lemonade", "A", "END", "OF", "_", "mc", "-", "OF", "OFF", "on",
            "ON", "THE", "WHO", "UN", "EU", "NA" };

    protected Location getRandomLocation(MockWorld world) {
        return new Location(world, random.nextDouble() * 1000, random.nextDouble() * 1000, random.nextDouble() * 1000,
                random.nextFloat() * 360, random.nextFloat() * 180 - 90);
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
                if (!assertContains(msg, ((Collection<?>) o).toArray()))
                    return false;
                continue; // next
            } else if (o instanceof Location) {
                Location loc = (Location) o;
                if (!assertContains(msg, loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(),
                        loc.getPitch()))
                    return false;
                continue;
            } else if (o instanceof NamedLocation) {
                NamedLocation loc = (NamedLocation) o;
                if (!assertContains(msg, loc.getName(), loc.getLocation()))
                    return false;
                continue;
            } else {
                expected = String.valueOf(o);
            }
            Assert.assertTrue(msg + " should contain " + o, msg.contains(expected));
        }
        return true;
    }

    protected String getRandomName() {
        return getRandomName(null);
    }

    protected String getRandomName(String start) {
        return getRandomName(start, 5);
    }

    protected String getRandomName(String start, int nr) {
        StringBuilder builder = new StringBuilder(start == null ? "" : start);
        int max = randomWords.length;
        for (int i = 0; i < nr; i++) {
            builder.append(randomWords[random.nextInt(max)]);
        }
        return builder.toString();
    }

}