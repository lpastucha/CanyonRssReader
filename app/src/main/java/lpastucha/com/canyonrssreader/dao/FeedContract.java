package lpastucha.com.canyonrssreader.dao;

import android.provider.BaseColumns;

/**
 * Created by lpastucha on 19.06.2017.
 */

public class FeedContract {

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Feed.TABLE_NAME + " (" +
                    Feed._ID + " INTEGER PRIMARY KEY," +
                    Feed.COLUMN_NAME_URL + " TEXT,"+
                    Feed.COLUMN_NAME_TITLE + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Feed.TABLE_NAME;

public static class Feed implements BaseColumns {
    public static final String TABLE_NAME = "Feeds";
    public static final String COLUMN_NAME_URL = "url";
    public static final String COLUMN_NAME_TITLE = "title";
}
}
