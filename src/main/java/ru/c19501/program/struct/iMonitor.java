package ru.c19501.program.struct;

public interface iMonitor {
    void writeMessage(String userMessage);

    int readInt(String userMessage);
    String readString(String userMessage);
}
