package com.example.kangjisung.likeroom.FragmentProduct;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.kangjisung.likeroom.FragmentProduct.ListView.FragmentSortRecyclerViewAdapter;
import com.example.kangjisung.likeroom.FragmentProduct.ListView.ProductListItem;
import com.example.kangjisung.likeroom.Util.SharedPreferenceManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class ProductObjManager {
    public static ArrayList<ProductListItem> productInfos = new ArrayList<ProductListItem>();
    public static Context context;
    public static int sortingStatus;
    public static ArrayList<RecyclerViewManager> recyclerViewManagers = new ArrayList<>();
    public static SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager();
    public ProductObjManager(){

    public static void getContext(Context _context)
    {
        context = _context;
    }
    public static void productLoad(Date date){
        Date today=date;
        String STtoday="";
        DateFormat todayformat =new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        STtoday=todayformat.format(today);
        //그날의 최적재고량이 없어서 데이터 표시
        new ClientDataBase("select S.`이름`, S.`등록일`, T.C from (select `제품코드`, `이름`, `등록일` from `제품정보`) S left join (select `제품정보`.`제품코드` AS A, `최적재고량`.`날짜` AS B, `최적재고량` AS C from `제품정보` left join `최적재고량` on `제품정보`.`제품코드`= `최적재고량`.`제품코드` WHERE `최적재고량`.`날짜` = \""+STtoday+"\") T on S.`제품코드` = T.A", 1, 3, context);
        int cnt = 0;
        muchStoreArrayList = new ArrayList<>();
        while (true) {
            if (DBstring[cnt] != null) {
                //그날의 최적재고량이 null이면 0으로표시
                muchStoreArrayList.add(new ProductMuchStoreListItem(DBstring[cnt], DBstring[cnt + 1], (DBstring[cnt + 2]==null)?(0):(Integer.parseInt(DBstring[cnt+2]))));
                cnt += 3;
            } else if (DBstring[cnt] == null) break;
        }

        //제품 이름,날짜,판매량 불러오기(오늘 데이터)
        new ClientDataBase("select `제품정보`.`이름`,`제품정보`,`등록일,`제품판매량`.`판매량` from `제품정보` join `제품판매량` on `제품정보`.`제품코드`= `제품판매량`.`제품코드` where `제품판매량`.`년`="+today.getYear()+1900+"and `제품판매량`.`월`="+today.getMonth()+1+"and `제품판매량`.`일`="+today.getDay()+"", 1, 3, context);
        cnt = 0;
        sellTodayArrayList = new ArrayList<>();
        while (true) {
            if (DBstring[cnt] != null) {
                sellTodayArrayList.add(new ProductSellTodayListItem(DBstring[cnt], DBstring[cnt + 1], Integer.parseInt(DBstring[cnt + 2])));
                cnt += 3;
            } else if (DBstring[cnt] == null){
                //오늘 판매량이 없을시 muchStoreArrayList크기만큼 sellTodayArrayList에 빈값 넣기(데이터 뿌려줄떄 null이면 에러나서)
                if(cnt==0){
                    for(int i=0; i<muchStoreArrayList.size(); i++) sellTodayArrayList.add(new ProductSellTodayListItem(" "," ", 0));
                }
                break;
            }
        }

        productInfos = new ArrayList<ProductListItem>();
        for(int i=0; i<sellTodayArrayList.size(); i++){
            ProductSellTodayListItem sellToday = sellTodayArrayList.get(i);
            ProductMuchStoreListItem muchStore;
            if(i<muchStoreArrayList.size()) muchStore = muchStoreArrayList.get(i);
            else break;
            try {
                add(new ProductListItem(muchStore.getName(), false, muchStore.getDate(), new Date(today.getYear()+1900, today.getMonth()+1, today.getDay()),sellToday.getSell(), muchStore.getMuch()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public static void add(ProductListItem productInfo){
        productInfo.getSellTodayDate().setYear(productInfo.getSellTodayDate().getYear());
        productInfo.getMuchStoreDate().setYear(productInfo.getMuchStoreDate().getYear());
        productInfos.add(productInfo);
        notifyChanged();
    }
    public static ProductListItem get(int position){
        return productInfos.get(position);
    }
    public static void modify(ProductListItem productInfo, int position){
        productInfo.getSellTodayDate().setYear(productInfo.getSellTodayDate().getYear());
        productInfo.getMuchStoreDate().setYear(productInfo.getMuchStoreDate().getYear());
        productInfos.set(position,productInfo);
        notifyChanged();
    }
    public static void addRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter<FragmentSortRecyclerViewAdapter.ViewHolder> recyclerViewAdapter){
        recyclerViewManagers.add(new RecyclerViewManager(recyclerView,recyclerViewAdapter));
    }
    public static String date2String(Date productInfo){
        return new SimpleDateFormat("yyyy-MM-dd").format(productInfo);
    }
    public static void notifyChanged(){
        for(int i=0;i<recyclerViewManagers.size();i++){
            recyclerViewManagers.get(i).getRecyclerViewAdapter().notifyDataSetChanged();
            recyclerViewManagers.get(i).getRecyclerView().invalidate();
        }
    }
    public static int sort(){
        sortingStatus=sharedPreferenceManager.getInt("product_sort",context);
        switch (sortingStatus){
            case 0:
                sortByNameAsc();
                break;
            case 1:
                sortByNameDesc();
                break;
            case 2:
                sortByModifySelltodayAsc();
                break;
            case 3:
                sortByModifySelltodayDesc();
                break;
            case 4:
                sortByModifySelltodayAsc();
                break;
            case 5:
                sortByModifySelltodayDesc();
                break;
            case 6:
                sortByModifyMuchStoreAsc();
                break;
            case 7:
                sortByModifyMuchStoreDesc();
                break;
        }
        notifyChanged();
        return sortingStatus;
    }
    public static void sortByName(){
        if(sharedPreferenceManager.getInt("product_sort",context)==0){
            sortByNameDesc();
            sharedPreferenceManager.putInt("product_sort",1,context);

        }
        else{
            sortByNameAsc();
            sharedPreferenceManager.putInt("product_sort",0,context);
        }
        notifyChanged();
    }
    public static void sortByModifySellToday(){
        if(sharedPreferenceManager.getInt("product_sort",context)==4){
            sortByModifySelltodayDesc();
            sharedPreferenceManager.putInt("product_sort",5,context);
        }
        else{
            sortByModifySelltodayAsc();
            sharedPreferenceManager.putInt("product_sort",4,context);
        }
        notifyChanged();
    }
    public static void sortByModifyMuchStore(){
        if(sharedPreferenceManager.getInt("product_sort",context)==6){
            sortByModifyMuchStoreDesc();
            sharedPreferenceManager.putInt("product_sort",7,context);

        }
        else{
            sortByModifyMuchStoreAsc();
            sharedPreferenceManager.putInt("product_sort",6,context);
        }
        notifyChanged();
    }
    public static void sortByNameAsc(){
        Collections.sort(productInfos,new NameAscCompare());
        notifyChanged();
    }
    public static void sortByNameDesc(){
        Collections.sort(productInfos,new NameDescCompare());
        notifyChanged();
    }
    public static void sortByModifySelltodayAsc(){
        Collections.sort(productInfos,new ModifySellTodayAscCompare());
        notifyChanged();
    }
    public static void sortByModifySelltodayDesc(){
        Collections.sort(productInfos,new ModifySellTodayDescCompare());
        notifyChanged();
    }
    public static void sortByModifyMuchStoreAsc(){
        Collections.sort(productInfos,new ModifyMuchStoreAscCompare());
        notifyChanged();
    }
    public static void sortByModifyMuchStoreDesc(){
        Collections.sort(productInfos,new ModifyMuchStoreDescCompare());
        notifyChanged();
    }
    static class NameAscCompare implements Comparator<ProductListItem>{
        @Override
        public int compare(ProductListItem arg0, ProductListItem arg1) {
            // TODO Auto-generated method stub
            return arg0.getName().compareTo(arg1.getName());
        }
    }
    static class NameDescCompare implements Comparator<ProductListItem> {
        @Override
        public int compare(ProductListItem arg0, ProductListItem arg1) {
            // TODO Auto-generated method stub
            return arg1.getName().compareTo(arg0.getName());
        }
    }
    static class ModifyMuchStoreAscCompare implements Comparator<ProductListItem> {
        @Override
        public int compare(ProductListItem arg0, ProductListItem arg1) {
            // TODO Auto-generated method stub
            return arg0.getMuchStoreDate().compareTo(arg1.getMuchStoreDate());
        }
    }
    static class ModifyMuchStoreDescCompare implements Comparator<ProductListItem> {
        @Override
        public int compare(ProductListItem arg0, ProductListItem arg1) {
            // TODO Auto-generated method stub
            return arg1.getMuchStoreDate().compareTo(arg0.getMuchStoreDate());
        }
    }
    static class ModifySellTodayAscCompare implements Comparator<ProductListItem> {
        @Override
        public int compare(ProductListItem arg0, ProductListItem arg1) {
            // TODO Auto-generated method stub
            return arg0.getSellTodayDate().compareTo(arg1.getSellTodayDate());
        }
    }
    static class ModifySellTodayDescCompare implements Comparator<ProductListItem> {
        @Override
        public int compare(ProductListItem arg0, ProductListItem arg1) {
            // TODO Auto-generated method stub
            return arg1.getSellTodayDate().compareTo(arg0.getSellTodayDate());
        }
    }
}
