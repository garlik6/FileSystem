package com.c19501.core.FileSystem;

import com.c19501.core.BinRepository.BinRepository;
import com.c19501.core.configLoader.ConfigLoader;
import com.c19501.core.files.Segment;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

public class FileSystem {
    private int systemVersion;
    private String owner;
    private String systemFileName;
    private String systemRepository;
    private BinRepository binRepository;
    private final Segment[] segments = new Segment[Segment.getMaxSegmentNumber()];


    public static boolean checkIfExists(File directory, String filename) {
        return Arrays.stream(Objects.requireNonNull(directory.listFiles())).anyMatch(file -> file.getName().equals(filename));
    }

    public void save() {
        File binFile = new File(systemRepository + "/" + systemFileName);
        binRepository.writeBinFile(binFile);
    }


    public void load() {
        File binFile = new File(systemRepository + "/" + systemFileName);
        binRepository = BinRepository.loadBinFile(binFile);
    }

    public static FileSystem createNew(File directory, String filename) {
        File file = new File("src/config.properties");
        Properties properties = ConfigLoader.load(file);
        FileSystem fileSystem = new FileSystem();
        fileSystem.systemFileName = properties.getProperty("fs.systemFileName");
        fileSystem.systemRepository = properties.getProperty("fs.SystemRepository");
        fileSystem.systemVersion = Integer.parseInt(properties.getProperty("fs.systemVersion"));
        fileSystem.owner = properties.getProperty("fs.owner");
        fileSystem.binRepository = new BinRepository();
        fileSystem.binRepository.writeSysInfo(fileSystem);
        fileSystem.binRepository.diskSegmentLayout();
        return fileSystem;
    }

    public int getSystemVersion() {
        return systemVersion;
    }

    public String getOwner() {
        return owner;
    }

    public String getSystemFileName() {
        return systemFileName;
    }

    public BinRepository getBinRepository() {
        return binRepository;
    }

    public Segment[] getSegments() {
        return segments;
    }

    public void setSystemRepository(String systemRepository) {
        this.systemRepository = systemRepository;
    }
}
