package com.example.todaynews;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {

    private Button display_btn;
    private Button keyword_btn;
    private Button credit_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        display_btn = root.findViewById(R.id.display_btn);
        keyword_btn = root.findViewById(R.id.keyword_btn);
        credit_btn = root.findViewById(R.id.credit_btn);

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

        credit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent credit_setting = new Intent(root.getContext(),Credit.class);
                startActivity(credit_setting);
            }
        });
        return root;
    }
}