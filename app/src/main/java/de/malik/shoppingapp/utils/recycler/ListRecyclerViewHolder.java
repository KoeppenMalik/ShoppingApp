package de.malik.shoppingapp.utils.recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.malik.shoppingapp.R;

public class ListRecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView mTvDesc, mTvProduct;

    public ListRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mTvDesc = itemView.findViewById(R.id.tv_desc);
        mTvProduct = itemView.findViewById(R.id.tv_product);
    }

    public TextView getTvDescription() {
        return mTvDesc;
    }

    public TextView getTvProduct() {
        return mTvProduct;
    }
}
