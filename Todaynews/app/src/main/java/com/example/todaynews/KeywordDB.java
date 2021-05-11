package com.example.todaynews;


import android.provider.BaseColumns;

public final class KeywordDB {

    public static final class CreateDB implements BaseColumns {
        public static final String KEYWORD = "keyword";
        public static final String _TABLENAME0 = "keyword";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement Default 0, "
                +KEYWORD+" text not null );";
    }
}
