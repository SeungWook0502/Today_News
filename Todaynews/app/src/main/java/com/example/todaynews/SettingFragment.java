package com.example.todaynews;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.todaynews.R;

public class SettingFragment extends Fragment {

    private Button display_btn;
    private Button keyword_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        display_btn = root.findViewById(R.id.display_btn);
        keyword_btn = root.findViewById(R.id.keyword_btn);

        display_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent display_setting = new Intent(root.getContext(),DisplaySetting.class);
                startActivity(display_setting);
            }
        });

        keyword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent keyword_setting = new Intent(root.getContext(),KeywordSetting.class);
                startActivity(keyword_setting);
            }
        });
        return root;
    }
}