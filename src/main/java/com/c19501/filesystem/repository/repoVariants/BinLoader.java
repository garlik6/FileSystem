package com.c19501.filesystem.repository.repoVariants;

import com.c19501.filesystem.repository.RepoLoader;
import com.c19501.filesystem.repository.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class BinLoader extends RepoLoader {
    @Override
    public Repository loadRepository(){
        BinRepository binRepository = new BinRepository();
        String path = binRepository.getSystemRepository() + "/" + binRepository.getSystemFileName();
        File file = new File(path);
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
