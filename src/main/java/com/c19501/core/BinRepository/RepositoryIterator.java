package com.c19501.core.BinRepository;

import com.c19501.core.configLoader.ConfigLoader;

import javax.imageio.stream.ImageOutputStream;
import java.io.File;
import java.util.Iterator;
import java.util.Properties;

public class RepositoryIterator implements IRepositoryIterator<Block>{

    BinRepository binRepository;
    int current = 0;

    public RepositoryIterator(BinRepository binRepository) {
        this.binRepository = binRepository;
    }

    public boolean hasNext() {
        File file = new File("src/config.properties");
        Properties properties = ConfigLoader.load(file);
        return current < Integer.parseInt(properties.getProperty("fs.maxBlockCount"));
    }

    public Block next() {
        Block currentBlock = binRepository.getBlocks().get(current);
        current++;
        return currentBlock;
    }
}

