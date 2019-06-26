package com.example.moataz.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;

public class MyHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="my_database";
    private static final String TABLE_NAME="notes";
    private static final int VIRSION=1;
    private static final String KEY_NAME="note";
    private static final String KEY_DATE="date";
    private static final String KEY_ID="id";
    public MyHelper( Context context) {
        super(context, DATABASE_NAME, null, VIRSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY , "+KEY_NAME+" VARCHAR(30) , "+KEY_DATE+" VARCHAR(30));";
db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    // to add the item in database
    public void add(Item item)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
               values.put(KEY_NAME,item.getNote());
               values.put(KEY_DATE,item.getDate());
               db.insert(TABLE_NAME,null,values);

    }
    //to get all the item from data base
    public ArrayList<Item> getData()
    {
        ArrayList<Item>arrayList=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        String selectQuery="SELECT * FROM "+TABLE_NAME+"";
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do {
                String note=cursor.getString(cursor.getColumnIndex(KEY_NAME));
                String date=cursor.getString(cursor.getColumnIndex(KEY_DATE));
                int id=cursor.getInt(cursor.getColumnIndex(KEY_ID));
                Item item =new Item(note,date,id);
                arrayList.add(item);


            }while (cursor.moveToNext());

        }
        return arrayList;
    }
public Item getDataById(int id)
{
    SQLiteDatabase db=getReadableDatabase();
    String select_query="SELECT * FROM "+TABLE_NAME+" WHERE ID="+id+"";
    Item item=null;
    Cursor cursor=db.rawQuery(select_query,null);
    if(cursor.moveToFirst())
    {
        int itemId=cursor.getInt(cursor.getColumnIndex(KEY_ID));
        String note=cursor.getString(cursor.getColumnIndex(KEY_NAME));
        String date=cursor.getString(cursor.getColumnIndex(KEY_DATE));
        item=new Item(note,date,itemId);
        }
        return item;

}
public void update(Item item)
{
    SQLiteDatabase db=getWritableDatabase();
    int itemId=item.getId();
    String note=item.getNote();
    String date=item.getDate();
  ContentValues contentValues=new ContentValues();
  contentValues.put(KEY_ID,itemId);
  contentValues.put(KEY_NAME,note);
  contentValues.put(KEY_DATE,date);
  db.update(TABLE_NAME,contentValues,"id=?",new String[]{String.valueOf(itemId)});


}
public void delete(int id)
{
    SQLiteDatabase db=getWritableDatabase();
    db.delete(TABLE_NAME,"id=?",new String[]{String.valueOf(id)});
}

}
