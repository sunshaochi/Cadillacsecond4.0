package com.ist.cadillacpaltform.SDK.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.ist.cadillacpaltform.SDK.bean.Authorization;

import java.io.File;

/**
 * Created by dearlhd on 2016/12/28.
 * 目前仅用于保存用户信息
 */
public class SQLiteHelper {
    private final String PATH = android.os.Environment.getExternalStorageDirectory() + "/cadillac/";
    private final String DB_NAME = "cadillac.db";

    private final String[] ATTRS = {"authorization", "account", "type", "userId"};

    private SQLiteDatabase mDatabase;

    public SQLiteHelper () {
        File dir = new File(PATH);
        if (!dir.exists()) {//不存在创建
            dir.mkdir();
        }
    }

    public void setAuth (Authorization auth) throws Exception {
        final boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
//        final String databaseFilename = sdCardExist ? (PATH + DB_NAME) : mContext.getFileStreamPath(DB_NAME).getAbsolutePath();

        if (!sdCardExist) {
            throw new Exception("No External Storage!");
        }

        final String databaseFilename = PATH + DB_NAME;

        mDatabase = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);

        synchronized (SQLiteHelper.class) {
            if (mDatabase != null) {
                String dropQuery = "DROP TABLE IF EXISTS auth";
                mDatabase.execSQL(dropQuery);

                if (auth != null) {
                    String createQuery = "CREATE TABLE IF NOT EXISTS auth ("
                            + ATTRS[0] + " text,"
                            + ATTRS[1] + " text,"
                            + ATTRS[2] + " integer,"
                            + ATTRS[3] + " integer,"
                            + "PRIMARY KEY (" + ATTRS[3] + ")"
                            + ");";
                    mDatabase.execSQL(createQuery);

                    String insertQuery = "INSERT INTO auth ("
                            + ATTRS[0] + ","
                            + ATTRS[1] + ","
                            + ATTRS[2] + ","
                            + ATTRS[3]
                            + ") VALUES(?, ?, ?, ?)";
                    mDatabase.execSQL(insertQuery, new Object[]{auth.authorization, auth.account, auth.type, auth.userId});
                }
            }
        }
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public Authorization getAuth () {
//        final boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
//        final String databaseFilename = sdCardExist ? (PATH + DB_NAME) : mContext.getFileStreamPath(DB_NAME).getAbsolutePath();
        final String databaseFilename = PATH + DB_NAME;

        mDatabase = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);

        String createQuery = "CREATE TABLE IF NOT EXISTS auth ("
                + ATTRS[0] + " text,"
                + ATTRS[1] + " text,"
                + ATTRS[2] + " integer,"
                + ATTRS[3] + " integer,"
                + "PRIMARY KEY (" + ATTRS[3] + ")"
                + ");";
        mDatabase.execSQL(createQuery);

        String query = "SELECT * FROM auth";
        Cursor cursor = mDatabase.rawQuery(query, null);

        Authorization auth = null;

        while (cursor.moveToNext()) {
            auth = new Authorization();
            auth.authorization = cursor.getString(cursor.getColumnIndex(ATTRS[0]));
            auth.account = cursor.getString(cursor.getColumnIndex(ATTRS[1]));
            auth.type = cursor.getInt(cursor.getColumnIndex(ATTRS[2]));
            auth.userId = cursor.getLong(cursor.getColumnIndex(ATTRS[3]));
        }

        if (mDatabase != null) {
            mDatabase.close();
        }

        return auth;
    }
}
