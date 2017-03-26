package com.teamdk.android.bakery.fragments.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.teamdk.android.bakery.fragments.user.adapter.UserMainListAdapter;
import com.teamdk.android.bakery.objectmanager.MemberListItem;
import com.teamdk.android.bakery.objectmanager.MemberObjectManager;
import com.teamdk.android.bakery.objectmanager.ProductObjectManager;
import com.teamdk.android.bakery.utility.ColorTheme;
import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.utility.LayoutManager;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.teamdk.android.bakery.utility.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class UserMain extends Fragment
{
    private View fragmentView;
    private RecyclerView userRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private UserMainListAdapter mAdapter;

    private CheckBox checkBoxStampAll;
    private FloatingActionMenu famMenu;
    private FloatingActionButton fabStampOk;
    private FloatingActionButton fabStampCancel;

    private UserEditDialog userEditDialog;
    private UserStampDialog userStampDialog;
    private DrawerLayout mDrawerLayout;

    private String searchString;
    private boolean stampSendMode = false;

    private int tabPosition;
    private int sortStateImageView;
    private int sortStateTextView;
    private SharedPreferenceManager mSharedPreferenceManager;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.user_main, container, false);

        mAdapter = new UserMainListAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());

        userRecyclerView = (RecyclerView) fragmentView.findViewById((R.id.recyclerView));
        userRecyclerView.setAdapter(mAdapter);
        userRecyclerView.setLayoutManager(mLayoutManager);

        setTextViewSearchResult(false);
        registerForContextMenu(userRecyclerView);

        mSharedPreferenceManager = new SharedPreferenceManager();

        setFloatingMenu();

        mDrawerLayout = (DrawerLayout) fragmentView.findViewById(R.id.layout_drawer);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener(){
            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        famMenu = (FloatingActionMenu) fragmentView.findViewById(R.id.menu);
        fabStampOk = (FloatingActionButton) fragmentView.findViewById(R.id.fab_stamp_ok);
        fabStampOk.setVisibility(View.INVISIBLE);
        fabStampOk.setEnabled(false);
        fabStampCancel = (FloatingActionButton) fragmentView.findViewById(R.id.fab_stamp_cancel);
        fabStampCancel.setVisibility(View.INVISIBLE);

        checkBoxStampAll = (CheckBox) fragmentView.findViewById(R.id.checkBoxStampAll);
        checkBoxStampAll.setVisibility(View.GONE);
        checkBoxStampAll.setOnClickListener(new CompoundButton.OnClickListener(){
            @Override
            public void onClick(View onClickView){
                mAdapter.setCheckAll(checkBoxStampAll.isChecked());
                mAdapter.setTextViewSearchResult((TextView) fragmentView.findViewById(R.id.textView_search_result));
            }
        });

        fabStampCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                famMenu.close(true);
                checkBoxStampAll.setVisibility(View.GONE);
                fabStampCancel.setVisibility(View.INVISIBLE);
                fabStampOk.setVisibility(View.INVISIBLE);
                famMenu.setVisibility(View.VISIBLE);
                mAdapter.updateCheckboxState(false);
                setTextViewSearchResult(false);
            }
        });

        fabStampOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                userStampDialog = new UserStampDialog(mAdapter.getListItemToStampDialog(), getActivity());
                userStampDialog.show();
                famMenu.close(true);
                checkBoxStampAll.setVisibility(View.GONE);
                fabStampCancel.setVisibility(View.INVISIBLE);
                fabStampOk.setVisibility(View.INVISIBLE);
                famMenu.setVisibility(View.VISIBLE);
                mAdapter.updateCheckboxState(false);
                setTextViewSearchResult(false);
            }
        });

        final EditText editTextSearch = (EditText) fragmentView.findViewById(R.id.editText_search);
        final View acivClear = fragmentView.findViewById(R.id.iv_clear);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0){
                    acivClear.setVisibility(View.GONE);
                }
                else{
                    acivClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchString = s.toString();
                mAdapter.getFilter().filter(searchString);
            }
        });

        acivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                editTextSearch.setText("");
            }
        });

        final SlidingUpPanelLayout layoutSliding = (SlidingUpPanelLayout) fragmentView.findViewById(R.id.layout_sliding);
        layoutSliding.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutSliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
        Button mButtonSortName = (Button) fragmentView.findViewById(R.id.btn_sort_1);
        Button mButtonSortAdd = (Button) fragmentView.findViewById(R.id.btn_sort_2);
        Button mButtonSortPoint = (Button) fragmentView.findViewById(R.id.btn_sort_3);
        mButtonSortName.setOnClickListener(onButtonSortClickListener);
        mButtonSortAdd.setOnClickListener(onButtonSortClickListener);
        mButtonSortPoint.setOnClickListener(onButtonSortClickListener);
        tabPosition = mSharedPreferenceManager.getInt("member_sort_order", getContext());
        sortStateImageView = getSortId(mSharedPreferenceManager.getInt("member_sort_mode_iv", getContext()));
        sortStateTextView = getSortId(mSharedPreferenceManager.getInt("member_sort_mode_tv", getContext()));

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
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return fragmentView;
    }

    public void setFloatingMenu()
    {
        View layoutFloating1 = fragmentView.findViewById(R.id.layout_floating1);
        View layoutFloating2 = fragmentView.findViewById(R.id.layout_floating2);
        if(mSharedPreferenceManager.getInt("set_menu", getContext()) == 0) {
            layoutFloating1.setVisibility(View.VISIBLE);
            layoutFloating2.setVisibility(View.GONE);
        }
        else{
            layoutFloating1.setVisibility(View.GONE);
            layoutFloating2.setVisibility(View.VISIBLE);
        }
        famMenu = (FloatingActionMenu) layoutFloating1.findViewById(R.id.fam_menu);
        layoutFloating1.findViewById(R.id.fab_notice).setOnClickListener(onFabClickListener);
        layoutFloating1.findViewById(R.id.fab_sort).setOnClickListener(onFabClickListener);
        layoutFloating2.findViewById(R.id.fab_notice).setOnClickListener(onFabClickListener);
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
                sortMode = "POINT";
                break;
        }
        mSharedPreferenceManager.putInt("member_sort_order", tabPosition, getContext());
        mSharedPreferenceManager.putInt("member_sort_mode_iv", getSortId(sortStateImageView), getContext());
        mSharedPreferenceManager.putInt("member_sort_mode_tv", getSortId(sortStateTextView), getContext());
        mAdapter.setSortOption(sortMode, sortOrder);
        mAdapter.sort();
        fragmentView.findViewById(sortStateImageView).setVisibility(View.VISIBLE);
        ((TextView) fragmentView.findViewById(sortStateTextView)).setTextColor(ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_type1));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
            case LayoutManager.MENU_USER_MODIFY:
                // TODO : 수정을 눌렀을 경우
                MemberListItem userItem = mAdapter.getLongClickPosition();
                userEditDialog = new UserEditDialog(getContext(), userItem);
                userEditDialog.show();
                userEditDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(((UserEditDialog)dialog).getDismissMessage() == 1) {
                            MemberObjectManager.load(getContext());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                break;
            case LayoutManager.MENU_USER_DELETE:
                // TODO : 삭제를 눌렀을 경우
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void setTextViewSearchResult(boolean StampMode)
    {
        TextView textViewSearchResult = (TextView) fragmentView.findViewById(R.id.textView_search_result);
        if(StampMode == false){
            textViewSearchResult.setText("등록된 회원 수 : " + (mAdapter.getItemCount() - 1));
        }
        else{
            textViewSearchResult.setText(mAdapter.getItemCount() + "명 중 0명 선택됨");
        }
    }

    private View.OnClickListener onFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(famMenu != null){
                famMenu.close(true);
            }
            switch (view.getId()) {
                default:
                case R.id.fab_add_user:
                    userEditDialog = new UserEditDialog(getContext());
                    userEditDialog.show();
                    userEditDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            MemberObjectManager.load(getContext());
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                    break;
                case R.id.fab_stamp:
                    checkBoxStampAll.setVisibility(View.VISIBLE);
                    fabStampCancel.setVisibility(View.VISIBLE);
                    fabStampOk.setVisibility(View.VISIBLE);
                    famMenu.setVisibility(View.INVISIBLE);
                    mAdapter.updateCheckboxState(true);
                    setTextViewSearchResult(true);
                    break;
                case R.id.fab_notice:
                    UserNoticeMain fragmentNotice = new UserNoticeMain();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.user_main_layout, fragmentNotice);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case R.id.fab_sort:
                    SlidingUpPanelLayout layoutSliding = (SlidingUpPanelLayout) getActivity().findViewById(R.id.layout_sliding);
                    layoutSliding.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                    break;
            }

        }
    };

    public void refreshRecyclerView(){
        mAdapter.getFilter().filter(searchString);
        mAdapter.notifyDataSetChanged();
    }
}