package ru.c19501.program.function;

import ru.c19501.system.FileSystem;
import ru.c19501.program.struct.iCommand;
import ru.c19501.program.struct.iMonitor;


public class Hello extends BaseCommand implements iCommand {

    public Hello(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }
    @Override
    public void execute(FileSystem fs) {
        readParameters();
        if (!fs.load()) {
            fs.save();
        }
    }

    @Override
    public void readParameters() {
        monitor.writeMessage("Монитор команд запущен. Файловая система загружена.");
    }

}
