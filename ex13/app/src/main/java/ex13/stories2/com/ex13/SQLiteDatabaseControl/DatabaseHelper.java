package ex13.stories2.com.ex13.SQLiteDatabaseControl;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by stories2 on 2016. 11. 25..
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    String logCatTag = "ex13", dbPath;
    Context context;

    public DatabaseHelper(Context context, String dbName) {
        super(context, dbName, null, 1);

        this.context = context;
        this.dbPath = this.context.getApplicationInfo().dataDir + "/databases/";
        try {
            if(!IsTargetFileAlreadyExist(dbPath, dbName)) {
                CopyTargetFileToSomethingPath(dbPath, dbName);
            }
            else {
                Log.d(logCatTag, "already Exist Target File: " + dbPath + dbName);
            }
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error: " + err.getMessage());
        }
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public String GetDatabaseCopiedPath() {
        return dbPath;
    }

    public void CopyTargetFileToSomethingPath(String copyPathDirection, String targetFileName) {
        try {
            MakeTargetPathFolder(this.context.getApplicationInfo().dataDir, "databases");
            InputStream fileInputStream = context.getAssets().open(targetFileName);
            OutputStream fileOutputStream = new FileOutputStream(copyPathDirection + targetFileName);
            byte[] fileCopyBufferMemory = new byte[1];
            int fileBufferLength, fileSizeCounter = 0;
            while((fileBufferLength = fileInputStream.read(fileCopyBufferMemory)) > 0) {
                fileOutputStream.write(fileCopyBufferMemory, 0, fileBufferLength);
                fileSizeCounter += fileBufferLength;
            }
            fileOutputStream.flush();
            fileInputStream.close();
            fileOutputStream.close();
            Log.d(logCatTag, "CopyFileProcess End! Copied Size: " + fileSizeCounter);
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in CopyTargetFileToSomethingPath: " + err.getMessage());
        }
    }

    public void MakeTargetPathFolder(String targetPath, String targetFolderName) {
        try {
            File targetFile = new File(targetPath + "/" + targetFolderName + "/");
            if(!targetFile.exists()) {
                targetFile.mkdirs();
                Log.d(logCatTag, "Target Folder Created!");
            }
            else {
                Log.d(logCatTag, "Already Exist Folder!");
            }
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in MakeTargetPathFolder: " + err.getMessage());
        }
    }

    public boolean IsTargetFileAlreadyExist(String targetFileSavedPath, String targetFileSavedName) {
        try {
            File somethingYouWantToKnowThisFile = new File(targetFileSavedPath + targetFileSavedName);
            return somethingYouWantToKnowThisFile.exists();
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in IsTargetFileAlreadyExist: " + err.getMessage());
        }
        return false;
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
        super.close();
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
