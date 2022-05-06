package com.c19501.filesystem.repository.repoVariants;

import com.c19501.filesystem.FileSystem.configLoader.ConfigLoader;
import com.c19501.filesystem.repository.Repository;
import com.c19501.filesystem.repository.files.Segment;

import java.io.*;
import java.util.ArrayList;
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
            ObjectOutputStream objectOutputStream;
            fos = new FileOutputStream(binFile);
            objectOutputStream = new ObjectOutputStream(fos);
            this.writeInStream(objectOutputStream);
            objectOutputStream.flush();
            objectOutputStream.close();
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(System.out);
            this.writeInStream(objectOutputStream);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
