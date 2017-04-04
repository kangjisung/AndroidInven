package com.example.kangjisung.likeroom.FragmentStamp;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;

import java.util.ArrayList;

import static com.example.kangjisung.likeroom.DefineManager.shopIdSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.standardMileage;
import static com.example.kangjisung.likeroom.DefineManager.synchronizedLocalAndServerDatabase;

public class StampListAdapter extends BaseAdapter {
    private ArrayList<Integer> data = new ArrayList<>();
    private int numStampInPage;

    public StampListAdapter(int stampPage, int numStamp) {
        super();

        numStampInPage = (numStamp > (stampPage + 1) * 10)?(10):(numStamp - stampPage * 10);

        int level = (stampPage + 1) * (standardMileage * 2);
        for(int p=0; p<10; p++){
            if(p == 4){
                data.add(level - standardMileage);
            }
            else if(p == 9){
                data.add(level);
            }
            else{
                if(p >= numStampInPage){
                    data.add(0);
                }
                else{
                    data.add(1);
                }
            }
        }
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.include_stamp, parent, false);
            View layoutNormal = convertView.findViewById(R.id.layout_normal);
            View layoutUse = convertView.findViewById(R.id.layout_use);
            if(data.get(position) == 0){
                layoutNormal.setVisibility(View.GONE);
                layoutUse.setVisibility(View.GONE);
            }
            else{
                if(data.get(position) == 1){
                    layoutUse.setVisibility(View.GONE);
                }
                else{
                    layoutNormal.setVisibility(View.GONE);
                    ((TextView) convertView.findViewById(R.id.view_text)).setText(String.valueOf(data.get(position)));
                    if(position >= numStampInPage){
                        convertView.findViewById(R.id.view_on).setVisibility(View.INVISIBLE);
                    }
                    else{
                        convertView.findViewById(R.id.view_off).setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return null ;
    }

    public int getStampPoint(int position)
    {
        if(position >= numStampInPage || data.get(position) == 0 || data.get(position) == 1){
            return -1;
        }
        return data.get(position);
    }
}