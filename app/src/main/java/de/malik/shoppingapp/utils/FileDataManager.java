package de.malik.shoppingapp.utils;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FileDataManager {

    public static final String KEY_DEV_FEATURES = FileManager.KEYS[0];
    public static final String KEY_CURRENT_REFERENCE = FileManager.KEYS[1];
    private HashMap<String, String> data;
    private boolean isDeveloper;
    private String currentRef;
    private File mFile;

    public FileDataManager(File file) {
        mFile = file;
        try {
            data = DataParser.parseAppData(FileReader.readLines(file));
            currentRef = data.get(KEY_CURRENT_REFERENCE);
            isDeveloper = Boolean.parseBoolean(data.get(KEY_DEV_FEATURES));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveData() {
        try {
            FilePrinter.printAppData(new String[] {String.valueOf(isDeveloper), currentRef}, mFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setCurrentRef(String currentRef) {
        set(KEY_CURRENT_REFERENCE, currentRef);
        this.currentRef = currentRef;
    }

    public boolean isDeveloper() {
        return isDeveloper;
    }

    public void setDeveloper(boolean developer) {
        set(KEY_DEV_FEATURES, String.valueOf(developer));
        isDeveloper = developer;
    }

    public String getCurrentRef() {
        return currentRef;
    }

    public void set(String key, String value) {
        data.put(key, value);
    }
}
