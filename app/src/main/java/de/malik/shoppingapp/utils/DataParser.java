package de.malik.shoppingapp.utils;

import java.util.ArrayList;

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
}
