package ru.c19501.core.repository;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ru.c19501.core.config.ConfigLoader;
import ru.c19501.core.files.FileRecord;
import ru.c19501.core.files.Segment;
import ru.c19501.core.files.Views;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
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

    @JsonGetter("segments")
    public List<Segment> getSegmentsCopy() {
        return new ArrayList<>(segments);
    }
}




