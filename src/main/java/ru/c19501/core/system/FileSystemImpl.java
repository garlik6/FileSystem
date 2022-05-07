package ru.c19501.core.system;

import lombok.Getter;
import ru.c19501.core.FileSystem;
import ru.c19501.core.config.ConfigLoader;
import ru.c19501.core.repository.LoaderRepository;
import ru.c19501.core.repository.loaders.BinLoaderRepository;
import ru.c19501.core.repository.repositories.BinRepository;
import ru.c19501.core.repository.loaders.JsonLoaderRepository;
import ru.c19501.core.repository.repositories.JsonRepository;
import ru.c19501.core.repository.Repository;

import java.io.File;
import java.util.Objects;

@Getter
public class FileSystemImpl implements FileSystem {

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
        FileSystemImpl fileSystem = new FileSystemImpl();
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
