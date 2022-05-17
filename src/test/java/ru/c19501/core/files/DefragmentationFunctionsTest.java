package ru.c19501.core.files;

import org.junit.jupiter.api.Test;
import ru.c19501.core.defragmentation.Defragmentation;
import ru.c19501.exceptions.CoreException;

import static org.junit.jupiter.api.Assertions.*;

class DefragmentationFunctionsTest {

    @Test
    void maxLengthToInsert() throws CoreException {
        Segment segment = Segment.createSegment(0);

        segment.addFileRecord("1", "txt", 1);
        String id2 = segment.addFileRecord("2", "txt", 2);
        segment.addFileRecord("3", "txt", 3);
        segment.deleteFileRecordById(id2);
        assertTrue(DefragmentationFunctions.maxLengthToInsert(segment) == 14);
        segment.addFileRecord("4", "txt", 1);
        segment.addFileRecord("5", "txt", 14);

        assertTrue(DefragmentationFunctions.maxLengthToInsert(segment) == 1);

    }

    @Test
    void possibleToInsert() throws CoreException {
        Segment segment = Segment.createSegment(0);

        String id1 = segment.addFileRecord("1", "txt", 1);
        String id2 = segment.addFileRecord("2", "txt", 2);
        segment.addFileRecord("3", "txt", 3);
        segment.deleteFileRecordById(id2);
        String id4 = segment.addFileRecord("4", "txt", 1);
        segment.addFileRecord("5", "txt", 14);

        assertFalse(DefragmentationFunctions.possibleToInsert(segment, 2), "lenFile > freespace");
        assertTrue(DefragmentationFunctions.possibleToInsert(segment, 1), "lenFile = freespace");


        segment.deleteFileRecordById(id1);

        assertFalse(DefragmentationFunctions.possibleToInsert(segment, 2));

        Defragmentation.defragment(segment);
        assertTrue(DefragmentationFunctions.possibleToInsert(segment, 2));
    }

    @Test
    void howMuchSpace() throws CoreException {
        Segment segment = Segment.createSegment(0);
        String id1 = segment.addFileRecord("1", "txt", 1);
        String id2 = segment.addFileRecord("2", "txt", 2);
        segment.addFileRecord("3", "txt", 3);

        assertTrue(DefragmentationFunctions.howMuchSpace(segment) == 14);

        segment.deleteFileRecordById(id2);
        assertTrue(DefragmentationFunctions.howMuchSpace(segment) == 16);
    }

    @Test
    void defragExt() throws CoreException {
        Segment segment = Segment.createSegment(0);

        String id1 = segment.addFileRecord("1", "txt", 1);
        String id2 = segment.addFileRecord("2", "txt", 5);
        segment.addFileRecord("3", "txt", 3);
        segment.deleteFileRecordById(id2);
        String id4 = segment.addFileRecord("4", "txt", 1);
        segment.addFileRecord("5", "txt", 3);
        segment.addFileRecord("6", "txt", 7);
        segment.deleteFileRecordById(id4);
        segment.deleteFileRecordById(id1);

        assertTrue(DefragmentationFunctions.defragExt(segment) == 1.5);
    }

    @Test
    void checkDef() throws CoreException {
        Segment segment = Segment.createSegment(0);

        String id1 = segment.addFileRecord("1", "txt", 1);
        String id2 = segment.addFileRecord("2", "txt", 5);

        assertFalse(DefragmentationFunctions.checkDef(segment));

        segment.addFileRecord("3", "txt", 3);

        assertFalse(DefragmentationFunctions.checkDef(segment));

        segment.deleteFileRecordById(id2);
        String id4 = segment.addFileRecord("4", "txt", 1);
        segment.addFileRecord("5", "txt", 3);
        segment.addFileRecord("6", "txt", 7);
        segment.deleteFileRecordById(id4);
        segment.deleteFileRecordById(id1);

        assertTrue(DefragmentationFunctions.checkDef(segment));
    }
}