package me.ford.srt.config;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ford.srt.mock.MockSpecRandomTeleport;

public class CustomConfigHandlerTests {
    private final String fileName = "file_name_" + System.currentTimeMillis() + ".yml";
    private MockSpecRandomTeleport srt;
    private CustomConfigHandler handler;

    @Before
    public void setUp() {
        srt = new MockSpecRandomTeleport();
        handler = new CustomConfigHandler(srt, fileName);
    }

    @After
    public void tearDown() {
        srt.disable();
        handler.getFile().delete();
    }

    @Test
    public void fileNameNotNull() {
        Assert.assertNotNull(handler.getFileName());
    }

    @Test
    public void fileNameCorrect() {
        Assert.assertEquals(fileName, handler.getFileName());
    }

    @Test
    public void fileNotNull() {
        Assert.assertNotNull(handler.getFile());
    }

    @Test
    public void fileCorrect() {
        Assert.assertEquals(new File(srt.getDataFolder(), fileName), handler.getFile());
    }

    @Test
    public void configNotNull() {
        Assert.assertNotNull(handler.getConfig());
    }

    @Test
    public void cannotSaveDefault() {
        File file = handler.getFile();
        if (file.exists()) {
            file.delete();
        }
        handler.saveDefaultConfig();
        Assert.assertFalse(file.exists()); // no default to save
    }

    @Test
    public void fileExistsAfterSave() {
        File file = handler.getFile();
        if (file.exists()) {
            file.delete();
        }
        handler.saveDefaultConfig();
        Assert.assertFalse(file.exists()); // no default to save
        
        handler.getConfig(); // create config

        handler.saveConfig();
        Assert.assertTrue(file.exists()); // saved empty

        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void getResultsInSame() {
        String path = "somePath";
        String value = "someValue";
        handler.getConfig().set(path, value);
        Assert.assertEquals(value, handler.getConfig().getString(path));
    }

    @Test
    public void newInstanceGetsSame() {
        String path = "som3Path";
        double value = 3.1415;
        handler.getConfig().set(path, value);

        handler.saveConfig();
        // new instance
        handler = new CustomConfigHandler(srt, handler.getFileName());

        Assert.assertEquals(value, handler.getConfig().getDouble(path), 0.01);
    }

}