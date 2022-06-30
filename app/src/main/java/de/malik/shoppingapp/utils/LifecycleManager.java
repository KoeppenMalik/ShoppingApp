package de.malik.shoppingapp.utils;

import android.app.ProgressDialog;

import androidx.annotation.AnimRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import de.malik.shoppingapp.ui.activities.MainActivity;
import de.malik.shoppingapp.ui.fragments.ShoppingListFragment;

public class LifecycleManager {

    public static final int NO_ANIM = 0;

    private Fragment mBackFragment;
    private int backAnimIn, backAnimOut;
    private AppCompatActivity mActivity;

    public LifecycleManager(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void replaceFragment(@NonNull Fragment newFragment, @AnimRes int animIn, @AnimRes int animOut) {
        mActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(animIn, animOut).replace(MainActivity.CONTAINER_LAYOUT_ID, newFragment).commit();
    }

    public void updateAppTitle(String title) {
        mActivity.getSupportActionBar().setTitle("Einkaufsliste: " + title);
    }

    public void showProgressDialog(String text, int delay, Fragment fragmentToShow) {
        ProgressDialog dialog = new ProgressDialog(mActivity);
        dialog.setMessage(text);
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.create();
        dialog.show();
        new Thread(() -> {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mActivity.runOnUiThread(() -> dialog.hide());
            replaceFragment(fragmentToShow, LifecycleManager.NO_ANIM, LifecycleManager.NO_ANIM);
        }).start();
    }

    public Fragment getBackFragment() {
        return mBackFragment;
    }

    public void setBackFragment(Fragment backFragment) {
        mBackFragment = backFragment;
    }

    public int getBackAnimIn() {
        return backAnimIn;
    }

    public void setBackAnimIn(int backAnimIn) {
        this.backAnimIn = backAnimIn;
    }

    public int getBackAnimOut() {
        return backAnimOut;
    }

    public void setBackAnimOut(int backAnimOut) {
        this.backAnimOut = backAnimOut;
    }
}
