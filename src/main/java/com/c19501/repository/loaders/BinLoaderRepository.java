package com.c19501.repository.loaders;

import com.c19501.repository.LoaderRepository;
import com.c19501.repository.Repository;
import com.c19501.repository.repositories.BinRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class BinLoaderRepository extends LoaderRepository {
    @Override
    public Repository loadRepository() {
        BinRepository binRepository = new BinRepository();
        String path = binRepository.getSystemRepository() + '/' + binRepository.getSystemFileName();
        File file = new File(path);
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream oin = new ObjectInputStream(fis)) {

            binRepository = (BinRepository) oin.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return binRepository;
    }
}
