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
            monitor.writeMessage("�������� �������"+fs.getName()+" �������.");
        }else
        monitor.writeMessage("�������� ������� "+fs.getName()+" ���������.\n���������� ���������:"+fs.getSeg()+"\n������ �������� �������: "+fs.getSpace()+"\n��������: "+fs.getFreeSpace());

    }
    @Override
    public void readParameters() {
        this.Sysname = monitor.readString("�������� ������� �� ���� ���������, ���������� ������� �����.\n������� ��� �������� �������: ");
        this.volume = monitor.readInt("������� ������ �������� �������: ");
        this.segmentAmount = monitor.readInt("������� ���������� ��������� �������� �������: ");
    }

}
