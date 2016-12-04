package ex13.stories2.com.ex13;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import ex13.stories2.com.ex13.SQLiteDatabaseControl.DatabaseHelper;
import ex13.stories2.com.ex13.SQLiteDatabaseControl.LocalHostDatabaseManager;

public class SQLiteManager extends Activity {

    DatabaseHelper databaseHelperTest;
    LocalHostDatabaseManager localHostDatabaseManager;
    String testDatabaseName = "AndroidTestDB.db";
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sqlite_manager);

        databaseHelperTest = new DatabaseHelper(getApplicationContext(), testDatabaseName);
        localHostDatabaseManager = new LocalHostDatabaseManager(getApplicationContext(), getApplicationInfo().dataDir + "/databases/", testDatabaseName);

        sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
        Cursor sqlResult = sqLiteDatabase.rawQuery("select * from HelloSQLiteTest;", null);
        while(sqlResult.moveToNext()) {
            int indexKey = sqlResult.getInt(0);
            String msg = sqlResult.getString(1);
            Log.d("ex13", "result: " + indexKey + " " + msg);
        }
        sqLiteDatabase.close();
    }
}
