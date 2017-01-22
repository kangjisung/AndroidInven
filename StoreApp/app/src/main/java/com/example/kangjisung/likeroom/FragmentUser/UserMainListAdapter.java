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

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        holder.textViewPoint.setText(userMainItem.getPoint() + " p");
        holder.buttonDescription.getBackground().setColorFilter(Color.parseColor("#55000000"), PorterDuff.Mode.SRC_IN);
        holder.buttonDescription.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                SingleToast.show(context, userMainItem.getName() + " 항목의 버튼 클릭", Toast.LENGTH_SHORT);
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view){
                SingleToast.show(context, userMainItem.getName() + " 항목을 길게 눌렀습니다", Toast.LENGTH_SHORT);
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

    void addItem(String addName, String addPhone, String addPoint)
    {
        UserMainListItem addItemList = new UserMainListItem();

        addItemList.setName(addName);
        addItemList.setPhone(addPhone);
        addItemList.setPoint(addPoint);

        userMainList.add(addItemList);
        userMainCheckboxStateList.add(false);
    }

    public void sort(final String sortMode, final String sortOrder)
    {
        Collections.sort(userMainList, new Comparator<UserMainListItem>(){
            @Override
            public int compare(UserMainListItem obj1, UserMainListItem obj2) {
                if(sortMode.equals("NAME")){
                    if(sortOrder.equals("ASC")){
                        return obj1.getName().compareToIgnoreCase(obj2.getName());
                    }
                    else{
                        return obj2.getName().compareToIgnoreCase(obj1.getName());
                    }
                }
                else if(sortMode.equals("PHONE")){
                    if(sortOrder.equals("ASC")){
                        return obj1.getPhone().compareToIgnoreCase(obj2.getPhone());
                    }
                    else{
                        return obj2.getPhone().compareToIgnoreCase(obj1.getPhone());
                    }
                }
                else if(sortMode.equals("POINT")){
                    int p1 = Integer.valueOf(obj1.getPoint());
                    int p2 = Integer.valueOf(obj2.getPoint());
                    if(sortOrder.equals("ASC")){
                        return (p1 < p2) ? -1 : (p1 > p2) ? 1 : 0;
                    }
                    else{
                        return (p1 > p2) ? -1 : (p1 < p2) ? 1 : 0;
                    }
                }
                return -1;
            }
        });
        notifyDataSetChanged();
    }

    public void clearData() {
        int size = userMainList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                userMainList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
}