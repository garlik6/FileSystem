package ru.c19501.program.function;

import ru.c19501.program.struct.iCommand;
import ru.c19501.program.struct.iMonitor;
import ru.c19501.service.CoreService;
import ru.c19501.service.CoreServiceImpl;
import ru.c19501.service.config.JacksonConfig;
import ru.c19501.system.FileSystem;

public class Defragmentation extends BaseCommand implements iCommand {

    public Defragmentation(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }


    @Override
    public void execute(FileSystem fs) {
        CoreService coreService = new CoreServiceImpl(fs, JacksonConfig.createObjectMapper());
        coreService.defragmentation();
        readParameters();
        fs.save();
    }

    @Override
    public void readParameters() {
        monitor.writeMessage("Дефрагментация файловой системы успешно проведена.");
    }


}
