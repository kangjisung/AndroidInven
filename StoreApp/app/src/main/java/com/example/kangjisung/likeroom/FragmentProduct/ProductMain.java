package com.example.kangjisung.likeroom.FragmentProduct;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.NoScrollViewPager;
import com.example.kangjisung.likeroom.Util.FirstPageFragmentListener;
import com.github.clans.fab.FloatingActionMenu;

public class ProductMain extends Fragment implements View.OnClickListener {
    private ImageButton btnSellToday;
    private ImageButton btnMuchStore;
    public static NoScrollViewPager noScrollViewPager;
    private TextView tvFragmentItemMain;
    private TextView tvFragmentItemMainDate;
    static public FirstPageFragmentListener firstPageListener;
    ProductAddDialog productAddDialog;
    private FloatingActionMenu famMenu;

    public ProductMain() {
    }
    public ProductMain(FirstPageFragmentListener listener) {
        firstPageListener=listener;
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.item_main, container, false);

        ///////init///////
        btnSellToday=(ImageButton)rootView.findViewById(R.id.btn_fragment_item_main_sell_today);
        btnMuchStore=(ImageButton)rootView.findViewById(R.id.btn_fragment_item_main_store_match);
        noScrollViewPager=(NoScrollViewPager) rootView.findViewById(R.id.vp_fragment_item_main);
        tvFragmentItemMain=(TextView)rootView.findViewById(R.id.tv_fragment_item_main);
        tvFragmentItemMainDate=(TextView)rootView.findViewById(R.id.tv_fragment_item_main_date);

        final ProductMainPagerAdapter adapter = new ProductMainPagerAdapter(getFragmentManager());
        noScrollViewPager.setAdapter(adapter);
        noScrollViewPager.setPagingDisabled();
        noScrollViewPager.setOffscreenPageLimit(3);
        btnMuchStore.setOnClickListener(this);
        btnSellToday.setOnClickListener(this);
        //////////////////

        famMenu = (FloatingActionMenu) rootView.findViewById(R.id.menu);
        rootView.findViewById(R.id.fab_add_product).setOnClickListener(onFabClickListener);
        rootView.findViewById(R.id.fab_sort).setOnClickListener(onFabClickListener);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        if(view==btnMuchStore){
            noScrollViewPager.setCurrentItem(1,false);
            tvFragmentItemMain.setText("월 최적재고량");
            btnSellToday.setBackgroundResource(R.mipmap.disable_sell_today);
            btnMuchStore.setBackgroundResource(R.mipmap.enable_store_much);
            ((ViewGroup.MarginLayoutParams)btnSellToday.getLayoutParams()).bottomMargin=(int)DpToPx((float)42.0);
            ((ViewGroup.MarginLayoutParams)btnMuchStore.getLayoutParams()).bottomMargin=(int)DpToPx((float)20.0);

/*    여기서 날짜 갱신
      tvFragmentItemMainDate.setText("");
  */
        }
        else if(view==btnSellToday){
            noScrollViewPager.setCurrentItem(0,false);
            tvFragmentItemMain.setText("월 일일판매량");

            btnSellToday.setBackgroundResource(R.mipmap.enable_sell_today);
            btnMuchStore.setBackgroundResource(R.mipmap.disable_store_much);
            ((ViewGroup.MarginLayoutParams)btnSellToday.getLayoutParams()).bottomMargin=(int)DpToPx((float)20.0);
            ((ViewGroup.MarginLayoutParams)btnMuchStore.getLayoutParams()).bottomMargin=(int)DpToPx((float)40.0);

            /*    여기서 날짜 갱신
      tvFragmentItemMainDate.setText("");
  */
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
    /* 이벤트 코드는 여기서 */
}