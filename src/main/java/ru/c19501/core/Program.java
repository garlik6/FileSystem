package ru.c19501.core;

import ru.c19501.core.system.FileSystemFactoryImpl;
import ru.c19501.core.system.FileSystemImpl;

public class Program {
    public static void main(String[] args) {
       FileSystemFactory factory = new FileSystemFactoryImpl();
       FileSystem fileSystem = factory.getSystem();
       fileSystem.print();
    }
}
