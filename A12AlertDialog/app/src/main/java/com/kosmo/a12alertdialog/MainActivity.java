package com.kosmo.a12alertdialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //
    private  String[] girlGroup = {"트와이스", "AOA", "모모랜드", "블랙핑크"};
    //선택한 라디오 항목의 인덱스
    private  int radio_index = -1;
    //선택한 체크받스 항목 저장
    boolean[] which_checks = {false, false, true, true};
    //목록형 대화상자에서 사용
    private  String[] sports = {"축구", "야구", "배구", "농구"};
    //진행대화상자 객체 생성
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼 위젯 가져오기
        Button btnAlertBasic = (Button)findViewById(R.id.btn_alert_basic);
        Button btnAlertCheck = (Button)findViewById(R.id.btn_alert_checkbox);
        Button btnAlertRadio = (Button)findViewById(R.id.btn_alert_radio);
        Button btnAlertList = (Button)findViewById(R.id.btn_alert_list);
        Button btnAlertProgress = (Button)findViewById(R.id.btn_alert_progress);
        Button btnCustom = (Button)findViewById(R.id.btn_alert_custom);

        //기본 대화상자 띄우기
        btnAlertBasic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBasic =
                        new AlertDialog.Builder(v.getContext());
                alertBasic.setCancelable(false);
                alertBasic.setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("대화상자제목")
                        .setMessage("여기는 메세지가 들어갑니다.")
                        .setPositiveButton("확인버튼",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this,
                                                "확인 클릭합니다.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("취소버튼",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this,
                                                "최소 클릭합니다.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).show();


            }
        });

        /*
        목록형 대화상자 : 항목중 하나만 선택가능함
            .setItems(목록에 출력할 배열, 리스너)
                :배열이 항목에 출력되고 항목을 클릭할 경우 바로 리스너가
                감지하여 콜백한다. 콜백함수쪽으로 선택한 항목의 인덱스가
                전달된다.
         */

        btnAlertList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_lock_power_off)
                        .setTitle("당신이 좋아하는 스포치는?")
                        .setItems(sports,
                                new DialogInterface.OnClickListener() {
                            //항목을 클릭할때 즉시 호출됨 i를 통해 인덱스가 전달됨.
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this,
                                                sports[which],
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })

                        .setNegativeButton("최소",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this,
                                                "목록 최소함",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).show();


            }
        });

        /*
        라디오형 대화상자 - 항목중 하나만 선택 가능
            .setSingleChoiceItems(배열, 디폴트로 선택될 인덱스, 리스너)
            두번째 매개변수를 마이너스값으로 지정하면 선택항목이 없는
            상태로 대화창이 설정된다.
         */
        btnAlertRadio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setIcon(android.R.drawable.ic_dialog_email)
                        .setTitle("당신이 좋아하는 걸그룹은?")
                        .setSingleChoiceItems(girlGroup, radio_index,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        radio_index = which;
                                    }
                                })
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this,
                                                girlGroup[radio_index],
                                                Toast.LENGTH_SHORT).show();
                                    }
                        })
                        .setNegativeButton("CANCEL",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this,
                                                "취소하셧습니다.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).show();


            }
        });

        /*
        체크박스형 대화상자 - 여러개 선택가능
        setMultiChoiceItems(배열, 디폴트로 선택될 항목의 boolean값을 담은 배열, 리스너)
        *현재 맴버변수로 which_check가 선언되었음
         */
        btnAlertCheck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                new AlertDialog.Builder(V.getContext())
                        .setIcon(android.R.drawable.ic_dialog_dialer)
                        .setTitle("당신이 좋아하는 걸그룹은?(여러개선택)")
                        .setMultiChoiceItems(girlGroup, which_checks,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i, boolean b) {
                                        /*
                                        매개변수
                                            i : 선택한 체크박스의 인덱스
                                            b : 선택한 항목의 체크여부(boolean)체크했을때 ture가 전달된다.
                                         */
                                        Toast.makeText(MainActivity.this,
                                                String.format("which:%d, isChecked:%b", i,b),
                                                Toast.LENGTH_SHORT).show();
                                        //라디오 항목을 선택했을때 인덱스를 변수에 저장한다.
                                        which_checks[i] = b;
                                    }
                                })
                        .setPositiveButton("네~",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        /*
                                        선택한 체크 항목을 StringBuffer클래스로 저장한다.
                                         */
                                        StringBuffer buf = new StringBuffer();
                                        for(int i=0; i<girlGroup.length; i++){
                                            if(which_checks[i]==true)
                                                buf.append(girlGroup[i]+" ");
                                        }
                                        Toast.makeText(MainActivity.this,
                                                buf,
                                                Toast.LENGTH_LONG).show();
                                    }
                                })
                        .setNegativeButton("아니요~",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //'아니요~'버튼 클릭했을때
                                        Toast.makeText(MainActivity.this,
                                                "아니요~.버튼을 클릭하였습니다",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).show();


            }
        });

        /*
        커스텀 대화상자
        순서
            1. layout폴더에 사용자가 정의한 대화상자 xml파일을 생성한다.
            2. inflate()를 통해 레이아웃을 인플레이트(전개)한다.
            3. Builder객체를 통해 대화상자를 설정할대 setView()생성자를 통해
                2번에서 전개한 레이아웃을 대화상자에 적용한다.
         */
        btnCustom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater()
                        .inflate(R.layout.custom_dialog, null);
                final EditText sportTxt =
                        (EditText)view.findViewById(R.id.sport_txt);
                new AlertDialog.Builder(v.getContext())
                        .setView(view)
                        .setIcon(android.R.drawable.ic_media_play)
                        .setTitle("커스텀대화상자")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //확인을 눌렀을때는 입력한 내용을 출력한다.
                                Toast.makeText(MainActivity.this,
                                        sportTxt.getText(),
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("최소", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this,
                                                "최소를 눌렀습니다.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
            }
        });




        //진행대화창 객체 생성
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIcon(android.R.drawable.ic_menu_day);
        progress.setTitle("KOSMO여러분들");
        progress.setMessage("열공하는 중입니다. 조용히 합시다.");

        //진행 대화상자 띄우기
        btnAlertProgress.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //버튼을 눌렀을대 대화상자가 열려있지 않을때만 보임처리한다.
                if(!progress.isShowing()){
                    progress.show();
                }
                //2초긴 대기후 진행창 닫기
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();;//무조건 닫아준다.
                        //progress.cancel();//열린상태일때 닫아준다.
                    }
                }, 2000);
            }
        });



    }
}