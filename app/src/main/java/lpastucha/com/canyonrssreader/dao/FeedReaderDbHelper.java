package lpastucha.com.canyonrssreader.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by lpastucha on 19.06.2017.
 */

public class FeedReaderDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static FeedReaderDbHelper instance;

    private FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final FeedReaderDbHelper getInstance(Context context) {
        if (FeedReaderDbHelper.instance == null) {
            instance = new FeedReaderDbHelper(context);
            return instance;
        } else
            return instance;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedContract.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(FeedContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void saveUrl(String url, String title) {
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedContract.Feed.COLUMN_NAME_URL, url);
        values.put(FeedContract.Feed.COLUMN_NAME_TITLE, title);

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedContract.Feed.TABLE_NAME, null, values);
    }

    public Cursor getFeeds() {
        SQLiteDatabase db = this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                FeedContract.Feed.COLUMN_NAME_URL,
                FeedContract.Feed.COLUMN_NAME_TITLE
        };

        Cursor cursor = db.query(
                FeedContract.Feed.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        return cursor;
    }
}

