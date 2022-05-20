package ru.c19501.fileAdder;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import ru.c19501.core.files.FileRecord;
import ru.c19501.core.files.Segment;
import ru.c19501.core.repository.Repository;
import ru.c19501.core.repository.repositories.JsonRepository;
import ru.c19501.exceptions.CoreException;
import ru.c19501.service.CoreService;
import ru.c19501.service.CoreServiceImpl;
import ru.c19501.service.config.JacksonConfig;
import ru.c19501.system.FileSystem;
import ru.c19501.system.FileSystemFactory;
import ru.c19501.system.FileSystemFactoryImpl;

import java.util.ArrayList;
import java.util.List;

class FileAdderTest {
    /**
     * Что будет если добавить 1 файл
     * репо до и после
     * репо вызывается фнукция
     * создаеть систему до и после
     * к системе до добавить функци добавления
     * Проверить до и после
     */
    @Test

    void addFileRecord() {
        FileSystemFactory fileSystemFactory = new FileSystemFactoryImpl();
        FileSystem fileSystem = fileSystemFactory.getSystem();
        List<Segment> list = new ArrayList<Segment>();
        list.add(new Segment(2));
        list.add(new Segment(2));
//        JsonRepository jsonRepository = new JsonRepository(1, 1, 1, "1", 1, 1, 1, 1, 1, 1);
        JsonRepository repository  = new JsonRepository(6, 6, 6, list);
        JsonRepository repository2  = new JsonRepository();
        try {
            repository.addFileRecord("a1","txt",1);
            repository2.addFileRecord("a1","txt",1);
            System.out.println(repository.getJson());
            System.out.println(repository2.getJson());
        } catch (CoreException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}