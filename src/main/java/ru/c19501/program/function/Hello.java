package ru.c19501.program.function;

import ru.c19501.system.FileSystem;
import ru.c19501.program.struct.iCommand;
import ru.c19501.program.struct.iMonitor;


public class Hello extends BaseCommand implements iCommand {

    public Hello(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    protected String Sysname;

    @Override
    public void execute(FileSystem fs) {
        if (!fs.load()) {
            fs.save(fs.getName());
        }
        monitor.writeMessage("Файловая система "+fs.getName()+" загружена.");
    }
//Количество сегментов и количество
    @Override
    public void readParameters() {
    }

}
