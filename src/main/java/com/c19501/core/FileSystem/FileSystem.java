package com.c19501.core.FileSystem;

import com.c19501.core.BinRepository.BinRepository;
import com.c19501.core.configLoader.ConfigLoader;
import com.c19501.core.files.Segment;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

public class FileSystem {
    private int systemVersion;
    private String owner;
    private String systemFileName;
    private String systemRepository;
    private BinRepository binRepository;
    private Segment[] segments = new Segment[31];


//    public boolean init(){
//
//    }

    public static boolean checkIfExists(File directory, String filename) {
        return Arrays.stream(Objects.requireNonNull(directory.listFiles())).anyMatch(file -> file.getName().equals(filename));
    }

    public boolean save() {

        File binFile = new File(systemRepository + "/" + systemFileName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(binFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        ObjectOutputStream objectOutputStreams;
        try {
            objectOutputStreams = new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            binRepository.print(objectOutputStreams);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean load() throws IOException {


        File binFile = new File(systemRepository + "/" + systemFileName);
        FileInputStream fis = new FileInputStream(binFile);
        ObjectInputStream oin = new ObjectInputStream(fis);

        try {
            binRepository = (BinRepository) oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
