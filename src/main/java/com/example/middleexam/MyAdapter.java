package com.example.middleexam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView data;
        TextView theme;

        viewHolder(View view){
            super(view);
            data = view.findViewById(R.id.date);
            theme = view.findViewById(R.id.PastTheme);
        }
    }

    private ArrayList<ThemeList> ThemeArrayList;
    MyAdapter(ArrayList<ThemeList> themeArrayList){
        ThemeArrayList = themeArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.block, parent, false);

        return new viewHolder(v);
    }

    //각 viewholder에 데이터를 연결
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        viewHolder myViewHolder = (viewHolder) holder;

        myViewHolder.data.setText(ThemeArrayList.get(position).data);
        myViewHolder.theme.setText(ThemeArrayList.get(position).theme);
    }

    //리사이클러뷰 안에 들어가는 갯수
    @Override
    public int getItemCount() {
        return ThemeArrayList.size();
    }
}

