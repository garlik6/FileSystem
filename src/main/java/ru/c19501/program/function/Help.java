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
        StringBuilder returnStr= new StringBuilder();
        for (String key : registeredCommands.keySet()) {
            returnStr.append("* ").append(key).append("\n");
        }
        return returnStr.toString();
    }
    public void showStringAllCommandsHelp(){
        monitor.writeMessage("�������:");
        monitor.writeMessage(stringAllCommandsHelp());
    }

    public static String stringHelpPerCommand(String commandName) {
        return switch (commandName) {
            case "create" -> commandName + " - " + "�������� ������ ����� � �������� ��� ����� � �����." + "\n" +
                    "� �������� ���������� ������� ��������� ���, ��� � ������ ������ �����.";
            case "delete" -> commandName + " - " + "�������� ������������� ����� �� �����." + "\n" +
                    "� �������� ���������� ������� ��������� ��� � ��� ���������� �����.";
            case "print" -> commandName + " - " + "����� ���� ������ � ������� � ���� ������ �������� ������ � �� ������." + "\n" +
                    "� ���� ������� ��� ����������.";
            case "printA" -> commandName + " - " + "����� ���� ������ � ������� � ���� ������ �������� ������ � �� ������ � ���������� �������." + "\n" +
                    "� ���� ������� ��� ����������.";
            case "help" -> commandName + " - " + "����� ������ ������ � ���������������� ������." + "\n" +
                    "� ���� ������� ��� ����������.";
            case "defrag" -> commandName + " - " + "���������� �������������� ������� � ����� ����������� ���������� �����." + "\n" +
                    "� ���� ������� ��� ����������.";
            case "down" -> commandName + " - " + "���������� �������� ������������ �������� �������." + "\n" +
                    "� ���� ������� ��� ����������.";
            case "exit" -> null;
            default -> "������� �� �������� ���� ������� ��� ��� �������. ��� �� �������� ������. ��������� ��� ���?";
        };
    }
    public boolean showStringHelpPerCommand(String stringHelpPerCommand){
        if (stringHelpPerCommand == null)
            return false;
        monitor.writeMessage(stringHelpPerCommand + "\n" +
                "���� ����� ������� �� �������, ������� � ��������. ���� ��� - ������� exit.");
        return true;
    }

    @Override
    public void execute(FileSystem fs) {

        monitor.writeMessage("���� ����� ������� �� �������, ������� � ��������. ���� ��� - ������� exit.");

        String stringHelpPerCommand;
        do{
            readParameters();
            stringHelpPerCommand=stringHelpPerCommand(classCommandName);
        }

        while (showStringHelpPerCommand(stringHelpPerCommand));
    }

    @Override
    public void readParameters() {
        this.classCommandName = monitor.readString("������� �������:");
    }
}
