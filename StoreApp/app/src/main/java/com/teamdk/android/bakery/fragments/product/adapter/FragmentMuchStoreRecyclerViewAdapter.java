package com.teamdk.android.bakery.fragments.product.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teamdk.android.bakery.fragments.product.ProductMain;
import com.teamdk.android.bakery.objectmanager.ProductObjectManager;
import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.utility.LayoutManager;
import com.teamdk.android.bakery.inventory.calc;
import com.teamdk.android.bakery.inventory.InventoryMain;

public class FragmentMuchStoreRecyclerViewAdapter extends RecyclerView.Adapter<FragmentMuchStoreRecyclerViewAdapter.ViewHolder> {

    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView tvName;
        public TextView tvAddedDate;
        public RelativeLayout ivIsStar;
        public Button ibWrite;
        public TextView tvNumber;
        public View view;
        public ViewHolder(View view) {
            super(view);
            tvName=(TextView)view.findViewById(R.id.tv_name);
            tvAddedDate=(TextView)view.findViewById(R.id.tv_added_date);
            ibWrite=(Button)view.findViewById(R.id.ib_write);
            tvNumber=(TextView)view.findViewById(R.id.tv_number);
            this.view = view;
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("작업 선택");
            menu.add(Menu.NONE, LayoutManager.MENU_PRODUCT_MODIFY, Menu.NONE, "수정");
            menu.add(Menu.NONE, LayoutManager.MENU_PRODUCT_DELETE, Menu.NONE, "삭제");
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FragmentMuchStoreRecyclerViewAdapter(Context context) {
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_much_store_listitem, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(ProductObjectManager.get(position).getName());
        holder.tvAddedDate.setText(ProductObjectManager.date2String(ProductObjectManager.get(position).getMuchStoreDate()));
        holder.tvNumber.setText(ProductObjectManager.get(position).getMuchStore()+"");
        final Intent mil = new Intent(context, InventoryMain.class);
        holder.ibWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calc c;
                c = calc.getInstance();
                c.RefreshClass(ProductObjectManager.get(position).getName());
                context.startActivity(mil);
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ProductMain.longClickPosition = position;
                return false;
            }
        });

        /*
        if(ProductObjectManager.productInfos.get(position).isStar()) {
            holder.ivIsStar.setBackgroundResource(R.drawable.user_listview_background_star);
        }
        else{
            holder.ivIsStar.setBackgroundResource(R.drawable.user_listview_background);
        }
        */
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ProductObjectManager.productInfos.size();
    }
}

