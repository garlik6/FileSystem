package ru.c19501.monitor;

import ru.c19501.program.struct.iStreamActions;

import java.util.ArrayList;

    public class StreamActionsFake implements iStreamActions {
        ArrayList<String> stringListInput = new ArrayList<String>();
        ArrayList<String> stringListOutput = new ArrayList<String>();

        public void println(String value) {
            print(value);
            stringListOutput.add("");

        }
        public void print(String value) {
            if (stringListOutput.isEmpty()) stringListOutput.add("");

            stringListOutput.add(stringListOutput.remove(stringListOutput.size() - 1) + value);


        }
        public String getLine() {
            if (stringListInput.isEmpty())
                return "бширх";
            stringListOutput.add("");
            return stringListInput.remove(0);
        }
        public int nextInt() {
            if (stringListInput.isEmpty())
                return 0;
            return Integer.parseInt(stringListInput.remove(0));
        }
    }

