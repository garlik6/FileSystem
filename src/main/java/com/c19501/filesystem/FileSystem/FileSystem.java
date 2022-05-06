package com.c19501.filesystem.FileSystem;

import com.c19501.filesystem.FileSystem.configLoader.ConfigLoader;
import com.c19501.filesystem.repository.RepoLoader;
import com.c19501.filesystem.repository.repoVariants.BinLoader;
import com.c19501.filesystem.repository.repoVariants.BinRepository;
import com.c19501.filesystem.repository.repoVariants.JsonLoader;
import com.c19501.filesystem.repository.repoVariants.JsonRepository;
import com.c19501.filesystem.repository.Repository;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

public class FileSystem {

    private Repository repository;
    private static RepoLoader loader;

    public static boolean checkIfExists(File directory, String filename) {
        return Arrays.stream(Objects.requireNonNull(directory.listFiles())).anyMatch(file -> file.getName().equals(filename));
    }


    public void save() {
        repository.writeRepository();
    }

    private static void configure(){
        if (Objects.equals(ConfigLoader.load(new File("src/main/resources/config.properties")).getProperty("fs.mode"), "JSON")){
            loader = new JsonLoader();
        }
        if (Objects.equals(ConfigLoader.load(new File("src/main/resources/config.properties")).getProperty("fs.mode"), "BIN")){
            loader = new BinLoader();
        }
    }


    public void load() {
        configure();
       repository = loader.loadRepository();
    }
    public void print(){
        repository.print();
    }

    public static FileSystem createNew() throws Exception {
        Properties prop = ConfigLoader.load(new File("src/main/resources/config.properties"));
        FileSystem fileSystem = new FileSystem();
        if (Objects.equals(prop.getProperty("fs.mode"), "BIN")){
            fileSystem.repository = new BinRepository();
            return fileSystem;
        }
        if (Objects.equals(prop.getProperty("fs.mode"), "JSON")){
            fileSystem.repository = new JsonRepository();
            return fileSystem;
        }
        throw new Exception("no such mode");

    }
}
