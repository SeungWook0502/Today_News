package com.example.todaynews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Article extends AppCompatActivity {

    private ListView lt_id;
    private String title;
    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Toolbar toolbar = findViewById(R.id.article_toolbar);

        title = getIntent().getStringExtra("title");
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        lt_id = findViewById(R.id.lt_id);


        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        lt_id.setAdapter(adapter);

        for (int i = 1; i < 100; i++) {
            data.add("기사 타이틀" + String.valueOf(i));
        }
        adapter.notifyDataSetChanged();

        lt_id.setOnItemClickListener(listener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_toolbar, menu);
        //없앨수도 있음 예정
        menu.getItem(0).setTitle("음성으로 듣기");
        return true;
    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        //ListView의 아이템 중 하나가 클릭될 때 호출되는 메소드
        //첫번째 파라미터 : 클릭된 아이템을 보여주고 있는 AdapterView 객체(여기서는 ListView객체)
        //두번째 파라미터 : 클릭된 아이템 뷰
        //세번째 파라미터 : 클릭된 아이템의 위치(ListView이 첫번째 아이템(가장위쪽)부터 차례대로 0,1,2,3.....)
        //네번재 파리미터 : 클릭된 아이템의 아이디(특별한 설정이 없다면 세번째 파라이터인 position과 같은 값)
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(Article.this, Summary.class);
            intent.putExtra("menu_select", 1);
            intent.putExtra("title", title);
            startActivity(intent);
        }
    };

    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.save) {
            Toast.makeText(this, "음성으로 듣기", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}