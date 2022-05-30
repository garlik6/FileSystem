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
            case "create" -> commandName + " - " + "�������� ������ ����� � �������� ��� ����� � �����." + "\n" +
                    "� �������� ���������� ������� ��������� ���, ��� � ������ ������ �����.";
            case "delete" -> commandName + " - " + "�������� ������������� ����� �� �����." + "\n" +
                    "� �������� ���������� ������� ��������� ��� � ��� ���������� �����.";
            case "resize" -> commandName + " - " + "��������� ������� ������������� ����� �� �����." + "\n" +
                    "� �������� ���������� ������� ��������� ���, ��� � ����� ������ ���������� �����.";
            case "print" -> commandName + " - " + "����� ���� ������ � ������� � ���� ������ �������� ������ � �� ������." + "\n" +
                    "� ���� ������� ��� ����������.";
            case "printA" -> commandName + " - " + "����� ���� ������ � ������� � ���� ������ �������� ������ � �� ������ � ���������� �������." + "\n" +
                    "� ���� ������� ��� ����������.";
            case "exit" -> commandName + " - " + "���������� ������ ���������." + "\n" +
                    "� ���� ������� ��� ����������.";
            case "help" -> commandName + " - " + "������� �� �������." + "\n" +
                    "� ���� ������� ��� ����������.";
            case "commands" -> commandName + " - " + "����� ������ ���� ��������� ������." + "\n" +
                    "� ���� ������� ��� ����������.";
            case "defrag" -> commandName + " - " + "���������� �������������� ������� � ����� ����������� ���������� �����." + "\n" +
                    "� ���� ������� ��� ����������.";
            case "Hello" -> commandName + " - " + "���������� �������� ������������ �������� ������� ��� �������� �����." + "\n" +
                    "� �������� ���������� ������� ��������� ���, ������ � ���������� ��������� �������� �������.";
            case "out" -> null;
            default -> "������� �� �������� ���� ������� ��� ��� �������. ��� �� �������� ������. ��������� ��� ���?";
        };
    }
    public boolean showStringHelpPerCommand(String stringHelpPerCommand){
        if (stringHelpPerCommand == null)
            return false;
        monitor.writeMessage(stringHelpPerCommand + "\n" +
                "���� ����� ������� �� �������, ������� � ��������. ���� ��� - ������� out.");
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
