package com.example.kangjisung.likeroom.SQLiteDatabaseControl;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kangjisung.likeroom.R;


/**
 * Created by stories2 on 2016. 11. 27..
 */

public class LocalHostDatabaseManager extends SQLiteOpenHelper {
    String savedDatabasePath, savedDatabaseName, logCatTag = "ex13";
    SQLiteDatabase sqLiteDatabase;
    Context context;

    public LocalHostDatabaseManager(Context context, String databaseSavedPath, String databaseName) {
        super(context, databaseName, null, 1);
        this.context = context;
        this.savedDatabasePath = databaseSavedPath;
        this.savedDatabaseName = databaseName;

        logCatTag = context.getString(R.string.app_name);
    }
    public LocalHostDatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public LocalHostDatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
    public SQLiteDatabase OpenSQLiteDatabase() {
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase(savedDatabasePath + savedDatabaseName, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in OpenSQLiteDatabase: " + err.getMessage());
        }
        return sqLiteDatabase;
    }
    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }
    @Override
    public void setWriteAheadLoggingEnabled(boolean enabled) {
        super.setWriteAheadLoggingEnabled(enabled);
    }
    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }
    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }
    @Override
    public synchronized void close() {
        if(sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
        super.close();
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}