package com.example.home7.customlistview;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locallistview.R;

import java.util.ArrayList;
import java.util.Date;

import static android.media.CamcorderProfile.get;
import static com.example.locallistview.R.id.imageViewType;
import static com.example.locallistview.R.id.textViewBody;
import static com.example.locallistview.R.id.textViewDate;
import static com.example.locallistview.R.id.textViewTitle;

/**
 * Created by kangjisung on 2017-01-16.
 */

public class UserNoticeListAdapter extends BaseAdapter {
    private ArrayList<UserNoticeListItem> userNoticeList;
    private int clickPosition = 0;

    UserNoticeListAdapter() {
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
            convertView = inflater.inflate(R.layout.notice_list, parent, false);
        }

        final TextView textViewTitle = (TextView)convertView.findViewById(R.id.textViewTitle);
        final TextView textViewBody = (TextView)convertView.findViewById(R.id.textViewBody);
        TextView textViewDate = (TextView)convertView.findViewById(R.id.textViewDate);
        ImageView imageViewType = (ImageView)convertView.findViewById(R.id.imageViewType);
        Button buttonClick = (Button)convertView.findViewById(R.id.buttonClick);

        final UserNoticeListItem userNoticeItem = userNoticeList.get(position);

        textViewTitle.setText(userNoticeItem.getTitle());
        textViewBody.setText(userNoticeItem.getBody());
        String textDate = String.valueOf(userNoticeItem.getStartDate().getYear()+1900) + "-"
                + String.valueOf(userNoticeItem.getStartDate().getMonth()+1)+ "-"
                + String.valueOf(userNoticeItem.getStartDate().getDate()) + " ~~ "
                + String.valueOf(userNoticeItem.getEndDate().getYear()+1900) + "-"
                + String.valueOf(userNoticeItem.getEndDate().getMonth()+1) + "-"
                + String.valueOf(userNoticeItem.getEndDate().getDate());
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

        //buttonClick.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.clrMenuIcon), PorterDuff.Mode.SRC_IN);
        buttonClick.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View onClickView) {
                clickPosition = position;
                final UserAddDialog userAddDialog = new UserAddDialog(userNoticeItem.getTitle(),userNoticeItem.getBody(),userNoticeItem.getStartDate(),userNoticeItem.getEndDate(),userNoticeItem.getType(),context);
                userAddDialog.show();
                userAddDialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
                    @Override
                    public void onDismiss(DialogInterface dialog){
                        UserNoticeListItem modifyData = userAddDialog.ppp();
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

    public void addItem(String addTitle, String addBody, Date addStartDate, Date addEndDate, int addType)
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
