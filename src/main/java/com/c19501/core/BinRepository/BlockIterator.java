package com.c19501.core.BinRepository;

public class BlockIterator implements IBlockIterator<Word>{

    Block block;

    public BlockIterator(Block block) {
        this.block = block;
    }

    @Override
    public Word next() {
        int cursor = block.getCursor();
        try {
            block.moveCursorForward(1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return block.getData()[cursor];
    }

    @Override
    public boolean hasNext() {
        return block.getCursor() != 255;
    }

    @Override
    public int numberFromBeginning() {
        return block.getCursor() ;
    }

    @Override
    public void writeNext(char word) {
        block.addWord(word);
    }
}
