package com.c19501.filesystem.repository;

import com.c19501.filesystem.FileSystem.configLoader.ConfigLoader;
import com.c19501.filesystem.repository.files.Segment;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

public abstract class Repository {
    protected int systemVersion;
    protected String owner;
    protected String systemFileName;
    protected String systemRepository;
    protected final ArrayList<Segment> segments = new ArrayList<>();
    public abstract void writeRepository();
    public abstract Repository loadRepository();

    public Repository() {
        File file = new File("src/main/resources/config.properties");
        Properties properties = ConfigLoader.load(file);
        systemVersion = Integer.parseInt(properties.getProperty("fs.systemVersion"));
        owner = properties.getProperty("fs.owner");
        int maxSegments = Integer.parseInt(properties.getProperty("fs.SegmentAmountInCatalog"));
        for (int i = 0; i < maxSegments; i++) {
            segments.add(Segment.createSegment(i));
        }
    }
}




