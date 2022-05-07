package com.c19501.system;

import com.c19501.config.ConfigLoader;
import com.c19501.repository.LoaderRepository;
import com.c19501.repository.loaders.BinLoaderRepository;
import com.c19501.repository.repositories.BinRepository;
import com.c19501.repository.loaders.JsonLoaderRepository;
import com.c19501.repository.Repository;
import com.c19501.repository.repositories.JsonRepository;

import java.io.File;
import java.util.Objects;

public class FileSystem {

    private Repository repository;
    private static LoaderRepository loader;

    /*    public static boolean checkIfFileExists(File directory, String filename) {
            return Arrays.stream(Objects.requireNonNull(directory.listFiles())).anyMatch(file -> file.getName().equals(filename));
        }*/
    public void save() {
        repository.writeRepository();
    }

    private static void configure() {
        String config = ConfigLoader.load(new File("src/main/resources/config.properties")).getProperty("fs.mode");
        if (Objects.equals(config, "JSON")) {
            loader = new JsonLoaderRepository();
        }
        if (Objects.equals(config, "BIN")) {
            loader = new BinLoaderRepository();
        }
    }

    public void load() {
        configure();
        repository = loader.loadRepository();
    }

    public void print() {
        repository.print();
    }

    public static FileSystem createNew() throws Exception {
        String config = ConfigLoader.load(new File("src/main/resources/config.properties")).getProperty("fs.mode");
        FileSystem fileSystem = new FileSystem();
        if (Objects.equals(config, "BIN")) {
            fileSystem.repository = new BinRepository();
            return fileSystem;
        }
        if (Objects.equals(config, "JSON")) {
            fileSystem.repository = new JsonRepository();
            return fileSystem;
        }
        throw new Exception("no such mode");
    }
}
