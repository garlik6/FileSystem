package ru.c19501.core.files;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.c19501.exceptions.CoreException;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SegmentTest {
    @DisplayName("file addition when there is fitting unused space")
    @Test
    void addFileRecord1() throws CoreException {
        Segment segment = Segment.createSegment(0);
        String id = segment.addFileRecord("addition", "txt", segment.getAmountOfBlocks());
        FileRecord fileRecord = segment.findFileById(id);

        assertEquals(0, segment.getFreeAndDeletedSpace());
        assertEquals("addition", fileRecord.getFileName());
        assertEquals("txt", fileRecord.getFileType());
        assertEquals(segment.getAmountOfBlocks(), fileRecord.getVolumeInBlocks());
        assertFalse(fileRecord.isDeleted());
    }

    @Test
    @DisplayName("file addition when there is fitting(same or more volume) deleted file(file chain) present")
    void addFileRecord2() throws CoreException {
        Segment segment = Segment.createSegment(0);
        String id1 = segment.addFileRecord("1", "txt", 1);
        String id2 = segment.addFileRecord("2", "txt", 1);
        String id3 = segment.addFileRecord("3", "txt", 1);//
        String id4 = segment.addFileRecord("4", "txt", 1);//
        String id5 = segment.addFileRecord("5", "txt", 1);//
        String id6 = segment.addFileRecord("6", "txt", 1);
        String id7 = segment.addFileRecord("7", "txt", 1);
        String id8 = segment.addFileRecord("2", "txt", 1);
        String id9 = segment.addFileRecord("2", "txt", 1);//
        String id10 = segment.addFileRecord("2", "txt", 1);//
        String id11 = segment.addFileRecord("2", "txt", 1);//
        String id12 = segment.addFileRecord("2", "txt", 1);//
        String id13 = segment.addFileRecord("2", "txt", 1);
        String id14 = segment.addFileRecord("2", "txt", 1);
        String id15 = segment.addFileRecord("2", "txt", 1);
        String id16 = segment.addFileRecord("2", "txt", 5);

        segment.deleteFileRecordById(id3);
        segment.deleteFileRecordById(id4);
        segment.deleteFileRecordById(id5);
        segment.deleteFileRecordById(id9);
        segment.deleteFileRecordById(id10);
        segment.deleteFileRecordById(id11);
        segment.deleteFileRecordById(id12);

        assertEquals(7, segment.getFreeAndDeletedSpace());
        String id = segment.addFileRecord("ADDITIONAL1", "txt", 4);
        assertEquals(segment.getFileRecordByNumber(6).getId(), id);
        assertEquals(segment.getFileRecordByNumber(8).getId(), id14);

        String iD = segment.addFileRecord("ADDITIONAL2", "txt", 2);

        assertEquals(12, (long) segment.getFileRecordsCopy().size());

        assertEquals(1, segment.findFilesByCondition(fileRecord -> Objects.equals(fileRecord.getFileName(), "")).size());
        assertEquals(3, segment.findFilesByCondition(fileRecord -> Objects.equals(fileRecord.getFileName(), "")).get(0).getNumber());
        assertEquals(1, segment.findFilesByCondition(fileRecord -> Objects.equals(fileRecord.getFileName(), "")).get(0).getVolumeInBlocks());
    }

    @DisplayName("adding at last block")
    @Test
    void addFileRecord13() throws CoreException {
        Segment segment = Segment.createSegment(0);


        String id1 = segment.addFileRecord("1", "txt", 1);
        String id2 = segment.addFileRecord("2", "txt", 1);
        String id3 = segment.addFileRecord("3", "txt", 1);//
        String id4 = segment.addFileRecord("4", "txt", 1);//
        String id5 = segment.addFileRecord("5", "txt", 1);//
        String id6 = segment.addFileRecord("6", "txt", 1);
        String id7 = segment.addFileRecord("7", "txt", 1);
        String id8 = segment.addFileRecord("2", "txt", 1);
        String id9 = segment.addFileRecord("2", "txt", 1);//
        String id10 = segment.addFileRecord("2", "txt", 1);//
        String id11 = segment.addFileRecord("2", "txt", 1);//
        String id12 = segment.addFileRecord("2", "txt", 1);//
        String id13 = segment.addFileRecord("2", "txt", 1);
        String id14 = segment.addFileRecord("2", "txt", 1);
        String id15 = segment.addFileRecord("2", "txt", 1);
        String id16 = segment.addFileRecord("2", "txt", 4);
        String id17 = segment.addFileRecord("2", "txt", 1);
        segment.deleteFileRecordById(id17);
        String id18 = segment.addFileRecord("ADDITIONAL", "txt", 1);

        assertEquals(16, segment.findFileById(id18).getNumber());


    }

    @DisplayName("adding at several last deleted blocks volume < deleted volume")
    @Test
    void addFileRecord4() throws CoreException {
        Segment segment = Segment.createSegment(0);
        String id1 = segment.addFileRecord("1", "txt", 1);
        String id2 = segment.addFileRecord("2", "txt", 1);
        String id3 = segment.addFileRecord("3", "txt", 1);//
        String id4 = segment.addFileRecord("4", "txt", 1);//
        String id5 = segment.addFileRecord("5", "txt", 1);//
        String id6 = segment.addFileRecord("6", "txt", 1);
        String id7 = segment.addFileRecord("7", "txt", 1);
        String id8 = segment.addFileRecord("2", "txt", 1);
        String id9 = segment.addFileRecord("2", "txt", 1);//
        String id10 = segment.addFileRecord("2", "txt", 1);//
        String id11 = segment.addFileRecord("2", "txt", 1);//
        String id12 = segment.addFileRecord("2", "txt", 1);//
        String id13 = segment.addFileRecord("2", "txt", 1);
        String id14 = segment.addFileRecord("2", "txt", 1);
        String id15 = segment.addFileRecord("2", "txt", 1);
        String id16 = segment.addFileRecord("2", "txt", 4);
        String id17 = segment.addFileRecord("2", "txt", 1);
        segment.deleteFileRecordById(id17);
        segment.deleteFileRecordById(id16);
        String id18 = segment.addFileRecord("ADDITIONAL", "txt", 1);
        assertEquals(15, segment.findFileById(id18).getNumber());
        assertEquals(16, segment.findFilesByCondition(fileRecord -> Objects.equals(fileRecord.getFileName(), "")).get(0).getNumber());

    }

    @DisplayName("REFACTORING NEEDED")
    @Test
    void addFileRecord5() throws CoreException {
        Segment segment = Segment.createSegment(0);
        String id1 = segment.addFileRecord("1", "txt", 1);
        String id2 = segment.addFileRecord("2", "txt", 1);
        String id3 = segment.addFileRecord("3", "txt", 1);//
        String id4 = segment.addFileRecord("4", "txt", 1);//
        String id5 = segment.addFileRecord("5", "txt", 1);//
        String id6 = segment.addFileRecord("6", "txt", 1);
        String id7 = segment.addFileRecord("7", "txt", 1);
        String id8 = segment.addFileRecord("2", "txt", 1);
        String id9 = segment.addFileRecord("2", "txt", 1);//
        String id10 = segment.addFileRecord("2", "txt", 1);//
        String id11 = segment.addFileRecord("2", "txt", 1);//
        String id12 = segment.addFileRecord("2", "txt", 1);//
        String id13 = segment.addFileRecord("2", "txt", 1);
        String id14 = segment.addFileRecord("2", "txt", 1);
        String id15 = segment.addFileRecord("2", "txt", 1);
        String id16 = segment.addFileRecord("2", "txt", 4);
        String id17 = segment.addFileRecord("2", "txt", 1);
        segment.deleteFileRecordById(id17);
        segment.deleteFileRecordById(id4);
        segment.deleteFileRecordById(id5);
        segment.deleteFileRecordById(id8);
        assertThrows(CoreException.class, () ->
                segment.addFileRecord("ADDITIONAL", "txt", 4));
    }


    @DisplayName("Difragmentation NEEDED")
    @Test
    void addFileRecord6() throws CoreException {
        Segment segment = Segment.createSegment(0);
        String id1 = segment.addFileRecord("1", "txt", 2);
        String id2 = segment.addFileRecord("1", "txt", 1);
        segment.deleteFileRecordById(id1);
        String id3 = segment.addFileRecord("2", "txt", 1);
        String id4 = segment.addFileRecord("3", "txt", 1);

        assertEquals("[0, 1, 2]", Arrays.toString(segment.getFileRecords().stream().mapToInt(FileRecord::getNumber).toArray()));
    }

}
