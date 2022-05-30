package ru.c19501.monitor;

import ru.c19501.Main;
import ru.c19501.program.monitor.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.c19501.system.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class MainTest {
    static MonitorClass monitor = new MonitorClass(new FileSystemFactoryImpl().getSystem(), new StreamActionsFake());

    @Test
    public void checkMainRealization1() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        RegisteredCommands.init();
        StreamActionsFake stream = (StreamActionsFake) monitor.stream;

        ArrayList<String> expected = new ArrayList<String>();
        expected.add("������� ������� \n(��� ��������� ������ ������ ������� commands)");
        expected.add(">");
        expected.add("������� ������� \n(��� ��������� ������ ������ ������� commands)");
        expected.add(">");
        expected.add("����� ��������, ����� �������!");
        stream.stringListInput.add("exitt");
        stream.stringListInput.add("exit");


        Main.mainRealization(stream);
        assertEquals(expected, stream.stringListOutput);
    }
    @Test
    public void checkMainInit1() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        RegisteredCommands.init();
        StreamActionsFake stream = (StreamActionsFake) monitor.stream;
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("������� ������ �������.");
        expected.add("������� ��� ������������ �������� �������: ");
        expected.add("�������� ������� MySys ���������.\n���������� ���������: 30\n������ �������� �������: 100\n��������: 100");
        expected.add("������� ������� \n(��� ��������� ������ ������ ������� commands):");
        expected.add(">");
        expected.add("����� ��������, ����� �������!");
        stream.stringListInput.add("MySys");
        stream.stringListInput.add("exit");
    }
    @Test
    public void checkMainInit2() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        RegisteredCommands.init();
        StreamActionsFake stream = (StreamActionsFake) monitor.stream;
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("������� ������ �������.");
        expected.add("������� ��� ������������ �������� �������: ");
        expected.add("�������� ������� new �� �������, � ���������� �������.\n������� ������ �������� �������: ");
        expected.add("������� ���������� ��������� �������� �������: ");
        expected.add("�������� ������� new �������.");
        expected.add("������� ������� \n(��� ��������� ������ ������ ������� commands):");
        expected.add(">");
        expected.add("����� ��������, ����� �������!");
        stream.stringListInput.add("new");
        stream.stringListInput.add("20");
        stream.stringListInput.add("4");
        stream.stringListInput.add("exit");
    }
}
