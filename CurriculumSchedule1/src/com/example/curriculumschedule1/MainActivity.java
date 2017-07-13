package com.example.curriculumschedule1;

import java.util.*;
import android.os.Bundle;
import android.app.Activity;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

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
	public CurSch()
	{
		
		couList=new OneDaySch[7];
		for (int i=0;i<7;i++)
			couList[i]=new OneDaySch();
		this.GetCouFromDB();
	}
	public void GetCouFromDB()
	{
		this.AddCou(1, new Course("操作系统", "范训礼", "1308", "8:00~9:50 a.m."));
		this.AddCou(1, new Course("软件工程", "侯红", "3305", "10:00~11:50 a.m."));
		this.AddCou(2, new Course("毛社", "王青", "3J201", "8:00~9:50 a.m."));
		this.AddCou(3, new Course("日语", "T1", "1305", "10:00~11:50 a.m."));
		this.AddCou(3, new Course("日语", "T1", "1305", "10:00~11:50 a.m."));
		this.AddCou(2, new Course("软件工程", "红侯", "3305", "10:00~11:50 a.m."));
	}
	public void AddCou(int Day,Course course)
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
		return couList[Day].course.elementAt(index);
	}
}


public class MainActivity extends Activity {
	Button btn_Class;
	Button btn_Test;
	Spinner spn_Day;
	TableLayout tbl_Course;
	TableLayout tbl_Test;
	Boolean status;
	int day;
	CurSch curSch;
	
	private ArrayAdapter<String> adapter;

	void ShowClass()
	{
		tbl_Course.removeAllViews();
        tbl_Course.setStretchAllColumns(true);
        
        TableRow row1=new TableRow(this);
        TextView tv=new TextView(this);
        String []context={"   ","时间                 ","课程名    ","教师","教室"};
        for (int i=0;i<=4;i++)
        {
        	tv=new TextView(this);
        	tv.setText(context[i]);
        	row1.addView(tv);
        }
        tbl_Course.addView(row1);
        for (int j=0;j<curSch.GetNum(day);j++)
        {
        	Course course=curSch.GetCourse(day, j);
        	context[0]=String.valueOf(j+1);
        	context[1]=course.time;
        	context[2]=course.name;
        	context[3]=course.teacher;
        	context[4]=course.classroom;
        	row1=new TableRow(this);
        	for (int i=0;i<=4;i++)
            {
            	tv=new TextView(this);
            	tv.setText(context[i]);
            	row1.addView(tv);
            }
            tbl_Course.addView(row1);
        }
        row1=new TableRow(this);
        
        row1.addView(new TextView(this));
        row1.addView(new TextView(this));
        tv=new TextView(this);
        tv.setText("+添加课程");
        row1.addView(tv);
        row1.addView(new TextView(this));
        row1.addView(new TextView(this));
        tbl_Course.addView(row1);
	}
	
    class SpinnerSelectedListener implements OnItemSelectedListener
    {   
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
        {   
        	day=arg2;   
        	ShowClass();
        }   
  
        public void onNothingSelected(AdapterView<?> arg0) {   
        }   
    }   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        status=true;
        day=1;
        //获取控件
    	btn_Class=(Button) findViewById(R.id.btn_Class);
    	btn_Test=(Button) findViewById(R.id.btn_Test);
    	spn_Day=(Spinner)findViewById(R.id.spn_Day);
    	tbl_Course=(TableLayout)findViewById(R.id.tbl_Class);
    	tbl_Test=(TableLayout)findViewById(R.id.tbl_Test);
    	String []Days={"星期日","星期一","星期二","星期三","星期四","星期五","星期六",};
    	adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Days);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spn_Day.setAdapter(adapter);
    	spn_Day.setSelection(1);
    	curSch=new CurSch();
    	ShowClass();
    	
    	
    	//课程表监听
    	btn_Class.setOnClickListener(new Button.OnClickListener() 
    	{    	
			public void onClick(View v)
			{
				spn_Day.setVisibility(View.VISIBLE);
				tbl_Course.setVisibility(View.VISIBLE);
				tbl_Test.setVisibility(View.INVISIBLE);
				status=true;
				if (status)
					return;
				ShowClass();
			}
        });
    	//星期监听
    	spn_Day.setOnItemSelectedListener(new SpinnerSelectedListener()); 
    	
    	//考试表监听
    	btn_Test.setOnClickListener(new Button.OnClickListener() 
    	{    
    		
			public void onClick(View v)
			{
				spn_Day.setVisibility(View.INVISIBLE);
				tbl_Course.setVisibility(View.INVISIBLE);
				tbl_Test.setVisibility(View.VISIBLE);
				status=false;
			}
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
