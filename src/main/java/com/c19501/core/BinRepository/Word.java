package com.c19501.core.BinRepository;

import java.io.Serializable;

public class Word implements Serializable {
    private char bytes;

    public char getBytes() {
        return bytes;
    }

    void setBytes(char bytes) {
        this.bytes = bytes;
    }
}
