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

        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);

        TextView txtText = findViewById(R.id.txtText);
        txtText.setText("정말 삭제하시겠습니까?");
    }

    public void yes(View v){
        Intent intent = new Intent();
        intent.putExtra("result", "yes");
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);

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