package com.jalmaksa.bm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    //찜리스트 DB
    public static final String DATABASE_NAME = "BM.db";

    public static final String ZIMLIST_TABLE_NAME = "zimlist";
    public static final String ZIMLIST_COLUMN_ID = "id";
    public static final String ZIMLIST_COLUMN_DB_MARTNAME = "db_martname";
    public static final String ZIMLIST_COLUMN_DB_ITEM = "db_item";
    public static final String ZIMLIST_COLUMN_DB_PRICE = "db_price";
    public static final String[] array_seq = new String[50];
    public static final String[] array_price = new String[50];
    public static final String[] array_martname = new String[50];
    public static final String[] array_item = new String[50];

    // 개인정보 Table
    public static final String LOGIN_TABLE_NAME = "logInfo";
    public static final String LOGIN_COLUMN_ID = "id";
    public static final String LOGIN_COLUMN_NICKNAME = "nickname";
    public static String nickname = "";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table zimlist " +
                        "(id integer primary key,db_martname string, db_item string, db_price string)"
        );
        db.execSQL(
                "create table logInfo" +
                        "(id integer primary key,nickname stinrg)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS zimlist");
        onCreate(db);
    }

    public boolean insertZimlist(String db_martname, String db_item, String db_price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("db_martname", db_martname);
        contentValues.put("db_item", db_item);
        contentValues.put("db_price", db_price);

        db.insert("zimlist", null, contentValues);
        return true;
    }

    public boolean insertLogInfo(String nickname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nickname", nickname);
        db.insert("logInfo", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from zimlist where id=" + id + "", null);
        return res;
    }

    public Cursor getnickNameData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from logInfo where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, ZIMLIST_TABLE_NAME);
        return numRows;
    }

        public Integer deleteZimlist(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("zimlist",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }
    public ArrayList getZimlist() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from zimlist", null);
        res.moveToFirst();

        int seq = 1;
        while (res.isAfterLast() == false) {
            array_list.add(seq +". "+   // 목록 순서 번호 매기기
                    res.getString(res.getColumnIndex(ZIMLIST_COLUMN_DB_ITEM))+ " \n"+
                    res.getString(res.getColumnIndex(ZIMLIST_COLUMN_DB_MARTNAME))+" || "+
                    res.getString(res.getColumnIndex(ZIMLIST_COLUMN_DB_PRICE)));

            array_seq[seq] = res.getString(res.getColumnIndex(ZIMLIST_COLUMN_ID));  // 배열에 PK값 넣기
            seq++;

            res.moveToNext();
        }
        return array_list;
    }

    public String[] getArray_seq() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from zimlist", null);
        res.moveToFirst();
        int seq = 1;
        while (res.isAfterLast() == false) {
            array_seq[seq] = res.getString(res.getColumnIndex(ZIMLIST_COLUMN_ID));
            seq++;

            res.moveToNext();
        }
        return array_seq;
    }

    public String getNickname() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from logInfo", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            nickname = res.getString(res.getColumnIndex(LOGIN_COLUMN_NICKNAME));

            res.moveToNext();
        }
        return nickname;
    }

    public String[] getArray_price() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from zimlist", null);
        res.moveToFirst();
        int seq = 0;
        while (res.isAfterLast() == false) {
            array_price[seq] = res.getString(res.getColumnIndex(ZIMLIST_COLUMN_DB_PRICE));
            seq++;

            res.moveToNext();
        }
        return array_price;
    }

    public String[] getArray_martname() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from zimlist", null);
        res.moveToFirst();
        int seq = 0;
        while (res.isAfterLast() == false) {
            array_martname[seq] = res.getString(res.getColumnIndex(ZIMLIST_COLUMN_DB_MARTNAME));
            seq++;
            res.moveToNext();
        }
        return array_martname;
    }

    public String[] getArray_item() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from zimlist", null);
        res.moveToFirst();
        int seq = 0;
        while (res.isAfterLast() == false) {
            array_item[seq] = res.getString(res.getColumnIndex(ZIMLIST_COLUMN_DB_ITEM));
            seq++;
            res.moveToNext();
        }
        return array_item;
    }
}