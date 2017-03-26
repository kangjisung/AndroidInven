package com.teamdk.android.bakery.fragments.product;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.teamdk.android.bakery.MainActivity;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.teamdk.android.bakery.objectmanager.ProductListItem;
import com.teamdk.android.bakery.objectmanager.ProductObjectManager;
import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.utility.ColorTheme;
import com.teamdk.android.bakery.utility.LayoutManager;
import com.teamdk.android.bakery.utility.NetworkManager.NetworkModule;
import com.teamdk.android.bakery.utility.NoScrollViewPager;
import com.github.clans.fab.FloatingActionMenu;
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase;
import com.teamdk.android.bakery.utility.SharedPreferenceManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.teamdk.android.bakery.MainActivity.PriNum;
import static com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class ProductMain extends Fragment {
    private Button btnSellToday;
    private Button btnMuchStore;
    private NoScrollViewPager noScrollViewPager;
    private TextView tvFragmentItemMain;
    private TextView tvFragmentItemMainDate;
    private ProductEditDialog productAddDialog;
    private Button buttonSelectDate;
    private FloatingActionMenu famMenu;
    private View fragmentView;
    private Calendar nowDate;
    private Calendar selectedDate;
    private TabLayout tabLayout;
    private ProductMainPagerAdapter adapter;
    private SharedPreferenceManager mSharedPreferenceManager;
    private SlidingUpPanelLayout layoutSliding;

    private int tabPosition;
    private int sortStateImageView;
    private int sortStateTextView;

    public ProductMain() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.product_main, container, false);

        btnSellToday = (Button) fragmentView.findViewById(R.id.btn_fragment_item_main_sell_today);
        btnMuchStore = (Button) fragmentView.findViewById(R.id.btn_fragment_item_main_store_match);
        btnSellToday.setOnClickListener(onClickSelectButton);
        btnMuchStore.setOnClickListener(onClickSelectButton);

        noScrollViewPager = (NoScrollViewPager) fragmentView.findViewById(R.id.vp_fragment_item_main);
        tvFragmentItemMain = (TextView) fragmentView.findViewById(R.id.tv_fragment_item_main);
        tvFragmentItemMainDate = (TextView) fragmentView.findViewById(R.id.tv_fragment_item_main_date);

        adapter = new ProductMainPagerAdapter(getFragmentManager());
        noScrollViewPager.setAdapter(adapter);
        noScrollViewPager.setPagingDisabled();
        noScrollViewPager.setOffscreenPageLimit(3);
        btnMuchStore.setOnClickListener(onClickSelectButton);
        btnSellToday.setOnClickListener(onClickSelectButton);

        mSharedPreferenceManager = new SharedPreferenceManager();

        changeSelection(0);
        setFloatingMenu();

        nowDate = Calendar.getInstance();
        tvFragmentItemMain.setText("일일판매량");
        tvFragmentItemMainDate.setText((new SimpleDateFormat("yyyy년 M월 d일", Locale.KOREA)).format(nowDate.getTime()));
        buttonSelectDate = (Button) fragmentView.findViewById(R.id.button_select_date);
        buttonSelectDate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                Context contextThemeWrapper = new ContextThemeWrapper(getContext(), ColorTheme.getTheme());
                LayoutInflater localInflater = getActivity().getLayoutInflater().cloneInContext(contextThemeWrapper);
                View view = localInflater.inflate(R.layout.alert_calendar, null);
                DatePicker mDatePicker = (DatePicker) view.findViewById(R.id.calendarView);
                selectedDate = nowDate;

                int year = selectedDate.get(Calendar.YEAR);
                int month = selectedDate.get(Calendar.MONTH);
                int day = selectedDate.get(Calendar.DAY_OF_MONTH);

                mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        selectedDate = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(view);
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nowDate = selectedDate;
                        tvFragmentItemMainDate.setText((new SimpleDateFormat("yyyy년 M월 d일", Locale.KOREA)).format(nowDate.getTime()));
                        ProductObjectManager.load(nowDate.getTime(), getContext());
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
                dialog.show();
            }
        });


        layoutSliding = (SlidingUpPanelLayout) fragmentView.findViewById(R.id.layout_sliding);
        layoutSliding.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutSliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
        Button mButtonSortName = (Button) fragmentView.findViewById(R.id.btn_sort_1);
        Button mButtonSortAdd = (Button) fragmentView.findViewById(R.id.btn_sort_2);
        Button mButtonSortSales = (Button) fragmentView.findViewById(R.id.btn_sort_3);
        mButtonSortName.setOnClickListener(onButtonSortClickListener);
        mButtonSortAdd.setOnClickListener(onButtonSortClickListener);
        mButtonSortSales.setOnClickListener(onButtonSortClickListener);
        tabPosition = mSharedPreferenceManager.getInt("product_sort_order", getContext());
        sortStateImageView = getSortId(mSharedPreferenceManager.getInt("product_sort_mode_iv", getContext()));
        sortStateTextView = getSortId(mSharedPreferenceManager.getInt("product_sort_mode_tv", getContext()));

        tabLayout = (TabLayout) fragmentView.findViewById(R.id.tabLayout);
        TabLayout.Tab tab = tabLayout.getTabAt(tabPosition);
        tab.select();
        tabLayout.setTabTextColors(ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_D2), ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_D4));
        onSortItem();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                onSortItem();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return fragmentView;
    }

    public void setFloatingMenu()
    {
        View layoutFloating1 = fragmentView.findViewById(R.id.layout_floating1);
        View layoutFloating2 = fragmentView.findViewById(R.id.layout_floating2);
        if (mSharedPreferenceManager.getInt("set_menu", getContext()) == 0) {
            layoutFloating1.setVisibility(View.VISIBLE);
            layoutFloating2.setVisibility(View.GONE);

        } else {
            layoutFloating1.setVisibility(View.GONE);
            layoutFloating2.setVisibility(View.VISIBLE);
        }

        famMenu = (FloatingActionMenu) layoutFloating1.findViewById(R.id.fam_menu);
        layoutFloating1.findViewById(R.id.fab_add).setOnClickListener(onFabClickListener);
        layoutFloating1.findViewById(R.id.fab_sort).setOnClickListener(onFabClickListener);
        layoutFloating2.findViewById(R.id.fab_add).setOnClickListener(onFabClickListener);
        layoutFloating2.findViewById(R.id.fab_sort).setOnClickListener(onFabClickListener);
    }

    public int getSortId(int p)
    {
        switch(p){
            case 101: return R.id.tv_sort_1;
            case 102: return R.id.tv_sort_2;
            case 103: return R.id.tv_sort_3;
            case 201: return R.id.iv_sort_1;
            case 202: return R.id.iv_sort_2;
            case 203: return R.id.iv_sort_3;
            case R.id.tv_sort_1: return 101;
            case R.id.tv_sort_2: return 102;
            case R.id.tv_sort_3: return 103;
            case R.id.iv_sort_1: return 201;
            case R.id.iv_sort_2: return 202;
            case R.id.iv_sort_3: return 203;
        }
        return 0;
    }

    public static int longClickPosition;
    private ProductEditDialog mProductEditDialog;
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        final ProductListItem mProductListItem = ProductObjectManager.get(longClickPosition);
        switch(item.getItemId()){
            case LayoutManager.MENU_PRODUCT_MODIFY:
                mProductEditDialog = new ProductEditDialog(getContext(), mProductListItem);
                mProductEditDialog.show();
                mProductEditDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(((ProductEditDialog)dialog).getDismissMessage() == 1) {
                            ProductObjectManager.load(nowDate.getTime(), getContext());
                        }
                    }
                });
                break;
            case LayoutManager.MENU_PRODUCT_DELETE:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setMessage("선택한 항목을 삭제하시겠습니까?");
                dialogBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {////제품에 관련된거 삭제
                        new ClientDataBase("select `제품코드` from `제품정보` where `이름`=\""+mProductListItem.getName()+"\"",1,1,MainActivity.con);
                        int ProductNum=Integer.parseInt(DBstring[0]);
                        new ClientDataBase("delete from `제품정보` where `제품코드`="+ProductNum+"",2,0, MainActivity.con);
                        new ClientDataBase("delete from `제품판매량` where `제품코드`="+ProductNum+"",2,0, MainActivity.con);
                        new ClientDataBase("delete from `최적재고량` where `제품코드`="+ProductNum+"",2,0, MainActivity.con);
                        // TODO : 삭제 쿼리
                        ProductObjectManager.load(nowDate.getTime(), getContext());
                        NetworkModule networkModule=new NetworkModule();
                        networkModule.UpdateRegisteredProductName(Integer.parseInt(PriNum),ProductNum); //샵 아이디,제품코드
                        // TODO : 여기서 리스트 갱신
                    }
                });
                dialogBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog mAlertDialog = dialogBuilder.create();
                mAlertDialog.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
        return super.onContextItemSelected(item);
    }

    public Button.OnClickListener onClickSelectButton = new Button.OnClickListener() {
        @Override
        public void onClick(View onClickView) {
            switch(onClickView.getId()){
                default:
                case R.id.btn_fragment_item_main_sell_today:
                    noScrollViewPager.setCurrentItem(0, false);
                    tvFragmentItemMain.setText("일일판매량");
                    changeSelection(0);
                    break;
                case R.id.btn_fragment_item_main_store_match:
                    noScrollViewPager.setCurrentItem(1, false);
                    tvFragmentItemMain.setText("최적재고량");
                    changeSelection(1);
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
                acivSelectIcon = (AppCompatImageView) fragmentView.findViewById(R.id.aciv_sell_today_icon);
                acivSelectDot = (AppCompatImageView) fragmentView.findViewById(R.id.aciv_sell_today_dot);
                acivUnselectIcon = (AppCompatImageView) fragmentView.findViewById(R.id.aciv_much_store_icon);
                acivUnselectDot = (AppCompatImageView) fragmentView.findViewById(R.id.aciv_much_store_dot);
                break;
            case 1:
                acivSelectIcon = (AppCompatImageView) fragmentView.findViewById(R.id.aciv_much_store_icon);
                acivSelectDot = (AppCompatImageView) fragmentView.findViewById(R.id.aciv_much_store_dot);
                acivUnselectIcon = (AppCompatImageView) fragmentView.findViewById(R.id.aciv_sell_today_icon);
                acivUnselectDot = (AppCompatImageView) fragmentView.findViewById(R.id.aciv_sell_today_dot);
                break;
        }
        acivSelectIcon.setSupportBackgroundTintList(ColorStateList.valueOf(ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_D3)));
        acivSelectDot.setSupportBackgroundTintList(ColorStateList.valueOf(ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_D3)));
        acivUnselectIcon.setSupportBackgroundTintList(ColorStateList.valueOf(ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_D3)));
        acivUnselectDot.setSupportBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.transparent)));
        acivSelectIcon.setAlpha(1.0f);
        acivUnselectIcon.setAlpha(0.3f);
    }

    private Button.OnClickListener onButtonSortClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View onClickView){
            fragmentView.findViewById(sortStateImageView).setVisibility(View.INVISIBLE);
            ((TextView) fragmentView.findViewById(sortStateTextView)).setTextColor(ContextCompat.getColor(getContext(), R.color.gray80));
            switch(onClickView.getId()){
                case R.id.btn_sort_1:
                    sortStateImageView = R.id.iv_sort_1;
                    sortStateTextView = R.id.tv_sort_1;
                    onSortItem();
                    break;
                case R.id.btn_sort_2:
                    sortStateImageView = R.id.iv_sort_2;
                    sortStateTextView = R.id.tv_sort_2;
                    onSortItem();
                    break;
                case R.id.btn_sort_3:
                    sortStateImageView = R.id.iv_sort_3;
                    sortStateTextView = R.id.tv_sort_3;
                    onSortItem();
                    break;
                default:
                    break;
            }
        }
    };

    private void onSortItem()
    {
        String sortOrder = "ASC";
        String sortMode = "NAME";
        switch(tabPosition){
            case 0:
                sortOrder = "ASC";
                break;
            case 1:
                sortOrder = "DESC";
                break;
        }
        switch(sortStateImageView){
            case R.id.iv_sort_1:
                sortMode = "NAME";
                break;
            case R.id.iv_sort_2:
                sortMode = "ADDEDDATE";
                break;
            case R.id.iv_sort_3:
                sortMode = "SALES";
                break;
        }
        mSharedPreferenceManager.putInt("product_sort_order", tabPosition, getContext());
        mSharedPreferenceManager.putInt("product_sort_mode_iv", getSortId(sortStateImageView), getContext());
        mSharedPreferenceManager.putInt("product_sort_mode_tv", getSortId(sortStateTextView), getContext());
        ProductObjectManager.setSortOption(sortMode, sortOrder);
        ProductObjectManager.sort();
        fragmentView.findViewById(sortStateImageView).setVisibility(View.VISIBLE);
        ((TextView) fragmentView.findViewById(sortStateTextView)).setTextColor(ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_type1));
    }

    private View.OnClickListener onFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                default:
                case R.id.fab_add:
                    productAddDialog = new ProductEditDialog(getContext());
                    productAddDialog.show();
                    productAddDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if(((ProductEditDialog)dialog).getDismissMessage() == 1){
                                ProductObjectManager.load(nowDate.getTime(), getContext());
                            }
                        }
                    });
                    break;
                case R.id.fab_sort:
                    layoutSliding.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                    /*
                    DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.layout_product_drawer);
                    drawerLayout.openDrawer(Gravity.RIGHT);
                    */
                    if(famMenu != null){
                        famMenu.close(true);
                    }
                    break;
            }
        }
    };
}