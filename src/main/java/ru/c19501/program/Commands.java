package ru.c19501.program;

import ru.c19501.system.FileSystem;
import ru.c19501.system.FileSystemFactory;
import ru.c19501.system.FileSystemFactoryImpl;
import ru.c19501.model.FileRecord.FileRecordReturnDTO;
import ru.c19501.service.CoreService;
import ru.c19501.service.CoreServiceImpl;
import ru.c19501.service.config.JacksonConfig;

import java.util.Scanner;

public class Commands {
    private final CoreService coreService;
    private final FileSystem fileSystem;
    private final Scanner inS = new Scanner(System.in);


    public Commands() {
        FileSystemFactory fileSystemFactory = new FileSystemFactoryImpl();
        fileSystem = fileSystemFactory.getSystem();
        {
            if (!fileSystem.load()) {
                fileSystem.save();
            }
            coreService = new CoreServiceImpl(fileSystem, JacksonConfig.createObjectMapper());
        }
    }

        public boolean checkCommandName () {
            System.out.println("Введите команду (список команд вызывается коммандой Help): ");
            String input = inS.nextLine();
            switch (input) {
                case "Help":
                    help();
                    break;

                case "FindFile":
                    find();
                    break;

                case "CreateFile":
                    create();
                    break;

                case "DeleteFile":
                    delete();
                    break;


                case "PrintSystem":
                    printS();
                    break;

                case "Exit":
                    return false;

                default:
                    System.out.println("Данной команды не существует: " + input);
            }
            return true;

        }


        private void printS () {
            System.out.println("Файловая система:");
            for (FileRecordReturnDTO file : coreService.readFiles()) {
                System.out.println(file.getFileName() + "." + file.getFileType());
            }
        }



        private void delete () {
            System.out.println("Введите тип файла:");
            String type = inS.nextLine();
            System.out.println("Введите имя файла:");
            String name = inS.nextLine();
            boolean b = coreService.deleteFile(name, type);
            if (!b) {
                System.out.println("Файл " + name + "." + type + " не найден в системе.");
            } else
                System.out.println("Файл " + name + "." + type + " удалён.");
        }


        private void create () {
            System.out.println("Введите тип файла:");
            String type = inS.nextLine();
            System.out.println("Введите имя файла:");
            String name = inS.nextLine();
            System.out.println("Введите размер файла:");
            int i = inS.nextInt();
            coreService.createFile(name, type, i);
            System.out.println("Файл " + name + "." + type + " успешно создан.");
        }


        private void find () {
            System.out.println("Введите тип файла:");
            String type = inS.nextLine();
            System.out.println("Введите имя файла:");
            String name = inS.nextLine();
            FileRecordReturnDTO file = coreService.foundFile(name, type);
            if (file == null) {
                System.out.println("Файл " + name + "." + type + " не найден в системе.");
            } else
                System.out.println("Файл " + name + "." + type + " найден. Дата и время создания: " + file.getCreationDate());
        }

        private void help () {
            System.out.print("""
                    Команды:

                    1.Help (Вывод краткого описания каждой команды)
                                     
                    2.Exit (Сохранение системы и завершение работы)
                                        
                    3.CreateFile (Создание нового файла по его имени и длине)
                                        
                    4.DeleteFile (Удаление файла по его имени)
                                        
                    5.Defragmentation (Дефрагментация системы)
                                        
                    6.FindFile (Поиск файла по его имени)
                                        
                    7.PrintSystem (Вывод всех файлов системы)
                           """);
        }

        public void exit () {
            fileSystem.save();
            System.out.println("Работа монитора команд завершена. Файловая система сохранена.");
        }
        public void hello(){
            System.out.println("Монитор команд запущен. Файловая система загружена.");
        }
    }
