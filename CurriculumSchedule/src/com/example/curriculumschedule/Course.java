package com.example.curriculumschedule;

import java.util.Vector;
//单个课程或者考试
class Course
{
	String name;
	String teacher;//在考试表中当作日期使用
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

//一天的课程或者整个考试
class OneDaySch
{
	Vector<Course>course;
	public OneDaySch()
	{
		course=new Vector<Course>();
	}
}
//整个课程表和考试表
class CurSch
{
	OneDaySch []couList;
	MyDataBase myDB;//数据库对象
	public CurSch(MainActivity main)
	{
		couList=new OneDaySch[8];
		for (int i=0;i<8;i++)
			couList[i]=new OneDaySch();
		myDB=new MyDataBase(main);
		this.GetFromDB();//读入数据
		
	}
	//将数据写入数据库
	public void WriteToDB()
	{
		myDB.AddAll(this);
	}
	//将数据从数据库读出
	public void GetFromDB()
	{
		myDB.ReadAll(this);
	}
	//清除整个表
	public void Clear()
	{
		for (int i=0;i<8;i++)
			couList[i].course.removeAllElements();
	}
	//在Day天添加课程course，Day=0表示考试
	public void Add(int Day,Course course)
	{
		couList[Day].course.add(course);
	}
	//替换Day天的第index堂课为course
	public void Change(int Day,int index,Course course)
	{
		couList[Day].course.setElementAt(course, index);
	}
	//删除第Day天的第index堂课
	public void Delete(int Day,int index)
	{
		couList[Day].course.remove(index);
	}
	
	//获取第Day天的课程数
	public int GetNum(int Day)
	{
		return couList[Day].course.size();
	}
	//获取Day天的index堂课，没有则返回null
	public Course GetCourse(int Day,int index)
	{
		if (index<couList[Day].course.size())
			return couList[Day].course.elementAt(index);
		else return null;
	}
}