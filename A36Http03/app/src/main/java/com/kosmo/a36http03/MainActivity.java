package com.kosmo.a36http03;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "iKosmo";

    ImageView ivPicture;
    TextView tvHtml1;
    String filePath1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPicture = findViewById(R.id.ivPicture);
        tvHtml1 = findViewById(R.id.tvHtml1);

        //권한 체크 후 사용자에 의해 취소되었다면 다시 요청청
        //메니페스트에 설정된 권한에 대해 사용자에게 물어본다.
       if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new
                    String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }//onCreate

    //이미지 선택
    public void onBtnGetPicture(View v){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }
    //파일 업로드
    public void onBtnUpload(View v){
        //텍스트뷰 내용 비우기
        tvHtml1.setText("");

        //파라미터를 맵에 저장
        HashMap<String, String> param1 = new HashMap<>();
        param1.put("userid", "홍길동");
        param1.put("userpwd", "패스워드");

        HashMap<String, String> param2 = new HashMap<>();
        param2.put("filename", filePath1);

        //AsyncTask를 통해 HttpURLConnection 수행
        UploadAsycn networkTask =
                new UploadAsycn(getApplicationContext(), param1, param2);
        //doInBackground() 호출
        networkTask.execute();
    }

    public void onBtnFinish(View v){
        finish();
    }

    //겔러리 리스뷰에서 사진 데이터를 가져오는 방법
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                Uri selPhotoUri = data.getData();
                showCapturedImage(selPhotoUri);
            }
        }
    }

    //사용자 정의 함수 - 사진의 절대 경로 구하기
    private  String getRealPathFromURI(Uri contentUri){
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    //사용자정의 함수 - 사진의 회전값을 처리하지 않으면 사진을 찍는 방향대로 이미지뷰에 처리되지 않는다.
    private int exifOrientationToDegrees(int exitOrientation){
        if(exitOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        }
        else if(exitOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        }
        else if(exitOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }

        return 0;
    }

    private Bitmap rotate(Bitmap src, float degress){
        //Matrix 객체 생성
        Matrix matrix = new Matrix();
        //회전 각도 셋팅
        matrix.postRotate(degress);
        return Bitmap.createBitmap(src, 0, 0,
                src.getWidth(), src.getHeight(), matrix, true);

    }

    private void showCapturedImage(Uri imageUri){
        //사진의 절대경로를 획득한다!!! 중요~
        filePath1 = getRealPathFromURI(imageUri);//사용자정의함수
        Log.d(TAG, "path1:"+filePath1);

        ExifInterface exifInterface = null;

        try{
            exifInterface = new ExifInterface(filePath1);
        }catch (IOException e){
            e.printStackTrace();
        }

        int exifOrientation =
                exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);//사용자 정의 함수

        //경로를 통해 비트맵으로 전환
        Bitmap bitmap = BitmapFactory.decodeFile(filePath1);
        Bitmap rotateBitmap = rotate(bitmap, exifDegree);
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(rotateBitmap, 800, 800, false);
        bitmap.recycle();

        //이미지뷰에 비트맵 삽입
        ivPicture.setImageBitmap(scaleBitmap);

    }

    public class UploadAsycn extends AsyncTask<Object, Integer, JSONObject>{

        //객체 생성시 전달한 파라미터로 맴버변수 초기화
        private Context mContext;
        private HashMap<String, String> param;//파라미터
        private HashMap<String, String> files;//사진파일일


        public UploadAsycn(Context context, HashMap<String, String> param,
                           HashMap<String, String> files){
            mContext = context;
            this.param = param;
            this.files = files;
        }

        protected  void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(Object... objects) {
            JSONObject rtn = null;
            try{
                //프로젝트명이나 요청명이 변경될 수 있음
                //따라서 서비스URL은 리소스의 상수로 저장하는 것이 좋다.
                String sUrl = getString(R.string.server_addr) +
                        "/k12springapi//fileUpload/uploadAction.do";
                //단말기 사진을 서버로 업로드하기위한 객체생성 및 메소드 호출
                //FileUpload 클래스는 기존내용을 그래도 가져다가 쓰면 됨(수정필요 없음)
                FileUpload multipartUpload = new FileUpload(sUrl, "UTF-8");
                rtn = multipartUpload.upload(param, files);
                //서버에서 반환받은 결과데이터를 로그로 출력
                Log.d(TAG, rtn.toString());
            }catch (IOException e){
                e.printStackTrace();
            }

            return rtn;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            //결과 데이터를 덱스트뷰에 출력
            super.onPostExecute(jsonObject);

            if(jsonObject != null){
                tvHtml1.setText(jsonObject.toString());
                try{
                    if(jsonObject.getInt("success") == 1){
                        Toast.makeText(mContext, "파일업로드 성공!",
                                Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(mContext, "파일 업로드 실패!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }



}///MainActivity