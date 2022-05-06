package com.c19501.filesystem.repository.repoVariants;

import com.c19501.filesystem.FileSystem.configLoader.ConfigLoader;
import com.c19501.filesystem.repository.Repository;

import java.io.*;
import java.util.Properties;

public class BinRepository extends Repository implements Serializable  {

    public BinRepository() {
        super();
        Properties prop = ConfigLoader.load(new File("src/main/resources/config.properties"));
        this.systemFileName = prop.getProperty("fs.systemBinFileName");
        this.systemRepository = prop.getProperty("fs.systemBinRepository");
    }

    private void writeInStream(ObjectOutputStream objectOutputStreams) throws IOException {
        objectOutputStreams.writeObject(this);
    }

    public void writeRepository() {
        try {

            File binFile = new File(systemRepository + "/" + systemFileName);
            FileOutputStream fos;
            ObjectOutputStream objectOutputStreams;
            fos = new FileOutputStream(binFile);
            objectOutputStreams = new ObjectOutputStream(fos);
            this.writeInStream(objectOutputStreams);
            objectOutputStreams.flush();
            objectOutputStreams.close();
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Repository loadRepository(){
        String path = systemRepository + "/" + systemFileName;
        File file = new File(path);
        BinRepository binRepository = new BinRepository();
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream oin = new ObjectInputStream(fis);
            binRepository = (BinRepository) oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return binRepository;
    }
}
