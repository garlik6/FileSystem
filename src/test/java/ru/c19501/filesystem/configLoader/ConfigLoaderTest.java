package ru.c19501.filesystem.configLoader;

import org.junit.jupiter.api.Test;
import ru.c19501.config.ConfigLoader;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {
    @Test
    void loadPropertiesTest() {
        //File file = new File("src/main/resources/config.properties");
        Properties properties = ConfigLoader.properties;
        assertEquals("c19-501", properties.getProperty("fs.owner"));
        assertEquals("1", properties.getProperty("fs.systemVersion"));
        assertEquals("fileSystem2",properties.getProperty("fs.systemBinFileName"));
        assertEquals("src/main/resources/fileSystemFolder",properties.getProperty("fs.systemBinRepository"));
    }
}
