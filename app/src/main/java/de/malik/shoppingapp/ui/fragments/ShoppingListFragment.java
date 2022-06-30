package de.malik.shoppingapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import de.malik.shoppingapp.R;
import de.malik.shoppingapp.utils.DatabaseManager;
import de.malik.shoppingapp.utils.FileDataManager;
import de.malik.shoppingapp.utils.LifecycleManager;
import de.malik.shoppingapp.utils.Product;
import de.malik.shoppingapp.utils.recycler.ListRecyclerAdapter;
import de.malik.shoppingapp.utils.recycler.ListRecyclerItemTouchHelper;

public class ShoppingListFragment extends Fragment {

    private View mV;

    private RecyclerView mRecycler;
    private ImageButton mButtonAdd;

    private ListRecyclerAdapter mAdapter;
    private ItemTouchHelper mIth;
    private DatabaseManager dbManager;
    private FileDataManager fdManager;
    private LifecycleManager mLcm;

    public ShoppingListFragment(DatabaseManager dbManager, LifecycleManager lcm, FileDataManager fdManager) {
        this.dbManager = dbManager;
        this.fdManager = fdManager;
        mLcm = lcm;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mV = inflater.inflate(R.layout.shopping_list_layout, container, false);
        mLcm.setBackFragment(new SettingsFragment(dbManager, mLcm, fdManager));
        mLcm.setBackAnimIn(R.anim.slide_in_right);
        mLcm.setBackAnimOut(R.anim.slide_out_left);
        createComponents();
        mRecycler.setAdapter(mAdapter);
        mIth.attachToRecyclerView(mRecycler);
        mRecycler.addItemDecoration(new DividerItemDecoration(container.getContext(), DividerItemDecoration.VERTICAL));
        setListeners();
        return mV;
    }

    private void createComponents() {
        mRecycler = mV.findViewById(R.id.recycler);
        mButtonAdd = mV.findViewById(R.id.button_add);
        mAdapter = new ListRecyclerAdapter(dbManager, mLcm, fdManager);
        mIth = new ItemTouchHelper(new ListRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, dbManager, mRecycler, mAdapter));
    }

    private void setListeners() {
        mButtonAdd.setOnClickListener((v) -> {
            Product newProduct = Product.getInstance(dbManager);
            dbManager.add(newProduct);
            mLcm.replaceFragment(new ProductFragment(newProduct, mLcm, dbManager, fdManager), R.anim.drop_down_in, R.anim.drop_down_out);
        });
    }
}
