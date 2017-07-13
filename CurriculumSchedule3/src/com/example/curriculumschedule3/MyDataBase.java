package com.example.curriculumschedule3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class / extends SQLiteOpenHelper {  
  
    private static final String DATABASE_NAME = "ClassDB.db";  
    private static final int DATABASE_VERSION = 1;  
      
    public DBHelper(Context context) {     
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }  
    
    @Override  
    public void onCreate(SQLiteDatabase db) {  
        db.execSQL("CREATE TABLE ClassTable " +
				"(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
				"Day INTEGER  NOT NULL," +
				"Teacher Text,"+
				"Time Text,"+
				"Name Text,"+
				"Classroom Text)");
    }  
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
        db.execSQL("ALTER TABLE person ADD COLUMN other STRING");  
    }  
}  
class MyDataBase
{
    private DBHelper helper;  
    int id;
    //private  SQLiteDatabase myDB;
    SQLiteDatabase myDB;
    public MyDataBase(Context context)
    {
        helper = new DBHelper(context);     
        myDB = helper.getWritableDatabase();  
    }
    public void AddAll(CurSch curSch)
    {
    	this.Clear();
    	id=0;
		ContentValues v =null;
		Course course=null;
		for (int day=0;day<=7;day++)
		{
			int index=0;
			while ((course=curSch.GetCourse(day, index))!=null)
			{
				v=new ContentValues();
				v.put("ID ",id);
				v.put("Day ",day);
				v.put("Teacher ",curSch.GetCourse(day, index).teacher);
				v.put("Time ",curSch.GetCourse(day, index).time);
				v.put("Name ",curSch.GetCourse(day, index).name);
				v.put("Classroom ",curSch.GetCourse(day, index).classroom);
				myDB.insert("ClassTable", null, v);
				id++;
				index++;
			}	    		
		}
    }
    public void Clear()
    {
    	//delect from
    	id=0;
    	myDB.delete("ClassTable", "Day>=0 and Day<=7", null);
    }
    public void ReadAll(CurSch curSch)
    {
    	Cursor c = myDB.rawQuery("SELECT * FROM ClassTable", null);
    	curSch.Clear();
    	c.getCount();
        while (c.moveToNext()) {  
            int day;
            String teacher,time,name,classroom;
            day = c.getInt(c.getColumnIndex("Day"));  
            teacher = c.getString(c.getColumnIndex("Teacher"));
            time = c.getString(c.getColumnIndex("Time"));
            name = c.getString(c.getColumnIndex("Name"));  
            classroom = c.getString(c.getColumnIndex("Classroom"));  
            
            Course course = new Course(name,teacher,time,classroom);
            curSch.Add(day, course);
        }  
        c.close(); 
    }
}