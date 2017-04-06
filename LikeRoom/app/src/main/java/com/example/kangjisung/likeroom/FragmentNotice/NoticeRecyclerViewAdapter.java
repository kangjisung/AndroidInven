package com.example.kangjisung.likeroom.FragmentNotice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kangjisung.likeroom.ActivityMenu;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.StoreAddDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.kangjisung.likeroom.DefineManager.isStoreListNeedsRefresh;
import static com.example.kangjisung.likeroom.DefineManager.selectedShopInfoDataKey;
import static com.example.kangjisung.likeroom.DefineManager.shopAddressSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.shopCloseTimeSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.shopIdSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.shopLatitudeSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.shopLongtitudedSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.shopNameSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.shopOpenTimeSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.shopPhoneNumberSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.showUnRegisteredStoreList;

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
    Activity activity;
    View acceptButtonEnableControl;
    private String listTypeText[] = {"알림", "신제품", "이벤트"};
    private int listTypeImage[] = {R.mipmap.icon_notice1_notification, R.mipmap.icon_notice2_event, R.mipmap.icon_notice3_newproduct};

    public static class NoticeRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewBody, txtNoticeDate, textViewType;
        Button btnEachNoticeItem;
        ImageView imgNoticeType;
        View view;

        TextView txtShopName, txtShopAddress, txtShopPhoneNumber;
        ImageView imgShopIcon;
        RelativeLayout layoutEachStoreItem;

        NoticeRecyclerViewHolder(View view) {
            super(view);

            this.view = view;
            switch (modeOfRecyclerView){
                case showNoticeList:
                    textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
                    textViewBody = (TextView) view.findViewById(R.id.textViewBody);
                    textViewType = (TextView) view.findViewById(R.id.textViewType);
                    txtNoticeDate = (TextView) view.findViewById(R.id.txtNoticeDate);
                    imgNoticeType = (ImageView) view.findViewById(R.id.imgNoticeType);
                    btnEachNoticeItem = (Button) view.findViewById(R.id.btnEachNoticeItem);
                    break;
                case showStoreList:
                    imgShopIcon = (ImageView) view.findViewById(R.id.imgShopIcon);
                    txtShopName = (TextView) view.findViewById(R.id.txtShopName);
                    txtShopAddress = (TextView) view.findViewById(R.id.txtShopAddress);
                    txtShopPhoneNumber = (TextView) view.findViewById(R.id.txtShopPhoneNumber);
                    layoutEachStoreItem = (RelativeLayout) view.findViewById(R.id.layoutEachStoreItem);
                    break;
                case showUnRegisteredStoreList:
                    imgShopIcon = (ImageView) view.findViewById(R.id.imgShopIcon);
                    txtShopName = (TextView) view.findViewById(R.id.txtShopName);
                    txtShopAddress = (TextView) view.findViewById(R.id.txtShopAddress);
                    txtShopPhoneNumber = (TextView) view.findViewById(R.id.txtShopPhoneNumber);
                    layoutEachStoreItem = (RelativeLayout) view.findViewById(R.id.layoutEachStoreItem);
                    break;

            }
        }
    }

    public NoticeRecyclerViewAdapter(int modeOfRecyclerView, Activity activity) {
        this.modeOfRecyclerView = modeOfRecyclerView;
        this.context = activity.getApplicationContext();
        this.activity = activity;
    }

    public NoticeRecyclerViewAdapter(int modeOfRecyclerView, Context context) {
        this.modeOfRecyclerView = modeOfRecyclerView;
        this.context = context;
    }

    public NoticeRecyclerViewAdapter(int modeOfRecyclerView, Context context, View acceptButtonEnableControl) {
        this.modeOfRecyclerView = modeOfRecyclerView;
        this.context = context;
        this.acceptButtonEnableControl = acceptButtonEnableControl;
    }

    public NoticeRecyclerViewAdapter.NoticeRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (modeOfRecyclerView) {
            case showNoticeList:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notice_recycler_view_new, parent, false);
                break;
            case showStoreList:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_each_registered_store_item, parent, false);
                break;
            case showUnRegisteredStoreList:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_store_recycler_view, parent, false);
                break;
        }
        NoticeRecyclerViewAdapter.NoticeRecyclerViewHolder vh = new NoticeRecyclerViewAdapter.NoticeRecyclerViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    public void ChangeListMode(int modeOfRecyclerView) {
        this.modeOfRecyclerView = modeOfRecyclerView;
    }

    @Override
    public void onBindViewHolder(NoticeRecyclerViewHolder holder, int position) {
        final NoticeRecyclerViewItem noticeRecyclerViewItem = noticeListViewItemRecycler.get(position);
        switch (modeOfRecyclerView) {
            case showNoticeList:
                String textDate = GetNoticeReadableDate(noticeRecyclerViewItem);
                holder.textViewTitle.setText(noticeRecyclerViewItem.getTitle());
                holder.textViewBody.setText(noticeRecyclerViewItem.getBody());
                holder.textViewType.setText(listTypeText[noticeRecyclerViewItem.getType()-1]);
                holder.imgNoticeType.setBackgroundResource(listTypeImage[noticeRecyclerViewItem.getType()-1]);
                ((TextView) holder.view.findViewById(R.id.tv_start_year)).setText(String.valueOf(noticeRecyclerViewItem.getStartDate().get(Calendar.YEAR)));
                ((TextView) holder.view.findViewById(R.id.tv_start_month)).setText(String.valueOf(noticeRecyclerViewItem.getStartDate().get(Calendar.MONTH)+1));
                ((TextView) holder.view.findViewById(R.id.tv_start_day)).setText((String.valueOf(noticeRecyclerViewItem.getStartDate().get(Calendar.DAY_OF_MONTH)) + " - "));
                ((TextView) holder.view.findViewById(R.id.tv_end_year)).setText(String.valueOf(noticeRecyclerViewItem.getEndDate().get(Calendar.YEAR)));
                ((TextView) holder.view.findViewById(R.id.tv_end_month)).setText(String.valueOf(noticeRecyclerViewItem.getEndDate().get(Calendar.MONTH)+1));
                ((TextView) holder.view.findViewById(R.id.tv_end_day)).setText(String.valueOf(noticeRecyclerViewItem.getEndDate().get(Calendar.DAY_OF_MONTH)));
                //holder.txtNoticeDate.setText(textDate);
                holder.view.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        //SingleToast.show(context, noticeRecyclerViewItem.getTitle().toString() + " 항목을 눌렀습니다", Toast.LENGTH_SHORT);
                        //Snackbar.make(v, noticeRecyclerViewItem.getTitle().toString(), Snackbar.LENGTH_SHORT).show();
                        //공지사항을 눌렀을 시 세부 내용을 출력하는 팝업을 구현해야함
                        NoticeReadDialog noticeReadDialog = new NoticeReadDialog(context, noticeRecyclerViewItem.getTitle(),
                                noticeRecyclerViewItem.getBody(), noticeRecyclerViewItem.getStartDate(), noticeRecyclerViewItem.getEndDate(), noticeRecyclerViewItem.getType());
                        noticeReadDialog.show();
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
                        showDetailTargetStore.putExtra(selectedShopInfoDataKey[shopNameSavedPoint], noticeRecyclerViewItem.GetStoreName());
                        showDetailTargetStore.putExtra(selectedShopInfoDataKey[shopAddressSavedPoint], noticeRecyclerViewItem.GetStoreAddress());
                        showDetailTargetStore.putExtra(selectedShopInfoDataKey[shopPhoneNumberSavedPoint], noticeRecyclerViewItem.GetStorePhoneNumber());
                        showDetailTargetStore.putExtra(selectedShopInfoDataKey[shopOpenTimeSavedPoint], noticeRecyclerViewItem.GetStoreOpenTime());
                        showDetailTargetStore.putExtra(selectedShopInfoDataKey[shopCloseTimeSavedPoint], noticeRecyclerViewItem.GetStoreCloseTime());
                        showDetailTargetStore.putExtra(selectedShopInfoDataKey[shopLatitudeSavedPoint], "" + noticeRecyclerViewItem.GetStoreLatitude());
                        showDetailTargetStore.putExtra(selectedShopInfoDataKey[shopLongtitudedSavedPoint], "" + noticeRecyclerViewItem.GetStoreLongtitude());
                        showDetailTargetStore.putExtra(selectedShopInfoDataKey[shopIdSavedPoint], noticeRecyclerViewItem.GetStoreId());
                        Log.d("LikeRoom", "la: " + noticeRecyclerViewItem.GetStoreLatitude() + " lo: " + noticeRecyclerViewItem.GetStoreLongtitude());
                        //context.startActivity(showDetailTargetStore);
                        activity.startActivityForResult(showDetailTargetStore, isStoreListNeedsRefresh);
                        //Log.d("LikeRoom", "can u see me?");
                    }
                });
                break;
            case showUnRegisteredStoreList:
                //holder.imgShopIcon.setImageDrawable(noticeRecyclerViewItem.GetStoreImage());
                holder.txtShopName.setText(noticeRecyclerViewItem.GetStoreName());
                holder.txtShopPhoneNumber.setText(noticeRecyclerViewItem.GetStorePhoneNumber());
                holder.txtShopAddress.setText(noticeRecyclerViewItem.GetStoreAddress());

                holder.layoutEachStoreItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(StoreAddDialog.selectedWantRegisterNewStoreId == null) {
                            Snackbar.make(view, noticeRecyclerViewItem.GetStoreName() + " " + context.getString(R.string.willRegisterNewStore),
                                    Snackbar.LENGTH_LONG).show();
                            StoreAddDialog.selectedWantRegisterNewStoreId = noticeRecyclerViewItem.GetStoreId();
                            acceptButtonEnableControl.setEnabled(true);
                        }
                        else {
                            Snackbar.make(view, noticeRecyclerViewItem.GetStoreName() + " " + context.getString(R.string.cancelRegisterNewStore),
                                    Snackbar.LENGTH_LONG).show();
                            StoreAddDialog.selectedWantRegisterNewStoreId = null;
                            acceptButtonEnableControl.setEnabled(false);
                        }
                        //Snackbar.make(view, context.getString(R.string.featureLoadFail), Snackbar.LENGTH_SHORT).show();
                        //StoreAddDialog.selectedWantRegisterNewStoreId = noticeRecyclerViewItem.GetStoreId();
                    }
                });
                break;
        }
    }

    public String GetNoticeReadableDate(NoticeRecyclerViewItem noticeRecyclerViewItem) {
        if(noticeRecyclerViewItem == null)
            return null;
        return String.valueOf(noticeRecyclerViewItem.getStartDate().get(Calendar.YEAR)) + "/"
                + String.valueOf(noticeRecyclerViewItem.getStartDate().get(Calendar.MONTH)+1) + "/"
                + String.valueOf(noticeRecyclerViewItem.getStartDate().get(Calendar.DAY_OF_MONTH)) + "/ - "
                + String.valueOf(noticeRecyclerViewItem.getEndDate().get(Calendar.YEAR)) + "/"
                + String.valueOf(noticeRecyclerViewItem.getEndDate().get(Calendar.MONTH)+1) + "/"
                + String.valueOf(noticeRecyclerViewItem.getEndDate().get(Calendar.DAY_OF_MONTH));
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

    public void addItem(String addTitle, String addBody, String addStartDate, String addEndDate, int addType) {
        NoticeRecyclerViewItem addItemList = new NoticeRecyclerViewItem();
        SimpleDateFormat noticeDateFormat = new SimpleDateFormat("yyyy MM dd", Locale.KOREA);
        Calendar addStartDateCalendar = Calendar.getInstance(), addEndDateCalendar = Calendar.getInstance();
        try {
            addStartDateCalendar.setTime(noticeDateFormat.parse(addStartDate));
            addEndDateCalendar.setTime(noticeDateFormat.parse(addEndDate));
        }
        catch(Exception err) {
            Log.d(context.getString(R.string.app_name), "Error in addItem: " + err.getMessage());
        }

        addItemList.setTitle(addTitle);
        addItemList.setBody(addBody);
        addItemList.setStartDate(addStartDateCalendar);
        addItemList.setEndDate(addEndDateCalendar);
        addItemList.setType(addType);

        noticeListViewItemRecycler.add(addItemList);
    }

    public void addItem(Drawable imgOfStore, String storeId, String storeName, String storeAddress, String storePhoneNumber, String storeOpenTime,
                        String storeCloseTime, Double storeLatitude, Double storeLongtitude) {
        NoticeRecyclerViewItem newItemWillAddToList = new NoticeRecyclerViewItem();

        //Log.d("LikeRoom","la: " + storeLatitude + " lo: " + storeLongtitude);
        newItemWillAddToList.SetStoreImage(imgOfStore);
        newItemWillAddToList.SetStoreId(storeId);
        newItemWillAddToList.SetStoreName(storeName);
        newItemWillAddToList.SetStoreAddress(storeAddress);
        newItemWillAddToList.SetStorePhoneNumber(storePhoneNumber);
        newItemWillAddToList.SetStoreOpenTime(storeOpenTime);
        newItemWillAddToList.SetStoreCloseTime(storeCloseTime);
        newItemWillAddToList.SetStoreLatitude(storeLatitude);
        newItemWillAddToList.SetStoreLongtitude(storeLongtitude);

        noticeListViewItemRecycler.add(newItemWillAddToList);
    }

    public void DeleteAllItems() {
        int i;
        for(i = noticeListViewItemRecycler.size() - 1; i >= 0; i -= 1) {
            noticeListViewItemRecycler.remove(i);
        }
    }
}