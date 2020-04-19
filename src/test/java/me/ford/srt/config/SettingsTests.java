package me.ford.srt.config;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ford.srt.mock.MockSpecRandomTeleport;

public class SettingsTests {
    private MockSpecRandomTeleport srt;
    private Settings settings;

    @Before
    public void setup() {
        srt = new MockSpecRandomTeleport();
        settings = srt.getSettings();
    }

    @After
    public void tearDown() {
        srt.disable();
    }

    @Test
    public void debugIsDisabled() {
        Assert.assertFalse(settings.isDebugEnabled());
    }

    @Test
    public void metricsAreEnabled() {
        Assert.assertTrue(settings.useMetrics());
    }

    @Test
    public void perWorldsIsEnabled() {
        Assert.assertTrue(settings.usePerWorld());
    }

}