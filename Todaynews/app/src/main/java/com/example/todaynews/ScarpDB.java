package com.example.todaynews;


import android.provider.BaseColumns;

public final class ScarpDB {

    public static final class CreateDB implements BaseColumns {
        public static final String KEYWORD = "keyword";
        public static final String ARTICLE_TITLE = "article_title";
        public static final String ARTICLE_CONTENT = "article_content";
        public static final String ARTICLE_URL = "article_url";
        public static final String _TABLENAME0 = "scarp";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement Default 0, "
                +KEYWORD+" text not null , "
                +ARTICLE_TITLE+" text not null , "
                +ARTICLE_CONTENT+" text not null , "
                +ARTICLE_URL+" text not null );";
    }
}
