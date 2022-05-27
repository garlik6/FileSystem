package ru.c19501.program.function;

import ru.c19501.program.struct.iCommand;
import ru.c19501.program.struct.iMonitor;
import ru.c19501.system.FileSystem;

import static ru.c19501.program.monitor.RegisteredCommands.registeredCommands;

public class AllComm extends BaseCommand implements iCommand {
    protected String classCommandName;

    public AllComm(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    public static String stringAllCommandsHelp(){
        StringBuilder returnStr= new StringBuilder();
        for (String key : registeredCommands.keySet()) {
            returnStr.append("* ").append(key).append("\n");
        }
        return returnStr.toString();
    }
    public void showStringAllCommandsHelp(){
        monitor.writeMessage("Команды системы:");
        monitor.writeMessage(stringAllCommandsHelp());
    }

    @Override
    public void execute(FileSystem fs) {
        showStringAllCommandsHelp();
    }

    @Override
    public void readParameters() {
    }

}
