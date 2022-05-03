package com.c19501.core.BinRepository;

public interface IBinRepository {
    IRepositoryIterator<Block> createIterator();
}
