package com.example.kangjisung.likeroom.FragmentNotice;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kangjisung.likeroom.ActivityMenu;
import com.example.kangjisung.likeroom.R;

import java.util.ArrayList;
import java.util.Calendar;

// 공지사항리스트에 관한 부분이다.
// 레이아웃에서는 layout_notice_recycler_view에서 확인할 수 있다.
// 지금은 어플에서 공지사항을 클릭했을 때 알림창이 뜨는 정도로 구현되어있으며
// 각각의 공지사항에 대해서 제목,내용,날짜 등을 담당하는 부분이며, 메소드나 변수 등은 NoticeRecyclerViewItem에서 확인할 수 있다.

public class NoticeRecyclerViewAdapter extends RecyclerView.Adapter<NoticeRecyclerViewAdapter.NoticeRecyclerViewHolder> {
    static int modeOfRecyclerView;
    final static int showNoticeList = 0, showStoreList = 1;
    //0 : 공지 리스트뷰
    //1 : 매장 리스트뷰
    private ArrayList<NoticeRecyclerViewItem> noticeListViewItemRecycler = new ArrayList<NoticeRecyclerViewItem>();
    private Context context;

    public static class NoticeRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewBody, txtNoticeDate;
        Button btnEachNoticeItem;
        ImageView imgNoticeType;
        View view;

        TextView txtShopName, txtShopAddress, txtShopPhoneNumber;
        ImageView imgShopIcon;
        LinearLayout layoutEachStoreItem;

        NoticeRecyclerViewHolder(View view) {
            super(view);

            this.view = view;
            switch (modeOfRecyclerView){
                case showNoticeList:
                    textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
                    textViewBody = (TextView) view.findViewById(R.id.textViewBody);
                    txtNoticeDate = (TextView) view.findViewById(R.id.txtNoticeDate);
                    imgNoticeType = (ImageView) view.findViewById(R.id.imgNoticeType);
                    btnEachNoticeItem = (Button) view.findViewById(R.id.btnEachNoticeItem);
                    break;
                case showStoreList:
                    imgShopIcon = (ImageView) view.findViewById(R.id.imgShopIcon);
                    txtShopName = (TextView) view.findViewById(R.id.txtShopName);
                    txtShopAddress = (TextView) view.findViewById(R.id.txtShopAddress);
                    txtShopPhoneNumber = (TextView) view.findViewById(R.id.txtShopPhoneNumber);
                    layoutEachStoreItem = (LinearLayout) view.findViewById(R.id.layoutEachStoreItem);
                    break;
            }
        }
    }

    public NoticeRecyclerViewAdapter(int modeOfRecyclerView, Context context) {
        this.modeOfRecyclerView = modeOfRecyclerView;
        this.context = context;
    }

    public NoticeRecyclerViewAdapter.NoticeRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (modeOfRecyclerView) {
            case showNoticeList:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notice_recycler_view, parent, false);
                break;
            case showStoreList:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_each_registered_store_item, parent, false);
                break;
        }
        NoticeRecyclerViewAdapter.NoticeRecyclerViewHolder vh = new NoticeRecyclerViewAdapter.NoticeRecyclerViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(NoticeRecyclerViewHolder holder, int position) {
        final NoticeRecyclerViewItem noticeRecyclerViewItem = noticeListViewItemRecycler.get(position);
        switch (modeOfRecyclerView) {
            case showNoticeList:
                holder.textViewTitle.setText(noticeRecyclerViewItem.getTitle());
                holder.textViewBody.setText(noticeRecyclerViewItem.getBody());
                String textDate = String.valueOf(noticeRecyclerViewItem.getStartDate().get(Calendar.YEAR)) + "/"
                        + String.valueOf(noticeRecyclerViewItem.getStartDate().get(Calendar.MONTH)+1) + "/"
                        + String.valueOf(noticeRecyclerViewItem.getStartDate().get(Calendar.DAY_OF_MONTH)) + "/ - "
                        + String.valueOf(noticeRecyclerViewItem.getEndDate().get(Calendar.YEAR)) + "/"
                        + String.valueOf(noticeRecyclerViewItem.getEndDate().get(Calendar.MONTH)+1) + "/"
                        + String.valueOf(noticeRecyclerViewItem.getEndDate().get(Calendar.DAY_OF_MONTH));
                holder.txtNoticeDate.setText(textDate);
                switch (noticeRecyclerViewItem.getType()) {
                    default:
                    case 1:
                        holder.imgNoticeType.setBackgroundResource(R.mipmap.icon_menu_item);
                        break;
                    case 2:
                        holder.imgNoticeType.setBackgroundResource(R.mipmap.icon_menu_point);
                        break;
                    case 3:
                        holder.imgNoticeType.setBackgroundResource(R.mipmap.icon_menu_user);
                        break;
                }
                holder.btnEachNoticeItem.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        //SingleToast.show(context, noticeRecyclerViewItem.getTitle().toString() + " 항목을 눌렀습니다", Toast.LENGTH_SHORT);
                        Snackbar.make(v, noticeRecyclerViewItem.getTitle().toString(), Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
            case showStoreList:
                holder.imgShopIcon.setImageDrawable(noticeRecyclerViewItem.GetStoreImage());
                holder.txtShopName.setText(noticeRecyclerViewItem.GetStoreName());
                holder.txtShopPhoneNumber.setText(noticeRecyclerViewItem.GetStorePhoneNumber());
                holder.txtShopAddress.setText(noticeRecyclerViewItem.GetStoreAddress());

                holder.layoutEachStoreItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Snackbar.make(view, context.getString(R.string.featureLoadFail), Snackbar.LENGTH_SHORT).show();
                        Intent showDetailTargetStore = new Intent(context, ActivityMenu.class);
                        context.startActivity(showDetailTargetStore);
                    }
                });
                break;
        }
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

    public void addItem(Drawable imgOfStore, String storeName, String storeAddress, String storePhoneNumber) {
        NoticeRecyclerViewItem newItemWillAddToList = new NoticeRecyclerViewItem();

        newItemWillAddToList.SetStoreImage(imgOfStore);
        newItemWillAddToList.SetStoreName(storeName);
        newItemWillAddToList.SetStoreAddress(storeAddress);
        newItemWillAddToList.SetStorePhoneNumber(storePhoneNumber);

        noticeListViewItemRecycler.add(newItemWillAddToList);
    }
}