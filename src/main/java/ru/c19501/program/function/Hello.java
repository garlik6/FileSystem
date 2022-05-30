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
        sysname = monitor.readString("������� ��� ������������ �������� �������: ");
        if (!fs.loadByName(sysname)) {
            readParameters();
            fs.save(sysname, volume, segmentAmount);
            monitor.writeMessage("�������� ������� "+fs.getName()+" �������.");
        }else
        monitor.writeMessage("�������� ������� "+fs.getName()+" ���������.\n���������� ���������: "+fs.getSeg()+"\n������ �������� �������: "+fs.getSpace()+"\n��������: "+fs.getFreeSpace());

    }
    @Override
    public void readParameters() {
        this.volume = monitor.readInt("�������� ������� "+sysname+" �� �������, � ���������� �������.\n������� ������ �������� �������: ");
        this.segmentAmount = monitor.readInt("������� ���������� ��������� �������� �������: ");
    }

}
