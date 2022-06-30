package de.malik.shoppingapp.listeners;

import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationBarView;

import de.malik.shoppingapp.R;
import de.malik.shoppingapp.ui.fragments.SettingsFragment;
import de.malik.shoppingapp.ui.fragments.ShoppingListFragment;
import de.malik.shoppingapp.utils.DatabaseManager;
import de.malik.shoppingapp.utils.LifecycleManager;

public class OnNavBarItemSelected implements NavigationBarView.OnItemSelectedListener {

    private LifecycleManager mLcm;
    private DatabaseManager dbManager;

    public OnNavBarItemSelected(LifecycleManager lcm, DatabaseManager dbManager) {
        mLcm = lcm;
        this.dbManager = dbManager;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.tab_list) {
            mLcm.replaceFragment(new ShoppingListFragment(dbManager, mLcm), android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
        else if (item.getItemId() == R.id.tab_settings) {
            mLcm.replaceFragment(new SettingsFragment(dbManager, mLcm), R.anim.slide_in_right, R.anim.slide_out_left);
        }
        return true;
    }
}
