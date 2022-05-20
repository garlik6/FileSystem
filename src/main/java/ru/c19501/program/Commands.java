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
            System.out.println("������� ������� (������ ������ ���������� ��������� Help): ");
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
                    System.out.println("������ ������� �� ����������: " + input);
            }
            return true;

        }


        private void printS () {
            System.out.println("�������� �������:");
            for (FileRecordReturnDTO file : coreService.readFiles()) {
                System.out.println(file.getFileName() + "." + file.getFileType());
            }
        }



        private void delete () {
            System.out.println("������� ��� �����:");
            String type = inS.nextLine();
            System.out.println("������� ��� �����:");
            String name = inS.nextLine();
            boolean b = coreService.deleteFile(name, type);
            if (!b) {
                System.out.println("���� " + name + "." + type + " �� ������ � �������.");
            } else
                System.out.println("���� " + name + "." + type + " �����.");
        }


        private void create () {
            System.out.println("������� ��� �����:");
            String type = inS.nextLine();
            System.out.println("������� ��� �����:");
            String name = inS.nextLine();
            System.out.println("������� ������ �����:");
            int i = inS.nextInt();
            coreService.createFile(name, type, i);
            System.out.println("���� " + name + "." + type + " ������� ������.");
        }


        private void find () {
            System.out.println("������� ��� �����:");
            String type = inS.nextLine();
            System.out.println("������� ��� �����:");
            String name = inS.nextLine();
            FileRecordReturnDTO file = coreService.foundFile(name, type);
            if (file == null) {
                System.out.println("���� " + name + "." + type + " �� ������ � �������.");
            } else
                System.out.println("���� " + name + "." + type + " ������. ���� � ����� ��������: " + file.getCreationDate());
        }

        private void help () {
            System.out.print("""
                    �������:

                    1.Help (����� �������� �������� ������ �������)
                                     
                    2.Exit (���������� ������� � ���������� ������)
                                        
                    3.CreateFile (�������� ������ ����� �� ��� ����� � �����)
                                        
                    4.DeleteFile (�������� ����� �� ��� �����)
                                        
                    5.Defragmentation (�������������� �������)
                                        
                    6.FindFile (����� ����� �� ��� �����)
                                        
                    7.PrintSystem (����� ���� ������ �������)
                           """);
        }

        public void exit () {
            fileSystem.save();
            System.out.println("������ �������� ������ ���������. �������� ������� ���������.");
        }
        public void hello(){
            System.out.println("������� ������ �������. �������� ������� ���������.");
        }
    }
