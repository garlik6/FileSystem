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
        expected.add("Введите команду \n(Для просмотра списка команд введите commands)");
        expected.add(">");
        expected.add("Введите команду \n(Для просмотра списка команд введите commands)");
        expected.add(">");
        expected.add("Сеанс закончен, всего доброго!");
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
        expected.add("Монитор команд запущен.");
        expected.add("Введите имя существующей файловой системы: ");
        expected.add("Файловая система MySys загружена.\nКоличество сегментов: 30\nРазмер файловой системы: 100\nСвободно: 100");
        expected.add("Введите команду \n(Для просмотра списка команд введите commands):");
        expected.add(">");
        expected.add("Сеанс закончен, всего доброго!");
        stream.stringListInput.add("MySys");
        stream.stringListInput.add("exit");
    }
    @Test
    public void checkMainInit2() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        RegisteredCommands.init();
        StreamActionsFake stream = (StreamActionsFake) monitor.stream;
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("Монитор команд запущен.");
        expected.add("Введите имя существующей файловой системы: ");
        expected.add("Файловая система new не найдена, её необходимо создать.\nВведите размер файловой системы: ");
        expected.add("Введите количество сегментов файловой системы: ");
        expected.add("Файловая система new создана.");
        expected.add("Введите команду \n(Для просмотра списка команд введите commands):");
        expected.add(">");
        expected.add("Сеанс закончен, всего доброго!");
        stream.stringListInput.add("new");
        stream.stringListInput.add("20");
        stream.stringListInput.add("4");
        stream.stringListInput.add("exit");
    }
}
