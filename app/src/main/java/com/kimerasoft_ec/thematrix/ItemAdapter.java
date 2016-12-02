package com.kimerasoft_ec.thematrix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
public class ItemAdapter extends BaseAdapter {
    private List<MatrixItem> numbers;
    private Context context;
    public ItemAdapter(Context context, List<MatrixItem> numbers)
    {
        this.numbers = numbers;
        this.context = context;
    }
    @Override
    public int getCount() {
        return numbers.size();
    }

    @Override
    public Object getItem(int position) {
        return numbers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.matrix_item, null);
        TextView tv = (TextView) convertView.findViewById(R.id.tvNumber);
        LinearLayout ly = (LinearLayout) convertView.findViewById(R.id.lyItemLayout);
        tv.setText(String.valueOf(numbers.get(position).getValue()));
        ly.setBackgroundColor(numbers.get(position).getBackgroundColor());
        tv.setTextColor(numbers.get(position).getTextColor());
        return convertView;
    }
}
