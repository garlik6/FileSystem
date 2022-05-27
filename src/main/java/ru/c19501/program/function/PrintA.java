package ru.c19501.program.function;

import ru.c19501.model.FileRecord.FileRecordReturnDTO;
import ru.c19501.program.struct.iCommand;
import ru.c19501.program.struct.iMonitor;
import ru.c19501.service.CoreService;
import ru.c19501.service.CoreServiceImpl;
import ru.c19501.service.config.JacksonConfig;
import ru.c19501.system.FileSystem;

import java.util.ArrayList;

public class PrintA extends BaseCommand implements iCommand {
    public PrintA(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    public static String toString(FileSystem fs) {


        StringBuilder string = new StringBuilder();
        CoreService coreService = new CoreServiceImpl(fs, JacksonConfig.createObjectMapper());

        string.append("Файловая система (в алфавитном порядке): ").append("\n");

        for (FileRecordReturnDTO file : coreService.readFilesNaturalOrder()) {
            string.append(file).append("\n");
        }
        return string.toString();
    }

    @Override
    public void execute(FileSystem fs) {
        monitor.writeMessage(toString(fs));
    }

    @Override
    public void readParameters() {
    }

}
