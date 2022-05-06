package com.c19501.filesystem;

import com.c19501.filesystem.FileSystem.FileSystem;

public class Program {
    public static void main(String[] args) throws Exception {
        FileSystem fileSystem = FileSystem.createNew();
        fileSystem.save();
        fileSystem.load();
    }
}
