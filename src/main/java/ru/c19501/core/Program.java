package ru.c19501.core;

import ru.c19501.core.system.FileSystemImpl;

public class Program {
    public static void main(String[] args) {
       FileSystemImpl fileSystemImpl = new FileSystemImpl();
       fileSystemImpl.load();
       fileSystemImpl.print();
    }
}
