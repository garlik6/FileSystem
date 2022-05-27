package ru.c19501.program.function;

import ru.c19501.model.FileRecord.FileRecordReturnDTO;
import ru.c19501.program.struct.iCommand;
import ru.c19501.program.struct.iMonitor;
import ru.c19501.service.CoreService;
import ru.c19501.service.CoreServiceImpl;
import ru.c19501.service.config.JacksonConfig;
import ru.c19501.system.FileSystem;

public class Info extends BaseCommand implements iCommand {

    public Info(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    public static String toString(FileSystem fs) {


        StringBuilder string = new StringBuilder();
        CoreService coreService = new CoreServiceImpl(fs, JacksonConfig.createObjectMapper());

        string.append("Свободное место на диске: ").append(fs.getFreeSpace()).append("\n").append("Степень дерагментации:").append(fs.getDefragmentationExt()).append("\n");


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
