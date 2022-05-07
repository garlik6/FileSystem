package ru.c19501.core.system;

import ru.c19501.core.FileSystem;
import ru.c19501.core.FileSystemFactory;
import ru.c19501.core.config.ConfigLoader;
import ru.c19501.core.repository.repositories.BinRepository;
import ru.c19501.core.repository.repositories.JsonRepository;

import java.io.File;
import java.util.Objects;

public class FileSystemFactoryImpl implements FileSystemFactory {
    @Override
    public  FileSystem getSystem() {
        String config = ConfigLoader.load(new File("src/main/resources/config.properties")).getProperty("fs.mode");
        FileSystemImpl fileSystem = FileSystemImpl.getInstance();
        if (Objects.equals(config, "BIN")) {
            fileSystem.initRepository(new BinRepository());
            return fileSystem;
        }
        if (Objects.equals(config, "JSON")) {
            fileSystem.initRepository(new JsonRepository());
            return fileSystem;
        }
        return fileSystem;
    }
}
