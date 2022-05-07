package ru.c19501.core.repository;

import ru.c19501.core.config.ConfigLoader;
import ru.c19501.core.files.Segment;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.*;

@Getter
@Setter
public abstract class Repository {
    protected int systemVersion;
    protected String owner;
    protected String systemFileName;
    protected String systemRepository;
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

    public abstract void writeRepository();

    public abstract void print();
}




