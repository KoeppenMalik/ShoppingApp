package de.malik.shoppingapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import de.malik.shoppingapp.R;
import de.malik.shoppingapp.utils.DatabaseManager;
import de.malik.shoppingapp.utils.LifecycleManager;
import de.malik.shoppingapp.utils.Product;

public class ProductFragment extends Fragment {

    private View mV;
    private Button mButtonFinish;
    private EditText mEtProduct, mEtDescription;

    private Product mProduct, mOldProduct;
    private LifecycleManager mLcm;
    private DatabaseManager dbManager;

    public ProductFragment(Product product, LifecycleManager lcm, DatabaseManager dbManager) {
        mProduct = product;
        mLcm = lcm;
        this.dbManager = dbManager;
        mOldProduct = Product.copy(mProduct);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLcm.setBackFragment(new ShoppingListFragment(dbManager, mLcm));
        mLcm.setBackAnimIn(R.anim.float_up_in);
        mLcm.setBackAnimOut(R.anim.float_up_out);
        mV = inflater.inflate(R.layout.product_layout, container, false);
        createComponents();
        mEtProduct.setText(mProduct.getName());
        mEtDescription.setText(mProduct.getDescription());
        setListeners();
        return mV;
    }

    private void createComponents() {
        mButtonFinish = mV.findViewById(R.id.buttonFinish);
        mEtProduct = mV.findViewById(R.id.et_product_name);
        mEtDescription = mV.findViewById(R.id.et_description);
    }

    private void setListeners() {
        mButtonFinish.setOnClickListener((v) -> {
            mProduct.setName(mEtProduct.getText().toString());
            mProduct.setDescription(mEtDescription.getText().toString());
            dbManager.update(mProduct);
            mLcm.showProgressDialog("Speichern...", 1000, new ShoppingListFragment(dbManager, mLcm));
        });
    }
}
