package com.example.todaynews;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

public class TextWall extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener {

    private int[] colors = {R.color.red, R.color.orange, R.color.yellow, R.color.green, R.color.cyan, R.color.blue, R.color.purple, R.color.yellow, R.color.green, R.color.cyan};
    private int width, height;
    private int select_keyword;

    public TextWall(Context context) {
        super(context);
    }

    public TextWall(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public TextWall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean isFirst = true;

    public void setSelect_keyword(int select_keyword){
        this.select_keyword = select_keyword;
    }
    public void onGlobalLayout() {
        if (isFirst) {
            height = getHeight();
            isFirst = false;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);//(API 16)
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    int count;

    public void setData(List<TextItem> items, final Context context) {
        count = items.size();

        //위치 고정
        int[] temp_left = {40, 40, 40, 40, 40, 40, 40, 40, 40, 40};
        int[] temp_top = {10, 200, 370, 520, 650, 760, 850, 920, 970, 1000};

        //1.우선순위정렬
        items = sortTextItem(items);

        //2.글자크기 정렬
        for (int i = 0; i < count; i++) {
            TextItem temp = items.get(i);
            temp.setFrontSize(i);
            temp.setFrontColor(colors[i]);
            items.set(i, temp);
        }

        for (int i = count - 1; i >= 0; i--) {
            TextView textView = new TextView(context);
            textView.setText(items.get(i).getValue());
            //textView.setEms((i - count) * -1);
            textView.setTextSize((i - count) * -5);
            textView.setTextColor(context.getResources().getColor(items.get(i).getFrontColor()));

            //객체 크기 보기(배경색)
            textView.setBackgroundColor(colors[i]);


            textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            textView.setTag(items.get(i));
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Article.class);
                    intent.putExtra("title", ((TextItem) view.getTag()).getValue());
                    intent.putExtra("select_keyword",select_keyword);
                    context.startActivity(intent);
                }
            });
            addView(textView);

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            MarginLayoutParams marginParams = null;
            //획득view의margin설정계수
            if (params instanceof MarginLayoutParams) {
                marginParams = (MarginLayoutParams) params;
            } else {
                //없을때 새로운 계수 만들기
                //View기준으로 기본레이아웃 인자 개채
                marginParams = new MarginLayoutParams(params);
            }
            //margin 설정

            marginParams.setMargins(temp_left[i], temp_top[i], 0, 0);
            textView.setLayoutParams(marginParams);
        }
        TextView tv = null;
    }

    List<TextItem> sortTextItem(List<TextItem> items) {
        int count = items.size();
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (items.get(i).getIndex() > items.get(j).getIndex()) {
                    TextItem item_c = items.get(i);
                    items.add(i, items.get(j));
                    items.add(j, item_c);
                }
            }
        }
        return items;
    }

}
