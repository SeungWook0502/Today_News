package com.example.todaynews;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.moxun.tagcloudlib.view.TagsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class TextTagsAdapter extends TagsAdapter {
    private List<String> dataSet = new ArrayList<>();

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
        String[] name = {"비트코인", "LH", "박범계", "대검", "한명숙", "투기", "근로중"};
        Random rand = new Random();
        int randNum = rand.nextInt(7);

        TextView tv = new TextView(context);

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(200, 200);
        tv.setLayoutParams(lp);
        tv.setText(name[randNum]);
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Click", "Tag " + position + " clicked.");
                //정보 넘겨주는 코드 필요!
                Intent intent = new Intent(context,Article.class);
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
        return position % 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {
        ((TextView) view).setTextColor(themeColor);
    }
} 