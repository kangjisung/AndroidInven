package com.example.kangjisung.likeroom.FragmentUser;

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

import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.SingleToast;
import com.example.kangjisung.likeroom.R;

import java.util.ArrayList;
import java.util.Calendar;

public class UserNoticeListViewAdapter extends BaseAdapter
{
    private ArrayList<UserNoticeListViewItem> userNoticeListViewItem;

    UserNoticeListViewAdapter() {
        super();

        userNoticeListViewItem = new ArrayList<UserNoticeListViewItem>() ;
    }

    @Override
    public int getCount() {
        return userNoticeListViewItem.size() ;
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

        final UserNoticeListViewItem userNoticeViewItem = userNoticeListViewItem.get(position);

        textViewTitle.setText(userNoticeViewItem.getTitle());
        textViewBody.setText(userNoticeViewItem.getBody());
        String textDate = String.valueOf(userNoticeViewItem.getStartDate().get(Calendar.YEAR)) + "/"
                        + String.valueOf(userNoticeViewItem.getStartDate().get(Calendar.MONTH) + 1) + "/"
                        + String.valueOf(userNoticeViewItem.getStartDate().get(Calendar.DAY_OF_MONTH)) + "/ - "
                        + String.valueOf(userNoticeViewItem.getEndDate().get(Calendar.YEAR)) + "/"
                        + String.valueOf(userNoticeViewItem.getEndDate().get(Calendar.MONTH) + 1) + "/"
                        + String.valueOf(userNoticeViewItem.getEndDate().get(Calendar.DAY_OF_MONTH));
        textViewDate.setText(textDate);
        switch(userNoticeViewItem.getType()){
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
                SingleToast.show(context, String.valueOf(userNoticeViewItem.getTitle()) + " 항목의 버튼 클릭", Toast.LENGTH_SHORT);
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
        return userNoticeListViewItem.get(position) ;
    }

    public void addItem(String addTitle, String addBody, Calendar addStartDate, Calendar addEndDate, int addType)
    {
        UserNoticeListViewItem addItemList = new UserNoticeListViewItem();

        addItemList.setTitle(addTitle);
        addItemList.setBody(addBody);
        addItemList.setStartDate(addStartDate);
        addItemList.setEndDate(addEndDate);
        addItemList.setType(addType);

        userNoticeListViewItem.add(addItemList);
    }
}
