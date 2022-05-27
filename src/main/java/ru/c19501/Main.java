package ru.c19501;


import ru.c19501.program.monitor.MonitorClass;
import ru.c19501.program.monitor.RegisteredCommands;
import ru.c19501.program.monitor.StreamActions;
import ru.c19501.program.struct.*;
import ru.c19501.system.FileSystemFactoryImpl;

import java.lang.reflect.InvocationTargetException;

public class Main {

    static MonitorClass monitor = new MonitorClass(new FileSystemFactoryImpl().getSystem(), new StreamActions());

    public static void init(iStreamActions stream) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        stream.println("������� ������ �������.");
        do {
            stream.println("(������� download ��� �������� ������������ ��� createSys ��� �������� ����� �������� �������)");
        } while( initialization(stream.getLine() ) );
    }

    public static boolean initialization(String choice) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var commandObject = monitor.runStart(choice);
        if (commandObject == null)
            return true;

        commandObject.execute(monitor.fs);
        return false;
    }

    public static void mainRealization(iStreamActions stream) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        while (true) {
            stream.println("������� ������� \n(��� ��������� ������ ������ ������� commands)");
            stream.print(">");
            String command = stream.getLine();

            if (command.equals("exit")){
                stream.print("����� ��������, ����� �������!");
                break;
            }

            var commandObject = monitor.runFunction(command);
            if (commandObject != null)
                commandObject.execute(monitor.fs);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init();
        init(monitor.stream);
        mainRealization(monitor.stream);
    }
}
