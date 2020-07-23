package com.afzaal.database;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHandler  extends SQLiteOpenHelper {












        public static final String DATABASE_NAME = "tweetify.db";
        public static final String TABLE_NAME = "tweets";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SNAME = "screen_name";

    public static final String COLUMN_CREATED_TIME = "created_at";
        public static final String COLUMN_TIME = "time";
    public static final String COLUMN_TEXT = "data";
    public static final String COLUMN_IMG_URL = "profile_image_url_https";
    public static final String COLUMN_RETWEETS = "retweet_count";
    public static final String COLUMN_RETWEETED = "retweeted";
    public static final String COLUMN_FAV = "favorite_count";

        private HashMap hp;
        Context con;
        public DBHandler(Context context) {

            super(context, DATABASE_NAME , null, 1);

            con=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            try
            {
                db.execSQL(
                        "create table tweets " +
                                "( name text , screen_name text," +
                                " created_at text, time text," +
                                " data text, profile_image_url_https text," +
                                " retweet_count text, retweeted text," +
                                " favorite_count text)"
                );
            }catch(Exception e)
            {

            }

        }
        public void createTable()
        {
            try {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(
                        "create table tweets " +
                                "( name text , screen_name text," +
                                " created_at text, time text," +
                                " data text, profile_image_url_https text," +
                                " retweet_count text, retweeted text," +
                                " favorite_count text)"
                );

            }catch(Exception e)
            {

            }










        }





        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS tweets");
            onCreate(db);
        }

        public boolean insertData (List<List>data) {
            try
            {

                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();

for(List<String> l : data)
                {
                    contentValues.put(COLUMN_NAME, l.get(0));
                    contentValues.put(COLUMN_SNAME, l.get(1));
                    contentValues.put(COLUMN_CREATED_TIME, l.get(2));
                    contentValues.put(COLUMN_TEXT, l.get(3));
                    contentValues.put(COLUMN_IMG_URL, l.get(4));
                    contentValues.put(COLUMN_RETWEETS, l.get(5));
                    contentValues.put(COLUMN_RETWEETED, l.get(6));
                    contentValues.put(COLUMN_FAV, l.get(7));
                    db.insert("tweets", null, contentValues);
                }

                return true;
            }catch(Exception exp)
            {
                return false;
            }

        }

        public ArrayList getData(String name) {
            try
            {
                ArrayList<ArrayList> response=new ArrayList();
                SQLiteDatabase db = this.getReadableDatabase();
                @SuppressLint("Recycle") Cursor res =  db.rawQuery( "select * from tweets where name='"+name+"'", null );


                res.moveToFirst();

                while(res.isAfterLast() == false){
                    ArrayList array_list=new ArrayList();
                    array_list.add(res.getString(res.getColumnIndex(COLUMN_NAME)));
                    array_list.add(res.getString(res.getColumnIndex(COLUMN_SNAME)));
                    array_list.add(res.getString(res.getColumnIndex(COLUMN_CREATED_TIME)));
                    array_list.add(res.getString(res.getColumnIndex(COLUMN_TEXT)));
                    array_list.add(res.getString(res.getColumnIndex(COLUMN_IMG_URL)));
                    array_list.add(res.getString(res.getColumnIndex(COLUMN_RETWEETS)));
                    array_list.add(res.getString(res.getColumnIndex(COLUMN_RETWEETED)));
                    array_list.add(res.getString(res.getColumnIndex(COLUMN_FAV)));
                    response.add(array_list);
                    res.moveToNext();
                }
                return response;
            }catch(Exception e)
            {
                return  null;
            }

        }

        public int numberOfRows(){
            SQLiteDatabase db = this.getReadableDatabase();
            int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
            return numRows;
        }

        public boolean updateTASBEEH (Integer id,String topic, String dua) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("topic", topic);

            contentValues.put("dua", dua);
            db.update("duatable", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
            return true;
        }
public boolean deleteOldData(String name)
{
    try {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                " DELETE FROM tweets WHERE name ='"+name+"'"
        );
return true;
    }catch(Exception e)
    {
        return false;

    }
}

//        public ArrayList<String[]> getAllDua() {
//            try
//            {
//                ArrayList<String[]> array_list = new ArrayList();
//
//                //hp = new HashMap();
//                SQLiteDatabase db = this.getReadableDatabase();
//                @SuppressLint("Recycle") Cursor res =  db.rawQuery( "select * from duatable", null );
//                res.moveToFirst();
//                String[] dua1=new String[res.getCount()];
//                String[] dua2=new String[res.getCount()];
//                int i=0;
//
//                while(res.isAfterLast() == false){
//
//                    dua1[i]=res.getString(res.getColumnIndex(Dua_COLUMN_TOPIC));
//                    dua2[i]=res.getString(res.getColumnIndex(Dua_COLUMN_DUA));
//                    res.moveToNext();
//                }
//                array_list.add(dua1);
//                array_list.add(dua2);
//                return array_list;
//            } catch(Exception e)
//            {
//                return null;
//            }
//
//
//        }
//        public ArrayList getAllDuaList() {
//
//            try
//            {
//                ArrayList<String> array_list = new ArrayList();
//
//                //hp = new HashMap();
//                SQLiteDatabase db = this.getReadableDatabase();
//                @SuppressLint("Recycle") Cursor res =  db.rawQuery( "select * from duatable", null );
//                res.moveToFirst();
//
//                int i=0;
//
//                while(res.isAfterLast() == false){
//
//                    array_list.add(res.getString(res.getColumnIndex(Dua_COLUMN_TOPIC)));
//
//                    res.moveToNext();
//                }
//
//                return array_list;
//            }catch(Exception e)
//            {
//                return null;
//            }
//
//        }
    }


