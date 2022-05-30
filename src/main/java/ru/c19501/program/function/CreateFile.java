package ru.c19501.program.function;

import ru.c19501.system.FileSystem;
import ru.c19501.program.struct.iCommand;
import ru.c19501.program.struct.iMonitor;
import ru.c19501.service.CoreService;
import ru.c19501.service.CoreServiceImpl;
import ru.c19501.service.config.JacksonConfig;

public class CreateFile extends BaseCommand implements iCommand {
    protected String[] fileName = new String[3];
    protected int length;

    public CreateFile(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }


    @Override
    public void execute(FileSystem fs) {
        readParameters();
        CoreService coreService = new CoreServiceImpl(fs, JacksonConfig.createObjectMapper());
        boolean b = coreService.createFile(fileName[1], this.fileName[2], length);
        if (b) {
            System.out.println("Файл " + fileName[1] + "." + fileName[2] + " успешно создан.");
        } else
            System.out.println("Не удалось добавить файл(не хватает места либо неправильный формат) " + fileName[1] + "." + fileName[2] + ".");
        fs.save(fs.getName());
    }

    @Override
    public void readParameters() {
        this.fileName[1] = monitor.readString("Введите имя файла");
        this.fileName[2] = monitor.readString("Введите тип файла");
        this.length = monitor.readInt("Введите размер файла");
    }


}
