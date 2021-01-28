package com.kosmo.a40fragmentlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IdolView extends LinearLayout {

    TextView textView1;//그룹명
    TextView textView2;//인원수

    public IdolView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.idol_view, this, true);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
    }

    public void setName(String name) {
        textView1.setText(name);
    }

    public void setPerson(String pCount) {
        textView2.setText(pCount);

    }
}