package com.example.todaynews;

import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.todaynews.MainActivity;
import com.example.todaynews.R;
import com.example.todaynews.TextItem;
import com.example.todaynews.TextWall;
import com.moxun.tagcloudlib.view.TagCloudView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    phpDown task;
    ArrayList<String> keyword = new ArrayList<String>();
    private TextWall textWall;
    private TagCloudView tagCloudView;
    int select = 1;

    private KeywordDBOpenHelper mDBOpenHelper;
    View root = null;
    String[] texts = {"로딩중","로딩중","로딩중","로딩중","로딩중","로딩중","로딩중","로딩중","로딩중","로딩중"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/data/data/com.example.todaynews/files/myfile.txt"));
            String readStr = br.readLine();
            select = Integer.parseInt(readStr);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            select = 1;
        }

        task = new phpDown();

        try {
            task.execute("http://todaynews.dothome.co.kr/find_keyword.php");
        } catch (Exception e) {
            e.printStackTrace();
            task.cancel(true);
            task = new phpDown();
            task.execute("http://todaynews.dothome.co.kr/find_keyword.php");
        }

        if (select == 1) {
            root = inflater.inflate(R.layout.fragment_home, container, false);
        } else if (select == 2) {
            root = inflater.inflate(R.layout.fragment_home2, container, false);
            showDisplay();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i =0;i<10;i++){
                    texts[i] = keyword.get(i);
                }
                showDisplay();
            }
        }, 1000); //딜레이 타임 조절
        return root;
    }

    public View showDisplay() {
        if (select == 1) {
            textWall = (TextWall) root.findViewById(R.id.tw_test);
            textWall.post(new Runnable() {
                @Override
                public void run() {
                    List<TextItem> textItems = new ArrayList<TextItem>();
                    for (int i = 0; i < 10; i++) {
                        TextItem item = new TextItem();
                        item.setIndex(10);
                        item.setValue(texts[i % 10]);
                        textItems.add(item);
                    }
                    textWall.setData(textItems, getContext());
                }
            });


        } else if (select == 2) {
            tagCloudView = (TagCloudView) root.findViewById(R.id.tag_cloud);
            TextTagsAdapter tagsAdapter = new TextTagsAdapter(texts);
            tagCloudView.setAdapter(tagsAdapter);
        }
        return root;
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
                keyword.clear();
                JSONObject jsonObject = new JSONObject(str);
                JSONArray keywordArray = jsonObject.getJSONArray("Keyword_Rank");
                for (int i = 0; i < keywordArray.length(); i++) {
                    JSONObject keywordObject = keywordArray.getJSONObject(i);
                    String temp = keywordObject.getString("Keyword_Word");
                    keyword.add(temp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

