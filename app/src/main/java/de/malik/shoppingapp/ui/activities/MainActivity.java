package de.malik.shoppingapp.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuItemImpl;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import de.malik.shoppingapp.R;
import de.malik.shoppingapp.listeners.OnNavBarItemSelected;
import de.malik.shoppingapp.ui.fragments.ShoppingListFragment;
import de.malik.shoppingapp.utils.DatabaseManager;
import de.malik.shoppingapp.utils.FileDataManager;
import de.malik.shoppingapp.utils.FileManager;
import de.malik.shoppingapp.utils.LifecycleManager;

public class MainActivity extends AppCompatActivity {

    public static final int CONTAINER_LAYOUT_ID = R.id.container_layout;

    private BottomNavigationView mNavBar;

    private LifecycleManager mLcm;
    private DatabaseManager dbManager;
    private FileDataManager fdManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Einkaufsliste: default");
        mLcm = new LifecycleManager(this);
        FileManager fileManager = new FileManager(getFilesDir().getPath());
        fdManager = new FileDataManager(fileManager.getFile());
        dbManager = new DatabaseManager(fdManager);
        Log.e("", fdManager.isDeveloper() + "");
        mLcm.updateAppTitle(fdManager.getCurrentRef());
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

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        MenuItemImpl subMenu = (MenuItemImpl) menu.getItem(0);
        // turn on
        subMenu.getSubMenu().getItem(0).setOnMenuItemClickListener((v) -> {
            fdManager.setDeveloper(true);
            fdManager.saveData();
            Snackbar.make(mNavBar, "Zur Übernahme App neustarten", Snackbar.LENGTH_SHORT).show();
            return true;
        });
        // turn off
        subMenu.getSubMenu().getItem(1).setOnMenuItemClickListener((v) -> {
            fdManager.setDeveloper(false);
            fdManager.saveData();
            Snackbar.make(mNavBar, "Zur Übernahme App neustarten", Snackbar.LENGTH_SHORT).show();
            return true;
        });
        return super.onCreateOptionsMenu(menu);
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
        mNavBar.setOnItemSelectedListener(new OnNavBarItemSelected(mLcm, dbManager, fdManager));
    }
}