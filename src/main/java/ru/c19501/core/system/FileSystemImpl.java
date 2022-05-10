
package ru.c19501.core.system;

import lombok.AccessLevel;
import lombok.Getter;

import ru.c19501.core.FileSystem;
import ru.c19501.core.config.ConfigLoader;
import ru.c19501.core.files.Segment;
import ru.c19501.core.repository.RepoLoader;
import ru.c19501.core.repository.Repository;
import ru.c19501.core.repository.loaders.BinLoaderRepository;
import ru.c19501.core.repository.loaders.JsonLoaderRepository;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Getter
public class FileSystemImpl implements FileSystem {


    @Getter(AccessLevel.NONE)
    private Repository repository;
    private static RepoLoader loader;
    private static FileSystemImpl instance;


    public void save() {
        repository.writeRepository();
    }

    @Override
    public String addFileInSegment(String name, String type, int length, int segment) throws Segment.DefragmentationNeeded {
        if(Objects.equals(name, ""))
            throw new IllegalArgumentException("empty string is reserved name");
        return repository.getSegmentsCopy().get(segment).addFileRecord(name,type,length);
    }

    @Override
    public void deleteFileInSegmentById(int segment, String id) {
        repository.getSegmentsCopy().get(segment).deleteFileRecordById(id);
    }

    @Override
    public String findFileInSegmentById(int segment, String id) {
        return repository.fileRecordsToString(repository.getSegmentsCopy().get(segment).findFileById(id));
    }

    @Override
    public String findFilesInSegmentByName(int segment, String name) {
        return repository.fileRecordsToString(repository.getSegmentsCopy().get(segment).findFilesByCondition(fileRecord -> Objects.equals(fileRecord.getFileName(), name)));
    }

    @Override
    public String findFilesInSegmentByType(int segment, String type) {
        return repository.fileRecordsToString(repository.getSegmentsCopy().get(segment).findFilesByCondition(fileRecord -> Objects.equals(fileRecord.getFileType(), type)));
    }



    @Override
    public int getFreeSpaceInSegment(int segment) {
        return repository.getSegmentsCopy().get(segment).getFreeAndDeletedSpace();
    }

    @Override
    public String retrieveAllFilesFromSegment(int segment) {
        return repository.fileRecordsToString(repository.getSegmentsCopy().get(segment).getFileRecordsCopy());
    }

    private static void configure() {
        String config = ConfigLoader.load(new File("src/main/resources/config.properties")).getProperty("fs.mode");
        if (Objects.equals(config, "JSON")) {
            loader = new JsonLoaderRepository();
        }
        if (Objects.equals(config, "BIN")) {
            loader = new BinLoaderRepository();
        }
    }

    public boolean load()  {
        configure();
        try {
            repository = loader.loadRepository();
        }catch (IOException e){
            return false;
        }
        return true;
    }

    public void print() {
        repository.print();
    }

    protected void initRepository(Repository repository) {
        this.repository = repository;
    }

    private FileSystemImpl() {
    }

    protected static FileSystemImpl getInstance() {
        if (Objects.isNull(instance)) {
            instance = new FileSystemImpl();
            return instance;
        }
        return instance;
    }
}
