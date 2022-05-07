package com.c19501.repository;

import com.c19501.config.ConfigLoader;
import com.c19501.files.Segment;
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




