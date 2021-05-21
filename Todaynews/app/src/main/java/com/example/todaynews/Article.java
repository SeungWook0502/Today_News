package com.example.todaynews;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class Article extends AppCompatActivity {
    private ListView lt_id;
    private String title;
    private TextToSpeech tts;
    private int count = 0;
    int pause = 0;
    List<String> Article_Title = new ArrayList<>();
    List<String> Article_Content = new ArrayList<>();
    List<String> Article_URL = new ArrayList<>();
    List<String> Article_Emotion = new ArrayList<>();
    int[] sid = {100, 101, 102, 103, 104, 105};
    phpDown task;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Toolbar toolbar = findViewById(R.id.article_toolbar);

        title = getIntent().getStringExtra("title");
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        lt_id = findViewById(R.id.lt_id);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Article_Title);
        lt_id.setAdapter(adapter);
        lt_id.setOnItemClickListener(listener);

        task = new phpDown();

        int select_keyword = getIntent().getIntExtra("select_keyword", 1);

        if (select_keyword == 1) {
            try {
                task.execute("http://todaynews.dothome.co.kr/Search_Article.php?Keyword_Word=\"" + title + "\"");
            } catch (Exception e) {
                e.printStackTrace();
                task.cancel(true);
                task = new phpDown();
                task.execute("http://todaynews.dothome.co.kr/Search_Article.php?Keyword_Word=\"" + title + "\"");
            }
        } else {

            try {
                BufferedReader br = new BufferedReader(new FileReader("/data/data/com.example.todaynews/files/keywordfile.txt"));
                for (int i = 0; i < 6; i++) {
                    String readStr = br.readLine();
                    sid[i] = Integer.parseInt(readStr);
                    sid[i] = sid[i] == 1 ? (100 + i) : 0;
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                task.execute("http://todaynews.dothome.co.kr/Search_FavoriteArticle.php?Keyword_Word=\"" + title + "\"&Keyword_Sidnum1=\"" + sid[0] + "\"&Keyword_Sidnum2=\"" + sid[1] + "\"&Keyword_Sidnum3=\"" + sid[2] + "\"&Keyword_Sidnum4=\"" + sid[3] + "\"&Keyword_Sidnum5=\"" + sid[4] + "\"&Keyword_Sidnum6=\"" + sid[5] + "\"");
            } catch (Exception e) {
                e.printStackTrace();
                task.cancel(true);
                task = new phpDown();
                task.execute("http://todaynews.dothome.co.kr/Search_FavoriteArticle.php?Keyword_Word=\"" + title + "\"&Keyword_Sidnum1=\"" + sid[0] + "\"&Keyword_Sidnum2=\"" + sid[1] + "\"&Keyword_Sidnum3=\"" + sid[2] + "\"&Keyword_Sidnum4=\"" + sid[3] + "\"&Keyword_Sidnum5=\"" + sid[4] + "\"&Keyword_Sidnum6=\"" + sid[5] + "\"");
            }
        }

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_toolbar, menu);
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
            intent.putExtra("Article_Title", Article_Title.get(position));
            intent.putExtra("Article_Content", Article_Content.get(position));
            intent.putExtra("Article_URL", Article_URL.get(position));
            startActivity(intent);
        }
    };

    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.save) {
            if (pause == 0) {
                item.setTitle("정지");
                pause = 1;
                for (int i = 0; i < count; i++) {
                    tts.speak(Article_Title.get(i) + "  ", TextToSpeech.QUEUE_ADD, null);
                    tts.speak(Article_Content.get(i) + "     ", TextToSpeech.QUEUE_ADD, null);
                }
            } else {
                item.setTitle("음성으로 듣기");
                tts.stop();
                pause = 0;
            }
        }
        return true;
    }

    private class phpDown extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 연결되었으면.
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for (; ; ) {
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if (line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return jsonHtml.toString();
        }


        protected void onPostExecute(String str) {
            try {
                Article_Title.clear();
                Article_Content.clear();
                Article_URL.clear();
                Article_Emotion.clear();
                JSONObject jsonObject = new JSONObject(str);
                JSONArray ArticleArray = jsonObject.getJSONArray("Article_List");
                for (int i = 0; i < ArticleArray.length(); i++) {
                    JSONObject articleObject = ArticleArray.getJSONObject(i);
                    Article_Title.add(articleObject.getString("Article_Title"));
                    Article_Content.add(articleObject.getString("Article_Content"));
                    Article_URL.add(articleObject.getString("Keyword_URL"));
                    //Article_Emotion.add(articleObject.getString("Article_Emotion"));
                    count++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    }
}