package com.kosmo.a30xmljsonparser02;

import android.app.PendingIntent;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//BaseAdapter 클래를 상속받아 커스텀 어뎁터를 정의한다.
public class ActorAdapter extends BaseAdapter {
    private Context context; //MainAcitivty에서 사용하기 위한 컨텍스트
    private List<ActorVO> items; //어뎁터에서 사용할 데이터를 저장한 컬렉션
    private  int layoutResId; //커스텀 레이아웃의 리소스 아이디

    public ActorAdapter(Context context, List<ActorVO> items, int layoutResId) {
        this.context = context;
        this.items = items;
        this.layoutResId = layoutResId;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /*
    하나의 항목을 표현하는 메소드
     */

    //convertView???
    //.get(position)????

    //getView를 오버라이딩 하게되면 items 리스트에 있는 데이터가 자동으로 하나씩 반환하게 된다.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //if문이 없어도 실행되는데는 문제 없음
        //생성된 커스텀 레이아웃이 없다면 inflate한다.?????
        if(convertView==null) {
            convertView = View.inflate(context, layoutResId, null);
        }


        //커스텀뷰(actor_layout.xml)에서 위젯 가져오기
        TextView tvName = convertView.findViewById(R.id.tv_name);
        TextView tvAge = convertView.findViewById(R.id.tv_age);
        TextView tvHobbys = convertView.findViewById(R.id.tv_hobbys);
        TextView tvLogin = convertView.findViewById(R.id.tv_login);
        ImageView profileImg = convertView.findViewById(R.id.imageView);

        //컬렉션에서 저장된 값을 통해 각 위젯을 설정(컬렉션에 저장된 VO객체를 하났기 가져옴)
        tvName.setText(items.get(position).getName());
        tvAge.setText(items.get(position).getAge());
        tvHobbys.setText(items.get(position).getHobbys());
        tvLogin.setText(items.get(position).getLogin());
        profileImg.setImageResource(items.get(position).getProfileImg());

        //리스트뷰에 스트라이프 효과를 주기 휘안 연산산
        if(position%2==0){
           convertView.setBackgroundColor(0x99dadada);
        }else{
            convertView.setBackgroundColor(0x99ffffff);
        }

        return convertView;
    }
}
