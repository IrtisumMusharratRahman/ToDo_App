package com.example.android.todo.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.android.todo.Model.ToDoModel;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DB_NAME = "ToDoDatabase";
    public static final String TABLE_NAME = "todo";
    public static final String ID = "id";
    public static final String TASK = "task";
    public static final String STATUS = "status";

    private SQLiteDatabase db;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + STATUS + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void openDb() {
        db = this.getReadableDatabase();
    }

    public void insertTask(ToDoModel toDoModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK,toDoModel.getTask());
        contentValues.put(STATUS,0);
        db.insert(TABLE_NAME,null,contentValues);
    }

    @SuppressLint("Range")
    public ArrayList<ToDoModel>  getAllTask(){
        ArrayList<ToDoModel> list = new ArrayList<>();
        Cursor cursor;
        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME,null,null,null,null,null,null,null);
            if (cursor!= null){
                ToDoModel toDoModel = new ToDoModel();
                while (cursor.moveToNext()){
                    toDoModel.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                    toDoModel.setTask(cursor.getString(cursor.getColumnIndex(TASK)));
                    toDoModel.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                    list.add(toDoModel);
                }
            }
        }finally {
            db.endTransaction();
        }


        cursor.close();
        return list;
    }


    public void updateTask(int id, String task){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK,task);
        db.update(TABLE_NAME,contentValues, ID + "=?", new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id, int status){
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATUS,status);
        db.update(TABLE_NAME,contentValues,ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TABLE_NAME,ID + "=? ", new String[]{String.valueOf(id)});
    }
}
