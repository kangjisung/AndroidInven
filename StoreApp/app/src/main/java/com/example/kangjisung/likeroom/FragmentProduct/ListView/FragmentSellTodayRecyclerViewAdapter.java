package com.example.kangjisung.likeroom.FragmentProduct.ListView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kangjisung.likeroom.FragmentProduct.ProductObjManager;
import com.example.kangjisung.likeroom.R;

public class FragmentSellTodayRecyclerViewAdapter extends RecyclerView.Adapter<FragmentSellTodayRecyclerViewAdapter.ViewHolder> {

    Context context;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvName;
        public TextView tvAddedDate;
        public EditText etInput;
        public RelativeLayout star;
        public ViewHolder(View view) {
            super(view);
            tvName=(TextView)view.findViewById(R.id.tv_name);
            tvAddedDate=(TextView)view.findViewById(R.id.tv_added_date);
            etInput=(EditText)view.findViewById(R.id.et_input);
            star=(RelativeLayout)view.findViewById(R.id.star);
        }
    }

    public FragmentSellTodayRecyclerViewAdapter( Context context) {
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_sell_today_listitem, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(ProductObjManager.get(position).getName());
        holder.tvAddedDate.setText(ProductObjManager.date2String(ProductObjManager.get(position).getMuchStoreDate()));
        holder.etInput.setText(String.valueOf(ProductObjManager.get(position).getSellToday()));
        if(ProductObjManager.get(position).isStar()) {
            holder.star.setBackgroundResource(R.drawable.sell_today_recyclerview_background_star);
        }
        else{
            holder.star.setBackgroundResource(R.drawable.sell_today_recyclerview_background);
        }

    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ProductObjManager.productInfos.size();
    }
}

