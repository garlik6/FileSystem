package ru.c19501.system;

import lombok.AccessLevel;
import lombok.Getter;

import ru.c19501.config.ConfigLoader;
import ru.c19501.core.files.FileRecord;
import ru.c19501.core.repository.repositories.JsonRepository;
import ru.c19501.defragmentation.Defragmentation;
import ru.c19501.exceptions.CoreException;
import ru.c19501.core.repository.RepoLoader;
import ru.c19501.core.repository.Repository;
import ru.c19501.core.repository.loaders.JsonLoaderRepository;

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
    public String addFile(String name, String type, int length) throws CoreException {
        if(Objects.equals(name, ""))
            throw new IllegalArgumentException("empty string is reserved name");
        return repository.addFileRecord(name, type, length);
    }

    @Override
    public void deleteFileById(String id) throws CoreException {
        FileRecord fileRecord = repository.findFileById(id);
        repository.setReadyToAddSpace(repository.getReadyToAddSpace() + fileRecord.getVolumeInBlocks());
        repository.deleteFileRecordById(id);
    }

    @Override
    public String findFileById(String id) {
        return repository.fileRecordsToString(repository.findFileById(id));
    }

    @Override
    public String findFilesByName(String name) {
        return repository.fileRecordsToString(repository.findFilesByCondition(fileRecord -> Objects.equals(fileRecord.getFileName(), name)));
    }

    @Override
    public String findFilesByType(String type) {
        return repository.fileRecordsToString(repository.findFilesByCondition(fileRecord -> Objects.equals(fileRecord.getFileType(), type)));
    }

    @Override
    public void deleteAllFiles() throws CoreException {
        repository.clear();
    }

    @Override
    public int getFreeSpace() {
        return repository.getReadyToAddSpace();
    }

    @Override
    public String retrieveAllFiles() {
        return repository.fileRecordsToString(repository.getAllFilesCopy());
    }


    private static void configure() {
        String config = ConfigLoader.properties.getProperty("fs.mode");
        if (Objects.equals(config, "JSON")) {
            loader = new JsonLoaderRepository();
        }
    }

    public boolean load()  {
        configure();
        try {
            repository = loader.loadRepository();
        }catch (IOException e){
            e.printStackTrace();
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

    @Override
    public void defragmentation() throws CoreException {
        repository.defragmentation();
    }
}
