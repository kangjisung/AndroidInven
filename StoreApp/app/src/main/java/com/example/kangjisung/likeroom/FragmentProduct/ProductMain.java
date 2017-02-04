package com.example.kangjisung.likeroom.FragmentProduct;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.NoScrollViewPager;
import com.example.kangjisung.likeroom.Util.FirstPageFragmentListener;
import com.github.clans.fab.FloatingActionMenu;

public class ProductMain extends Fragment {
    private Button btnSellToday;
    private Button btnMuchStore;
    public static NoScrollViewPager noScrollViewPager;
    private TextView tvFragmentItemMain;
    private TextView tvFragmentItemMainDate;
    static public FirstPageFragmentListener firstPageListener;
    private ProductAddDialog productAddDialog;
    private FloatingActionMenu famMenu;
    private View rootView;

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
                    noScrollViewPager.setCurrentItem(1,false);
                    tvFragmentItemMain.setText("월 최적재고량");
                    changeSelection(0);

                    /*  여기서 날짜 갱신
                    tvFragmentItemMainDate.setText("");
                    */
                    break;
                case R.id.btn_fragment_item_main_store_match:
                    noScrollViewPager.setCurrentItem(0,false);
                    tvFragmentItemMain.setText("월 일일판매량");
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
        AppCompatImageView acivSellTodayIcon = (AppCompatImageView)rootView.findViewById(R.id.aciv_sell_today_icon);
        AppCompatImageView acivSellTodayDot = (AppCompatImageView)rootView.findViewById(R.id.aciv_sell_today_dot);
        AppCompatImageView acivMuchStoreIcon = (AppCompatImageView)rootView.findViewById(R.id.aciv_much_store_icon);
        AppCompatImageView acivMuchStoreDot = (AppCompatImageView)rootView.findViewById(R.id.aciv_much_store_dot);

        switch(selectSwitch){
            default:
            case 0:
                acivSellTodayIcon.getBackground().setColorFilter(ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_type1), PorterDuff.Mode.SRC_IN);
                acivSellTodayDot.getBackground().setColorFilter(ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_type1), PorterDuff.Mode.SRC_IN);
                acivMuchStoreIcon.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.gray200), PorterDuff.Mode.SRC_IN);
                acivMuchStoreDot.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.transparent), PorterDuff.Mode.SRC_IN);
                break;
            case 1:
                acivMuchStoreIcon.getBackground().setColorFilter(ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_type1), PorterDuff.Mode.SRC_IN);
                acivMuchStoreDot.getBackground().setColorFilter(ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_type1), PorterDuff.Mode.SRC_IN);
                acivSellTodayIcon.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.gray200), PorterDuff.Mode.SRC_IN);
                acivSellTodayDot.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.transparent), PorterDuff.Mode.SRC_IN);
                break;
        }
    }

    public static float DpToPx(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
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