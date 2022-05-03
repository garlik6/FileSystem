package com.c19501.core.BinRepository;

import java.io.Serializable;

public class Block implements Serializable {
    private int cursor = 0;
    private int number;
    private final Word[] data = new Word[256];

    Block(int number) {
        this.number = number;
    }

    public void moveCursorForward(int bits) {
        if (bits > 0 && cursor + bits <= 255)
            cursor += bits;
    }

    public boolean isFull() {
        return cursor <= 255;
    }

    public void addWord(char word) {
        if (!isFull()) {
            data[cursor].bytes = word;
            moveCursorForward(1);
        }
    }


    public void writeOnlyOneInteger(int integer) {
        if (cursor == 0) {
            String intString = integerToBinaryStringWithPadding(integer);
            writeOnlyOneString(intString);
        }
    }

    public void writeOnlyOneString(String string) {
        if (checkString(string) && cursor == 0) {
            moveCursorForward(255 - (string.length() - 1));
            char[] chars = string.toCharArray();
            for (char i : chars
            ) {
                addWord(i);
            }
        }
    }

    private boolean checkString(String string) {
        return string.length() <= 256 && string.length() > 0;
    }

    public String integerToBinaryStringWithPadding(int integer) {
        return String.format(Integer.toBinaryString(integer), 32).replaceAll(" ", "0");
    }
}
