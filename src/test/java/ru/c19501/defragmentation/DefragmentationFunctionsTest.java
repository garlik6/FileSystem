package ru.c19501.defragmentation;

import org.junit.jupiter.api.Test;
import ru.c19501.core.files.FileRecord;
import ru.c19501.core.files.Segment;
import ru.c19501.core.repository.repositories.JsonRepository;
import ru.c19501.exceptions.CoreException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefragmentationFunctionsTest {

    public static FileRecord getFileRecordNotDeleted(FileRecord fileRecord) {
        fileRecord.setFileStatus(FileRecord.FileStatus.NOT_DELETED);
        return fileRecord;
    }

    public static FileRecord getFileRecordDeleted(FileRecord fileRecord) {
        fileRecord.setFileStatus(FileRecord.FileStatus.DELETED);
        return fileRecord;
    }
    @Test
    void defragExt(){
        List<Segment> list = new ArrayList<>();
        ArrayList<FileRecord> listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a1","txt",0,1,0)));
        listRec.add(getFileRecordDeleted(new FileRecord("a2","txt",1,3,1)));
        list.add(new Segment(2,0,listRec));
        listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a3","txt",4,2,0)));
        list.add(new Segment(2,4,listRec));

        JsonRepository repository =  new JsonRepository(6,0,3,list);


        assertEquals(DefragmentationFunctions.defragExt(repository), 0.5);
    }

    @Test
    void maxLengthToInsert1() {
        List<Segment> list = new ArrayList<>();
        ArrayList<FileRecord> listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a1","txt",0,2,0)));
        listRec.add(getFileRecordDeleted(new FileRecord("a2","txt",1,2,1)));
        list.add(new Segment(2,0,listRec));
        listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a3","txt",4,1,0)));
        listRec.add(getFileRecordNotDeleted(new FileRecord("a4","txt",5,1,0)));
        list.add(new Segment(2,4,listRec));

        JsonRepository repository =  new JsonRepository(6,0,3,list);


        assertEquals(DefragmentationFunctions.maxLengthToInsert(repository), 2);
    }

    @Test
    void maxLengthToInsert2() {
        List<Segment> list = new ArrayList<>();
        ArrayList<FileRecord> listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a1","txt",0,2,0)));
        listRec.add(getFileRecordDeleted(new FileRecord("a2","txt",1,2,1)));
        list.add(new Segment(2,0,listRec));
        listRec = new ArrayList<>();
        list.add(new Segment(2,4,listRec));

        JsonRepository repository =  new JsonRepository(6,2,2, list);

        assertEquals(DefragmentationFunctions.maxLengthToInsert(repository), 2);
    }

    @Test
    void possibleToInsert(){

        List<Segment> list = new ArrayList<>();
        ArrayList<FileRecord> listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a1","txt",0,2,0)));
        listRec.add(getFileRecordNotDeleted(new FileRecord("a2","txt",1,2,1)));
        list.add(new Segment(2,0,listRec));
        listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a3","txt",4,1,0)));
        listRec.add(getFileRecordNotDeleted(new FileRecord("a4","txt",5,1,0)));
        list.add(new Segment(2,4,listRec));

        JsonRepository repository =  new JsonRepository(6,0,0,list);

        assertFalse(DefragmentationFunctions.possibleToInsert(repository, 1));
    }

    @Test
    void checkDef(){
        List<Segment> list = new ArrayList<>();
        ArrayList<FileRecord> listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a1","txt",0,2,0)));
        listRec.add(getFileRecordDeleted(new FileRecord("a2","txt",1,2,1)));
        list.add(new Segment(2,0,listRec));
        listRec = new ArrayList<>();
        listRec.add(getFileRecordNotDeleted(new FileRecord("a3","txt",4,1,0)));
        listRec.add(getFileRecordDeleted(new FileRecord("a4","txt",5,1,0)));
        list.add(new Segment(2,4,listRec));

        JsonRepository repository =  new JsonRepository(6,0,3,list);

        assertFalse(DefragmentationFunctions.checkDef(repository));
    }

}