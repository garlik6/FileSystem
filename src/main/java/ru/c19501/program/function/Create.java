package ru.c19501.program.function;

import ru.c19501.program.struct.iCommand;
import ru.c19501.program.struct.iMonitor;
import ru.c19501.system.FileSystem;

public class Create extends BaseCommand implements iCommand {
    public Create(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    protected String Sysname;

    @Override
    public void execute(FileSystem fs) {
        readParameters();
        fs.save(Sysname);
        monitor.writeMessage("Файловая система "+fs.getName()+" создана.");
    }

    @Override
    public void readParameters() {
        this.Sysname = monitor.readString("Введите имя файловой системы.");

    }
}
