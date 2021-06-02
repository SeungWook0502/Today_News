package com.example.todaynews;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.moxun.tagcloudlib.view.TagCloudView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    private phpDown task;
    private ArrayList<String> favorite_keyword = new ArrayList<String>();
    private ArrayList<String> favorite_emotion = new ArrayList<String>();
    private TagCloudView tagCloudView;
    private int select = 1;
    private int[] sid = {100,101,102,103,104,105};
    private View root = null;
    private String[] texts = {"", "", "", "", "", "", "", "", "", ""};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        try {
            BufferedReader br = new BufferedReader(new FileReader("/data/data/com.example.todaynews/files/myfile.txt"));
            String readStr = br.readLine();
            select = Integer.parseInt(readStr);
            br.close();
        } catch (Exception e){
            select = 1;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("/data/data/com.example.todaynews/files/keywordfile.txt"));
            for(int i =0;i<6;i++) {
                String readStr = br.readLine();
                sid[i] = Integer.parseInt(readStr);
                sid[i] = sid[i]==1? (100+i):0;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        task = new phpDown();

        try {
            task.execute("http://todaynews.dothome.co.kr/Find_FavoriteKeyword.php?Keyword_Sidnum1=\"" + sid[0] + "\"&Keyword_Sidnum2=\""+ sid[1] + "\"&Keyword_Sidnum3=\""+ sid[2] + "\"&Keyword_Sidnum4=\""+ sid[3] + "\"&Keyword_Sidnum5=\""+ sid[4] + "\"&Keyword_Sidnum6=\""+ sid[5] + "\"");
        } catch (Exception e) {
            e.printStackTrace();
            task.cancel(true);
            task = new phpDown();
            task.execute("http://todaynews.dothome.co.kr/Find_FavoriteKeyword.php?Keyword_Sidnum1=\"" + sid[0] + "\"&Keyword_Sidnum2=\""+ sid[1] + "\"&Keyword_Sidnum3=\""+ sid[2] + "\"&Keyword_Sidnum4=\""+ sid[3] + "\"&Keyword_Sidnum5=\""+ sid[4] + "\"&Keyword_Sidnum6=\""+ sid[5] + "\"");
        }

        if (select == 1) {
            root = inflater.inflate(R.layout.fragment_home, container, false);
        } else if (select == 2) {
            root = inflater.inflate(R.layout.fragment_home2, container, false);
            showDisplay();
        } else if (select == 3) {
            root = inflater.inflate(R.layout.fragment_home3, container, false);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        texts[i] = favorite_keyword.get(i);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                showDisplay();
            }
        }, 2000); //딜레이 타임 조절
        return root;
    }


    public View showDisplay() {
        if (select == 1) {
            TextView[] tv = new TextView[10];
            TextView[] tv_negative = new TextView[10];
            Drawable color = getResources().getDrawable(R.color.light_blue);

            Display display = getActivity().getWindowManager().getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);

            Point size = new Point();
            display.getRealSize(size);

            float density = getResources().getDisplayMetrics().density;
            int width = (int) (size.x/density);

            for (int i = 0; i < 10; i++) {
                int tv_id = getResources().getIdentifier("home_tv" + i, "id", "com.example.todaynews");
                int tv_negative_id = getResources().getIdentifier("tv" + i + "_negative", "id", "com.example.todaynews");
                tv[i] = ((TextView) root.findViewById(tv_id));
                tv_negative[i] = ((TextView) root.findViewById(tv_negative_id));
            }

            for (int i = 0; i < 10; i++) {
                tv[i].setText(i + 1 + ":" + texts[i]);
                tv[i].setBackground(color);
                long result = Math.round(Double.parseDouble(favorite_emotion.get(i)) * 100);
                String str = " ";
                if (result == 0) {
                    for(int j = 0; j<width;j++){
                        str+=" ";
                    }
                    tv_negative[i].setTextSize(width/100);
                    tv_negative[i].setText(str);
                } else if (result >= 100) {
                } else {
                    for(int j = 0; j<(width/100)*(100-result);j++){
                        str+=" ";
                    }
                    tv_negative[i].setTextSize(width/100);

                    tv_negative[i].setText(str);
                }
            }

            tv[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(getContext(), Article.class);
                        intent.putExtra("title", texts[0]);
                        intent.putExtra("select_keyword", 2);
                        getContext().startActivity(intent);
                }
            });
            tv[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(getContext(), Article.class);
                        intent.putExtra("title", texts[1]);
                        intent.putExtra("select_keyword", 2);
                        getContext().startActivity(intent);
                }
            });
            tv[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(getContext(), Article.class);
                        intent.putExtra("title", texts[2]);
                        intent.putExtra("select_keyword", 2);
                        getContext().startActivity(intent);
                }
            });
            tv[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(getContext(), Article.class);
                        intent.putExtra("title", texts[3]);
                        intent.putExtra("select_keyword", 2);
                        getContext().startActivity(intent);
                }
            });
            tv[4].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(getContext(), Article.class);
                        intent.putExtra("title", texts[4]);
                        intent.putExtra("select_keyword", 2);
                        getContext().startActivity(intent);
                }
            });
            tv[5].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(getContext(), Article.class);
                        intent.putExtra("title", texts[5]);
                        intent.putExtra("select_keyword", 2);
                        getContext().startActivity(intent);
                }
            });
            tv[6].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(getContext(), Article.class);
                        intent.putExtra("title", texts[6]);
                        intent.putExtra("select_keyword", 2);
                        getContext().startActivity(intent);
                }
            });
            tv[7].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(getContext(), Article.class);
                        intent.putExtra("title", texts[7]);
                        intent.putExtra("select_keyword", 2);
                        getContext().startActivity(intent);
                }
            });
            tv[8].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(getContext(), Article.class);
                        intent.putExtra("title", texts[8]);
                        intent.putExtra("select_keyword", 2);
                        getContext().startActivity(intent);
                }
            });
            tv[9].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(getContext(), Article.class);
                        intent.putExtra("title", texts[9]);
                        intent.putExtra("select_keyword", 2);
                        getContext().startActivity(intent);
                }
            });


        } else if (select == 2) {
            tagCloudView = (TagCloudView) root.findViewById(R.id.tag_cloud);
            TextTagsAdapter tagsAdapter = new TextTagsAdapter(texts,2);
            tagCloudView.setAdapter(tagsAdapter);
        } else if (select == 3) {
            int weight = 40;
            WordCloudView wordCloudView = root.findViewById(R.id.wcv);
            wordCloudView.setSelect_keyword(2);
            for (int i = 0; i < 10; i++) {
                wordCloudView.addTextView(texts[i], weight);
                if (i % 3 == 2)
                    weight -= 8;
            }
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
                favorite_keyword.clear();
                favorite_emotion.clear();
                JSONObject jsonObject = new JSONObject(str);
                JSONArray favoritekeywordArray = jsonObject.getJSONArray("FavoriteKeyword_Rank");
                for (int i = 0; i < favoritekeywordArray.length(); i++) {
                    JSONObject favoritekeywordObject = favoritekeywordArray.getJSONObject(i);
                    String keyword = favoritekeywordObject.getString("Keyword_Word");
                    String emotion = favoritekeywordObject.getString("Emotion");
                    favorite_keyword.add(keyword);
                    favorite_emotion.add(emotion);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

