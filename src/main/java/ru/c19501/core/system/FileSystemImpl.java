
package ru.c19501.core.system;

import lombok.Getter;
import ru.c19501.core.FileSystem;
import ru.c19501.core.config.ConfigLoader;
import ru.c19501.core.repository.LoaderRepository;
import ru.c19501.core.repository.Repository;
import ru.c19501.core.repository.loaders.BinLoaderRepository;
import ru.c19501.core.repository.loaders.JsonLoaderRepository;

import java.io.File;
import java.util.Objects;

@Getter
public class FileSystemImpl implements FileSystem {

    private Repository repository;
    private static LoaderRepository loader;
    private static FileSystemImpl instance;


    public void save() {
        repository.writeRepository();
    }

    public void addFileInSegment(String name, String type, int length, int segment){
        repository.getSegments().get(segment).addFileRecord(name,type,length);
    }

    @Override
    public void deleteFileInSegmentById(int segment, int fileId) {
        repository.getSegments().get(segment).deleteFileRecord(fileId);
    }

    @Override
    public int getFreeSpaceInSegment(int segment) {
        return repository.getSegments().get(segment).getFreeSpaceRemained();
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

    public void load() {
        configure();
        repository = loader.loadRepository();
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
