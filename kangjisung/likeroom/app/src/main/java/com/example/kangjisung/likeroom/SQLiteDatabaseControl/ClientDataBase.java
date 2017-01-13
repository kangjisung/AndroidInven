package com.example.kangjisung.likeroom.SQLiteDatabaseControl;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


/**
 * Created by kangjisung on 2016-12-27.
 */

public class ClientDataBase{
    static DatabaseHelper databaseHelperTest;
    static LocalHostDatabaseManager localHostDatabaseManager;
    public static String testDatabaseName = "ShopkeeperDatabase.db";
    static SQLiteDatabase sqLiteDatabase;
    public static String DBstring[]=new String[30];
    Context context;
    int ii=0;

    /////////////////db가동
   public ClientDataBase(String SQL, int i,int k, Context context){ ////i=1이면 select문// i=2이면 insert문// i=3이면 update문  k=받아오는인자수
       this.context = context;
       //databaseHelperTest = new DatabaseHelper(this.context, testDatabaseName);
       localHostDatabaseManager = new LocalHostDatabaseManager(this.context, context.getApplicationInfo().dataDir + "/databases/", testDatabaseName);
       sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
       while(DBstring[ii]!=null) {
           DBstring[ii]=null;
           ii++;
       }
       if(i==1){
           Cursor c = sqLiteDatabase.rawQuery(SQL,null);
           int cnt=0;
           while(c.moveToNext()){
               for(int j=0;j<k;j++) {
                   DBstring[cnt++] = c.getString(j);
               }
           }
       }
       else if(i==2||i==3){
           sqLiteDatabase.execSQL(SQL);
       }

       sqLiteDatabase.close(); /////db 종료
    }


}