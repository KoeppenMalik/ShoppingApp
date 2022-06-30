package de.malik.shoppingapp.listeners;

import android.util.Log;
import android.view.View;

import de.malik.shoppingapp.utils.DatabaseManager;
import de.malik.shoppingapp.utils.FileDataManager;
import de.malik.shoppingapp.utils.LifecycleManager;

public class OnClickCardViewSwitchList implements View.OnClickListener {

    private DatabaseManager dbManager;
    private LifecycleManager mLcm;
    private FileDataManager fdManager;
    private String mListName;

    public OnClickCardViewSwitchList(DatabaseManager dbManager, String listName, LifecycleManager lcm, FileDataManager fdManager) {
        this.dbManager = dbManager;
        this.fdManager = fdManager;
        mListName = listName;
        mLcm = lcm;
    }

    @Override
    public void onClick(View v) {
        dbManager.switchReference(mListName);
        dbManager.readDatabase();
        fdManager.setCurrentRef(mListName);
        fdManager.saveData();
        mLcm.updateAppTitle(mListName);
    }
}
