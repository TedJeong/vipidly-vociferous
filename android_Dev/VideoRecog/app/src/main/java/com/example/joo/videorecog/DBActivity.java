package com.example.joo.videorecog;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by Joo on 2015-11-10.
 */
// DataBase Table
class DataBases {

    public static final class Profile implements BaseColumns {
        public static final String _ID = "_id";
        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String PHONE = "phone";
        public static final String PHOTO = "photo";
        public static final String TABLENAME = "facebook";
        public static final String _CREATE =
                "CREATE TABLE IF NOT EXISTS "+TABLENAME+"("
                        +_ID+" integer primary key autoincrement"
                        +NAME+", text not null"
                        +AGE+", text not null"
                        +PHONE+", text not null"
                        +PHOTO+", text not null);";
    }
}

class DbOpenHelper {

    private static final String DATABASE_NAME = "facebook.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        // 생성자
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 최초 DB를 만들때 한번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBases.Profile._CREATE);
        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DataBases.Profile.TABLENAME);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context) {
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx,DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDB.close();
    }


    public long createBook(String name, String age, String phone, String photo) {
// 레코드 생성
        ContentValues initialValues = new ContentValues();
        initialValues.put(DataBases.Profile.NAME, name);
        initialValues.put(DataBases.Profile.AGE, age);
        initialValues.put(DataBases.Profile.PHONE, phone);
        initialValues.put(DataBases.Profile.PHOTO, photo);

        return mDB.insert(DataBases.Profile.TABLENAME, null, initialValues);
    }

    public boolean deleteBook(long rowID) {
// 레코드 삭제
        return mDB.delete(DataBases.Profile.TABLENAME, DataBases.Profile._ID + "=" + rowID, null) > 0;
    }

    public Cursor fetchAllBooks() {
// 모든 레코드 반환
        return mDB.query(DataBases.Profile.TABLENAME
                , new String[]{DataBases.Profile._ID, DataBases.Profile.NAME, DataBases.Profile.AGE}
                , null, null, null, null, null);
    }

    public Cursor fetchBook(long rowID) throws SQLException {
// 특정 레코드 반환
        Cursor mCursor =
                mDB.query(true, DataBases.Profile.TABLENAME
                        , new String[]{DataBases.Profile._ID, DataBases.Profile.NAME, DataBases.Profile.AGE}
                        , DataBases.Profile._ID + "=" + rowID, null, null, null, null, null);
        if (mCursor != null)
            mCursor.moveToFirst();
        return mCursor;
    }

    public boolean updateBook(long rowID, String name, String phone) {
// 레코드 수정
        ContentValues args = new ContentValues();
        args.put(DataBases.Profile.NAME, name);
        args.put(DataBases.Profile.AGE, phone);

        return mDB.update(DataBases.Profile.TABLENAME, args, DataBases.Profile._ID + "=" + rowID, null) > 0;

    }
}
    public class DBActivity extends Activity {
        private static final String TAG = "DBActivity";
        private DbOpenHelper mDbOpenHelper;
        private Cursor mCursor;

        // mine
        EditText editText1,editText2;
        EditText[] editTexts3;
        EditText editText4;
        TextView textView;
        ListView listView;

        String databasename;
        String tablename;
        SQLiteDatabase database;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.image_db);

            editText1 = (EditText) findViewById(R.id.editText1);
            editText2 = (EditText) findViewById(R.id.editText2);

            editTexts3 = new  EditText[]{
                    (EditText)findViewById(R.id.editText3_0),
                    (EditText)findViewById(R.id.editText3_1),
                    (EditText)findViewById(R.id.editText3_2)};

            editText4 = (EditText) findViewById(R.id.editText4);
            textView = (TextView) findViewById(R.id.textView);
            listView = (ListView) findViewById(R.id.facebook);
            // DB Create and Open
            mDbOpenHelper = new DbOpenHelper(this);
            mDbOpenHelper.open();

            mDbOpenHelper.createBook("김태희", "31", "010-3333-1234","null");

            //ListAdapter listAdapter = new ArrayAdapter<String>(this,android.R.layout.);

//        startManagingCursor(mCursor);

        }// end onCreate


        public void DB_UploadButton1(View view){
            databasename = editText1.getText().toString();
            try {
                database = openOrCreateDatabase(databasename,Context.MODE_PRIVATE,null);
                println("data base is open with name : "+databasename);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        public void DB_UploadButton2(View view){
            tablename = editText2.getText().toString();
            try {
                if(database != null){
                    database.execSQL("CREATE TABLE if not exists " + tablename + "("
                                        +"_id integer PRIMARY KEY autoincrement"
                                        +",name text"
                                        +",age text"
                                        +",phone text"
                                        +")");
                    println("Table is created : " + tablename);
                }else{
                    println("First Create Database. Click first button");
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }

/*
        public void DB_UploadButton3(View view){
            println("Button 3 is clicked.");
            try {
                if(tablename == null)
                    println("First Create table name. Click second button");
                if(database != null){
                    database.execSQL("INSERT INTO " + tablename + "(name,age,phone) VALUES "
                            + "('joo',25,'010-3602-6399')");
                    println("Data is added : " + databasename);
                }else{
                    println("First Create Database. Click first button");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
*/
        public void DB_UploadButton3(View view) {
// 레코드 생성
            String tmp1,tmp2,tmp3;
            tmp1 = editTexts3[0].getText().toString();
            tmp2 = editTexts3[1].getText().toString();
            tmp3 = editTexts3[2].getText().toString();
            println("Button 3 is clicked.");
            try {
                if(tablename == null)
                    println("First Create table name. Click second button");
                if(database != null){
                    database.execSQL("INSERT INTO " + tablename + "(name,age,phone) VALUES "
                            + "('"+tmp1+"','"+tmp2+"','"+tmp3+"')");
                    println("Data is added : " + databasename);
                }else{
                    println("First Create Database. Click first button");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        public void DB_UploadButton4(View view){

            try {
                if(tablename == null)
                    tablename = editText4.getText().toString();
                if(database != null){
                    Cursor cursor_mine = database.rawQuery("SELECT name, age, phone FROM " + tablename,null);
                    int count = cursor_mine.getCount();
                    println("the # of record : "+count);
                    println("Data is searched at " + databasename);

                    for(int i=0;i<count;i++){
                        cursor_mine.moveToNext();
                        String name = cursor_mine.getString(0);
                        String age = cursor_mine.getString(1);
                        String phone = cursor_mine.getString(2);
                        println("Record # " + i + ":" + name +","+age+","+phone);
                    }
                    cursor_mine.close();
                }else{
                    println("First Create Database. Click first button");
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
        public void println(String data){
            textView.append(data+"\n");
        }
        @Override
        protected void onDestroy() {
            mDbOpenHelper.close();
            super.onDestroy();
        }
    }// end  DBActivity


