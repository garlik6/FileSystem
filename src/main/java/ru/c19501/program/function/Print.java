package ru.c19501.program.function;

import ru.c19501.system.FileSystem;
import ru.c19501.model.FileRecord.FileRecordReturnDTO;
import ru.c19501.program.struct.*;
import ru.c19501.service.CoreService;
import ru.c19501.service.CoreServiceImpl;
import ru.c19501.service.config.JacksonConfig;

public class Print extends BaseCommand implements iCommand {
    public Print(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    public static String toString(FileSystem fs) {

            StringBuilder string = new StringBuilder();
            CoreService coreService = new CoreServiceImpl(fs, JacksonConfig.createObjectMapper());

            for (FileRecordReturnDTO file : coreService.readFiles()) {
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
