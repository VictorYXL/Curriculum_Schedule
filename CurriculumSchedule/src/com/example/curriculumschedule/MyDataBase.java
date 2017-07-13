package com.example.curriculumschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//���ݿ⸨����
class DBHelper extends SQLiteOpenHelper {  
  
    private static final String DATABASE_NAME = "ClassDB.db";//���ݿ�����  
    private static final int DATABASE_VERSION = 1;  //���ݿ�汾
      
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
				"Classroom Text)");//�����±�
    }  
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
        db.execSQL("ALTER TABLE person ADD COLUMN other STRING");//����ʹ��  
    }  
}
//���ݿ�ӿ�
class MyDataBase
{
    private DBHelper helper;  
    int id;
    //private  SQLiteDatabase myDB;
    SQLiteDatabase myDB;//�������ݿ�
    public MyDataBase(Context context)
    {
    	//����������ݿ��ʼ��
        helper = new DBHelper(context);     
        myDB = helper.getWritableDatabase();  
    }
    public void AddAll(CurSch curSch)
    {
    	//д����������
    	this.Clear();//���֮ǰ�ļ�¼
    	id=0;
		ContentValues v =null;
		Course course=null;
		//�ӿ��Ա�����
		for (int day=0;day<=7;day++)
		{
			int index=0;
			//һ��������ֱ������null
			while ((course=curSch.GetCourse(day, index))!=null)
			{
				v=new ContentValues();//��������
				//д���������
				v.put("ID ",id);
				v.put("Day ",day);
				v.put("Teacher ",curSch.GetCourse(day, index).teacher);
				v.put("Time ",curSch.GetCourse(day, index).time);
				v.put("Name ",curSch.GetCourse(day, index).name);
				v.put("Classroom ",curSch.GetCourse(day, index).classroom);
				//�������ݿ�
				myDB.insert("ClassTable", null, v);
				id++;
				index++;
			}	    		
		}
    }
    public void Clear()
    {
    	//delect from
    	//���ݿ����
    	id=0;
    	myDB.delete("ClassTable", "Day>=0 and Day<=7", null);
    }
    //������������
    public void ReadAll(CurSch curSch)
    {
    	//��������д��Cursor��
    	Cursor c = myDB.rawQuery("SELECT * FROM ClassTable", null);
    	curSch.Clear();//�γ̿��Ա�����
    	c.getCount();
        while (c.moveToNext()) { //������һ�� 
            int day;
            String teacher,time,name,classroom;//������������
            day = c.getInt(c.getColumnIndex("Day"));  
            teacher = c.getString(c.getColumnIndex("Teacher"));
            time = c.getString(c.getColumnIndex("Time"));
            name = c.getString(c.getColumnIndex("Name"));  
            classroom = c.getString(c.getColumnIndex("Classroom"));  
            //�����γ̻��Բ������
            Course course = new Course(name,teacher,time,classroom);
            curSch.Add(day, course);
        }  
        //�ر�Cursor
        c.close(); 
    }
}