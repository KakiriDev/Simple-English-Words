package com.kakiridev.simpleenglishwords.Ranking;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kakiridev.simpleenglishwords.R;
import com.kakiridev.simpleenglishwords.ShowListWords.WordListViewAdapter;
import com.kakiridev.simpleenglishwords.User;
import com.kakiridev.simpleenglishwords.Word;

import java.util.List;

public class UserListViewAdapter extends ArrayAdapter<User> {
    private final LayoutInflater mInflater;

    public UserListViewAdapter(Context context, int textViewResourceId, List<User> objects) {
        super(context, textViewResourceId, objects);

        mInflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UserListViewAdapter.ViewHolder holder;

        if(convertView == null) { //tylko w przypadku gdy layout nie istnieje to go tworzymy
            convertView = mInflater.inflate(R.layout.user_listview_row, null);
            holder = new UserListViewAdapter.ViewHolder();
            holder.position = (TextView) convertView.findViewById(R.id.position);
            holder.userName = (TextView) convertView.findViewById(R.id.userName);
            holder.score = (TextView) convertView.findViewById(R.id.score);

            convertView.setTag(holder);
        } else {
            holder = (UserListViewAdapter.ViewHolder) convertView.getTag();
        }

        User rekord = getItem(position);
        holder.position.setText(String.valueOf(position+1));
        holder.userName.setText(rekord.getUserName());
        holder.score.setText(rekord.getUserScore() + "PKT");

        return convertView;
    }


    private static class ViewHolder{
        public TextView position;
        public TextView userName;
        public TextView score;
    }


}