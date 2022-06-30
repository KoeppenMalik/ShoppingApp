package de.malik.shoppingapp.listeners;

import android.view.View;

import de.malik.shoppingapp.utils.DatabaseManager;
import de.malik.shoppingapp.utils.LifecycleManager;

public class OnClickCardViewSwitchList implements View.OnClickListener {

    private DatabaseManager dbManager;
    private LifecycleManager mLcm;
    private String mListName;

    public OnClickCardViewSwitchList(DatabaseManager dbManager, String listName, LifecycleManager lcm) {
        this.dbManager = dbManager;
        mListName = listName;
        mLcm = lcm;
    }

    @Override
    public void onClick(View v) {
        dbManager.switchReference(mListName);
        dbManager.readDatabase();
        mLcm.updateAppTitle(mListName);
    }
}
