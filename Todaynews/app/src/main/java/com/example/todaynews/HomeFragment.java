package com.example.todaynews;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextWall textWall;
    int select;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = null;
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

        //파일입출력으로 테마 변환
        if (select == 1) {
            root = inflater.inflate(R.layout.fragment_home, container, false);
            textWall = (TextWall) root.findViewById(R.id.tw_test);
            final String[] texts = {"비트코인", "LH", "박범계", "대검", "한명숙", "투기", "근로중"};
            textWall.post(new Runnable() {
                @Override
                public void run() {
                    List<TextItem> textItems = new ArrayList<TextItem>();
                    for (int i = 0; i < 7; i++) {
                        TextItem item = new TextItem();
                        item.setIndex(7);
                        item.setValue(texts[i % 7]);
                        textItems.add(item);
                    }
                    textWall.setData(textItems, getContext());
                }
            });
        } else if (select == 2) {
            root = inflater.inflate(R.layout.fragment_home2, container, false);
            TagCloudView tagCloudView = (TagCloudView) root.findViewById(R.id.tag_cloud);
            TextTagsAdapter tagsAdapter = new TextTagsAdapter(new String[20]);
            tagCloudView.setAdapter(tagsAdapter);
        }

        return root;
    }
}

