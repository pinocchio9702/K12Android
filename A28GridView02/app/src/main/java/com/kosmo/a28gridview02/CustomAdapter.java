package com.kosmo.a28gridview02;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    //메인액티비티에서 어댑터 생성시 매개변수로 전달되는 값 사용
    private Context context;//메인액티비티를 가르킴.
    private String[] movies;//영화제목을 저장한 배열
    private int[] resIds;//영화이미지의 아이디값을 저장한 배열
    //생성자
    public CustomAdapter(Context context, String[] movies, int[] resIds){
        this.context = context;
        this.movies  = movies;
        this.resIds  = resIds;
    }

    @Override
    public int getCount() {
        return movies.length;
    }

    @Override
    public Object getItem(int position) {
        return movies[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //생성된 아이템 레이아웃이 없을경우 xml레이아웃 파일을 통해 인플레이트 처리
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.movie_item, null);
        }
        //위젯을 가져온후 position 인덱스에 해당하는 각 위젯의 데이터 설정
        ImageView movieImage = (ImageView)convertView.findViewById(R.id.movieImg);
        //영화포스터 이미지를 커스텀뷰에 설정
        movieImage.setImageResource(resIds[position]);
        //영화제목을 설정
        final TextView movieTitle = (TextView)convertView.findViewById(R.id.movieTitle);
        movieTitle.setText(movies[position]);


//        movieImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                LayoutInflater inflater=(LayoutInflater)context.getSystemService(
//                        Context.LAYOUT_INFLATER_SERVICE);
//                View view=inflater.inflate(R.layout.dialog_layout,null);
//
//                //다이얼로그용 이미지에 클릭한 해당 이미지 설정
//                ImageView image=(ImageView)view.findViewById(R.id.moviedialog);
//                image.setImageResource(resIds[position]);
//
//                //다이얼로그 생성]setView(dialogview)로 위에서 전개한 뷰를
//                // 다이얼로그에 설정
//                new AlertDialog.Builder(context)
//                        .setTitle(movies[position])
//                        .setIcon(android.R.drawable.ic_dialog_email)
//                        .setView(view)
//                        .setPositiveButton("OK",null)
//                        .show();
//
//            }
//        });

        return convertView;
    }
}