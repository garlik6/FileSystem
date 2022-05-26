package ru.c19501.defragmentation;

import ru.c19501.config.ConfigLoader;
import ru.c19501.core.files.FileRecord;
import ru.c19501.core.files.Segment;
import ru.c19501.core.repository.repositories.JsonRepository;
import ru.c19501.exceptions.CoreException;
import ru.c19501.fileAdder.FileAdder;

import java.util.ArrayList;
import java.util.List;

public class Defragmentation {
    public static void defragment(JsonRepository disk) throws CoreException {
        ArrayList<Segment.NewFileParams> data = new ArrayList<>();

        for (Segment segment: disk.getSegmentsCopy()) {
            for (FileRecord fileRecord: segment.getFileRecords()) {
                if (fileRecord.getFileStatus().equals(FileRecord.FileStatus.NOT_DELETED)) {
                    Segment.NewFileParams newFileParams = new Segment.NewFileParams(
                            fileRecord.getFileName(),
                            fileRecord.getFileType(),
                            fileRecord.getVolumeInBlocks()
                    );
                    data.add(newFileParams);
                }
            }
        }

        //обнуляем репозиторий
        disk.setFreeSpace(Integer.parseInt(ConfigLoader.properties.getProperty("fs.space")));
        disk.setReadyToAddSpace(disk.getFreeSpace());
        for (Segment segment: disk.getSegmentsCopy()) {
            segment.setStartingBlock(0);
            segment.nullifyFileRecords();
        }

        FileAdder fileAdder = new FileAdder(disk, disk.getSegment(0));
        for (Segment.NewFileParams newFileParams: data) {
            fileAdder.addFileRecord(newFileParams);
        }


    }
}
