package ru.c19501.defragmentation;

import org.junit.jupiter.api.Test;
import ru.c19501.core.files.Segment;
import ru.c19501.core.repository.repositories.JsonRepository;
import ru.c19501.exceptions.CoreException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefragmentationFunctionsTest {

    @Test
    void defragExt() throws CoreException {
        List<Segment> list = new ArrayList<>();
        list.add(new Segment(2));
        list.add(new Segment(2));
        JsonRepository repository  = new JsonRepository(6, 6, 6, list);
        repository.addFileRecord("a1","txt",2);
        String id2 = repository.addFileRecord("a2","txt",2);
        repository.addFileRecord("a3","txt",1);
        String id4 = repository.addFileRecord("a4","txt",1);

        repository.deleteFileRecordById(id2);
        repository.deleteFileRecordById(id4);

        assertEquals(DefragmentationFunctions.defragExt(repository), 0.5);
    }

    @Test
    void maxLengthToInsert1() throws CoreException {
        List<Segment> list = new ArrayList<>();
        list.add(new Segment(2));
        list.add(new Segment(2));
        JsonRepository repository  = new JsonRepository(6, 6, 6, list);
        repository.addFileRecord("a1","txt",2);
        String id2 = repository.addFileRecord("a2","txt",2);
        repository.addFileRecord("a3","txt",1);
        String id4 = repository.addFileRecord("a4","txt",1);

        repository.deleteFileRecordById(id2);
        repository.deleteFileRecordById(id4);


        assertEquals(DefragmentationFunctions.maxLengthToInsert(repository), 2);
    }

    @Test
    void maxLengthToInsert2() throws CoreException {
        List<Segment> list = new ArrayList<>();
        list.add(new Segment(2));
        list.add(new Segment(2));
        JsonRepository repository  = new JsonRepository(6, 6, 6, list);
        repository.addFileRecord("a1","txt",2);
        repository.addFileRecord("a2","txt",2);


        assertEquals(DefragmentationFunctions.maxLengthToInsert(repository), 2);
    }


//    @Test
//    void possibleToInsert1() throws CoreException {
//        List<Segment> list = new ArrayList<>();
//        list.add(new Segment(2));
//        list.add(new Segment(2));
//        JsonRepository repository  = new JsonRepository(6, 6, 6, list);
//        repository.addFileRecord("a1","txt",2);
//        String id2 = repository.addFileRecord("a2","txt",2);
//        repository.addFileRecord("a3","txt",1);
//        String id4 = repository.addFileRecord("a4","txt",1);
//
//        repository.deleteFileRecordById(id2);
//        repository.deleteFileRecordById(id4);
//
//
//        assertTrue(DefragmentationFunctions.possibleToInsert(repository, 2));
//    }

    @Test
    void possibleToInsert() throws CoreException {
        List<Segment> list = new ArrayList<>();
        list.add(new Segment(2));
        list.add(new Segment(2));
        JsonRepository repository  = new JsonRepository(6, 6, 6, list);
        repository.addFileRecord("a1","txt",2);
        repository.addFileRecord("a2","txt",2);
        repository.addFileRecord("a3","txt",1);
        repository.addFileRecord("a4","txt",1);


        assertFalse(DefragmentationFunctions.possibleToInsert(repository, 3));
    }

}