package ru.c19501.monitor;

import ru.c19501.program.function.*;
import ru.c19501.program.monitor.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.c19501.system.*;
import java.lang.reflect.InvocationTargetException;



public class MonitorClassTest {
    static MonitorClass monitor = new MonitorClass(new FileSystemFactoryImpl().getSystem(), new StreamActionsFake());
    @Test
    /*
     * ��������� ������� ������ ������ ������� ������� �� ���������� �������
     */
    public void checkRunFunction0() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //�������������� ������ ���� ������
        var actual = monitor.runFunction("resize"); //��������� ���� �������
        assertTrue(actual instanceof Resize); //��������� ��� �������� ������ ����
    }
    @Test
    public void checkRunFunction1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //�������������� ������ ���� ������
        var actual = monitor.runFunction("create"); //��������� ���� �������
        assertTrue(actual instanceof CreateFile); //��������� ��� �������� ������ ����
    }
    @Test
    public void checkRunFunction3() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //�������������� ������ ���� ������
        var actual = monitor.runFunction("defrag"); //��������� ���� �������
        assertTrue(actual instanceof Defragmentation); //��������� ��� �������� ������ ����
    }
    @Test
    public void checkRunFunction4() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //�������������� ������ ���� ������
        var actual = monitor.runFunction("delete"); //��������� ���� �������
        assertTrue(actual instanceof DeleteFile); //��������� ��� �������� ������ ����
    }
    @Test
    public void checkRunFunction6() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //�������������� ������ ���� ������
        var actual = monitor.runFunction("print"); //��������� ���� �������
        assertTrue(actual instanceof Print); //��������� ��� �������� ������ ����
    }
    @Test
    public void checkRunFunction7() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //�������������� ������ ���� ������
        var actual = monitor.runFunction("printA"); //��������� ���� �������
        assertTrue(actual instanceof PrintA); //��������� ��� �������� ������ ����
    }
    @Test
    public void checkRunFunction8() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //�������������� ������ ���� ������
        var actual = monitor.runFunction("help"); //��������� ���� �������
        assertTrue(actual instanceof Help); //��������� ��� �������� ������ ����
    }
    @Test
    public void checkRunFunction9() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //�������������� ������ ���� ������
        var actual = monitor.runFunction("info"); //��������� ���� �������
        assertTrue(actual instanceof Info); //��������� ��� �������� ������ ����
    }
    /**
     * ��������� ������� ������ ������ ������� ������� �� ������������ �������
     */
    @Test
    public void checkRunFunction10() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //�������������� ������ ���� ������
        String  commandName = "exe"; //����������� �������
        var actual = monitor.runFunction(commandName); //��������� ���� �������
        assertNull(actual); //��������� ��� null
    }
}
