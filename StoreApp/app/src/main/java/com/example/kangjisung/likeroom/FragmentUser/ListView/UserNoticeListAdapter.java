package com.example.kangjisung.likeroom.FragmentUser.ListView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;

import android.support.v7.widget.AppCompatImageView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.FragmentUser.ListView.UserNoticeListItem;
import com.example.kangjisung.likeroom.FragmentUser.UserNoticeEditDialog;
import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.SingleToast;
import com.example.kangjisung.likeroom.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class UserNoticeListAdapter extends BaseAdapter
{
    private ArrayList<UserNoticeListItem> userNoticeList;

    private int clickPosition = 0;

    private int listTypeImage[] = {
            R.mipmap.icon_notice1_notification,
            R.mipmap.icon_notice2_event,
            R.mipmap.icon_notice3_newproduct};

    private String listTypeText[] = {"알림", "신제품", "이벤트"};


    public UserNoticeListAdapter() {
        super();

        userNoticeList = new ArrayList<UserNoticeListItem>() ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final Context context = parent.getContext();
        final UserNoticeListItem userNoticeItem = userNoticeList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_notice_listitem, parent, false);
        }

        TextView textViewTitle = (TextView)convertView.findViewById(R.id.textView_title);
        TextView textViewBody = (TextView)convertView.findViewById(R.id.textView_body);

        TextView mTextViewType = (TextView)convertView.findViewById(R.id.textView_type);
        TextView mTextViewClose = (TextView)convertView.findViewById(R.id.tv_close);
        AppCompatImageView imageViewType = (AppCompatImageView)convertView.findViewById(R.id.imageView_type);
        RelativeLayout mLayoutRoot = (RelativeLayout)convertView.findViewById(R.id.layout_root);
        mLayoutRoot.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        if(userNoticeItem.getClose() == 1){
            mTextViewClose.setVisibility(View.VISIBLE);
            mLayoutRoot.setBackgroundColor(ContextCompat.getColor(context, R.color.gray240));
        }
        else{
            mTextViewClose.setVisibility(View.INVISIBLE);
        }

        String textColor1 = "#" + Integer.toHexString(ColorTheme.getThemeColorRGB(context, R.attr.theme_color_N)).substring(2);
        String textColor2 = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.gray120)).substring(2);
        textViewTitle.setText(userNoticeItem.getTitle());
        String textBody = "<font color = " + textColor1 + ">" + /*listTypeText[userNoticeItem.getType()]*/ " </font>" +
                "<font color = " + textColor2 + ">" + userNoticeItem.getBody() + "</font>";
        textBody = textBody.replace("\n", "<br>");
        textViewBody.setText(Html.fromHtml(textBody));
        mTextViewType.setText(listTypeText[userNoticeItem.getType()]);
        ((TextView) convertView.findViewById(R.id.tv_start_year)).setText(String.valueOf(userNoticeItem.getStartDate().getYear()+1900));
        ((TextView) convertView.findViewById(R.id.tv_start_month)).setText(String.valueOf(userNoticeItem.getStartDate().getMonth()+1));
        ((TextView) convertView.findViewById(R.id.tv_start_day)).setText(String.valueOf(userNoticeItem.getStartDate().getDate()) + " - ");
        ((TextView) convertView.findViewById(R.id.tv_end_year)).setText(String.valueOf(userNoticeItem.getEndDate().getYear()+1900));
        ((TextView) convertView.findViewById(R.id.tv_end_month)).setText(String.valueOf(userNoticeItem.getEndDate().getMonth()+1));
        ((TextView) convertView.findViewById(R.id.tv_end_day)).setText(String.valueOf(userNoticeItem.getEndDate().getDate()));

        imageViewType.setBackgroundResource(listTypeImage[userNoticeItem.getType()]);
        //imageViewType.getBackground().setColorFilter(ColorTheme.getThemeColorRGB(context, R.attr.theme_color_D2), PorterDuff.Mode.SRC_IN);

        /*

        buttonClick.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                clickPosition = position;
                final UserNoticeEditDialog userNoticeEditDialog = new UserNoticeEditDialog(userNoticeItem, context);
                userNoticeEditDialog.show();
                userNoticeEditDialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
                    @Override
                    public void onDismiss(DialogInterface dialog){
                        notifyDataSetChanged();
                    }
                });
                final UserNoticeEditDialog userNoticeEditDialog = new UserNoticeEditDialog(userNoticeItem, context);
                userNoticeEditDialog.show();
                userNoticeEditDialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
                    @Override
                    public void onDismiss(DialogInterface dialog){
                        UserNoticeListItem modifyData = userNoticeEditDialog.ppp();
                        userNoticeList.get(clickPosition).setTitle(modifyData.getTitle());
                        userNoticeList.get(clickPosition).setBody(modifyData.getBody());
                        userNoticeList.get(clickPosition).setStartDate(modifyData.getStartDate());
                        userNoticeList.get(clickPosition).setEndDate(modifyData.getEndDate());
                        userNoticeList.get(clickPosition).setType(modifyData.getType());
                        notifyDataSetChanged();
                    }
                });

            }
        });
        */

        return convertView;
    }

    @Override
    public int getCount() {
        return userNoticeList.size() ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return userNoticeList.get(position) ;
    }

    public void addItem(String addTitle, String addBody, Date addStartDate, Date addEndDate, int addType)
    {
        UserNoticeListItem addItemList = new UserNoticeListItem();

        addItemList.setTitle(addTitle);
        addItemList.setBody(addBody);
        addItemList.setStartDate(addStartDate);
        addItemList.setEndDate(addEndDate);
        addItemList.setType(addType);
        if(addEndDate.getTime() < (new Date()).getTime()){
            addItemList.setClose(1);
        }
        else{
            addItemList.setClose(0);
        }

        userNoticeList.add(addItemList);
    }

    public void addItem(UserNoticeListItem addListItem)
    {
        if(addListItem.getEndDate().getTime() + (1000 * 60 * 60 * 24) < (new Date()).getTime()){
            addListItem.setClose(1);
        }
        else{
            addListItem.setClose(0);
        }
        userNoticeList.add(addListItem);
    }

    public void sort()
    {
        Collections.sort(userNoticeList, new Comparator<UserNoticeListItem>() {
            @Override
            public int compare(UserNoticeListItem obj1, UserNoticeListItem obj2) {
                int p1 = obj1.getClose();
                int p2 = obj2.getClose();
                return (p1 < p2) ? -1 : (p1 > p2) ? 1 : 0;
            }
        });
    }
}