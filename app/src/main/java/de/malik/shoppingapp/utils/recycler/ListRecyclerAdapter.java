package de.malik.shoppingapp.utils.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.malik.shoppingapp.R;
import de.malik.shoppingapp.ui.fragments.ProductFragment;
import de.malik.shoppingapp.utils.DatabaseManager;
import de.malik.shoppingapp.utils.LifecycleManager;
import de.malik.shoppingapp.utils.Product;

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerViewHolder> {

    private View mV;

    private DatabaseManager dbManager;
    private LifecycleManager mLcm;

    public ListRecyclerAdapter(DatabaseManager dbManager, LifecycleManager lcm) {
        this.dbManager = dbManager;
        mLcm = lcm;
    }

    @NonNull
    @Override
    public ListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mV = inflater.inflate(R.layout.list_recycler_layout, parent, false);
        return new ListRecyclerViewHolder(mV);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRecyclerViewHolder holder, int position) {
        Product product = dbManager.get(position);
        holder.getTvProduct().setText(product.getName());
        holder.getTvDescription().setText(product.getDescription());
        mV.setOnClickListener((v) -> {
            mLcm.replaceFragment(new ProductFragment(product, mLcm, dbManager), R.anim.drop_down_in, R.anim.drop_down_out);
        });
    }

    @Override
    public int getItemCount() {
        return dbManager.getDataSize();
    }
}
