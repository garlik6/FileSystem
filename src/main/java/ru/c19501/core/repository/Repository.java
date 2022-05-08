package ru.c19501.core.repository;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonView;
import ru.c19501.core.config.ConfigLoader;
import ru.c19501.core.files.FileRecord;
import ru.c19501.core.files.Segment;
import lombok.Getter;
import lombok.Setter;
import ru.c19501.core.files.Views;

import java.io.File;
import java.util.*;

@Getter
@Setter
public abstract class Repository {
    @JsonView(Views.Internal.class)
    protected int systemVersion;
    @JsonView(Views.Internal.class)
    protected String owner;
    @JsonView(Views.Internal.class)
    protected String systemFileName;
    @JsonView(Views.Internal.class)
    protected String systemRepository;
    @JsonAlias("SEGMENTS")
    @JsonView(Views.Public.class)
    protected final List<Segment> segments = new ArrayList<>();
    protected Repository() {
        File file = new File("src/main/resources/config.properties");
        Properties properties = ConfigLoader.load(file);
        systemVersion = Integer.parseInt(properties.getProperty("fs.systemVersion"));
        owner = properties.getProperty("fs.owner");
        int maxSegments = Integer.parseInt(properties.getProperty("fs.SegmentAmountInCatalog"));
        for (int i = 0; i < maxSegments; i++) {
            segments.add(Segment.createSegment(i));
        }
    }
    public abstract String fileRecordsToString(FileRecord fileRecord);
    public abstract String fileRecordsToString(List<FileRecord> fileRecords);
    public abstract void writeRepository();
    public abstract void print();
}




