package com.teamdk.android.bakery.objectmanager;

import android.content.Context;

import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import static com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class NoticeObjectManager {
    private static ArrayList<NoticeListItem> noticeItemList;

    public NoticeObjectManager() {
        noticeItemList = new ArrayList<>();
    }

    public static void load(Context context) {
        String query = "SELECT `코드`, `제목`, `내용`, `공지시작날짜`, `공지마감날짜`, `작성시간`, `공지사항종류`, `삭제` FROM `매장공지`;";
        noticeItemList = new ArrayList<>();
        new ClientDataBase(query, 1, 8, context);

        int count=0;

        DateFormat dateFormat = new SimpleDateFormat("y-M-d", Locale.KOREA);
        DateFormat dateTimeFormat = new SimpleDateFormat("y-M-d HH:mm:ss", Locale.KOREA);
        while(DBstring[count] != null)
        {
            NoticeListItem addListItem = new NoticeListItem();
            try {
                addListItem.setNum(Integer.parseInt(DBstring[count]));
                addListItem.setTitle(DBstring[count+1]);
                addListItem.setBody(DBstring[count+2]);
                addListItem.setStartDate(dateFormat.parse(DBstring[count+3]));
                addListItem.setEndDate(dateFormat.parse(DBstring[count+4]));
                addListItem.setMakeDate(dateTimeFormat.parse(DBstring[count+5]));
                addListItem.setType(Integer.parseInt(DBstring[count+6]));
                addListItem.setDelete(Integer.parseInt(DBstring[count+7]));
                if(addListItem.getEndDate().getTime() + (1000 * 60 * 60 * 24) < (new Date()).getTime()){
                    addListItem.setClose(1);
                }
                else{
                    addListItem.setClose(0);
                }
                if(addListItem.getDelete() == 0) {
                    add(addListItem);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                count += 8;
            }
        }
    }

    public static int size() {
        return noticeItemList.size();
    }

    public static void add(NoticeListItem addItem)
    {
        noticeItemList.add(addItem);
    }

    public static NoticeListItem get(int position)
    {
        return noticeItemList.get(position);
    }

    public static ArrayList<NoticeListItem> getArray()
    {
        return noticeItemList;
    }

    public static void delete(int position)
    {
        noticeItemList.remove(position);
    }

    public static void sort()
    {
        Collections.sort(noticeItemList, new Comparator<NoticeListItem>() {
            @Override
            public int compare(NoticeListItem obj1, NoticeListItem obj2) {
                int p1 = obj1.getClose();
                int p2 = obj2.getClose();
                return (p1 < p2) ? -1 : (p1 > p2) ? 1 : 0;
            }
        });
    }
}