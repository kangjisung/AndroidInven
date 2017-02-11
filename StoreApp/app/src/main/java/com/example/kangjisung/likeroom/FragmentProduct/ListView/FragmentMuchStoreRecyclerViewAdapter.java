package com.example.kangjisung.likeroom.FragmentProduct.ListView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kangjisung.likeroom.FragmentProduct.ProductObjManager;
import com.example.kangjisung.likeroom.R;

public class FragmentMuchStoreRecyclerViewAdapter extends RecyclerView.Adapter<FragmentMuchStoreRecyclerViewAdapter.ViewHolder> {

    Context context;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        // each data item is just a string in this case
        public TextView tvName;
        public TextView tvAddedDate;
        public RelativeLayout ivIsStar;
        public Button ibWrite;
        public TextView tvNumber;
        public ViewHolder(View view) {
            super(view);
            tvName=(TextView)view.findViewById(R.id.tv_name);
            tvAddedDate=(TextView)view.findViewById(R.id.tv_added_date);
            ibWrite=(Button)view.findViewById(R.id.ib_write);
            tvNumber=(TextView)view.findViewById(R.id.tv_number);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("작업 선택");
            menu.add(Menu.NONE, 2, Menu.NONE, "수정");
            menu.add(Menu.NONE, 3, Menu.NONE, "삭제");
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
        holder.tvName.setText(ProductObjManager.get(position).getName());
        holder.tvAddedDate.setText(ProductObjManager.date2String(ProductObjManager.get(position).getMuchStoreDate()));
        holder.tvNumber.setText(ProductObjManager.get(position).getMuchStore()+"");
        holder.ibWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        /*
        if(ProductObjManager.productInfos.get(position).isStar()) {
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
        return ProductObjManager.productInfos.size();
    }
}

