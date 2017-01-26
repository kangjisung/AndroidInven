package com.example.kangjisung.likeroom.FragmentProduct;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.kangjisung.likeroom.Util.SharedPreferenceManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by user on 2016-12-30.
 */

public class ProductObjManager {
    public static ArrayList<ProductListItem> productInfos = new ArrayList<ProductListItem>();
    public static Context context;
    public static int sortingStatus;
    public static ArrayList<RecyclerViewManager> recyclerViewManagers = new ArrayList<>();
    public static SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager();
    public ProductObjManager(){

    }
    public static void add(ProductListItem productInfo){
        productInfo.getSellTodayDate().setYear(productInfo.getSellTodayDate().getYear()-1900);
        productInfo.getRegisteredDate().setYear(productInfo.getRegisteredDate().getYear()-1900);
        productInfo.getMuchStoreDate().setYear(productInfo.getMuchStoreDate().getYear()-1900);
        productInfos.add(productInfo);
        notifyChanged();
    }
    public static ProductListItem get(int position){
        return productInfos.get(position);
    }
    public static void modify(ProductListItem productInfo, int position){
        productInfo.getSellTodayDate().setYear(productInfo.getSellTodayDate().getYear()-1900);
        productInfo.getRegisteredDate().setYear(productInfo.getRegisteredDate().getYear()-1900);
        productInfo.getMuchStoreDate().setYear(productInfo.getMuchStoreDate().getYear()-1900);
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
                sortByRegisterAsc();
                break;
            case 3:
                sortByRegisterDesc();
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
    public static void sortByRegister(){
        if(sharedPreferenceManager.getInt("product_sort",context)==2){
            sortByRegisterDesc();
            sharedPreferenceManager.putInt("product_sort",3,context);

        }
        else{
            sortByRegisterAsc();
            sharedPreferenceManager.putInt("product_sort",2,context);
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
    public static void sortByRegisterAsc(){
        Collections.sort(productInfos,new RegisterAscCompare());
        notifyChanged();
    }
    public static void sortByRegisterDesc(){
        Collections.sort(productInfos,new RegisterDescCompare());
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
    static class RegisterAscCompare implements Comparator<ProductListItem> {
        @Override
        public int compare(ProductListItem arg0, ProductListItem arg1) {
            // TODO Auto-generated method stub
            return arg0.getRegisteredDate().compareTo(arg1.getRegisteredDate());
        }
    }
    static class RegisterDescCompare implements Comparator<ProductListItem> {
        @Override
        public int compare(ProductListItem arg0, ProductListItem arg1) {
            // TODO Auto-generated method stub
            return arg1.getRegisteredDate().compareTo(arg0.getRegisteredDate());
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
