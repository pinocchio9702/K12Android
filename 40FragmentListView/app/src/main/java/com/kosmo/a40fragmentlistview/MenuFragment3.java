package com.kosmo.a40fragmentlistview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MenuFragment3 extends Fragment {

    public static final String TAG = "iKosmo";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "MenuFragment3 > onCreateView()");
        View view = inflater.inflate(R.layout.menu_fragment3, container, false);

        ((Button)view.findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),
                        "세번째 프레그먼트 입니다.",
                        Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
