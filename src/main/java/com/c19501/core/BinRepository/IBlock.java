package com.c19501.core.BinRepository;

public interface IBlock {
    boolean isFull();

    void writeOnlyOneIntegerAsString(int integer);

    void writeOnlyOneString(String string);

    IBlockIterator<Word> createIterator();
}
