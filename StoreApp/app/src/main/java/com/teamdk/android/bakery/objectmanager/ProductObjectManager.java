package com.teamdk.android.bakery.objectmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.teamdk.android.bakery.fragments.product.RecyclerViewManager;
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase;
import com.teamdk.android.bakery.utility.SharedPreferenceManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import static com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class ProductObjectManager {
    private static ArrayList<ProductListItem> productItemList;
    private static ArrayList<RecyclerViewManager> recyclerViewManagers = new ArrayList<>();
    private static SharedPreferenceManager mSharedPreferenceManager = new SharedPreferenceManager();
    private static String sortMode = "ASC";
    private static String sortOrder = "ADDEDDATE";

    public ProductObjectManager() {}

    public static void load(Date selectedDate, Context context){
        ProductListItem addItem;
        DateFormat dateFormat =new SimpleDateFormat("y-M-d", Locale.KOREA);
        String query;
        int dbCount;
        int itemCount;

        productItemList = new ArrayList<>();

        // 제품 정보 불러오기
        query = "SELECT * FROM `제품정보`";
        new ClientDataBase(query, 1, 9, context);
        dbCount = 0;
        while (DBstring[dbCount] != null) {
            try {
                addItem = new ProductListItem();
                addItem.setNum(Integer.parseInt(DBstring[dbCount + 1]));
                addItem.setName(DBstring[dbCount + 2]);
                addItem.setType(0);
                addItem.setCost((DBstring[dbCount + 3] == null)?(0):(Integer.parseInt(DBstring[dbCount + 3])));
                addItem.setPrice((DBstring[dbCount + 4] == null)?(0):(Integer.parseInt(DBstring[dbCount + 4])));
                addItem.setResidual((DBstring[dbCount + 5] == null)?(0):(Integer.parseInt(DBstring[dbCount + 5])));
                addItem.setAddedDateToString(DBstring[dbCount + 6]);
                productItemList.add(addItem);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
            finally {
                dbCount += 9;
            }
        }

        // 선택한 날짜의 최적재고량 불러오기
        query = String.format("SELECT S.`이름`, S.`등록일`, T.C FROM " +
                "(SELECT `제품코드`, `이름`, `등록일` FROM `제품정보`) S LEFT JOIN " +
                "(SELECT `제품정보`.`제품코드` AS A, `최적재고량`.`날짜` AS B, `최적재고량` AS C " +
                "FROM `제품정보` LEFT JOIN `최적재고량` ON `제품정보`.`제품코드`= `최적재고량`.`제품코드` WHERE `최적재고량`.`날짜` = \"%s\") " +
                "T on S.`제품코드` = T.A", dateFormat.format(selectedDate));
        new ClientDataBase(query, 1, 3, context);
        dbCount = 0;
        itemCount = 0;
        while (DBstring[dbCount] != null) {
            try {
                productItemList.get(itemCount).setMuchStore((DBstring[dbCount + 2] == null)?(0):(Integer.parseInt(DBstring[dbCount+2])));
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
            finally {
                dbCount += 3;
                itemCount++;
            }
        }

        // 선택한 날짜의 일일판매량 불러오기
        query = String.format("SELECT S.`이름`, T.B, T.C FROM " +
                        "(SELECT `제품코드`, `이름` FROM `제품정보`) S LEFT JOIN " +
                        "(SELECT `제품정보`.`제품코드` AS A, `판매량` AS B, `예상판매량` AS C " +
                        "FROM `제품정보` LEFT JOIN `제품판매량` ON `제품정보`.`제품코드` = `제품판매량`.`제품코드` WHERE `제품판매량`.`년` = %d AND `제품판매량`.`월` = %d AND `제품판매량`.`일` = %d) T on S.`제품코드` = T.A",
                selectedDate.getYear()+1900, selectedDate.getMonth()+1, selectedDate.getDate());
        new ClientDataBase(query, 1, 3, context);
        dbCount = 0;
        itemCount = 0;
        while (DBstring[dbCount] != null) {
            try {
                productItemList.get(itemCount).setSellToday((DBstring[dbCount + 1] == null)?(0):(Integer.parseInt(DBstring[dbCount+1])));
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
            finally {
                dbCount += 3;
                itemCount++;
            }
        }
        sort();
        notifyChanged();
    }

    public static ProductListItem get(int position){
        return productItemList.get(position);
    }

    public static int size() {
        return productItemList.size();
    }

    public static ArrayList<ProductListItem> getArray()
    {
        return productItemList;
    }

    public static void addRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter<RecyclerView.ViewHolder> recyclerViewAdapter){
        recyclerViewManagers.add(new RecyclerViewManager(recyclerView,recyclerViewAdapter));
    }

    public static void notifyChanged(){
        for(int i=0;i<recyclerViewManagers.size();i++){
            recyclerViewManagers.get(i).getRecyclerViewAdapter().notifyDataSetChanged();
            recyclerViewManagers.get(i).getRecyclerView().invalidate();
        }
    }

    public static void setSortOption(String _sortMode, String _sortOrder){
        sortMode = _sortMode;
        sortOrder = _sortOrder;
    }

    public static void sort()
    {
        Collections.sort(productItemList, new Comparator<ProductListItem>(){
            @Override
            public int compare(ProductListItem obj1,ProductListItem obj2) {
                if(sortMode.equals("NAME")){
                    if(sortOrder.equals("ASC")){
                        return obj1.getName().compareToIgnoreCase(obj2.getName());
                    }
                    else{
                        return obj2.getName().compareToIgnoreCase(obj1.getName());
                    }
                }
                else if(sortMode.equals("ADDEDDATE")){
                    if(sortOrder.equals("ASC")){
                        return obj1.getAddedDate().compareTo(obj2.getAddedDate());
                    }
                    else{
                        return obj2.getAddedDate().compareTo(obj1.getAddedDate());
                    }
                }
                else if(sortMode.equals("SALES")){
                    if(sortOrder.equals("ASC")){
                        return Integer.compare(obj1.getSellToday(), obj2.getSellToday());
                    }
                    else{
                        return Integer.compare(obj2.getSellToday(), obj1.getSellToday());
                    }
                }
                return -1;
            }
        });
        notifyChanged();
    }
}