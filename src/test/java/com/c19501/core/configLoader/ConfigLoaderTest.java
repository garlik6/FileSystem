package com.c19501.core.configLoader;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {
    @Test
    void loadPropertiesTest() {
        File file = new File("src/config.properties");
        Properties properties = ConfigLoader.load(file);
        assertEquals("c19-501", properties.getProperty("fs.owner"));
        assertEquals("0", properties.getProperty("fs.systemVersion"));
        assertEquals("fileSystem",properties.getProperty("fs.systemFileName"));
        assertEquals("src/main/resources/fileSystemFolder",properties.getProperty("fs.SystemRepository"));
    }
}