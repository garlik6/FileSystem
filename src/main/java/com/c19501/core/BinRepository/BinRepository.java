package com.c19501.core.BinRepository;

import com.c19501.core.FileSystem.FileSystem;
import com.c19501.core.configLoader.ConfigLoader;
import com.c19501.core.files.Segment;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class BinRepository implements Serializable {
    private final ArrayList<Block> blocks = new ArrayList<>();

    public void writeInStream(ObjectOutputStream objectOutputStreams) throws IOException {
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

    public void writeBinFile(File binFile) {
        try {
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

    public static BinRepository loadBinFile(File binFile){
        BinRepository binRepository = new BinRepository();
        try {

            FileInputStream fis = new FileInputStream(binFile);
            ObjectInputStream oin = new ObjectInputStream(fis);
            binRepository = (BinRepository) oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return binRepository;
    }


    public void diskSegmentLayout() {
        Properties properties = ConfigLoader.load(new File("src/config.properties"));
        int maxSegm = Integer.parseInt(properties.getProperty("fs.maxSegmentAmountInCatalog")) - 1;
        for (int i = 0; i <= maxSegm; i++) {
            Block block = addBlock();
            Segment segment = Segment.createSegment(i,0, 0);
            segment.writeSegmentInfoInBlock(block);
            addBlock();
        }
    }
}
