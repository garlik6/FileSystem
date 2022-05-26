package ru.c19501;

import ru.c19501.core.system.FileSystemFactoryImpl;
import ru.c19501.program.monitor.MonitorClass;
import ru.c19501.program.monitor.RegisteredCommands;
import ru.c19501.program.monitor.StreamActions;
import ru.c19501.program.struct.*;
import java.lang.reflect.InvocationTargetException;

public class Main {
    static MonitorClass monitor = new MonitorClass(new FileSystemFactoryImpl().getSystem(), new StreamActions());

    public static void init() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException{
        var commandObject = monitor.runFunction("start");
        if (commandObject != null)
            commandObject.execute(monitor.fs);
    }

    public static void mainRealization(iStreamActions stream) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        stream.println("Введите команду \n(Если не помните команду, введите help)");
        while (true) {
            stream.print(">");
            String command = stream.getLine();

            if (command.equals("exit")){
                stream.print("Сеанс закончен, всего доброго!");
                break;
            }

            var commandObject = monitor.runFunction(command);
            if (commandObject != null)
                commandObject.execute(monitor.fs);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init();
        init();
        mainRealization(monitor.stream);
    }
}
