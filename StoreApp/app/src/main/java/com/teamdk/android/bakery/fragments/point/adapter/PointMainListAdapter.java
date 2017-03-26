package com.teamdk.android.bakery.fragments.point.adapter;

import com.teamdk.android.bakery.fragments.point.PointSaveDialog;
import com.teamdk.android.bakery.objectmanager.MemberListItem;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.teamdk.android.bakery.objectmanager.MemberObjectManager;
import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.utility.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PointMainListAdapter extends BaseAdapter implements Filterable
{
    private ArrayList<MemberListItem> pointMainList = new ArrayList<>();
    private ArrayFilter mFilter;
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("y년 M월 d일", Locale.KOREA);

    public PointMainListAdapter() {
        super();
    }

    public PointMainListAdapter(Context context)
    {
        super();
        this.context = context;
        pointMainList = MemberObjectManager.getArray();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        final MemberListItem pointMainItem = pointMainList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.point_main_listitem, parent, false);
        }
        if (mFilter != null){
            TextView mTextViewName = (TextView) convertView.findViewById(R.id.tv_name);
            TextView mTextViewPhone = (TextView) convertView.findViewById(R.id.tv_phone);
            Button mButtonSelect = (Button) convertView.findViewById(R.id.btn_select);
            Button mButtonDetail = (Button) convertView.findViewById(R.id.btn_detail);

            mTextViewName.setText(pointMainItem.getName());
            mTextViewPhone.setText(Utility.convertPhoneNumber(pointMainItem.getPhone()));

            /*
            mButtonSelect.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View onClickView) {
                    pointSaveDialog = new PointSaveDialog(context, pointMainItem);
                    pointSaveDialog.show();
                }
            });
            */
            mButtonDetail.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View onClickView) {
                    DrawerLayout drawerLayout = (DrawerLayout) parent.getParent().getParent().getParent().getParent();
                    drawerLayout.openDrawer(Gravity.RIGHT);
                    ((TextView) drawerLayout.findViewById(R.id.tv_drawer_name)).setText(pointMainItem.getName());
                    ((TextView) drawerLayout.findViewById(R.id.tv_drawer_phone)).setText(Utility.convertPhoneNumber(pointMainItem.getPhone()));
                    ((TextView) drawerLayout.findViewById(R.id.tv_drawer_point)).setText(pointMainItem.getPoint());
                    ((TextView) drawerLayout.findViewById(R.id.tv_drawer_birth)).setText(dateFormat.format(pointMainItem.getBirth()));
                    ((TextView) drawerLayout.findViewById(R.id.tv_drawer_email)).setText(pointMainItem.getEmail());
                }
            });
            convertView.setVisibility(View.VISIBLE);
        }
        else{
            convertView.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return pointMainList.size() ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return pointMainList.get(position) ;
    }

    public void addItem(MemberListItem addListItem)
    {
        pointMainList.add(addListItem);
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (prefix == null || prefix.length() == 0) {
                results.values = null;
                results.count = 0;
            }
            else {
                final String prefixString = prefix.toString().toLowerCase();

                ArrayList<MemberListItem> newValues = new ArrayList<>();

                for (int i = 0; i < MemberObjectManager.size(); i++) {
                    String itemName = MemberObjectManager.get(i).getName();
                    String itemPhone = MemberObjectManager.get(i).getPhone();
                    if (itemName.toLowerCase().contains(prefixString) || itemPhone.toLowerCase().contains(prefixString)) {
                        newValues.add(MemberObjectManager.get(i));
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            if (results.values != null) {
                pointMainList = (ArrayList<MemberListItem>) results.values;
            } else {
                pointMainList = new ArrayList<MemberListItem>();
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}