package com.example.buddy.expandablelistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

/**
 * Created by Buddy on 7/17/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME="sqlitedemo.db";
    private static String TABLE_NAME="Student";
    private static String COL_1 = "stu_id";
    private static String COL_2 = "stu_name";
    private static String COL_3 = "stu_address";
    private static String COL_4 = "stu_phno";

    TextView name;
    TextView address;
    TextView phone;

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, 1);
        //SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query="create table "+TABLE_NAME+"("+ COL_1 +" integer primary key autoincrement,"+ COL_2 + " text,"+ COL_3 +" text,"+ COL_4 + " integer);";
        db.execSQL(query);
        //insertData();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query1="drop table if exists "+TABLE_NAME;
        db.execSQL(query1);
        onCreate(db);

    }
    public void insertData(GetDataAdapter getDataAdapter){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,getDataAdapter.getName());
        contentValues.put(COL_3,getDataAdapter.getAddress());
        contentValues.put(COL_4,getDataAdapter.getPhone());
        db.insert(TABLE_NAME,null,contentValues);

    }



    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " +TABLE_NAME,null);
//        while(res.moveToNext()){
//
//            name.setText(res.getString(1));
//            address=(TextView)findViewById(R.id.textView3);
//            address.setText(res.getString(2));
//            phone=(TextView)findViewById(R.id.textView4);
//            phone.setText(res.getString(3));




        return res;


    }
}
