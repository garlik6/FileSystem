package ru.c19501.program.function;

import ru.c19501.core.FileSystem;
import ru.c19501.program.struct.iMonitor;

public class BaseCommand {

    protected iMonitor monitor;
    protected FileSystem fileSystem;

    public BaseCommand(iMonitor im, FileSystem fileSystem) {
        this.monitor = im;
        this.fileSystem = fileSystem;
    }

    public BaseCommand(){
        monitor = null;
        fileSystem = null;
    }

}
