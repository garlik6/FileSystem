package ru.c19501.core.repository.loaders;

import ru.c19501.core.repository.LoaderRepository;
import ru.c19501.core.repository.Repository;
import ru.c19501.core.repository.repositories.BinRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class BinLoaderRepository implements LoaderRepository {
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
