package de.malik.shoppingapp.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class DatabaseManager {

    public static final String TAG = "DatabaseManager";
    public static final String ROOT_PATH = "default";
    public static final String CHILD_PATH = "product";
    public static final String SPLIT_REGEX = ";";

    private String currentPath = ROOT_PATH;
    private DatabaseReference mRef, mRootRef;
    private ArrayList<Product> mProducts = new ArrayList<>();
    private ArrayList<String> mListPaths = new ArrayList<>();
    private FirebaseDatabase db;

    public DatabaseManager() {
        db = FirebaseDatabase.getInstance("https://shopping-app-2ceff-default-rtdb.europe-west1.firebasedatabase.app/");
        mRef = db.getReference(currentPath);
        mRootRef = db.getReference();
        readLists();
        readDatabase();
    }

    public void switchReference(String path) {
        currentPath = path;
        mRef = db.getReference(path);
    }

    public void createNewList(String name) {
        Product placeholder = Product.getInstance(this);
        mRootRef.child(name).child(CHILD_PATH + placeholder.getId()).setValue(placeholder);
    }

    public void removeCurrentList() {
        mRootRef.child(currentPath).removeValue();
    }

    public void readLists() {
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> data = (HashMap<String, Object>) snapshot.getValue();
                    mListPaths.clear();
                    mListPaths.addAll(data.keySet());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public void readDatabase() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long startTime = System.nanoTime();
                Log.d(TAG, "Start data reading...");
                // reading data and converting into parsable ArrayList of String (format: name  description  id)
                if (snapshot.exists()) {
                    HashMap<String, Object> data = (HashMap<String, Object>) snapshot.getValue();
                    ArrayList<String> values = new ArrayList<>();
                    for (Object obj : data.values()) {
                        HashMap<String, Object> data1 = (HashMap<String, Object>) obj;
                        String value = "";
                        for (Object obj1 : data1.values()) {
                            value += obj1.toString() + SPLIT_REGEX;
                        }
                        values.add(value);
                    }
                    ArrayList<String> parsableValues = new ArrayList<>();
                    for (int i = 0; i < values.size(); i++) {
                        String str = values.get(values.size() - i - 1);
                        StringBuilder builder = new StringBuilder(str);
                        builder.deleteCharAt(str.length() -1);
                        parsableValues.add(i, builder.toString());
                    }
                    mProducts = DataParser.parseProducts(parsableValues);
                }
                Log.d(TAG, "Data reading finished. [" + (System.nanoTime() - startTime) * Math.pow(10, -9) + "s]");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void applyToDatabase(long id, Product value) {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mRef.child(CHILD_PATH + id).setValue(value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public void add(Product product) {
        mProducts.add(product);
    }

    public Product get(int index) {
        return mProducts.get(index);
    }

    public int getDataSize() {
        return mProducts.size();
    }

    public void update(Product product) {
        applyToDatabase(product.getId(), product);
    }

    public void remove(int index) {
        applyToDatabase(mProducts.get(index).getId(), null);
        mProducts.remove(index);
    }

    public long getNextId() {
        if (mProducts.size() == 0) return 1;
        ArrayList<Long> ids = new ArrayList<>();
        for (Product product : mProducts) {
            ids.add(product.getId());
        }
        return Collections.max(ids) + 1;
    }

    public ArrayList<String> getListPaths() {
        return mListPaths;
    }

    public String getCurrentPath() {
        return currentPath;
    }
}
