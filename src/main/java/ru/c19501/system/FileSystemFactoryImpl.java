package ru.c19501.system;

import ru.c19501.config.ConfigLoader;
import ru.c19501.core.repository.repositories.JsonRepository;

import java.util.Objects;

public class FileSystemFactoryImpl implements FileSystemFactory {
    @Override
    public  FileSystem getSystem() {
        String config = ConfigLoader.properties.getProperty("fs.mode");
        FileSystemImpl fileSystem = FileSystemImpl.getInstance();
        if (Objects.equals(config, "JSON")) {
            fileSystem.initRepository(new JsonRepository());

            return fileSystem;
        }
        return fileSystem;
    }
}
