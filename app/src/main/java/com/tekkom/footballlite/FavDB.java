package com.tekkom.footballlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tekkom.footballlite.data.Favorite;

import java.util.ArrayList;

public class FavDB extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DATABASE_NAME = "TeamDB";
    private static final String TABLE_NAME = "favoriteTable";
    public static String KEY_ID = "idTeam";
    public static String ITEM_STR_TEAM = "strTeam";
    public static String ITEM_STR_STADIUM = "strStadium";
    public static String ITEM_STR_DESC = "strDescriptionEN";
    public static String ITEM_STR_TEAMBADGE = "strTeamBadge";

    // dont forget write this spaces
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + " TEXT," + ITEM_STR_TEAM+ " TEXT,"
            + ITEM_STR_STADIUM + " TEXT," + ITEM_STR_DESC + " TEXT," + ITEM_STR_TEAMBADGE+" TEXT)";

    public FavDB(Context context) { super(context,DATABASE_NAME, null,DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(sqLiteDatabase);
    }

    // insert data to database
    public long insertIntoTheDatabase(String idTeam, String strTeam,  String strStadium,
                                      String strDescriptionEN, String strTeamBadge) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID, idTeam);
        cv.put(ITEM_STR_TEAM, strTeam);
        cv.put(ITEM_STR_STADIUM, strStadium);
        cv.put(ITEM_STR_DESC, strDescriptionEN);
        cv.put(ITEM_STR_TEAMBADGE, strTeamBadge);

        long insert = db.insert(TABLE_NAME, null, cv);

        return insert;
    }

    // delete line from database
    public void deleteFavorite(String idTeam) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(idTeam)});
    }

    // read all favorite list
    public ArrayList<Favorite> getFavorite() {
        ArrayList<Favorite> favoriteArrayList = new ArrayList<Favorite>();
        String getQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor query = sqLiteDatabase.rawQuery(getQuery, null);
        if (query.moveToFirst()) {
            do {
                Favorite favorite = new Favorite();
                favorite.setIdTeam(query.getString(query.getColumnIndex(KEY_ID)));
                favorite.setStrTeam(query.getString(query.getColumnIndex(ITEM_STR_TEAM)));
                favorite.setStrStadium(query.getString(query.getColumnIndex(ITEM_STR_STADIUM)));
                favorite.setStrDescriptionEN(query.getString(query.getColumnIndex(ITEM_STR_DESC)));
                favorite.setStrTeamBadge(query.getString(query.getColumnIndex(ITEM_STR_TEAMBADGE)));

                favoriteArrayList.add(favorite);
            }
            while (query.moveToNext());
        }
        return favoriteArrayList;
    }
}
