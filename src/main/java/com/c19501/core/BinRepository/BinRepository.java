package com.c19501.core.BinRepository;

import com.c19501.core.FileSystem.FileSystem;
import com.c19501.core.configLoader.ConfigLoader;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

public class BinRepository implements Serializable {
    private ArrayList<Block> blocks = new ArrayList<>();

    public void print(ObjectOutputStream objectOutputStreams) throws IOException {
        objectOutputStreams.writeObject(this);
    }


    public Block addBlock() {
        Block block = new Block(blocks.size());
        blocks.add(block);
        return block;
    }

    public void writeSysInfo(FileSystem fileSystem) {
        addBlock();
        addBlock();
        blocks.get(1).writeOnlyOneInteger(fileSystem.getSystemVersion());
        addBlock();
        blocks.get(2).writeOnlyOneString(fileSystem.getSystemFileName());
        addBlock();
        blocks.get(3).writeOnlyOneString(fileSystem.getOwner());
        addBlock();
        addBlock();
    }

    public void diskSegmentLayout() {
        Properties properties = ConfigLoader.load(new File("src/config.properties"));
        int maxSegm = Integer.parseInt(properties.getProperty("fs.maxSegmentAmountInCatalog"));
        int maxBlockCount = Integer.parseInt(properties.getProperty("fs.maxBlockCount"));
        if (maxSegm > 31 || maxBlockCount > 1000)
            throw new IllegalArgumentException();
        for (int i = 0; i <= maxSegm; i++) {
            Block block = addBlock();
            block.addWord((char) maxSegm);
            block.addWord((char) i);
            block.addWord((char) 0);
            block.addWord((char) 0);
            if(i == maxSegm){
                block.addWord((char) (maxBlockCount/maxSegm + maxBlockCount%maxSegm));
            } else {
                block.addWord((char) (maxBlockCount/maxSegm));
            }


        }
    }
}
