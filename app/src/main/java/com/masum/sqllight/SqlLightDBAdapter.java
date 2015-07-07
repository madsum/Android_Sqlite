package com.masum.sqllight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 1. Write a class that extends SQLiteOpenHelper. Good to keep it private
 * i.e. private static class SqlLightDBHelper extends SQLiteOpenHelper
 *
 * 2. There must be missing implementation on onCreate and onUpdate and super class constructor
 *
 * 3. I can wrap private  class SqlLightDBHelper by another public class to access it's function.
 * This public class should provide DB CURD function
 *
 * 4. see example code below
 */

public class SqlLightDBAdapter {

    private SQLiteOpenHelper sqLiteOpenHelper;
    private Context context;

    SqlLightDBAdapter(Context context) {
        this.context = context;
        sqLiteOpenHelper = new SqlLightDBHelper(context);
    }


    public long insertData(String name, String password, String address) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SqlLightDBHelper.NAME, name);
        contentValues.put(SqlLightDBHelper.PASSWORD, password);
        contentValues.put(SqlLightDBHelper.ADDRESS, address);
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        return sqLiteDatabase.insert(SqlLightDBHelper.TABLE_NAME, null, contentValues);
    }

    public String getAllData() {
        String[] columns = {SqlLightDBHelper.UID, SqlLightDBHelper.NAME, SqlLightDBHelper.PASSWORD, SqlLightDBHelper.ADDRESS};
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(SqlLightDBHelper.TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int index1 = cursor.getColumnIndex(SqlLightDBHelper.UID);
            int cid = cursor.getInt(index1);
            int index2 = cursor.getColumnIndex(SqlLightDBHelper.NAME);
            String name = cursor.getString(index2);
            int index3 = cursor.getColumnIndex(SqlLightDBHelper.PASSWORD);
            String pass = cursor.getString(index3);
            int index4 = cursor.getColumnIndex(SqlLightDBHelper.ADDRESS);
            String address = cursor.getString(index4);
            stringBuffer.append(cid + " " + name + " " + pass + " " + address + "\n");
        }
        return stringBuffer.toString();
    }

    public String getPassword(String name) {
        SQLiteDatabase DB = sqLiteOpenHelper.getWritableDatabase();
        String[] columns = {SqlLightDBHelper.PASSWORD};
        String[] selectionArgs = {name};
        Cursor cursor = DB.query(SqlLightDBHelper.TABLE_NAME, columns, SqlLightDBHelper.NAME + " = ? ", selectionArgs, null, null, null);
        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(SqlLightDBHelper.PASSWORD);
            String pass = cursor.getString(index);
            stringBuffer.append(pass + "\n");
        }
        return stringBuffer.toString();
    }

    public String getName(String address, String pass) {
        SQLiteDatabase DB = sqLiteOpenHelper.getWritableDatabase();
        // only mention those columns name whose data we want to get.
        String[] columns = {SqlLightDBHelper.NAME};
        // those columns name in the condition as ?
        String[] selectionArgs = {address, pass};
        Cursor cursor = DB.query(SqlLightDBHelper.TABLE_NAME, columns, SqlLightDBHelper.ADDRESS + " = ?  AND " + SqlLightDBHelper.PASSWORD + " = ? ", selectionArgs, null, null, null);
        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(SqlLightDBHelper.NAME);
            String name = cursor.getString(index);
            stringBuffer.append(name + "\n");
        }
        return stringBuffer.toString();
    }

    public int deleteRowByID(String rowID){

        SQLiteDatabase DB = sqLiteOpenHelper.getWritableDatabase();
        int ret = DB.delete(SqlLightDBHelper.TABLE_NAME, SqlLightDBHelper.UID+"="+rowID, null);
        Message.ShowMessage(context, "row deleted: "+ret);
        return  ret;
    }

    private static class SqlLightDBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "mysqldb.db";
        private static final String TABLE_NAME = "DataTable";
        private static final int DATABASE_VERSION = 5;
        private static final String UID = "_id";
        public static final String NAME = "name";
        private static final String ADDRESS = "address";
        private static final String PASSWORD = "password";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( " + UID + " INTEGER PRIMARY  KEY AUTOINCREMENT, " +
                NAME + " VARCHAR(255), " + PASSWORD + " VARCHAR(255), " + ADDRESS + " VARCHAR(255));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public SqlLightDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(MainActivity.TAG, "DB onCreate called!");
            Message.ShowMessage(context, "DB onCreate called!");
            try {
                db.execSQL(CREATE_TABLE);
            } catch (SQLException ex) {
                Log.i(MainActivity.TAG, ex.getMessage());
                Message.ShowMessage(context, ex.getMessage());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(MainActivity.TAG, "DB onUpgrade called!");
            Message.ShowMessage(context, "DB onUpgrade called!");
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (SQLException ex) {
                Log.i(MainActivity.TAG, ex.getMessage());
                Message.ShowMessage(context, ex.getMessage());
            }
        }
    }
}
