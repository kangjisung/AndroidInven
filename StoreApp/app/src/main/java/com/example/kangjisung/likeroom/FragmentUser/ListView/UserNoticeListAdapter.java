package com.example.kangjisung.likeroom.FragmentUser.ListView;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.FragmentUser.ListView.UserNoticeListItem;
import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.SingleToast;
import com.example.kangjisung.likeroom.R;

import java.util.ArrayList;
import java.util.Calendar;

public class UserNoticeListAdapter extends BaseAdapter
{
    private ArrayList<UserNoticeListItem> userNoticeList;

    public UserNoticeListAdapter() {
        super();

        userNoticeList = new ArrayList<UserNoticeListItem>() ;
    }

    @Override
    public int getCount() {
        return userNoticeList.size() ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_notice_listitem, parent, false);
        }

        TextView textViewTitle = (TextView)convertView.findViewById(R.id.textViewTitle);
        TextView textViewBody = (TextView)convertView.findViewById(R.id.textViewBody);
        TextView textViewDate = (TextView)convertView.findViewById(R.id.textViewDate);
        ImageView imageViewType = (ImageView)convertView.findViewById(R.id.imageViewType);
        Button buttonClick = (Button)convertView.findViewById(R.id.buttonClick);

        final UserNoticeListItem userNoticeItem = userNoticeList.get(position);

        textViewTitle.setText(userNoticeItem.getTitle());
        textViewBody.setText(userNoticeItem.getBody());
        String textDate = String.valueOf(userNoticeItem.getStartDate().get(Calendar.YEAR)) + "/"
                        + String.valueOf(userNoticeItem.getStartDate().get(Calendar.MONTH) + 1) + "/"
                        + String.valueOf(userNoticeItem.getStartDate().get(Calendar.DAY_OF_MONTH)) + " - "
                        + String.valueOf(userNoticeItem.getEndDate().get(Calendar.YEAR)) + "/"
                        + String.valueOf(userNoticeItem.getEndDate().get(Calendar.MONTH) + 1) + "/"
                        + String.valueOf(userNoticeItem.getEndDate().get(Calendar.DAY_OF_MONTH));
        textViewDate.setText(textDate);
        switch(userNoticeItem.getType()){
            default:
            case 1:
                imageViewType.setBackgroundResource(R.mipmap.icon_notice1_notification);
                break;
            case 2:
                imageViewType.setBackgroundResource(R.mipmap.icon_notice2_event);
                break;
            case 3:
                imageViewType.setBackgroundResource(R.mipmap.icon_notice3_newproduct);
                break;
        }
        imageViewType.getBackground().setColorFilter(ColorTheme.getThemeColorRGB(context, R.attr.theme_color_D2), PorterDuff.Mode.SRC_IN);

        //buttonClick.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.clrMenuIcon), PorterDuff.Mode.SRC_IN);
        buttonClick.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                SingleToast.show(context, String.valueOf(userNoticeItem.getTitle()) + " 항목의 버튼 클릭", Toast.LENGTH_SHORT);
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
        return userNoticeList.get(position) ;
    }

    public void addItem(String addTitle, String addBody, Calendar addStartDate, Calendar addEndDate, int addType)
    {
        UserNoticeListItem addItemList = new UserNoticeListItem();

        addItemList.setTitle(addTitle);
        addItemList.setBody(addBody);
        addItemList.setStartDate(addStartDate);
        addItemList.setEndDate(addEndDate);
        addItemList.setType(addType);

        userNoticeList.add(addItemList);
    }
}