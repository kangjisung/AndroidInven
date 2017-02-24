package com.example.kangjisung.likeroom.FragmentProduct.ListView;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
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

import com.example.kangjisung.likeroom.FragmentProduct.ProductObjManager;
import com.example.kangjisung.likeroom.MainActivity;
import com.example.kangjisung.likeroom.NetworkManager.NetworkModule;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.inventory.calc;

import java.util.Calendar;

import static android.R.attr.name;
import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class FragmentSellTodayRecyclerViewAdapter extends RecyclerView.Adapter<FragmentSellTodayRecyclerViewAdapter.ViewHolder>
{
    Context context;
    public Calendar cal;
    calc c;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        // each data item is just a string in this case
        public TextView tvName;
        public TextView tvAddedDate;
        public TextView etInput;
        public Button buttonInput;
        public ViewHolder(View view) {
            super(view);
            tvName=(TextView)view.findViewById(R.id.tv_name);
            tvAddedDate=(TextView)view.findViewById(R.id.tv_added_date);
            etInput=(TextView)view.findViewById(R.id.et_input);
            buttonInput = (Button)view.findViewById(R.id.button_input);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("작업 선택");
            menu.add(Menu.NONE, 4, Menu.NONE, "수정");
            menu.add(Menu.NONE, 5, Menu.NONE, "삭제");
        }
    }

    public FragmentSellTodayRecyclerViewAdapter(Context context) {
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_sell_today_listitem, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(ProductObjManager.get(position).getName());
        holder.tvAddedDate.setText(ProductObjManager.date2String(ProductObjManager.get(position).getMuchStoreDate()));
        holder.etInput.setText(String.valueOf(ProductObjManager.get(position).getSellToday()));
        holder.buttonInput.setOnClickListener(new Button.OnClickListener(){
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
                        ProductObjManager.get(position).setSellToday(Integer.parseInt(editTextInput.getText().toString()));
                        cal = Calendar.getInstance();
                        //판매량 업데이트
                        new ClientDataBase("update `제품판매량` set `판매량`=\""+Integer.parseInt(editTextInput.getText().toString())+"\" where `년`=\""+cal.get(Calendar.YEAR)+"\" and `월`=\""+(cal.get(Calendar.MONTH)+1)+"\" and `일`=\""+cal.get(Calendar.DATE)+"\"",3,0,MainActivity.con);
                        //판매량 서버넣기
                        int proDuct;
                        new ClientDataBase("select `제품코드` from `제품정보` where `이름`="+name+";",1,1,MainActivity.con);
                        proDuct=Integer.parseInt(DBstring[0]);
                        NetworkModule networkModule=new NetworkModule();
                        networkModule.InsertSalesVolume(proDuct,Integer.parseInt(editTextInput.getText().toString()),""+cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) +1) + "-" + (cal.get(Calendar.DATE)+1)+"",c.FD);

                        dialog.dismiss();
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setTitle(ProductObjManager.get(position).getName());
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                dialog.show();
            }
        });
        /*
        if(ProductObjManager.get(position).isStar()) {
            holder.star.setBackgroundResource(R.drawable.sell_today_recyclerview_background_star);
        }
        else{
            holder.star.setBackgroundResource(R.drawable.sell_today_recyclerview_background);
        }
        */
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ProductObjManager.productInfos.size();
    }
}