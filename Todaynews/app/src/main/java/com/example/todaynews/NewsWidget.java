package com.example.todaynews;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.RemoteViews;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class NewsWidget extends AppWidgetProvider {

    static phpDown task;
    static ArrayList<String> keyword = new ArrayList<String>();
    static RemoteViews views;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.news_widget);

        Intent start_activity = new Intent(Intent.ACTION_MAIN);
        start_activity.addCategory(Intent.CATEGORY_LAUNCHER);
        start_activity.setComponent(new ComponentName(context, MainActivity.class));
        PendingIntent pi = PendingIntent.getActivity(context, 0, start_activity, 0);
        views.setOnClickPendingIntent(R.id.widget_layout, pi);


        task = new phpDown();
        task.execute("http://todaynews.dothome.co.kr/Find_Keyword.php");
        try {
            task.execute("http://todaynews.dothome.co.kr/Find_Keyword.php");
        } catch (Exception e) {
            e.printStackTrace();
            task.cancel(true);
            task = new phpDown();
            task.execute("http://todaynews.dothome.co.kr/Find_Keyword.php");
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }, 5000); //딜레이 타임 조절
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
                for (int appWidgetId : appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId);
                }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static class phpDown extends AsyncTask<String, Integer, String> {
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
            views.setTextViewText(R.id.tv0, "현재 이슈는?");
            views.setTextViewText(R.id.tv1, "1 : " + keyword.get(0));
            views.setTextViewText(R.id.tv2, "2 : " + keyword.get(1));
            views.setTextViewText(R.id.tv3, "3 : " + keyword.get(2));
            views.setTextViewText(R.id.tv4, "4 : " + keyword.get(3));
            views.setTextViewText(R.id.tv5, "5 : " + keyword.get(4));
            views.setTextViewText(R.id.tv6, "6 : " + keyword.get(5));
            views.setTextViewText(R.id.tv7, "7 : " + keyword.get(6));
            views.setTextViewText(R.id.tv8, "8 : " + keyword.get(7));
            views.setTextViewText(R.id.tv9, "9 : " + keyword.get(8));
            views.setTextViewText(R.id.tv10, "10 : " + keyword.get(9));
        }
    }
}