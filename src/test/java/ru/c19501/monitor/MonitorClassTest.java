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
     * Проверяем функцию выдачи класса функции системы на правильную команду
     */
    public void checkRunFunction0() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("resize"); //запустили нашу функцию
        assertTrue(actual instanceof Resize); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("create"); //запустили нашу функцию
        assertTrue(actual instanceof CreateFile); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction3() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("defrag"); //запустили нашу функцию
        assertTrue(actual instanceof Defragmentation); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction4() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("delete"); //запустили нашу функцию
        assertTrue(actual instanceof DeleteFile); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction6() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("print"); //запустили нашу функцию
        assertTrue(actual instanceof Print); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction7() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("printA"); //запустили нашу функцию
        assertTrue(actual instanceof PrintA); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction8() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("help"); //запустили нашу функцию
        assertTrue(actual instanceof Help); //проверили что достался нужный клас
    }
    @Test
    public void checkRunFunction9() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //инициализируем список всех команд
        var actual = monitor.runFunction("info"); //запустили нашу функцию
        assertTrue(actual instanceof Info); //проверили что достался нужный клас
    }
    /**
     * Проверяем функцию выдачи класса функции системы на некорректную команду
     */
    @Test
    public void checkRunFunction10() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RegisteredCommands.init(); //инициализируем список всех команд
        String  commandName = "exe"; //некоректная команда
        var actual = monitor.runFunction(commandName); //запустили нашу функцию
        assertNull(actual); //проверили что null
    }
}
