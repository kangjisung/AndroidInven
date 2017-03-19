package com.teamdk.android.bakery.objectmanager;

import android.content.Context;

import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import static com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class MemberObjectManager {
    private static ArrayList<MemberListItem> memberItemList;

    public MemberObjectManager() {
        memberItemList = new ArrayList<>();
    }

    public static void load(Context context) {
        String query = "SELECT `회원정보`.`고유회원등록번호`, `회원정보`.`이름`, `회원정보`.`전화번호`, `포인트`.`포인트`, `회원정보`.`생년월일`, `회원정보`.`이메일`, `회원정보`.`삭제`" +
                "FROM `회원정보` LEFT JOIN `포인트` ON `회원정보`.`고유회원등록번호`= `포인트`.`고유회원등록번호`;";

        memberItemList = new ArrayList<>();
        new ClientDataBase(query, 1, 7, context);

        int count = 0;

        DateFormat dateFormat = new SimpleDateFormat("y-M-d", Locale.KOREA);
        while (DBstring[count] != null) {
            MemberListItem addListItem = new MemberListItem();
            try {
                addListItem.setNum(Integer.parseInt(DBstring[count]));
                addListItem.setName(DBstring[count + 1]);
                addListItem.setPhone(DBstring[count + 2]);
                addListItem.setPoint((DBstring[count + 3] == null) ? ("0") : (DBstring[count + 3]));
                addListItem.setBirth(dateFormat.parse(DBstring[count + 4]));
                addListItem.setEmail(DBstring[count + 5]);
                addListItem.setDelete(Integer.parseInt(DBstring[count + 6]));

                if (addListItem.getDelete() == 0) {
                    add(addListItem);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            finally {
                count += 7;
            }
        }
    }

    public static int size() {
        return memberItemList.size();
    }

    public static void add(MemberListItem addItem)
    {
        memberItemList.add(addItem);
    }

    public static MemberListItem get(int position)
    {
        return memberItemList.get(position);
    }

    public static ArrayList<MemberListItem> getArray()
    {
        return memberItemList;
    }

    public static void delete(int position)
    {
        memberItemList.remove(position);
    }

    public static void sort(final String sortMode, final String sortOrder)
    {
        Collections.sort(memberItemList, new Comparator<MemberListItem>(){
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
    }
}