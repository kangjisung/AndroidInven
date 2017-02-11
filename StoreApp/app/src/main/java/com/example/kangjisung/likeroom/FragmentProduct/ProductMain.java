package com.example.kangjisung.likeroom.FragmentProduct;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.NoScrollViewPager;
import com.example.kangjisung.likeroom.Util.FirstPageFragmentListener;
import com.github.clans.fab.FloatingActionMenu;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ProductMain extends Fragment {
    private Button btnSellToday;
    private Button btnMuchStore;
    public static NoScrollViewPager noScrollViewPager;
    private TextView tvFragmentItemMain;
    private TextView tvFragmentItemMainDate;
    static public FirstPageFragmentListener firstPageListener;
    private ProductAddDialog productAddDialog;
    private Button buttonSelectDate;
    private FloatingActionMenu famMenu;
    private View rootView;
    private Calendar nowDate;
    private Calendar selectDate;

    public ProductMain() {
    }
    public ProductMain(FirstPageFragmentListener listener) {
        firstPageListener=listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = this.rootView = inflater.inflate(R.layout.product_main, container, false);

        ///////init///////
        btnSellToday=(Button)rootView.findViewById(R.id.btn_fragment_item_main_sell_today);
        btnMuchStore=(Button)rootView.findViewById(R.id.btn_fragment_item_main_store_match);
        btnSellToday.setOnClickListener(onClickSelectButton);
        btnMuchStore.setOnClickListener(onClickSelectButton);

        noScrollViewPager=(NoScrollViewPager) rootView.findViewById(R.id.vp_fragment_item_main);
        tvFragmentItemMain=(TextView)rootView.findViewById(R.id.tv_fragment_item_main);
        tvFragmentItemMainDate=(TextView)rootView.findViewById(R.id.tv_fragment_item_main_date);

        final ProductMainPagerAdapter adapter = new ProductMainPagerAdapter(getFragmentManager());
        noScrollViewPager.setAdapter(adapter);
        noScrollViewPager.setPagingDisabled();
        noScrollViewPager.setOffscreenPageLimit(3);
        btnMuchStore.setOnClickListener(onClickSelectButton);
        btnSellToday.setOnClickListener(onClickSelectButton);
        //////////////////
        changeSelection(1);

        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-M-d");
        nowDate = Calendar.getInstance();
        tvFragmentItemMainDate.setText(timeStampFormat.format(nowDate.getTime()));
        buttonSelectDate = (Button) rootView.findViewById(R.id.button_select_date);
        buttonSelectDate.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View onClickView){
                LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.layout_date, null);
                final CalendarView calendarView = (CalendarView)view.findViewById(R.id.calendarView);
                selectDate = nowDate;
                long nowDateToLong = selectDate.getTimeInMillis();
                calendarView.setDate(nowDateToLong, true, true);
                calendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                        selectDate = new GregorianCalendar( year, month, dayOfMonth );
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view);
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nowDate = selectDate;
                        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-M-d");
                        tvFragmentItemMainDate.setText(timeStampFormat.format(nowDate.getTime()));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                //dialog.setTitle(ProductObjManager.get(position).getName());
                //dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                dialog.show();
            }
        });

        famMenu = (FloatingActionMenu) rootView.findViewById(R.id.menu);
        rootView.findViewById(R.id.fab_add_product).setOnClickListener(onFabClickListener);
        rootView.findViewById(R.id.fab_sort).setOnClickListener(onFabClickListener);

        return rootView;
    }

    public Button.OnClickListener onClickSelectButton = new Button.OnClickListener() {
        @Override
        public void onClick(View onClickView) {
            switch(onClickView.getId()){
                default:
                case R.id.btn_fragment_item_main_sell_today:
                    noScrollViewPager.setCurrentItem(0,false);
                    tvFragmentItemMain.setText("일일판매량");
                    changeSelection(0);
                    /*  여기서 날짜 갱신
                    tvFragmentItemMainDate.setText("");
                    */
                    break;
                case R.id.btn_fragment_item_main_store_match:
                    noScrollViewPager.setCurrentItem(1,false);
                    tvFragmentItemMain.setText("최적재고량");
                    changeSelection(1);

                    /*    여기서 날짜 갱신
                    tvFragmentItemMainDate.setText("");
                    */
                    break;
            }
        }
    };

    public void changeSelection(int selectSwitch)
    {
        AppCompatImageView acivSelectIcon, acivSelectDot, acivUnselectIcon, acivUnselectDot;

        switch(selectSwitch){
            default:
            case 0:
                acivSelectIcon = (AppCompatImageView)rootView.findViewById(R.id.aciv_sell_today_icon);
                acivSelectDot = (AppCompatImageView)rootView.findViewById(R.id.aciv_sell_today_dot);
                acivUnselectIcon = (AppCompatImageView)rootView.findViewById(R.id.aciv_much_store_icon);
                acivUnselectDot = (AppCompatImageView)rootView.findViewById(R.id.aciv_much_store_dot);
                break;
            case 1:
                acivSelectIcon = (AppCompatImageView)rootView.findViewById(R.id.aciv_much_store_icon);
                acivSelectDot = (AppCompatImageView)rootView.findViewById(R.id.aciv_much_store_dot);
                acivUnselectIcon = (AppCompatImageView)rootView.findViewById(R.id.aciv_sell_today_icon);
                acivUnselectDot = (AppCompatImageView)rootView.findViewById(R.id.aciv_sell_today_dot);
                break;
        }

        acivSelectIcon.getBackground().setColorFilter(ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_D3), PorterDuff.Mode.SRC_IN);
        acivSelectDot.getBackground().setColorFilter(ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_D3), PorterDuff.Mode.SRC_IN);
        acivUnselectIcon.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.alpha40), PorterDuff.Mode.SRC_IN);
        acivUnselectDot.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.transparent), PorterDuff.Mode.SRC_IN);
    }

    public static ProductMain createInstance(FirstPageFragmentListener listener){
        ProductMain fragmentItemMain=new ProductMain();
        fragmentItemMain.firstPageListener=listener;
        return fragmentItemMain;
    }

    private View.OnClickListener onFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                default:
                case R.id.fab_add_product:
                    famMenu.close(true);
                    productAddDialog = new ProductAddDialog(getContext());
                    productAddDialog.show();
                    productAddDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            // TODO : 새 제품 추가 다이얼로그에서 완료 버튼 클릭 시 동작 삽입
                        }
                    });
                    break;
                case R.id.fab_sort:
                    // TODO : 정렬 버튼 클릭 시 동작 삽입
                    break;
            }
        }
    };
}