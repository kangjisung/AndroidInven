package ex14.stories2.com.ex14.FragmentPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import ex14.stories2.com.ex14.CustomStampView.CustomStampViewAdapter;
import ex14.stories2.com.ex14.CustomStampView.EachStampViewItem;
import ex14.stories2.com.ex14.R;

/**
 * Created by stories2 on 2016. 11. 28..
 */

public class PlaceholderFragment extends Fragment {

    List<EachStampViewItem> eachStampViewItemList;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    String logCatTag = "ex14";
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {

        eachStampViewItemList = new ArrayList<EachStampViewItem>();
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_coupon_tan_store_info, container, false);
        //Log.d(logCatTag, "section NO." + ARG_SECTION_NUMBER);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        GridView myStampStatusView = (GridView)rootView.findViewById(R.id.stampGridLayout);
        CustomStampViewAdapter customStampViewAdapter = new CustomStampViewAdapter(getContext(), eachStampViewItemList);
        myStampStatusView.setAdapter(customStampViewAdapter);
        customStampViewAdapter.AddNewMyStamp(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_launcher), "helloWorld");
        return rootView;
    }

        /*public static Drawable LoadDrawable(String targetFileName, String targetFolderName) {
            Context context = this.getContext();
            int targetResourceID =
        }*/
}