package ru.c19501;

import ru.c19501.core.FileSystem;
import ru.c19501.core.FileSystemFactory;
import ru.c19501.core.system.FileSystemFactoryImpl;
import ru.c19501.service.CoreService;
import ru.c19501.service.CoreServiceImpl;
import ru.c19501.service.model.FileRecordDTO;
import ru.c19501.service.config.JacksonConfig;

public class Application {
    public static void main(String[] args) {

        FileSystemFactory fileSystemFactory = new FileSystemFactoryImpl();
        FileSystem fileSystem = fileSystemFactory.getSystem();
        {
            if (!fileSystem.load()) {
                fileSystem.save();
            }

            CoreService coreService = new CoreServiceImpl(fileSystem, JacksonConfig.createObjectMapper());

            coreService.createFile("a1", "txt", 1);
            coreService.createFile("a2", "txt", 1);
            coreService.createFile("a3", "txt", 1);
            coreService.createFile("a4", "txt", 1);

            for (FileRecordDTO file : coreService.readFiles()) {
                System.out.println(file);
            }
        }
        fileSystem.save();
    }
}
