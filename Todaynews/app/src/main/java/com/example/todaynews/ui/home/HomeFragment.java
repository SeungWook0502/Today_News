package com.example.todaynews.ui.home;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextWall textWall;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        textWall = (TextWall) root.findViewById(R.id.tw_test);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        final String[] texts = {"비트코인","LH","박범계","대검","한명숙","투기","근로중"};
        textWall.post(new Runnable(){
            @Override
            public void run() {
                List<TextItem> textItems = new ArrayList<TextItem>();
                for (int i = 0; i < 7; i++) {
                    TextItem item = new TextItem();
                    item.setIndex(7);
                    item.setValue(texts[i%7]);
                    textItems.add(item);
                }
                textWall.setData(textItems, getContext());
                Log.d("aaaaa",textWall.getWidth()+"dp");
            }
        });

    }

}

