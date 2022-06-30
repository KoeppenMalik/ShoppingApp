package de.malik.shoppingapp.utils;

public class Product {

    private long mId;
    private String mName, mDescription;

    public Product(long id, String name, String description) {
        mId = id;
        mName = name;
        mDescription = description;
    }

    public static Product getInstance(DatabaseManager dbManager) {
        long id = dbManager.getNextId();
        return new Product(id, "", "");
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }
}
