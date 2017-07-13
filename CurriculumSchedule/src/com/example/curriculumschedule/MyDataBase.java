package com.example.curriculumschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//数据库辅助类
class DBHelper extends SQLiteOpenHelper {  
  
    private static final String DATABASE_NAME = "ClassDB.db";//数据库名称  
    private static final int DATABASE_VERSION = 1;  //数据库版本
      
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
				"Classroom Text)");//创建新表
    }  
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
        db.execSQL("ALTER TABLE person ADD COLUMN other STRING");//升级使用  
    }  
}
//数据库接口
class MyDataBase
{
    private DBHelper helper;  
    int id;
    //private  SQLiteDatabase myDB;
    SQLiteDatabase myDB;//定义数据库
    public MyDataBase(Context context)
    {
    	//辅助类和数据库初始化
        helper = new DBHelper(context);     
        myDB = helper.getWritableDatabase();  
    }
    public void AddAll(CurSch curSch)
    {
    	//写入所有数据
    	this.Clear();//清除之前的记录
    	id=0;
		ContentValues v =null;
		Course course=null;
		//从考试表到周六
		for (int day=0;day<=7;day++)
		{
			int index=0;
			//一个个读，直到读出null
			while ((course=curSch.GetCourse(day, index))!=null)
			{
				v=new ContentValues();//创建键对
				//写入各项数据
				v.put("ID ",id);
				v.put("Day ",day);
				v.put("Teacher ",curSch.GetCourse(day, index).teacher);
				v.put("Time ",curSch.GetCourse(day, index).time);
				v.put("Name ",curSch.GetCourse(day, index).name);
				v.put("Classroom ",curSch.GetCourse(day, index).classroom);
				//插入数据库
				myDB.insert("ClassTable", null, v);
				id++;
				index++;
			}	    		
		}
    }
    public void Clear()
    {
    	//delect from
    	//数据库归零
    	id=0;
    	myDB.delete("ClassTable", "Day>=0 and Day<=7", null);
    }
    //读出所有数据
    public void ReadAll(CurSch curSch)
    {
    	//所有数据写入Cursor中
    	Cursor c = myDB.rawQuery("SELECT * FROM ClassTable", null);
    	curSch.Clear();//课程考试表清零
    	c.getCount();
        while (c.moveToNext()) { //移向下一个 
            int day;
            String teacher,time,name,classroom;//读出各种数据
            day = c.getInt(c.getColumnIndex("Day"));  
            teacher = c.getString(c.getColumnIndex("Teacher"));
            time = c.getString(c.getColumnIndex("Time"));
            name = c.getString(c.getColumnIndex("Name"));  
            classroom = c.getString(c.getColumnIndex("Classroom"));  
            //创建课程或考试并加入表
            Course course = new Course(name,teacher,time,classroom);
            curSch.Add(day, course);
        }  
        //关闭Cursor
        c.close(); 
    }
}