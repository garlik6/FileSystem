package com.c19501.core.BinRepository;

import java.util.Iterator;

public interface IBlockIterator<T> extends Iterator<T> {
    @Override
    T next();

    @Override
    boolean hasNext();

    int numberFromBeginning();

    void writeNext(char word);

}
