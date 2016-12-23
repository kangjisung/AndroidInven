package com.example.kangjisung.likeroom.FragmentNotice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.CustomClass.SingleToast;
import com.example.kangjisung.likeroom.R;

import java.util.ArrayList;
import java.util.Calendar;

public class NoticeRecyclerViewAdapter extends RecyclerView.Adapter<NoticeRecyclerViewAdapter.NoticeRecyclerViewHolder> {
    private ArrayList<NoticeRecyclerViewItem> noticeListViewItemRecycler = new ArrayList<NoticeRecyclerViewItem>();
    private Context context;

    public static class NoticeRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewBody;
        TextView textViewDate;
        ImageView imageViewType;
        Button buttonClick;
        View view;

        NoticeRecyclerViewHolder(View view) {
            super(view);

            this.view = view;
            textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            textViewBody = (TextView) view.findViewById(R.id.textViewBody);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            imageViewType = (ImageView) view.findViewById(R.id.imageViewType);
            buttonClick = (Button) view.findViewById(R.id.buttonClick);
        }
    }

    public NoticeRecyclerViewAdapter.NoticeRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notice_recycler_view, parent, false);
        NoticeRecyclerViewAdapter.NoticeRecyclerViewHolder vh = new NoticeRecyclerViewAdapter.NoticeRecyclerViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(NoticeRecyclerViewHolder holder, int position) {
        final NoticeRecyclerViewItem noticeRecyclerViewItem = noticeListViewItemRecycler.get(position);
        holder.textViewTitle.setText(noticeRecyclerViewItem.getTitle());
        holder.textViewBody.setText(noticeRecyclerViewItem.getBody());
        String textDate = String.valueOf(noticeRecyclerViewItem.getStartDate().get(Calendar.YEAR)) + "/"
                + String.valueOf(noticeRecyclerViewItem.getStartDate().get(Calendar.MONTH)) + "/"
                + String.valueOf(noticeRecyclerViewItem.getStartDate().get(Calendar.DAY_OF_MONTH)) + "/ - "
                + String.valueOf(noticeRecyclerViewItem.getEndDate().get(Calendar.YEAR)) + "/"
                + String.valueOf(noticeRecyclerViewItem.getEndDate().get(Calendar.MONTH)) + "/"
                + String.valueOf(noticeRecyclerViewItem.getEndDate().get(Calendar.DAY_OF_MONTH));
        holder.textViewDate.setText(textDate);
        switch (noticeRecyclerViewItem.getType()) {
            default:
            case 1:
                holder.imageViewType.setBackgroundResource(R.mipmap.icon_menu_item);
                break;
            case 2:
                holder.imageViewType.setBackgroundResource(R.mipmap.icon_menu_point);
                break;
            case 3:
                holder.imageViewType.setBackgroundResource(R.mipmap.icon_menu_user);
                break;
        }
        holder.buttonClick.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                SingleToast.show(context, noticeRecyclerViewItem.getTitle().toString() + " 항목을 눌렀습니다", Toast.LENGTH_SHORT);
            }
        });
        /*
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view){
                SingleToast.show(context, noticeRecyclerViewItem.getTitle().toString() + " 항목을 길게 눌렀습니다", Toast.LENGTH_SHORT);
                return true;
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return noticeListViewItemRecycler.size();
    }

    public void addItem(String addTitle, String addBody, Calendar addStartDate, Calendar addEndDate, int addType) {
        NoticeRecyclerViewItem addItemList = new NoticeRecyclerViewItem();

        addItemList.setTitle(addTitle);
        addItemList.setBody(addBody);
        addItemList.setStartDate(addStartDate);
        addItemList.setEndDate(addEndDate);
        addItemList.setType(addType);

        noticeListViewItemRecycler.add(addItemList);
    }
}