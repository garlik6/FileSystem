package com.c19501.core;

import com.c19501.core.FileSystem.FileSystem;
import com.c19501.core.configLoader.ConfigLoader;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Properties;

public class Program {
    public static void main(String[] args) throws IOException {
        Properties properties = ConfigLoader.load(new File("src/config.properties"));
        File dir = new File(properties.getProperty("fs.SystemRepository"));
        String name = properties.getProperty("fs.systemFileName");
        FileSystem fileSystem = FileSystem.createNew(dir, name);
        fileSystem.save();
        fileSystem.load();
    }
}
