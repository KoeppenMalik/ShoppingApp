package de.malik.shoppingapp.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.concurrent.atomic.AtomicReference;

import de.malik.shoppingapp.R;
import de.malik.shoppingapp.listeners.OnClickCardViewSwitchList;
import de.malik.shoppingapp.utils.DatabaseManager;
import de.malik.shoppingapp.utils.FileDataManager;
import de.malik.shoppingapp.utils.LifecycleManager;

public class SettingsFragment extends Fragment {

    private View mV;

    private DatabaseManager dbManager;
    private FileDataManager fdManager;
    private LifecycleManager mLcm;
    private ImageButton mButtonDeleteList, mButtonAddList;
    private LinearLayout mListsLayout;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;

    public SettingsFragment(DatabaseManager dbManager, LifecycleManager lcm, FileDataManager fdManager) {
        this.dbManager = dbManager;
        this.fdManager = fdManager;
        mLcm = lcm;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContainer = container;
        mInflater = inflater;
        mV = inflater.inflate(R.layout.settings_layout, container, false);
        mLcm.setBackFragment(new ShoppingListFragment(dbManager, mLcm, fdManager));
        mLcm.setBackAnimIn(android.R.anim.slide_in_left);
        mLcm.setBackAnimOut(android.R.anim.slide_out_right);
        createComponents();
        createLists();
        setListeners();
        return mV;
    }

    private void createComponents() {
        mButtonDeleteList = mV.findViewById(R.id.button_delete_list);
        mButtonAddList = mV.findViewById(R.id.button_add_list);
        mListsLayout = mV.findViewById(R.id.scroll).findViewById(R.id.ll);
    }

    private void createLists() {
        mListsLayout.removeAllViews();
        for (String title : dbManager.getListPaths()) {
            CardView cardView = (CardView) mInflater.inflate(R.layout.list_card_view, mContainer, false);
            ((TextView) cardView.findViewById(R.id.list_title)).setText(title);
            cardView.setOnClickListener(new OnClickCardViewSwitchList(dbManager, title, mLcm, fdManager));
            mListsLayout.addView(cardView);
        }
    }

    private void setListeners() {
        mButtonAddList.setOnClickListener((view) -> {
            final AtomicReference<String> name = new AtomicReference<>();
            Dialog dialog = new Dialog(mContainer.getContext());
            dialog.setContentView(R.layout.list_name_dialog);
            dialog.setCancelable(false);
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(lp);
            EditText etName = dialog.findViewById(R.id.et_name);
            etName.requestFocus();
            Button buttonFinish = dialog.findViewById(R.id.button_finish);
            buttonFinish.setOnClickListener((v) -> {
                name.set(etName.getText().toString());
                CardView cardView = (CardView) mInflater.inflate(R.layout.list_card_view, mContainer, false);
                ((TextView) cardView.findViewById(R.id.list_title)).setText(name.get());
                cardView.setOnClickListener(new OnClickCardViewSwitchList(dbManager, name.get(), mLcm, fdManager));
                mListsLayout.addView(cardView);
                dbManager.createNewList(name.get());
                dbManager.switchReference(name.get());
                mLcm.updateAppTitle(dbManager.getCurrentPath());
                dialog.dismiss();
            });
            dialog.create();
            dialog.show();
        });
        mButtonDeleteList.setOnClickListener((v) -> {
            removeListFromView(dbManager.getCurrentPath());
            dbManager.getListPaths().remove(dbManager.getCurrentPath());
            dbManager.removeCurrentList();
            if (dbManager.getListPaths().size() == 0)
                dbManager.switchReference(DatabaseManager.ROOT_PATH);
            else
                dbManager.switchReference(dbManager.getListPaths().get(0));
            mLcm.updateAppTitle(dbManager.getCurrentPath());
            mLcm.showProgressDialog("Anfrage wird bearbeitet...", 1000, new SettingsFragment(dbManager, mLcm, fdManager));
        });
    }

    private void removeListFromView(String title) {
        int index = -1;
        for (int i = 0; i < mListsLayout.getChildCount(); i++) {
            TextView tv = mListsLayout.getChildAt(i).findViewById(R.id.list_title);
            if (tv.getText().equals(title)) {
                index = i;
                break;
            }
        }
        mListsLayout.removeViewAt(index);
    }
}
