package com.example.curriculumschedule3;

import java.util.Vector;

class Course
{
	String name;
	String teacher;
	String classroom;
	String time;
	
	public Course(String name,String teacher,String classroom,String time)
	{
		this.name=name;
		this.teacher=teacher;
		this.classroom=classroom;
		this.time=time;
	}
}

class OneDaySch
{
	Vector<Course>course;
	public OneDaySch()
	{
		course=new Vector<Course>();
	}
}
class CurSch
{
	OneDaySch []couList;
	MyDataBase myDB;
	public CurSch(MainActivity main)
	{
		couList=new OneDaySch[8];
		for (int i=0;i<8;i++)
			couList[i]=new OneDaySch();
		myDB=new MyDataBase(main);
		
	}
	public void WriteToDB()
	{
		myDB.AddAll(this);
	}
	public void GetFromDB()
	{
		myDB.ReadAll(this);
	}
	public void Clear()
	{
		for (int i=0;i<8;i++)
			couList[i].course.removeAllElements();
	}
	public void Add(int Day,Course course)
	{
		couList[Day].course.add(course);
	}
	public void Change(int Day,int index,Course course)
	{
		couList[Day].course.setElementAt(course, index);
	}
	public void Delete(int Day,int index)
	{
		couList[Day].course.remove(index);
	}
	
	public int GetNum(int Day)
	{
		return couList[Day].course.size();
	}
	
	public Course GetCourse(int Day,int index)
	{
		if (index<couList[Day].course.size())
			return couList[Day].course.elementAt(index);
		else return null;
	}
}