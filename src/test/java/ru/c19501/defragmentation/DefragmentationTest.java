package ru.c19501.defragmentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import ru.c19501.core.files.FileRecord;
import ru.c19501.core.files.Segment;
import ru.c19501.core.repository.repositories.JsonRepository;
import ru.c19501.exceptions.CoreException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.c19501.defragmentation.Defragmentation.defragment;

class DefragmentationTest {
    public static FileRecord getFileRecordNotDeleted(FileRecord fileRecord) {
        fileRecord.setFileStatus(FileRecord.FileStatus.NOT_DELETED);
        return fileRecord;
    }

    public static FileRecord getFileRecordDeleted(FileRecord fileRecord) {
        fileRecord.setFileStatus(FileRecord.FileStatus.DELETED);
        return fileRecord;
    }

    @Test
    void defragmentTest() throws CoreException, JsonProcessingException {
        List<Segment> list = new ArrayList<>();
        list.add(new Segment(2));
        list.add(new Segment(2));
        ArrayList<FileRecord> listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a1","txt",0,1,0)));
        listRec.add(getFileRecordDeleted(new FileRecord("a2","txt",1,3,1)));
        list.add(new Segment(2,0,listRec));
        listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a3","txt",4,2,0)));
        list.add(new Segment(2,4,listRec));

        JsonRepository repository =  new JsonRepository(6,0,3,list);



        list = new ArrayList<>();
        listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a1","txt",0,1,0)));
        listRec.add(getFileRecordNotDeleted(new FileRecord("a3","txt",1,2,1)));
        list.add(new Segment(2,0, listRec));
        list.add(new Segment(2, 3, new ArrayList<>()));
        list.add(new Segment(2, 3, new ArrayList<>()));
        list.add(new Segment(2, 3, new ArrayList<>()));

        JsonRepository  repository2 =  new JsonRepository(6,3,3,list);

        defragment(repository);
        assertEquals(repository.getCurrentJson(),repository2.getCurrentJson());
    }

}