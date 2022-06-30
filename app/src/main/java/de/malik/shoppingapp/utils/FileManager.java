package de.malik.shoppingapp.utils;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

    public static final String SPLIT_REGEX = "=";
    public static final String FILE_NAME = "app_data.csv";
    public static final String COMMENT_PREFIX = "#";
    public static final String[] KEYS = new String[] {
            "show_development_features", "current_list_reference"
    };
    public static final String[] DEFAULT_VALUES = new String[] {
            "false", "default"
    };

    private File mFile;

    public FileManager(String path) {
        try {
            createFile(path);
            ArrayList<String> lines = FileReader.readLines(mFile);
            if (lines.size() == 0) {
                // print default data
                FilePrinter.printAppData(DEFAULT_VALUES, mFile);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void createFile(String path) throws IOException {
        mFile = new File(path, FILE_NAME);
        mFile.createNewFile();
    }

    public File getFile() {
        return mFile;
    }
}
