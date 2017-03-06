package com.example.kangjisung.likeroom.FragmentPoint;

import com.example.kangjisung.likeroom.MemberListItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.Utility;

import java.util.ArrayList;

public class PointMainListAdapter extends BaseAdapter implements Filterable
{
    private ArrayList<MemberListItem> pointMainList = new ArrayList<>();
    private ArrayList<MemberListItem> pointMainListToFilter = new ArrayList<>();
    private ArrayFilter mFilter;
    private Context context;
    private PointSaveDialog pointSaveDialog;

    public PointMainListAdapter() {
        super();
    }

    public PointMainListAdapter(Context context, ArrayList<MemberListItem> object)
    {
        super();
        this.context = context;
        pointMainList = object;
        pointMainListToFilter = object;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final MemberListItem pointMainItem = pointMainList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.point_main_listitem, parent, false);
        }
        if (mFilter != null){
            TextView mTextViewName = (TextView) convertView.findViewById(R.id.tv_name);
            TextView mTextViewPhone = (TextView) convertView.findViewById(R.id.tv_phone);
            Button mButtonSelect = (Button) convertView.findViewById(R.id.btn_select);
            Button mButtonDetail = (Button) convertView.findViewById(R.id.btn_detail);

            mTextViewName.setText(pointMainItem.getName());
            mTextViewPhone.setText(Utility.convertPhoneNumber(pointMainItem.getPhone()));
            mButtonSelect.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View onClickView) {
                    pointSaveDialog = new PointSaveDialog(context, pointMainItem);
                    pointSaveDialog.show();
                }
            });
            mButtonDetail.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View onClickView) {

                }
            });
            convertView.setVisibility(View.VISIBLE);
        }
        else{
            convertView.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return pointMainList.size() ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return pointMainList.get(position) ;
    }

    public void addItem(MemberListItem addListItem)
    {
        pointMainList.add(addListItem);
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {
        private Object lock;

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (prefix == null || prefix.length() == 0) {
                results.values = null;
                results.count = 0;
            }
            else {
                final String prefixString = prefix.toString().toLowerCase();

                ArrayList<MemberListItem> newValues = new ArrayList<>();

                for (int i = 0; i < pointMainListToFilter.size(); i++) {
                    String itemName = pointMainListToFilter.get(i).getName();
                    String itemPhone = pointMainListToFilter.get(i).getPhone();
                    if (itemName.toLowerCase().contains(prefixString) || itemPhone.toLowerCase().contains(prefixString)) {
                        newValues.add(pointMainListToFilter.get(i));
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            if (results.values != null) {
                pointMainList = (ArrayList<MemberListItem>) results.values;
            } else {
                pointMainList = new ArrayList<MemberListItem>();
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
/*
public class PointMainListAdapter extends RecyclerView.Adapter<PointMainListAdapter.UserRecyclerViewHolder> {
    private ArrayList<MemberListItem> userMainList;
    private Context context;
    private ViewGroup parent;
    private Boolean stampMode = false;

    private int longClickPosition;
    public MemberListItem getLongClickPosition() {return userMainList.get(longClickPosition);}
    public void setLongClickPosition(int longClickPosition) {this.longClickPosition = longClickPosition;}

    public PointMainListAdapter(View view) {
        userMainList = new ArrayList<MemberListItem>();
    }

    public static class UserRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textViewName;
        TextView textViewPhone;
        TextView textViewPoint;
        Button buttonDescription;
        CheckBox checkBoxStamp;
        View view;

        UserRecyclerViewHolder(View view) {
            super(view);

            this.view = view;
            textViewName = (TextView) view.findViewById(R.id.textView_name);
            textViewPhone = (TextView) view.findViewById(R.id.textView_phone);
            textViewPoint = (TextView) view.findViewById(R.id.textView_point);
            buttonDescription = (Button) view.findViewById(R.id.button_detail);
            checkBoxStamp = (CheckBox) view.findViewById(R.id.checkBox_stamp);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("작업 선택");
            menu.add(Menu.NONE, 0, Menu.NONE, "수정");
            menu.add(Menu.NONE, 1, Menu.NONE, "삭제");
        }
    }

    @Override
    public PointMainListAdapter.UserRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_main_listitem, parent, false);
        UserRecyclerViewHolder vh = new UserRecyclerViewHolder(v);
        context = parent.getContext();
        this.parent = parent;
        return vh;
    }

    @Override
    public void onBindViewHolder(final UserRecyclerViewHolder holder, final int position) {
        final MemberListItem userMainItem = userMainList.get(position);

        holder.textViewName.setText(userMainItem.getName());
        holder.textViewPhone.setText(userMainItem.getPhone());
        holder.textViewPoint.setText(userMainItem.getPoint() + " p");
        holder.buttonDescription.getBackground().setColorFilter(Color.parseColor("#55000000"), PorterDuff.Mode.SRC_IN);
        holder.buttonDescription.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                SingleToast.show(context, userMainItem.getName() + " 항목의 버튼 클릭", Toast.LENGTH_SHORT);
            }
        });

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setLongClickPosition(position);
                return stampMode;
            }
        });

        if (stampMode == false) {
            holder.checkBoxStamp.setVisibility(View.GONE);
            userMainList.get(position).setCheck(false);
            //userMainCheckboxStateList.set(position, false);
        } else {
            holder.checkBoxStamp.setVisibility(View.VISIBLE);
        }
        holder.checkBoxStamp.setChecked(userMainList.get(position).getCheck());

        holder.checkBoxStamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userMainList.get(position).setCheck(isChecked);
                int count = 0;
                for(int p = 0; p < userMainList.size(); p++){
                    if(userMainList.get(p).getCheck() == true){
                        count++;
                    }
                }
                RelativeLayout layoutUserMain = (RelativeLayout)parent.getParent().getParent();
                FloatingActionButton fabStampOk = (FloatingActionButton)layoutUserMain.findViewById(R.id.fab_stamp_ok);
                TextView textViewSearchResult = (TextView)layoutUserMain.findViewById(R.id.textView_search_result);
                if(count > 0){
                    if(count <= userMainList.size() - 1){
                        ((CheckBox)layoutUserMain.findViewById(R.id.checkBoxStampAll)).setChecked(false);
                    }
                    else{
                        ((CheckBox)layoutUserMain.findViewById(R.id.checkBoxStampAll)).setChecked(true);
                    }
                    fabStampOk.setEnabled(true);
                }
                else{
                    fabStampOk.setEnabled(false);
                }
                if(stampMode == true){
                    setTextViewSearchResult(textViewSearchResult);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userMainList.size();
    }

    public void setTextViewSearchResult(TextView textView)
    {
        int count = 0;
        for(int p = 0; p < userMainList.size(); p++){
            if(userMainList.get(p).getCheck() == true){
                count++;
            }
        }
        textView.setText(String.valueOf(userMainList.size()) + "명 중 " + String.valueOf(count) + "명 선택됨");
    }

    public void setCheckAll(boolean isChecked)
    {
        for(int p = 0; p < userMainList.size(); p++)
        {
            userMainList.get(p).setCheck(isChecked);
        }
        notifyDataSetChanged();
    }

    public ArrayList<MemberListItem> getListItemToStampDialog()
    {
        ArrayList<MemberListItem> uploadData = new ArrayList<MemberListItem>();

        for(int p = 0; p < userMainList.size(); p++){
            if(userMainList.get(p).getCheck() == true){
                uploadData.add(userMainList.get(p));
            }
        }
        return uploadData;
    }

    public void updateCheckboxState(boolean newStampMode)
    {
        stampMode = newStampMode;
        notifyDataSetChanged();
    }

    public void addItem(String addName, String addPhone, String addPoint)
    {
        MemberListItem addItemList = new MemberListItem();

        addItemList.setName(addName);
        addItemList.setPhone(addPhone);
        addItemList.setPoint(addPoint);
        addItemList.setCheck(false);

        userMainList.add(addItemList);
    }

    public void sort(final String sortMode, final String sortOrder)
    {
        Collections.sort(userMainList, new Comparator<MemberListItem>(){
            @Override
            public int compare(MemberListItem obj1, MemberListItem obj2) {
                if(sortMode.equals("NAME")){
                    if(sortOrder.equals("ASC")){
                        return obj1.getName().compareToIgnoreCase(obj2.getName());
                    }
                    else{
                        return obj2.getName().compareToIgnoreCase(obj1.getName());
                    }
                }
                else if(sortMode.equals("PHONE")){
                    if(sortOrder.equals("ASC")){
                        return obj1.getPhone().compareToIgnoreCase(obj2.getPhone());
                    }
                    else{
                        return obj2.getPhone().compareToIgnoreCase(obj1.getPhone());
                    }
                }
                else if(sortMode.equals("POINT")){
                    int p1 = Integer.valueOf(obj1.getPoint());
                    int p2 = Integer.valueOf(obj2.getPoint());
                    if(sortOrder.equals("ASC")){
                        return (p1 < p2) ? -1 : (p1 > p2) ? 1 : 0;
                    }
                    else{
                        return (p1 > p2) ? -1 : (p1 < p2) ? 1 : 0;
                    }
                }
                return -1;
            }
        });
        notifyDataSetChanged();
    }

    public void clearData() {
        int size = userMainList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                userMainList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
}
*/