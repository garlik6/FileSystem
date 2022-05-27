package ru.c19501.program.function;

import ru.c19501.system.FileSystem;
import ru.c19501.program.struct.*;
import ru.c19501.service.CoreService;
import ru.c19501.service.CoreServiceImpl;
import ru.c19501.service.config.JacksonConfig;

public class DeleteFile extends BaseCommand implements iCommand {

    protected String[] fileName = new String[3];

    public DeleteFile(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }


    @Override
    public void execute(FileSystem fs) {
        readParameters();
        CoreService coreService = new CoreServiceImpl(fs, JacksonConfig.createObjectMapper());
        boolean b = coreService.deleteFile(fileName[1], fileName[2]);
        if (!b) {
            System.out.println("���� " + fileName[1] + "." + fileName[2] + " �� ������ � �������.");
        } else
            System.out.println("���� " + fileName[1] + "." + fileName[2] + " �����.");
        fs.save(fs.getName());
    }

    @Override
    public void readParameters() {
        this.fileName[1] = monitor.readString("������� ��� �����");
        this.fileName[2] = monitor.readString("������� ��� �����");
    }

}
