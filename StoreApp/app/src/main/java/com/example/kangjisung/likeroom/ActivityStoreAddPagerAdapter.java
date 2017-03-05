package com.example.kangjisung.likeroom;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.FragmentUser.ListView.UserStampListAdapter;
import com.example.kangjisung.likeroom.NetworkManager.HttpCommunicationProcess;
import com.example.kangjisung.likeroom.NetworkManager.NetworkModule;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.NoScrollViewPager;

import org.apache.http.protocol.HTTP;

import java.util.ArrayList;

import static com.example.kangjisung.likeroom.R.id.et_name;


public class ActivityStoreAddPagerAdapter extends PagerAdapter
{
    private LayoutInflater mInflater;
    private Context context;
    private int countPage;
    private boolean isConfirm;
    private int pageList[] = {
            R.id.layout_page1,
            R.id.layout_page2};

    ActivityStoreAddPagerAdapter(Context context){
        super();
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.countPage = pageList.length;
    }

    @Override
    public int getCount(){
        return countPage;
    }

    @Override
    public Object instantiateItem(final View pager, int position) {
        View view = mInflater.inflate(R.layout.activity_store_add_page, null);

        final EditText etName=(EditText) view.findViewById(R.id.et_name);
        final EditText etAddress=(EditText)view.findViewById(R.id.et_address);
        final EditText etPhone=(EditText)view.findViewById(R.id.et_phone);

        for(int page = 0; page < countPage; page++) {
            (view.findViewById(pageList[page])).setVisibility((position == page)?(View.VISIBLE):(View.INVISIBLE));
        }

        switch(position){
            case 0:
                Button mButtonStart = (Button) view.findViewById(R.id.btn_start);
                mButtonStart.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View onClickView) {
                        ((NoScrollViewPager)pager).setCurrentItem(1);
                    }
                });
                break;
            case 1:
                Button mButtonComplete = (Button) view.findViewById(R.id.btn_complete);
                mButtonComplete.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View onClickView) {
                        if(etName.length()!=0&&etAddress.length()!=0&&etPhone.length()!=0)
                            isConfirm = true;
                        // TODO : 여기서 예외처리
                        if(isConfirm == true) {
                            //매장추가
                            // TODO : 여기서 DB에 입력
                            //NetworkModule networkModule=new NetworkModule();
                            //networkModule.InsertNewStoreInfoData(etName.getText().toString(),etAddress.getText().toString(),etPhone.getText().toString());
                            new ClientDataBase("insert into `매장` (`주소`,`이름`,`전화번호`) values (\""+etName.getText().toString()+"\",\""+etAddress.getText().toString()+"\",\""+etPhone.getText().toString()+"\");",2,0,context);
                            Toast.makeText(context, "매장이 등록되었습니다.", Toast.LENGTH_SHORT).show();

                            AppCompatActivity activity = (AppCompatActivity) context;
                            Intent intent = new Intent(activity, ActivityMenu.class);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                        else{
                            Toast.makeText(context, "입력을 정확하게 해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            default:
                break;
        }

        ((NoScrollViewPager)pager).addView(view);

        return view;
    }

    @Override
    public void destroyItem(View pager, int position, Object view) {
        ((ViewPager)pager).removeView((View)view);
    }

    @Override
    public boolean isViewFromObject(View pager, Object obj) {
        return pager == obj;
    }

    @Override public void restoreState(Parcelable arg0, ClassLoader arg1) {}
    @Override public Parcelable saveState() { return null; }
    @Override public void startUpdate(View arg0) {}
    @Override public void finishUpdate(View arg0) {}
}