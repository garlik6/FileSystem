package ru.c19501.program.monitor;

import ru.c19501.program.struct.iStreamActions;

import java.util.Scanner;

public class StreamActions implements iStreamActions {
    public void println(String value){
        System.out.println(value);
    }
    public void print(String value){
        System.out.print(value);
    }
    public String getLine(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine().trim();
    }
    public int nextInt(){
        Scanner sc = new Scanner(System.in);
        int number = 1;
        do {
            if(number <= 0)
                System.out.println("Некорректные данные! Повторите попытку");
            while (!sc.hasNextInt()) {
                System.out.println("Некорректные данные! Повторите попытку");
                sc.next(); // this is important!
            }
            number = sc.nextInt();
        } while (number <= 0);
        return number;
    }

}
