package com.example.todaynews;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.moxun.tagcloudlib.view.TagsAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TextTagsAdapter extends TagsAdapter {
    private List<String> dataSet = new ArrayList<>();
    int i=0;
    public TextTagsAdapter(@NonNull String... data) {
        dataSet.clear();
        Collections.addAll(dataSet, data);
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public View getView(final Context context, final int position, ViewGroup parent) {
        //데이터 입력파트
        if(i==10)
            i=0;

        TextView tv = new TextView(context);

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(200, 200);
        tv.setLayoutParams(lp);
        tv.setText(dataSet.get(i++));
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Click", "Tag " + position + " clicked.");
                //정보 넘겨주는 코드 필요!
                Intent intent = new Intent(context, Article.class);
                intent.putExtra("title", tv.getText());
                context.startActivity(intent);
            }
        });
        return tv;
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position % 10;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {
        ((TextView) view).setTextColor(themeColor);
    }
} 