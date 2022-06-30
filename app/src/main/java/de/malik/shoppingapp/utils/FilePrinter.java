package de.malik.shoppingapp.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FilePrinter {

    public static void printAppData(String[] values, File file) throws IOException {
        PrintWriter printer = createPrinter(file);
        try {
            for (int i = 0; i < FileManager.KEYS.length; i++) {
                printer.println(FileManager.KEYS[i] + FileManager.SPLIT_REGEX + values[i]);
            }
        } finally {
            printer.flush();
            printer.close();
        }
    }

    private static PrintWriter createPrinter(File file) throws IOException {
        return new PrintWriter(new BufferedWriter(new FileWriter(file, false)));
    }
}
