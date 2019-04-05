package com.asymptote.coursemanager;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class Custom_Adapter extends BaseAdapter {
    Context context;
    ArrayList<String> fname_list;
    LayoutInflater inflater;

    public Custom_Adapter(Context context, ArrayList<String> fname_list) {
        this.context = context;
        this.fname_list = fname_list;
    }

    @Override
    public int getCount() {
        return fname_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pdf_card, parent, false);
        }

        ImageView pdf_iv = convertView.findViewById(R.id.pdf_iv);
        TextView pdf_tv = convertView.findViewById(R.id.pdf_tv);

        pdf_tv.setText(fname_list.get(position));
        return convertView;
    }
}
