package ru.c19501.program.function;

import ru.c19501.program.struct.iCommand;
import ru.c19501.program.struct.iMonitor;
import ru.c19501.service.CoreService;
import ru.c19501.service.CoreServiceImpl;
import ru.c19501.service.config.JacksonConfig;
import ru.c19501.system.FileSystem;

public class Resize extends BaseCommand implements iCommand {

    public Resize(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    protected String[] fileName = new String[3];
    protected int length;

    @Override
    public void execute(FileSystem fs) {
        readParameters();
        CoreService coreService = new CoreServiceImpl(fs, JacksonConfig.createObjectMapper());
        boolean b = coreService.addInfoToFile(fileName[1], this.fileName[2], length);
        if (b) {
            System.out.println("Размер файла " + fileName[1] + "." + fileName[2] + " изменён на." + length);
        } else
            System.out.println("Не удалось изменить размер файла " + fileName[1] + "." + fileName[2] + ".");
        fs.save(fs.getName());

    }
    @Override
    public void readParameters() {
        this.fileName[1] = monitor.readString("Введите имя файла");
        this.fileName[2] = monitor.readString("Введите тип файла");
        this.length = monitor.readInt("Введите новый размер файла");
    }
}
