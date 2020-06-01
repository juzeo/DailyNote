package com.example.middleexam;

import android.view.View;
import android.widget.TextView;

public class DriveHolder {
    public TextView data;
    public  TextView theme;

    public DriveHolder(View root){
        data=root.findViewById(R.id.date);
        theme=root.findViewById(R.id.PastTheme);
    }
}
