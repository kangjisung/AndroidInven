package com.example.kangjisung.likeroom.FragmentStamp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;

public class StampPage extends android.support.v4.app.Fragment
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int mParam1;
    private int mParam2;

    private TextView textViewTemp1;
    private TextView textViewTemp2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.stamp_page, container, false);

        Log.v("Test","Param1 : "+mParam1 + " Param2 :"+mParam2);

        textViewTemp1 = (TextView)view.findViewById(R.id.textViewTemp1);
        textViewTemp2 = (TextView)view.findViewById(R.id.textViewTemp2);

        textViewTemp1.setText(String.valueOf(mParam1));
        textViewTemp2.setText(String.valueOf(mParam2));

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    public static Fragment newInstance(int param1, int param2) {
        Fragment fragment = new Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
}