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
        registeredCommands.put("find", FindFile.class.getName());
        registeredCommands.put("help", Help.class.getName());
        registeredCommands.put("start", Hello.class.getName());
    }

}
