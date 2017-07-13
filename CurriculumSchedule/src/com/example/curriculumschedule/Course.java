package com.example.curriculumschedule;

import java.util.Vector;
//�����γ̻��߿���
class Course
{
	String name;
	String teacher;//�ڿ��Ա��е�������ʹ��
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

//һ��Ŀγ̻�����������
class OneDaySch
{
	Vector<Course>course;
	public OneDaySch()
	{
		course=new Vector<Course>();
	}
}
//�����γ̱�Ϳ��Ա�
class CurSch
{
	OneDaySch []couList;
	MyDataBase myDB;//���ݿ����
	public CurSch(MainActivity main)
	{
		couList=new OneDaySch[8];
		for (int i=0;i<8;i++)
			couList[i]=new OneDaySch();
		myDB=new MyDataBase(main);
		this.GetFromDB();//��������
		
	}
	//������д�����ݿ�
	public void WriteToDB()
	{
		myDB.AddAll(this);
	}
	//�����ݴ����ݿ����
	public void GetFromDB()
	{
		myDB.ReadAll(this);
	}
	//���������
	public void Clear()
	{
		for (int i=0;i<8;i++)
			couList[i].course.removeAllElements();
	}
	//��Day����ӿγ�course��Day=0��ʾ����
	public void Add(int Day,Course course)
	{
		couList[Day].course.add(course);
	}
	//�滻Day��ĵ�index�ÿ�Ϊcourse
	public void Change(int Day,int index,Course course)
	{
		couList[Day].course.setElementAt(course, index);
	}
	//ɾ����Day��ĵ�index�ÿ�
	public void Delete(int Day,int index)
	{
		couList[Day].course.remove(index);
	}
	
	//��ȡ��Day��Ŀγ���
	public int GetNum(int Day)
	{
		return couList[Day].course.size();
	}
	//��ȡDay���index�ÿΣ�û���򷵻�null
	public Course GetCourse(int Day,int index)
	{
		if (index<couList[Day].course.size())
			return couList[Day].course.elementAt(index);
		else return null;
	}
}