package com.kakiridev.simpleenglishwords.ShowListWords;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kakiridev.simpleenglishwords.R;
import com.kakiridev.simpleenglishwords.Word;

import java.util.List;

public class WordListViewAdapter extends ArrayAdapter<Word> {

    private final LayoutInflater mInflater;

    public WordListViewAdapter(Context context, int textViewResourceId, List<Word> objects) {
        super(context, textViewResourceId, objects);
        Log.v("DTAG", "Class name: WordListViewAdapter Method name: WordListViewAdapter");

        mInflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("DTAG", "Class name: " + Thread.currentThread().getStackTrace()[2].getClassName() + " Method name: " + Thread.currentThread().getStackTrace()[2].getMethodName());


        ViewHolder holder;

        if(convertView == null) { //tylko w przypadku gdy layout nie istnieje to go tworzymy
            convertView = mInflater.inflate(R.layout.word_listview_row, null);
            holder = new ViewHolder();
            holder.tv_pl = (TextView) convertView.findViewById(R.id.tv_pl);
            holder.tv_en = (TextView) convertView.findViewById(R.id.tv_en);
            holder.tv_percent = (TextView) convertView.findViewById(R.id.tv_percent);
            holder.rl = (RelativeLayout) convertView.findViewById(R.id.row_id);



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Word rekord = getItem(position);
        holder.tv_pl.setText(rekord.getnazwaPl());
        holder.tv_en.setText(rekord.getnazwaEn());
        holder.tv_percent.setText(rekord.getscore() + "%");

        switch (rekord.getscore()) {
            case 0:
                holder.rl.setBackgroundResource(R.drawable.row_0);
                break;
            case 10:
                holder.rl.setBackgroundResource(R.drawable.row_10);
                break;
            case 20:
                holder.rl.setBackgroundResource(R.drawable.row_20);
                break;
            case 30:
                holder.rl.setBackgroundResource(R.drawable.row_30);
                break;
            case 40:
                holder.rl.setBackgroundResource(R.drawable.row_40);
                break;
            case 50:
                holder.rl.setBackgroundResource(R.drawable.row_50);
                break;
            case 60:
                holder.rl.setBackgroundResource(R.drawable.row_60);
                break;
            case 70:
                holder.rl.setBackgroundResource(R.drawable.row_70);
                break;
            case 80:
                holder.rl.setBackgroundResource(R.drawable.row_80);
                break;
            case 90:
                holder.rl.setBackgroundResource(R.drawable.row_90);
                break;
            case 100:
                holder.rl.setBackgroundResource(R.drawable.row_100);
                break;

        }


        return convertView;
    }


    private static class ViewHolder{
        public TextView tv_pl;
        public TextView tv_en;
        public TextView tv_percent;
        public RelativeLayout rl;
    }


}
