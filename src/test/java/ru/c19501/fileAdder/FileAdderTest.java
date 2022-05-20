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

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileAdderTest {
    static int id = 0;

    public  static  FileRecord getFileRecordNotDeleted(FileRecord fileRecord)
    {
        fileRecord.setFileStatus(FileRecord.FileStatus.NOT_DELETED);
        return fileRecord;
    }

    public  static  FileRecord getFileRecordDeleted(FileRecord fileRecord)
    {
        fileRecord.setFileStatus(FileRecord.FileStatus.DELETED);
        return fileRecord;
    }
    /**
     * Что будет если добавить 1 файл
     * репо до и после
     * репо вызывается фнукция
     * создаеть систему до и после
     * к системе до добавить функци добавления
     * Проверить до и после
     */
    @Test
    void addFileRecordIntoFreeSpace() {
        FileSystemFactory fileSystemFactory = new FileSystemFactoryImpl();
        FileSystem fileSystem = fileSystemFactory.getSystem();
        List<Segment> list = new ArrayList<Segment>();
        list.add(new Segment(2));
        list.add(new Segment(2));


       // JsonRepository jsonRepository = new JsonRepository(1, 1, 1, "1", 1, 1, 1, 1, 1, 1);
        JsonRepository repository  = new JsonRepository(6, 6, 6, list);
        ArrayList<FileRecord> listRec = new ArrayList<FileRecord>();
        listRec.add(new FileRecord("a1","txt",1,1,0));
        list.remove(0);
        list.add(new Segment(2,0,listRec));

        JsonRepository repository2  = new JsonRepository(6, 5, 5, list);
        JsonRepository repository3  = new JsonRepository();
        try {
            repository.addFileRecord("a1","txt",1);
            System.out.println(repository.getJson());
            System.out.println(repository2.getJson());
            assertEquals(repository.getJson(),repository2.getJson());
        } catch (CoreException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addFileRecordIntoDeletedSpace() {
        FileSystemFactory fileSystemFactory = new FileSystemFactoryImpl();
        FileSystem fileSystem = fileSystemFactory.getSystem();
        List<Segment> list = new ArrayList<Segment>();
        ArrayList<FileRecord> listRec = new ArrayList<FileRecord>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a1","txt",0,1,0)));
        listRec.add(getFileRecordDeleted(new FileRecord("a2","txt",1,3,1)));
        list.add(new Segment(2,0,listRec));
        listRec = new ArrayList<FileRecord>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a3","txt",4,2,0)));
        list.add(new Segment(2,4,listRec));


        JsonRepository  repository =  new JsonRepository(6,0,3,list); //Тестируемая сущность

        list = new ArrayList<Segment>();
        listRec = new ArrayList<FileRecord>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a1","txt",0,1,0)));
        listRec.add(getFileRecordDeleted(new FileRecord("a4","txt",1,2,1)));
        listRec.add(new FileRecord("","",3,1,2));
        list.add(new Segment(2,0,listRec));
        listRec = new ArrayList<FileRecord>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a3","txt",4,2,0)));
        list.add(new Segment(2,4,listRec));

        JsonRepository  repository2 =  new JsonRepository(6,0,1,list); //Тестируемая сущность
        //Тестируемый блок
        try {
            repository.addFileRecord("a4","txt",2);
            //
            assertEquals(repository.getJson(),repository2.getJson());
        } catch (CoreException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}