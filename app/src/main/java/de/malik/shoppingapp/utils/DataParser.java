package de.malik.shoppingapp.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class DataParser {

    public static ArrayList<Product> parseProducts(ArrayList<String> data) {
        ArrayList<Product> products = new ArrayList<>();
        if (data.size() == 0) return new ArrayList<>();
        for (String record : data) {
            String[] cols = record.split(DatabaseManager.SPLIT_REGEX);
            products.add(new Product(Integer.parseInt(cols[2]), cols[0], cols[1]));
        }
        return products;
    }

    public static HashMap<String, String> parseAppData(ArrayList<String> lines) {
        HashMap<String, String> data = new HashMap<>();
        if (lines.size() == 0) return new HashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            String[] cols = lines.get(i).split(FileManager.SPLIT_REGEX);
            data.put(cols[0], cols[1]);
        }
        return data;
    }
}
