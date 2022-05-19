package ru.c19501.core.repository;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ru.c19501.config.ConfigLoader;
import ru.c19501.core.files.FileRecord;
import ru.c19501.core.files.Segment;
import ru.c19501.core.files.Views;
import ru.c19501.exceptions.CoreException;
import ru.c19501.fileAdder.FileAdder;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class Repository {
    @JsonView(Views.Internal.class)
    protected int systemVersion;

    @JsonView(Views.Internal.class)
    protected String owner;

    @JsonView(Views.Internal.class)
    protected String systemFileName;

    @JsonView(Views.Internal.class)
    protected String systemRepository;

    @JsonView(Views.Public.class)
    protected int space;
    @JsonView(Views.Public.class)
    protected int freeSpace;
    @JsonView(Views.Public.class)
    protected int readyToAddSpace;
    @JsonView(Views.Public.class)
    protected int maxSegments;
    @JsonView(Views.Public.class)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    protected List<Segment> segments = new ArrayList<>();

    protected Repository() {
        Properties properties = ConfigLoader.properties;
        systemVersion = Integer.parseInt(properties.getProperty("fs.systemVersion"));
        owner = properties.getProperty("fs.owner");
        maxSegments = Integer.parseInt(properties.getProperty("fs.defaultSegmentAmountInCatalog"));
        space = Integer.parseInt(properties.getProperty("fs.space"));
        readyToAddSpace = space;
        freeSpace = space;
        for (int i = 0; i < maxSegments; i++) {
            segments.add(Segment.createSegment(this));
        }
    }

    public Repository(Repository repository) {
        this.systemVersion = repository.systemVersion;
        this.owner = repository.owner;
        this.systemFileName = repository.systemFileName;
        this.systemRepository = repository.systemRepository;
        this.space = repository.space;
        this.freeSpace = repository.freeSpace;
        this.readyToAddSpace = repository.readyToAddSpace;
        this.maxSegments = repository.maxSegments;
        this.segments = repository.getSegmentsCopy();
    }

    public String addFileRecord(String name, String type, int volumeInBlocks) throws CoreException {
        FileAdder fileAdder = new FileAdder(this, segments.get(0));
        Segment.NewFileParams fileParams = new Segment.NewFileParams(name, type, volumeInBlocks);
        return fileAdder.addFileRecord(fileParams);
    }

    public abstract String fileRecordsToString(FileRecord fileRecord);

    public abstract String fileRecordsToString(List<FileRecord> fileRecords);



    public abstract void writeRepository();

    public abstract void print();

    @JsonGetter("segments")
    public List<Segment> getSegmentsCopy() {
        return new ArrayList<>(segments);
    }

    public void deleteFileRecordById(String id) throws CoreException {
        for (FileRecord fileRecord : getAllFiles()) {
            if (Objects.equals(fileRecord.getId(), id)) {
                fileRecord.deleteFile();
            }
        }
    }

    public int getMaxSegments() {
        return maxSegments;
    }

    public Segment getSegment(int i) {
        return segments.get(i);
    }

    public void moveRestOfSegments(int number, int offset) {
        for (int i = 0; i < segments.size(); i++) {
            if (i > number) {
                Segment segment = segments.get(i);
                segment.setStartingBlock(segment.getStartingBlock() + offset);
            }
        }
    }

    public int getSegmentNumber(Segment segment) {
        for (int i = 0; i < segments.size(); i++) {
            if (segments.get(i).equals(segment)) {
                return i;
            }
        }
        throw new NoSuchElementException("No such segment");
    }

    @JsonIgnore
    public List<FileRecord> getAllFilesCopy() {

        return new ArrayList<>(getAllFiles());
    }
    @JsonIgnore
    protected List<FileRecord> getAllFiles() {
        Stream<FileRecord> stream = segments.get(0).getFileRecords().stream();
        for (int i = 1; i < segments.size(); i++) {
            Stream<FileRecord> additionalStream = segments.get(i).getFileRecords().stream();
            stream = Stream.concat(stream, additionalStream);
        }
        return stream.toList();
    }

    public FileRecord findFileById(String id) {
        return getAllFiles().stream().filter(fileRecord -> Objects.equals(fileRecord.getId(), id)).findFirst().orElseThrow();
    }

    public List<FileRecord> findFilesByCondition(Predicate<FileRecord> predicate) {
        return getAllFiles().stream().filter(predicate).toList();
    }
}




