package de.malik.shoppingapp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileReader {

    public static ArrayList<String> readLines(File file) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader = createReader(file);
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.charAt(0) != FileManager.COMMENT_PREFIX.charAt(0))
                    lines.add(line);
            }
        } finally {
            reader.close();
        }
        return lines;
    }

    private static BufferedReader createReader(File file) throws IOException {
        return new BufferedReader(new java.io.FileReader(file));
    }
}
