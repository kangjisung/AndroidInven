package com.teamdk.android.bakery.fragments.product.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teamdk.android.bakery.fragments.product.ProductMain;
import com.teamdk.android.bakery.objectmanager.ProductObjectManager;
import com.teamdk.android.bakery.utility.NetworkManager.NetworkModule;
import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase;
import com.teamdk.android.bakery.utility.LayoutManager;
import com.teamdk.android.bakery.inventory.calc;

import java.util.Calendar;

import static com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class FragmentSellTodayRecyclerViewAdapter extends RecyclerView.Adapter<FragmentSellTodayRecyclerViewAdapter.ViewHolder>
{
    Context context;
    public Calendar cal;
    calc c;
<<<<<<< HEAD:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/adapter/FragmentSellTodayRecyclerViewAdapter.java
=======

    public FragmentSellTodayRecyclerViewAdapter(Context context) {
        this.context = context;
    }
>>>>>>> refs/remotes/origin/store-app-byeongmun:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/adapter/FragmentSellTodayRecyclerViewAdapter.java

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public View view;
        public TextView mTextViewName;
        public TextView mTextViewAddedDate;
        public TextView mTextViewInput;
        public TextView mTextViewPoint;
        public Button mButtonInput;
        public Button mButtonInventory;
        public RelativeLayout mLayoutSellToday;
        public RelativeLayout mLayoutMuchStore;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            mTextViewName =(TextView)view.findViewById(R.id.tv_name);
            mTextViewAddedDate =(TextView)view.findViewById(R.id.tv_added_date);
            mTextViewInput =(TextView)view.findViewById(R.id.tv_input);
            mTextViewPoint = (TextView)view.findViewById(R.id.tv_point);
            mButtonInput = (Button)view.findViewById(R.id.btn_input);
            mButtonInventory = (Button)view.findViewById(R.id.btn_inventory);
            mLayoutSellToday = (RelativeLayout)view.findViewById(R.id.layout_sell_today);
            mLayoutMuchStore = (RelativeLayout)view.findViewById(R.id.layout_much_store);

            mTextViewPoint.setVisibility(View.GONE);
            mLayoutMuchStore.setVisibility(View.GONE);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("작업 선택");
            menu.add(Menu.NONE, LayoutManager.MENU_PRODUCT_MODIFY, Menu.NONE, "수정");
            menu.add(Menu.NONE, LayoutManager.MENU_PRODUCT_DELETE, Menu.NONE, "삭제");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_listitem, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
<<<<<<< HEAD:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/adapter/FragmentSellTodayRecyclerViewAdapter.java
        holder.tvName.setText(ProductObjectManager.get(position).getName());
        holder.tvAddedDate.setText(ProductObjectManager.date2String(ProductObjectManager.get(position).getMuchStoreDate()));
        holder.etInput.setText(String.valueOf(ProductObjectManager.get(position).getSellToday()));
        holder.buttonInput.setOnClickListener(new Button.OnClickListener(){
=======
        holder.mTextViewName.setText(ProductObjectManager.get(position).getName());
        holder.mTextViewAddedDate.setText("등록일 : " + ProductObjectManager.get(position).getAddedDateToString());
        holder.mTextViewInput.setText(String.valueOf(ProductObjectManager.get(position).getSellToday()));
        holder.mButtonInput.setOnClickListener(new Button.OnClickListener(){
>>>>>>> refs/remotes/origin/store-app-byeongmun:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/adapter/FragmentSellTodayRecyclerViewAdapter.java
            @Override
            public void onClick(View onClickView){
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.product_sell_today_input, null);
                final EditText editTextInput = (EditText)view.findViewById(R.id.editText_input);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(view);
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editTextInput.length() > 0) {
<<<<<<< HEAD:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/adapter/FragmentSellTodayRecyclerViewAdapter.java
                            c = calc.getInstance();
                            ProductObjectManager.get(position).setSellToday(Integer.parseInt(editTextInput.getText().toString()));
                            cal = Calendar.getInstance();
                            c.RefreshClass(ProductObjectManager.get(position).getName());
=======
                            ProductObjectManager.get(position).setSellToday(Integer.parseInt(editTextInput.getText().toString()));
                            cal = Calendar.getInstance();
>>>>>>> refs/remotes/origin/store-app-byeongmun:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/adapter/FragmentSellTodayRecyclerViewAdapter.java
                            //판매량 업데이트
                            new ClientDataBase("update `제품판매량` set `판매량`=\""+Integer.parseInt(editTextInput.getText().toString())+"\" where `년`=\""+cal.get(Calendar.YEAR)+"\" and `월`=\""+(cal.get(Calendar.MONTH)+1)+"\" and `일`=\""+cal.get(Calendar.DATE)+"\"",3,0, context);
                            //판매량 서버넣기
                            new ClientDataBase("select `제품코드` from `제품정보` where `이름`=\""+ ProductObjectManager.get(position).getName()+"\";",1,1, context);
                            int proDuct=Integer.parseInt(DBstring[0]);
                            NetworkModule networkModule=new NetworkModule();
                            networkModule.InsertSalesVolume(proDuct,Integer.parseInt(editTextInput.getText().toString()),""+cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) +1) + "-" + (cal.get(Calendar.DATE)+1)+"",c.FD);
                            dialog.dismiss();
                            notifyDataSetChanged();
                        }
                        else{
                            dialog.dismiss();
                        }
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setTitle(ProductObjectManager.get(position).getName());
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                dialog.show();
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ProductMain.longClickPosition = position;
                return false;
            }
        });
<<<<<<< HEAD:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/adapter/FragmentSellTodayRecyclerViewAdapter.java
        /*
        if(ProductObjectManager.get(position).isStar()) {
            holder.star.setBackgroundResource(R.drawable.sell_today_recyclerview_background_star);
        }
        else{
            holder.star.setBackgroundResource(R.drawable.sell_today_recyclerview_background);
        }
        */
=======
>>>>>>> refs/remotes/origin/store-app-byeongmun:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/adapter/FragmentSellTodayRecyclerViewAdapter.java
    }

    @Override
    public int getItemCount() {
<<<<<<< HEAD:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/adapter/FragmentSellTodayRecyclerViewAdapter.java
        return ProductObjectManager.productInfos.size();
=======
        return ProductObjectManager.size();
>>>>>>> refs/remotes/origin/store-app-byeongmun:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/adapter/FragmentSellTodayRecyclerViewAdapter.java
    }
}