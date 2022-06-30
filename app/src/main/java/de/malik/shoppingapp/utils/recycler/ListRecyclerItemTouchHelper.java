package de.malik.shoppingapp.utils.recycler;

import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import de.malik.shoppingapp.R;
import de.malik.shoppingapp.utils.DatabaseManager;
import de.malik.shoppingapp.utils.Product;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ListRecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private DatabaseManager dbManager;
    private RecyclerView mRecycler;
    private ListRecyclerAdapter mAdapter;

    public ListRecyclerItemTouchHelper(int dragDirs, int swipeDirs, DatabaseManager dbManager, RecyclerView recycler, ListRecyclerAdapter adapter) {
        super(dragDirs, swipeDirs);
        this.dbManager = dbManager;
        mRecycler = recycler;
        mAdapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (direction == ItemTouchHelper.LEFT) {
            final int index = viewHolder.getAdapterPosition();
            Product deleted = dbManager.get(index);
            dbManager.remove(index);
            Snackbar.make(mRecycler, deleted.getName() + " gelöscht", Snackbar.LENGTH_SHORT).show();
            mAdapter.notifyItemRemoved(index);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftActionIcon(R.drawable.ic_delete)
                .addSwipeLeftBackgroundColor(Color.parseColor("#FF0000"))
                .create()
                .decorate();
    }
}
