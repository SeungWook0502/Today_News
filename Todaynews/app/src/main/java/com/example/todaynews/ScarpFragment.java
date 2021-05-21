package com.example.todaynews;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class ScarpFragment extends Fragment {

    private ListView list;
    final ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    final ArrayList<String> no = new ArrayList<String>();
    HashMap<String, String> list_item;
    private DBOpenHelper mDBOpenHelper;
    private SimpleAdapter simpleAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_scarp, container, false);

        list = root.findViewById(R.id.list);

        simpleAdapter = new SimpleAdapter(root.getContext(), data, android.R.layout.simple_list_item_2,
                new String[]{"item 1", "item 2"},
                new int[]{android.R.id.text1, android.R.id.text2});

        list.setAdapter(simpleAdapter);


        list.setOnItemLongClickListener(long_listener);
        list.setOnItemClickListener(listener);

        mDBOpenHelper = new DBOpenHelper(root.getContext());
        mDBOpenHelper.open();
        mDBOpenHelper.create();

        showDatabase();

        return root;
    }

    AdapterView.OnItemLongClickListener long_listener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent intent = new Intent(getContext(), PopupActivity.class);
            intent.putExtra("position", Integer.parseInt(no.get(position)));
            startActivityForResult(intent, 1);
            return true;
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            //데이터 받기
            String result = data.getStringExtra("result");
            if (result.equals("yes")) {
                int position = data.getIntExtra("position", 0);
                mDBOpenHelper.deleteColumn(position);
                showDatabase();
                Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
            }
        }
    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        //ListView의 아이템 중 하나가 클릭될 때 호출되는 메소드
        //첫번째 파라미터 : 클릭된 아이템을 보여주고 있는 AdapterView 객체(여기서는 ListView객체)
        //두번째 파라미터 : 클릭된 아이템 뷰
        //세번째 파라미터 : 클릭된 아이템의 위치(ListView이 첫번째 아이템(가장위쪽)부터 차례대로 0,1,2,3.....)
        //네번재 파리미터 : 클릭된 아이템의 아이디(특별한 설정이 없다면 세번째 파라이터인 position과 같은 값)
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getContext(), Summary.class);
            intent.putExtra("title", data.get(position).get("item 1"));
            intent.putExtra("menu_select", 2);
            intent.putExtra("position", no.get(position));
            startActivity(intent);
        }
    };

    public void showDatabase() {
        Cursor iCursor = mDBOpenHelper.setFirst();
        data.clear();
        no.clear();
        while (iCursor.moveToNext()) {
            String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));
            String tempKeyword = iCursor.getString(iCursor.getColumnIndex("keyword"));
            String tempArticle_title = iCursor.getString(iCursor.getColumnIndex("article_title"));
            list_item = new HashMap<String, String>();
            list_item.put("item 1", tempKeyword);
            list_item.put("item 2", tempArticle_title);
            no.add(tempIndex);
            data.add(list_item);
        }
        simpleAdapter.notifyDataSetChanged();
    }
}