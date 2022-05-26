package ru.c19501.fileAdder;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.c19501.core.files.FileRecord;
import ru.c19501.core.files.Segment;
import ru.c19501.core.repository.repositories.JsonRepository;
import ru.c19501.exceptions.CoreException;
import ru.c19501.system.FileSystem;
import ru.c19501.system.FileSystemFactory;
import ru.c19501.system.FileSystemFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileAdderTest{

    public static FileRecord getFileRecordNotDeleted(FileRecord fileRecord) {
        fileRecord.setFileStatus(FileRecord.FileStatus.NOT_DELETED);
        return fileRecord;
    }

    public static FileRecord getFileRecordDeleted(FileRecord fileRecord) {
        fileRecord.setFileStatus(FileRecord.FileStatus.DELETED);
        return fileRecord;
    }
    private FileSystemFactory fileSystemFactory = new FileSystemFactoryImpl();
    private  FileSystem fileSystem = fileSystemFactory.getSystem();

    @BeforeEach
    public void refreshFileSystem(){
        fileSystemFactory = new FileSystemFactoryImpl();
        fileSystem = fileSystemFactory.getSystem();
    }

    @Test
    void addFileRecordIntoFreeSpace() {
        List<Segment> list = new ArrayList<>();
        list.add(new Segment(2));
        list.add(new Segment(2));
        JsonRepository repository  = new JsonRepository(6, 6, 6, list);
        ArrayList<FileRecord> listRec = new ArrayList<>();
        listRec.add(new FileRecord("a1","txt",1,1,0));
        list.remove(0);
        list.add(new Segment(2,0,listRec));

        JsonRepository repositoryToCompare  = new JsonRepository(6, 5, 5, list);
        try {
            repository.addFileRecord("a1","txt",1);
            System.out.println(repository.getCurrentJson());
            System.out.println(repositoryToCompare.getCurrentJson());
            assertEquals(repositoryToCompare.getCurrentJson(),repository.getCurrentJson());
        } catch (CoreException | JsonProcessingException e) {
            e.printStackTrace();
        }

    }
    @Test
    void addFileRecordIntoDeletedSpace() {
        List<Segment> list = new ArrayList<>();
        ArrayList<FileRecord> listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a1","txt",0,1,0)));
        listRec.add(getFileRecordDeleted(new FileRecord("a2","txt",1,3,1)));
        list.add(new Segment(2,0,listRec));
        listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a3","txt",4,2,0)));
        list.add(new Segment(2,4,listRec));

        JsonRepository  repository =  new JsonRepository(6,0,3,list);

        list = new ArrayList<>();
        listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a1","txt",0,1,0)));
        listRec.add(getFileRecordNotDeleted(new FileRecord("a4","txt",1,2,1)));
        listRec.add(new FileRecord("","",3,1,2));
        list.add(new Segment(2,0,listRec));
        listRec = new ArrayList<FileRecord>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a3","txt",4,2,0)));
        list.add(new Segment(2,4,listRec));

        JsonRepository  repository2 =  new JsonRepository(6,0,1,list);

        try {
            repository.addFileRecord("a4","txt",2);
            System.out.println(StringUtils.difference(repository.getCurrentJson(),repository2.getCurrentJson()));

            System.out.println(repository.getCurrentJson());
            assertEquals(repository.getCurrentJson(),repository2.getCurrentJson());
        } catch (CoreException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addFileRecordNeededDefragmentation() throws CoreException {
        List<Segment> list = new ArrayList<>();
        list.add(new Segment(2));
        list.add(new Segment(2));
        ArrayList<FileRecord> listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a1","txt",0,1,0)));
        listRec.add(getFileRecordDeleted(new FileRecord("a2","txt",1,3,1)));
        list.add(new Segment(2,0,listRec));
        listRec = new ArrayList<FileRecord>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a3","txt",4,2,0)));
        list.add(new Segment(2,4,listRec));

        JsonRepository repository =  new JsonRepository(6,0,3,list);
        list = new ArrayList<>();
        listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a1","txt",0,1,0)));
        listRec.add(getFileRecordDeleted(new FileRecord("a4","txt",1,2,1)));
        listRec.add(new FileRecord("","",3,1,2));
        list.add(new Segment(2,0,listRec));
        listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a3","txt",4,2,0)));
        list.add(new Segment(2,4,listRec));

        JsonRepository  repository2 =  new JsonRepository(6,0,1,list);
        Throwable thrown = assertThrows(CoreException.class, () -> {
            repository.addFileRecord("a4","txt",2);
        });

    }
}
