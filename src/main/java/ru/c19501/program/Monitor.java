package ru.c19501.program;


public class Monitor {

    private final Commands comm = new Commands();

    public void read() {
        comm.hello();
        while (comm.checkCommandName()){
        }
        comm.exit();
    }

}
