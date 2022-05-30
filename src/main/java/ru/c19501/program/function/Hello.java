package ru.c19501.program.function;

import ru.c19501.system.FileSystem;
import ru.c19501.program.struct.iCommand;
import ru.c19501.program.struct.iMonitor;


public class Hello extends BaseCommand implements iCommand {

    public Hello(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    protected String Sysname;
    protected int volume, segmentAmount;

    @Override
    public void execute(FileSystem fs) {
        if (!fs.load()) {
            readParameters();
            fs.save(Sysname, volume, segmentAmount);
            monitor.writeMessage("Файловая система"+fs.getName()+" создана.");
        }else
        monitor.writeMessage("Файловая система "+fs.getName()+" загружена.\nКоличество сегментов:"+fs.getSeg()+"\nРазмер файловой системы: "+fs.getSpace()+"\nСвободно: "+fs.getFreeSpace());

    }
    @Override
    public void readParameters() {
        this.Sysname = monitor.readString("Файловая система не была загружена, необходимо создать новую.\nВведите имя файловой системы: ");
        this.volume = monitor.readInt("Введите размер файловой системы: ");
        this.segmentAmount = monitor.readInt("Введите количество сегментов файловой системы: ");
    }

}
