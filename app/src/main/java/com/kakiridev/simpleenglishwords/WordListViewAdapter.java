package com.kakiridev.simpleenglishwords;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WordListViewAdapter extends ArrayAdapter<Word> {

    private final LayoutInflater mInflater;

    public WordListViewAdapter(Context context, int textViewResourceId, List<Word> objects) {
        super(context, textViewResourceId, objects);
        mInflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null) { //tylko w przypadku gdy layout nie istnieje to go tworzymy
            convertView = mInflater.inflate(R.layout.word_listview_row, null);
            holder = new ViewHolder();
            holder.tv_pl = (TextView) convertView.findViewById(R.id.tv_pl);
            holder.tv_en = (TextView) convertView.findViewById(R.id.tv_en);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Word rekord = getItem(position);
        holder.tv_pl.setText(rekord.getmNazwaPl());
        holder.tv_en.setText(rekord.getmNazwaEn());

        return convertView;
    }


    private static class ViewHolder{
        public TextView tv_pl;
        public TextView tv_en;
    }


}
