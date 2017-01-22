package com.example.kangjisung.likeroom.Garbage;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.Util.SingleToast;
import com.example.kangjisung.likeroom.FragmentUser.UserMainListItem;
import com.example.kangjisung.likeroom.R;

import java.util.ArrayList;

public class _UserListViewAdapter extends BaseAdapter
{
    ArrayList<UserMainListItem> userListViewItemRecycler;
    Button buttonDescription;

    public _UserListViewAdapter() {
        super();

        userListViewItemRecycler = new ArrayList<UserMainListItem>() ;
    }

    @Override
    public int getCount() {
        return userListViewItemRecycler.size() ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_main_listitem, parent, false);
        }

        TextView textViewName = (TextView)convertView.findViewById(R.id.textViewName) ;
        TextView textViewPhone = (TextView)convertView.findViewById(R.id.textViewPhone) ;
        TextView textViewPoint = (TextView)convertView.findViewById(R.id.textViewPoint) ;

        final UserMainListItem userRecyclerViewItem = userListViewItemRecycler.get(position);

        textViewName.setText(userRecyclerViewItem.getName());
        textViewPhone.setText(userRecyclerViewItem.getPhone());
        textViewPoint.setText(userRecyclerViewItem.getPoint());

        buttonDescription = (Button)convertView.findViewById(R.id.buttonDescription);
        buttonDescription.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.clrMenuIcon), PorterDuff.Mode.SRC_IN);
        buttonDescription.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                SingleToast.show(context, userRecyclerViewItem.getName().toString() + " 항목의 버튼 클릭", Toast.LENGTH_SHORT);
            }
        });

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return userListViewItemRecycler.get(position) ;
    }

    public void addItem(String addName, String addPhone, String addPoint)
    {
        UserMainListItem addItemList = new UserMainListItem();

        addItemList.setName(addName);
        addItemList.setPhone(addPhone);
        addItemList.setPoint(addPoint);

        userListViewItemRecycler.add(addItemList);
    }
}
