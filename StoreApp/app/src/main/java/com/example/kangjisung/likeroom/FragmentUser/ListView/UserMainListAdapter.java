package com.example.kangjisung.likeroom.FragmentUser.ListView;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kangjisung.likeroom.MemberListItem;
import com.example.kangjisung.likeroom.Util.Utility;
import com.example.kangjisung.likeroom.R;

import com.github.clans.fab.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class UserMainListAdapter extends RecyclerView.Adapter<UserMainListAdapter.UserRecyclerViewHolder> {
    private ArrayList<MemberListItem> userMainList;
    private Context context;
    private ViewGroup parent;
    private Boolean stampMode = false;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("y년 M월 d일", Locale.KOREA);

    private int longClickPosition;
    public MemberListItem getLongClickPosition() {return userMainList.get(longClickPosition);}
    public void setLongClickPosition(int longClickPosition) {this.longClickPosition = longClickPosition;}

    public UserMainListAdapter(View view) {
        userMainList = new ArrayList<MemberListItem>();
    }

    public static class UserRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textViewName;
        TextView textViewPhone;
        TextView textViewPoint;
        Button buttonDescription;
        CheckBox checkBoxStamp;
        AppCompatImageView mImageViewDot;
        View view;

        UserRecyclerViewHolder(View view) {
            super(view);

            this.view = view;
            textViewName = (TextView) view.findViewById(R.id.textView_name);
            textViewPhone = (TextView) view.findViewById(R.id.textView_phone);
            textViewPoint = (TextView) view.findViewById(R.id.textView_point);
            buttonDescription = (Button) view.findViewById(R.id.button_detail);
            checkBoxStamp = (CheckBox) view.findViewById(R.id.checkBox_stamp);
            mImageViewDot = (AppCompatImageView) view.findViewById(R.id.iv_dot);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("작업 선택");
            menu.add(Menu.NONE, Utility.MENU_USER_MODIFY, Menu.NONE, "수정");
            menu.add(Menu.NONE, Utility.MENU_USER_DELETE, Menu.NONE, "삭제");
        }
    }

    @Override
    public UserMainListAdapter.UserRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_main_listitem, parent, false);
        UserRecyclerViewHolder vh = new UserRecyclerViewHolder(v);
        context = parent.getContext();
        this.parent = parent;
        return vh;
    }

    @Override
    public void onBindViewHolder(final UserRecyclerViewHolder holder, final int position) {
        final MemberListItem userMainItem = userMainList.get(position);

        holder.textViewName.setText(userMainItem.getName());
        holder.textViewPhone.setText(Utility.convertPhoneNumber(userMainItem.getPhone()));
        holder.textViewPoint.setText(userMainItem.getPoint() + " p");
        holder.buttonDescription.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                DrawerLayout drawerLayout = (DrawerLayout) parent.getParent().getParent().getParent().getParent();
                drawerLayout.openDrawer(Gravity.RIGHT);
                ((TextView)drawerLayout.findViewById(R.id.tv_drawer_name)).setText(userMainItem.getName());
                ((TextView)drawerLayout.findViewById(R.id.tv_drawer_phone)).setText(Utility.convertPhoneNumber(userMainItem.getPhone()));
                ((TextView)drawerLayout.findViewById(R.id.tv_drawer_point)).setText(userMainItem.getPoint());
                ((TextView)drawerLayout.findViewById(R.id.tv_drawer_birth)).setText(dateFormat.format(userMainItem.getBirth()));
                ((TextView)drawerLayout.findViewById(R.id.tv_drawer_email)).setText(userMainItem.getEmail());
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stampMode){
                    holder.checkBoxStamp.callOnClick();
                }
            }
        });

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setLongClickPosition(position);
                return stampMode;
            }
        });

        if (stampMode == false) {
            holder.checkBoxStamp.setVisibility(View.GONE);
            holder.mImageViewDot.setVisibility(View.VISIBLE);
            userMainList.get(position).setCheck(false);
            //userMainCheckboxStateList.set(position, false);
        } else {
            holder.checkBoxStamp.setVisibility(View.VISIBLE);
            holder.mImageViewDot.setVisibility(View.GONE);
        }
        holder.checkBoxStamp.setChecked(userMainList.get(position).getCheck());

        holder.checkBoxStamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userMainList.get(position).setCheck(isChecked);
                int count = 0;
                for(int p = 0; p < userMainList.size(); p++){
                    if(userMainList.get(p).getCheck() == true){
                        count++;
                    }
                }
                RelativeLayout layoutUserMain = (RelativeLayout)parent.getParent().getParent();
                FloatingActionButton fabStampOk = (FloatingActionButton)layoutUserMain.findViewById(R.id.fab_stamp_ok);
                TextView textViewSearchResult = (TextView)layoutUserMain.findViewById(R.id.textView_search_result);
                if(count > 0){
                    if(count <= userMainList.size() - 1){
                        ((CheckBox)layoutUserMain.findViewById(R.id.checkBoxStampAll)).setChecked(false);
                    }
                    else{
                        ((CheckBox)layoutUserMain.findViewById(R.id.checkBoxStampAll)).setChecked(true);
                    }
                    fabStampOk.setEnabled(true);
                }
                else{
                    fabStampOk.setEnabled(false);
                }
                if(stampMode == true){
                    setTextViewSearchResult(textViewSearchResult);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userMainList.size();
    }

    public void setTextViewSearchResult(TextView textView)
    {
        int count = 0;
        for(int p = 0; p < userMainList.size(); p++){
            if(userMainList.get(p).getCheck() == true){
                count++;
            }
        }
        textView.setText(String.valueOf(userMainList.size()) + "명 중 " + String.valueOf(count) + "명 선택됨");
    }

    public void setCheckAll(boolean isChecked)
    {
        for(int p = 0; p < userMainList.size(); p++)
        {
            userMainList.get(p).setCheck(isChecked);
        }
        notifyDataSetChanged();
    }

    public ArrayList<MemberListItem> getListItemToStampDialog()
    {
        ArrayList<MemberListItem> uploadData = new ArrayList<MemberListItem>();

        for(int p = 0; p < userMainList.size(); p++){
            if(userMainList.get(p).getCheck() == true){
                uploadData.add(userMainList.get(p));
            }
        }
        return uploadData;
    }

    public void updateCheckboxState(boolean newStampMode)
    {
        stampMode = newStampMode;
        notifyDataSetChanged();
    }
    public void addItem(MemberListItem addListItem)
    {
        userMainList.add(addListItem);
    }

    public void sort(final String sortMode, final String sortOrder)
    {
        Collections.sort(userMainList, new Comparator<MemberListItem>(){
            @Override
            public int compare(MemberListItem obj1, MemberListItem obj2) {
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