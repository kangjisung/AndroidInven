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

public class FragmentMuchStoreRecyclerViewAdapter extends RecyclerView.Adapter<FragmentMuchStoreRecyclerViewAdapter.ViewHolder>
{
    Context context;

    public FragmentMuchStoreRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public View view;
        public TextView mTextViewName;
        public TextView mTextViewAddedDate;
        public TextView mTextViewInput;
        public TextView mTextViewPoint;
        public Button mButtonInput;
        public Button mButtonInventory;
        public RelativeLayout mLayoutSellToday;
        public RelativeLayout mLayoutMuchStore;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            mTextViewName =(TextView)view.findViewById(R.id.tv_name);
            mTextViewAddedDate =(TextView)view.findViewById(R.id.tv_added_date);
            mTextViewInput =(TextView)view.findViewById(R.id.tv_input);
            mTextViewPoint = (TextView)view.findViewById(R.id.tv_point);
            mButtonInput = (Button)view.findViewById(R.id.btn_input);
            mButtonInventory = (Button)view.findViewById(R.id.btn_inventory);
            mLayoutSellToday = (RelativeLayout)view.findViewById(R.id.layout_sell_today);
            mLayoutMuchStore = (RelativeLayout)view.findViewById(R.id.layout_much_store);

            mLayoutSellToday.setVisibility(View.GONE);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("작업 선택");
            menu.add(Menu.NONE, LayoutManager.MENU_PRODUCT_MODIFY, Menu.NONE, "수정");
            menu.add(Menu.NONE, LayoutManager.MENU_PRODUCT_DELETE, Menu.NONE, "삭제");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_listitem, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextViewName.setText(ProductObjectManager.get(position).getName());
        holder.mTextViewAddedDate.setText("등록일 : " + ProductObjectManager.get(position).getAddedDateToString());
        holder.mTextViewPoint.setText(ProductObjectManager.get(position).getMuchStore()+"");
        final Intent mil = new Intent(context, InventoryMain.class);
        holder.mButtonInventory.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return ProductObjectManager.size();
    }
}

