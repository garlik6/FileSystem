//package ru.c19501.core.repository.repositories;
//
//import ru.c19501.config.ConfigLoader;
//import ru.c19501.core.files.FileRecord;
//import ru.c19501.core.repository.Repository;
//
//import java.io.*;
//import java.util.List;
//import java.util.Properties;
//
//public class BinRepository extends Repository implements Serializable {
//
//    public BinRepository() {
//        super();
//        Properties prop = ConfigLoader.properties;
//        this.systemFileName = prop.getProperty("fs.systemBinFileName");
//        this.systemRepository = prop.getProperty("fs.systemBinRepository");
//    }
//
//    @Override
//    public String fileRecordsToString(FileRecord fileRecord) {
//        return null;
//    }
//
//    @Override
//    public String fileRecordsToString(List<FileRecord> fileRecords) {
//        return null;
//    }
//
//    private void writeInStream(ObjectOutputStream objectOutputStreams) throws IOException {
//        objectOutputStreams.writeObject(this);
//    }
//
//    public void writeRepository() {
//        File binFile = new File(systemRepository + '/' + systemFileName);
//        try (FileOutputStream fos = new FileOutputStream(binFile)) {
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
//            this.writeInStream(objectOutputStream);
//            objectOutputStream.flush();
//            objectOutputStream.close();
//            fos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void print() {
//        try {
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(System.out);
//            this.writeInStream(objectOutputStream);
//            objectOutputStream.flush();
//            objectOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}
