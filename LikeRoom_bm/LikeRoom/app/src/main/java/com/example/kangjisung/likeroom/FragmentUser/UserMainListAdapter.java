package com.example.kangjisung.likeroom.FragmentUser;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.Util.SingleToast;
import com.example.kangjisung.likeroom.R;

import java.util.ArrayList;

public class UserMainListAdapter extends RecyclerView.Adapter<UserMainListAdapter.UserRecyclerViewHolder>
{
    private ArrayList<UserMainListItem> userMainList = new ArrayList<UserMainListItem>();
    private ArrayList<Boolean> userMainCheckboxStateList = new ArrayList<Boolean>();
    private Context context;
    private Boolean stampMode = false;

    UserMainListAdapter(View view) {}

    public static class UserRecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewName;
        TextView textViewPhone;
        TextView textViewPoint;
        Button buttonDescription;
        CheckBox checkBoxStamp;
        View view;

        UserRecyclerViewHolder(View view)
        {
            super(view);

            this.view = view;
            textViewName = (TextView)view.findViewById(R.id.textView_name);
            textViewPhone = (TextView)view.findViewById(R.id.textView_phone);
            textViewPoint = (TextView)view.findViewById(R.id.textView_point);
            buttonDescription = (Button)view.findViewById(R.id.button_detail);
            checkBoxStamp = (CheckBox)view.findViewById(R.id.checkBox_stamp);
        }
    }

    @Override
    public UserMainListAdapter.UserRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_main_listitem, parent, false);
        UserRecyclerViewHolder vh = new UserRecyclerViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(UserRecyclerViewHolder holder, int position)
    {
        final UserMainListItem userMainItem = userMainList.get(position);
        final Boolean userMainCheckBoxState = userMainCheckboxStateList.get(position);

        holder.textViewName.setText(userMainItem.getName());
        holder.textViewPhone.setText(userMainItem.getPhone());
        holder.textViewPoint.setText(userMainItem.getPoint());
        holder.buttonDescription.getBackground().setColorFilter(Color.parseColor("#55000000"), PorterDuff.Mode.SRC_IN);
        holder.buttonDescription.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                SingleToast.show(context, userMainItem.getName().toString() + " 항목의 버튼 클릭", Toast.LENGTH_SHORT);
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view){
                SingleToast.show(context, userMainItem.getName().toString() + " 항목을 길게 눌렀습니다", Toast.LENGTH_SHORT);
                return true;
            }
        });
        if(userMainCheckBoxState == false){
            holder.checkBoxStamp.setVisibility(View.GONE);
        }
        else{
            holder.checkBoxStamp.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return userMainList.size();
    }

    public void updateCheckboxState(boolean newStampMode)
    {
        for(int p = 0; p< userMainCheckboxStateList.size(); p++) {
            userMainCheckboxStateList.set(p, newStampMode);
        }
        stampMode = newStampMode;
        notifyDataSetChanged();
    }

    public void addItem(String addName, String addPhone, String addPoint)
    {
        UserMainListItem addItemList = new UserMainListItem();

        addItemList.setName(addName);
        addItemList.setPhone(addPhone);
        addItemList.setPoint(addPoint);

        userMainList.add(addItemList);
        userMainCheckboxStateList.add(false);
    }
}