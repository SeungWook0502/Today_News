package com.example.todaynews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class PopupActivity extends Activity {

    int position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        //데이터 가져오기
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);

        TextView txtText = findViewById(R.id.txtText);
        txtText.setText("정말 삭제하시겠습니까?");
    }

    //확인 버튼 클릭
    public void yes(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "yes");
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    public void no(View v){
        Intent intent = new Intent();
        intent.putExtra("result", "no");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }
}