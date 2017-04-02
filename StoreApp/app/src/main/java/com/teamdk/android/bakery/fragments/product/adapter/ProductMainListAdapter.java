package com.teamdk.android.bakery.fragments.product.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.teamdk.android.bakery.inventory.InventoryMain;
import com.teamdk.android.bakery.objectmanager.ProductObjectManager;
import com.teamdk.android.bakery.utility.NetworkManager.NetworkModule;
import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase;
import com.teamdk.android.bakery.utility.LayoutManager;
import com.teamdk.android.bakery.inventory.calc;
import com.teamdk.android.bakery.utility.Utility;

import java.util.Calendar;

import static com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class ProductMainListAdapter extends RecyclerView.Adapter<ProductMainListAdapter.ViewHolder>
{
    private Context context;
    private String mode;
    private Calendar cal;
    private calc c;
    private int height = -1;

    public ProductMainListAdapter(Context context, String mode) {
        this.context = context;
        this.mode = mode;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public View view;
        public TextView mTextViewName;
        public TextView mTextViewYear;
        public TextView mTextViewMonth;
        public TextView mTextViewDay;
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
            mTextViewYear = (TextView)view.findViewById(R.id.tv_year);
            mTextViewMonth = (TextView)view.findViewById(R.id.tv_month);
            mTextViewDay = (TextView)view.findViewById(R.id.tv_day);
            mTextViewInput =(TextView)view.findViewById(R.id.tv_input);
            mTextViewPoint = (TextView)view.findViewById(R.id.tv_point);
            mButtonInput = (Button)view.findViewById(R.id.btn_input);
            mButtonInventory = (Button)view.findViewById(R.id.btn_inventory);
            mLayoutSellToday = (RelativeLayout)view.findViewById(R.id.layout_sell_today);
            mLayoutMuchStore = (RelativeLayout)view.findViewById(R.id.layout_much_store);

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
        if(position == getItemCount() - 1){
            holder.view.setLayoutParams(new RelativeLayout.LayoutParams(holder.view.getLayoutParams().width, Utility.convertDpToPixels(80, context)));
            holder.view.setVisibility(View.INVISIBLE);
            return;
        }
        if(height == -1){
            height = holder.view.getLayoutParams().height;
        }
        holder.view.setLayoutParams(new RelativeLayout.LayoutParams(holder.view.getLayoutParams().width, height));
        holder.view.setVisibility(View.VISIBLE);

        holder.mTextViewName.setText(ProductObjectManager.get(position).getName());
        holder.mTextViewYear.setText(String.valueOf(ProductObjectManager.get(position).getAddedDate().getYear()+1900));
        holder.mTextViewMonth.setText(String.valueOf(ProductObjectManager.get(position).getAddedDate().getMonth()+1));
        holder.mTextViewDay.setText(String.valueOf(ProductObjectManager.get(position).getAddedDate().getDate()));
        if(mode == "MuchStore"){
            // 최적재고량 리스트
            holder.mLayoutSellToday.setVisibility(View.GONE);
            holder.mTextViewPoint.setText(ProductObjectManager.get(position).getMuchStore()+"");
            final Intent mil = new Intent(context, InventoryMain.class);
            holder.mButtonInventory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    calc c;
                    c = calc.getInstance();
                    c.RefreshClass(ProductObjectManager.get(position).getName());
                    context.startActivity(mil);
                }
            });
            holder.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ProductMain.longClickPosition = position;
                    return false;
                }
            });
        }
        else{
            // 제품판매량 리스트
            holder.mTextViewPoint.setVisibility(View.GONE);
            holder.mLayoutMuchStore.setVisibility(View.GONE);
            holder.mTextViewInput.setText(String.valueOf(ProductObjectManager.get(position).getSellToday()));
            holder.mButtonInput.setOnClickListener(new Button.OnClickListener(){
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
                                ProductObjectManager.get(position).setSellToday(Integer.parseInt(editTextInput.getText().toString()));
                                cal = Calendar.getInstance();
                                //판매량 업데이트
                                new ClientDataBase("update `제품판매량` set `판매량`=\""+Integer.parseInt(editTextInput.getText().toString())+"\" where `년`=\""+cal.get(Calendar.YEAR)+"\" and `월`=\""+(cal.get(Calendar.MONTH)+1)+"\" and `일`=\""+cal.get(Calendar.DATE)+"\"",3,0, context);
                                //판매량 서버넣기
                                new ClientDataBase("select `제품코드` from `제품정보` where `이름`=\""+ ProductObjectManager.get(position).getName()+"\";",1,1, context);
                                int proDuct=Integer.parseInt(DBstring[0]);
                                calc c = new calc(ProductObjectManager.get(position).getName());
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
        }
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ProductMain.longClickPosition = position;
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return ProductObjectManager.size() + 1;
    }
}