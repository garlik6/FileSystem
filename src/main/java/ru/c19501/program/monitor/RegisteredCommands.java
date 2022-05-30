package ru.c19501.program.monitor;

import java.util.HashMap;
import java.util.Map;

import ru.c19501.program.function.*;

public class RegisteredCommands {
    public static Map<String, String> registeredCommands = new HashMap<>();

    public static void init() {
        registeredCommands.put("create", CreateFile.class.getName());
        registeredCommands.put("delete", DeleteFile.class.getName());
        registeredCommands.put("print", Print.class.getName());
        registeredCommands.put("printA", PrintA.class.getName());
        registeredCommands.put("help", Help.class.getName());
        registeredCommands.put("commands", AllComm.class.getName());
        registeredCommands.put("Hello", Hello.class.getName());
        registeredCommands.put("resize", Resize.class.getName());
        registeredCommands.put("info", Info.class.getName());
        registeredCommands.put("defrag", Defragmentation.class.getName());
    }

}
