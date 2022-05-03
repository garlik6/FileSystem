package com.c19501.core.BinRepository;

import java.io.Serializable;

public class Block implements Serializable, IBlock {
    private int cursor = 0;


    public int getNumber() {
        return number;
    }

    private final int number;
    private final Word[] data = new Word[256];

    public Word[] getData() {
        return data;
    }

    Block(int number) {
        this.number = number;
    }

    protected void moveCursorForward(int words) throws IllegalAccessException {
        if (words > 0 && cursor + words <= 255)
            cursor += words;
        else throw new IllegalAccessException();
    }

    public boolean isFull() {
        return cursor <= 255;
    }

    public void addWord(char word) {
        if (!isFull()) {
            data[cursor].setBytes(word);
            try {
                moveCursorForward(1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    public void writeOnlyOneIntegerAsString(int integer) {
        if (cursor == 0) {
            String intString = integerToBinaryStringWithPadding(integer);
            writeOnlyOneString(intString);
        }
    }

    public void writeOnlyOneString(String string)  {
        if (checkString(string) && cursor == 0) {
            try {
                moveCursorForward(255 - (string.length() - 1));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            char[] chars = string.toCharArray();
            for (char i : chars
            ) {
                addWord(i);
            }
        }
    }

    @Override
    public IBlockIterator<Word> createIterator() {
        return new BlockIterator(this);
    }

    private boolean checkString(String string) {
        return string.length() <= 256 && string.length() > 0;
    }

    private String integerToBinaryStringWithPadding(int integer) {
        return String.format(Integer.toBinaryString(integer), 32).replaceAll(" ", "0");
    }

    public int getCursor() {
        return cursor;
    }


}
