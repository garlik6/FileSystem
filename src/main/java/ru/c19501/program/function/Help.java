package ru.c19501.program.function;

import static ru.c19501.program.monitor.RegisteredCommands.registeredCommands;

import ru.c19501.system.FileSystem;
import ru.c19501.program.struct.*;

public class Help extends BaseCommand implements iCommand {
    protected String classCommandName;

    public Help(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    public static String stringHelpPerCommand(String commandName) {
        return switch (commandName) {
            case "create" -> commandName + " - " + "Создание нового файла с заданием его имени и длины." + "\n" +
                    "В качестве аргументов команда принимает Имя, Тип и Размер нового файла.";
            case "delete" -> commandName + " - " + "Удаление существующего файла по имени." + "\n" +
                    "В качестве аргументов команда принимает Имя и Тип удаляемого файла.";
            case "resize" -> commandName + " - " + "Изменение размера существующего файла по имени." + "\n" +
                    "В качестве аргументов команда принимает Имя, Тип и Новый размер выбранного файла.";
            case "print" -> commandName + " - " + "Вывод всех данных о системе в виде списка названий файлов и их длинны." + "\n" +
                    "У этой команды нет аргументов.";
            case "printA" -> commandName + " - " + "Вывод всех данных о системе в виде списка названий файлов и их длинны в алфавитном порядке." + "\n" +
                    "У этой команды нет аргументов.";
            case "exit" -> commandName + " - " + "Завершение работы программы." + "\n" +
                    "У этой команды нет аргументов.";
            case "help" -> commandName + " - " + "Справка по команде." + "\n" +
                    "У этой команды нет аргументов.";
            case "commands" -> commandName + " - " + "Вывод списка всех доступных команд." + "\n" +
                    "У этой команды нет аргументов.";
            case "defrag" -> commandName + " - " + "Проведение дефрагментации системы с целью оптимизации свободного места." + "\n" +
                    "У этой команды нет аргументов.";
            case "Hello" -> commandName + " - " + "Проведение загрузки существующей файловой системы или создание новой." + "\n" +
                    "В качестве аргументов команда принимает Имя, Размер и Количество сегментов файловой системы.";
            case "out" -> null;
            default -> "Кажется по введённой вами команде ещё нет справки. Или вы ошиблись вводом. Попробуем ещё раз?";
        };
    }
    public boolean showStringHelpPerCommand(String stringHelpPerCommand){
        if (stringHelpPerCommand == null)
            return false;
        monitor.writeMessage(stringHelpPerCommand + "\n" +
                "Если нужна справка по команде, введите её название. Если нет - введите out.");
        return true;
    }

    @Override
    public void execute(FileSystem fs) {

        monitor.writeMessage("Если нужна справка по команде, введите её название. Если нет - введите exit.");

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
