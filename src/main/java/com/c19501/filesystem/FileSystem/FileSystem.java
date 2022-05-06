package com.c19501.filesystem.FileSystem;

import com.c19501.filesystem.FileSystem.configLoader.ConfigLoader;
import com.c19501.filesystem.repository.repoVariants.BinRepository;
import com.c19501.filesystem.repository.repoVariants.JSONRepository;
import com.c19501.filesystem.repository.Repository;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

public class FileSystem {

    private Repository repository;

    public static boolean checkIfExists(File directory, String filename) {
        return Arrays.stream(Objects.requireNonNull(directory.listFiles())).anyMatch(file -> file.getName().equals(filename));
    }


    public void save() {
        repository.writeRepository();
    }


    public void load() {
       repository = repository.loadRepository();
    }

    public static FileSystem createNew() throws Exception {
        Properties prop = ConfigLoader.load(new File("src/main/resources/config.properties"));
        FileSystem fileSystem = new FileSystem();
        if (Objects.equals(prop.getProperty("fs.mode"), "BIN")){
            fileSystem.repository = new BinRepository();
            return fileSystem;
        }
        if (Objects.equals(prop.getProperty("fs.mode"), "BIN")){
            fileSystem.repository = new JSONRepository();
            return fileSystem;
        }
        throw new Exception("no such mode");

    }
}
