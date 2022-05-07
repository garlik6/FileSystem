package com.c19501;

import com.c19501.system.FileSystem;

public class Program {
    public static void main(String[] args) {
       FileSystem fileSystem = new FileSystem();
       fileSystem.load();
       fileSystem.print();
    }
}
