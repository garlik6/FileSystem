package ru.c19501.program.function;

import static ru.c19501.program.monitor.RegisteredCommands.registeredCommands;

import ru.c19501.system.FileSystem;
import ru.c19501.program.struct.*;

public class Help extends BaseCommand implements iCommand {
    protected String classCommandName;

    public Help(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    public static String stringAllCommandsHelp(){
        String returnStr="";
        for (String key : registeredCommands.keySet()) {
            returnStr+="* " + key + "\n";
        }
        return returnStr;
    }
    public void showStringAllCommandsHelp(){
        monitor.writeMessage("Команды:");
        monitor.writeMessage(stringAllCommandsHelp());
    }

    public static String stringHelpPerCommand(String commandName) {
        return switch (commandName) {
            case "create" -> commandName + " - " + "Создание нового файла с заданием его имени и длины." + "\n" +
                    "В качестве аргументов команда принимает Имя, Тип и Размер нового файла.";
            case "delete" -> commandName + " - " + "Удаление существующего файла по имени." + "\n" +
                    "В качестве аргументов команда принимает Имя и Тип удаляемого файла.";
            case "find" -> commandName + " - " + "Поиск существующего файла по имени." + "\n" +
                    "В качестве аргументов команда принимает Имя и Тип искомого файла.";
            case "print" -> commandName + " - " + "Вывод всех данных о системе в виде списка названий файлов и их длинны" + "\n" +
                    "У этой команды нет аргументов.";
            case "help" -> commandName + " - " + "Вывод списка команд и функциональности каждой." + "\n" +
                    "У этой команды нет аргументов.";
            case "exit" -> null;
            default -> "Кажется по введённой вами команде ещё нет справки. Или вы ошиблись вводом. Попробуем ещё раз?";
        };
    }
    public boolean showStringHelpPerCommand(String stringHelpPerCommand){
        if (stringHelpPerCommand == null)
            return false;

        monitor.writeMessage(stringHelpPerCommand + "\n" +
                "Если нужна информация по команде, введите её название. Если нет - введите exit.");
        return true;
    }

    @Override
    public void execute(FileSystem fs) {
        showStringAllCommandsHelp();

        monitor.writeMessage("Если нужно подробнее об одной из них, введите её название. Если нет - введите ВЫХОД.");

        String stringHelpPerCommand;
        do{
            readParameters();
            stringHelpPerCommand=stringHelpPerCommand(classCommandName);
        }

        while (showStringHelpPerCommand(stringHelpPerCommand));
    }

    @Override
    public void readParameters() {
        this.classCommandName = monitor.readString("Введите команду:");
    }
}
