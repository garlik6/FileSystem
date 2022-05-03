package com.c19501.core.BinRepository;

import java.util.Iterator;

public interface IRepositoryIterator<T> extends Iterator<T> {
    T next();
    boolean hasNext();
}
