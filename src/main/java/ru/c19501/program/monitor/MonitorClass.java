package ru.c19501.program.monitor;

import ru.c19501.program.struct.*;
import ru.c19501.system.FileSystem;

import java.lang.reflect.InvocationTargetException;
import static ru.c19501.program.monitor.RegisteredCommands.registeredCommands;

public class MonitorClass implements iMonitor {
    public FileSystem fs;
    public iStreamActions stream;

    public MonitorClass(FileSystem fs, iStreamActions stream) {
        this.fs = fs;
        this.stream = stream;
    }


    public iCommand runFunction(String commandName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String command = registeredCommands.get(commandName);
        if (command==null){
            writeMessage("Команда не найдена, попробуйте ещё раз или введите commands");
            return null;
        }
        return (iCommand) Class.forName(command)
                .getConstructor(iMonitor.class, FileSystem.class)
                .newInstance(this, fs);
    }

    @Override
    public void writeMessage(String userMessage) {
        stream.println(userMessage);
    }

    @Override
    public String readString(String userMessage) {
        writeMessage(userMessage);
        return stream.getLine();
    }

    @Override
    public int readInt(String userMessage){
        writeMessage(userMessage);
        return stream.nextInt();
    }
}
