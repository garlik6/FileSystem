package ru.c19501.program.function;

import ru.c19501.system.FileSystem;
import ru.c19501.program.struct.iCommand;
import ru.c19501.program.struct.iMonitor;


public class Hello extends BaseCommand implements iCommand {

    public Hello(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    protected String sysname;
    protected int volume, segmentAmount;

    @Override
    public void execute(FileSystem fs) {
        sysname = monitor.readString("Введите имя существующей файловой системы: ");
        if (!fs.loadByName(sysname)) {
            readParameters();
            fs.save(sysname, volume, segmentAmount);
            monitor.writeMessage("Файловая система "+fs.getName()+" создана.");
        }else
        monitor.writeMessage("Файловая система "+fs.getName()+" загружена.\nКоличество сегментов: "+fs.getSeg()+"\nРазмер файловой системы: "+fs.getSpace()+"\nСвободно: "+fs.getFreeSpace());

    }
    @Override
    public void readParameters() {
        this.volume = monitor.readInt("Файловая система "+sysname+" не найдена, её необходимо создать.\nВведите размер файловой системы: ");
        this.segmentAmount = monitor.readInt("Введите количество сегментов файловой системы: ");
    }

}
