package ru.c19501.core;

public interface FileSystem {
    void save();

    void load();

    void print();

    static FileSystem createNew() throws Exception {
        return null;
    }
}
