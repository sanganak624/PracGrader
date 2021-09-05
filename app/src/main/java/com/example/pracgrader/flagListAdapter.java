package com.example.pracgrader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.ListAdapter;

import java.util.List;

public class flagListAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> countryName;
    List<Integer> flags;

    public flagListAdapter(Context context, List<String> countryName, List<Integer>flags)
    {
        super(context,R.layout.flags_list,countryName);
        this.context = context;
        this.countryName = countryName;
        this.flags = flags;
    }

    @Override
    public View getDropDownView(int position, View convertVew, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.flags_list,null);
        TextView name = (TextView) row.findViewById(R.id.countryName);
        ImageView flag = (ImageView) row.findViewById(R.id.flag);

        name.setText(countryName.get(position));
        flag.setImageResource(flags.get(position));

        return row;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.flags_list,null);
        TextView name = (TextView) row.findViewById(R.id.countryName);
        ImageView flag = (ImageView) row.findViewById(R.id.flag);

        name.setText(countryName.get(position));
        flag.setImageResource(flags.get(position));

        return row;
    }


}
