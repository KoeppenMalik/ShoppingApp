package de.malik.shoppingapp.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.malik.shoppingapp.R;
import de.malik.shoppingapp.listeners.OnNavBarItemSelected;
import de.malik.shoppingapp.ui.fragments.ShoppingListFragment;
import de.malik.shoppingapp.utils.DatabaseManager;
import de.malik.shoppingapp.utils.LifecycleManager;

public class MainActivity extends AppCompatActivity {

    public static final int CONTAINER_LAYOUT_ID = R.id.container_layout;

    private BottomNavigationView mNavBar;

    private LifecycleManager mLcm;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Einkaufsliste: default");
        mLcm = new LifecycleManager(this);
        dbManager = new DatabaseManager();
        createComponents();
        setListeners();
        mLcm.showProgressDialog("Daten werden geladen...", 3000, new ShoppingListFragment(dbManager, mLcm));
    }

    @Override
    public void onBackPressed() {
        performBackAction();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        performBackAction();
        return super.onOptionsItemSelected(item);
    }

    private void performBackAction() {
        if (mLcm.getBackFragment() != null) {
            mLcm.replaceFragment(mLcm.getBackFragment(), mLcm.getBackAnimIn(), mLcm.getBackAnimOut());
            mLcm.setBackFragment(null);
            mLcm.setBackAnimIn(0);
            mLcm.setBackAnimOut(0);
        }
    }

    private void createComponents() {
        mNavBar = findViewById(R.id.nav_bar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setListeners() {
        mNavBar.setOnItemSelectedListener(new OnNavBarItemSelected(mLcm, dbManager));
    }
}