package ru.c19501.program.struct;

import ru.c19501.core.FileSystem;

public interface iCommand {
    void execute(FileSystem fs);

    void readParameters();
}