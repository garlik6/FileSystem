package com.c19501.filesystem.repository;

import com.c19501.filesystem.FileSystem.configLoader.ConfigLoader;
import com.c19501.filesystem.repository.files.Segment;
import com.c19501.filesystem.repository.repoVariants.BinLoader;
import com.c19501.filesystem.repository.repoVariants.JsonLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

public abstract class Repository {
    protected int systemVersion;
    protected String owner;
    protected String systemFileName;
    protected String systemRepository;
    protected final ArrayList<Segment> segments = new ArrayList<>();
    public abstract void writeRepository();
    public abstract void print();
    public Repository()  {
        File file = new File("src/main/resources/config.properties");
        Properties properties = ConfigLoader.load(file);
        systemVersion = Integer.parseInt(properties.getProperty("fs.systemVersion"));
        owner = properties.getProperty("fs.owner");
        int maxSegments = Integer.parseInt(properties.getProperty("fs.SegmentAmountInCatalog"));
        for (int i = 0; i < maxSegments; i++) {
            segments.add(Segment.createSegment(i));
        }
    }


    public int getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(int systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSystemFileName() {
        return systemFileName;
    }

    public void setSystemFileName(String systemFileName) {
        this.systemFileName = systemFileName;
    }

    public String getSystemRepository() {
        return systemRepository;
    }

    public void setSystemRepository(String systemRepository) {
        this.systemRepository = systemRepository;
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }
}




