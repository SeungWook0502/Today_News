package com.example.todaynews;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todaynews.TextItem;

import java.util.List;
import java.util.Random;

public class TextWall extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener {

    private int[] colors = {R.color.red, R.color.orange, R.color.yellow, R.color.green, R.color.cyan, R.color.blue, R.color.purple};
    private int width, height;
    Random random = new Random();

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
       // int[] temp_left = new int[10];
       // int[] temp_top = new int[10];

        //위치 고정
        int[] temp_left = {40, 40, 40, 40, 40, 40, 40};
        int[] temp_top = {10, 200, 370, 520, 650, 760, 850};

        //1.优先级排序
        items = sortTextItem(items);
        //2.字体大小排序
        int[] frontSizes = generateFrontSize(items.size());
        for (int i = 0; i < count; i++) {
            TextItem temp = items.get(i);
            temp.setFrontSize(frontSizes[i]);
            temp.setFrontColor(colors[i]);
            items.set(i, temp);
        }

        for (int i = count - 1; i >= 0; i--) {
            TextView textView = new TextView(context);
            textView.setText(items.get(i).getValue());
            textView.setEms((i-count)*-1);
            textView.setTextSize((i-count)*-8);
            textView.setTextColor(context.getResources().getColor(items.get(i).getFrontColor()));

            //객체 크기 보기(배경색)
            textView.setBackgroundColor(colors[i]);


            textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            textView.setTag(items.get(i));
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //터치시 화면전환부분 데이터 전송 필요!
                    Intent intent = new Intent(context,Article.class);
                    context.startActivity(intent);

                    //Toast.makeText(context, ((TextItem) view.getTag()).getValue(), Toast.LENGTH_SHORT).show();
                }
            });
            addView(textView);

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            MarginLayoutParams marginParams = null;
            //获取view的margin设置参数
            if (params instanceof MarginLayoutParams) {
                marginParams = (MarginLayoutParams) params;
            } else {
                //不存在时创建一个新的参数
                //基于View本身原有的布局参数对象
                marginParams = new MarginLayoutParams(params);
            }
            //设置margin

            marginParams.setMargins(temp_left[i], temp_top[i], 0, 0);
            textView.setLayoutParams(marginParams);
        }
        TextView tv = null;
        }


    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams p = (MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    private int[] generateFrontSize(int count) {
        int[] sizes = new int[count];
        for (int i = 0; i < count; i++) {
            random = new Random();
            sizes[i] = (random.nextInt(6) * 5 + 12);
        }
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (sizes[i] > sizes[j]) {
                    int c = sizes[i];
                    sizes[i] = sizes[j];
                    sizes[j] = c;
                }
            }
        }
        return sizes;
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
