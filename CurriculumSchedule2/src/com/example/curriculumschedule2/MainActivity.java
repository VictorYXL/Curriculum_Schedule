package com.example.curriculumschedule2;

import java.util.*;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.*;
import android.view.View.OnClickListener;
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
	//OneDaySch testList;
	public CurSch()
	{
		
		couList=new OneDaySch[8];
		//testList=new OneDaySch();
		for (int i=0;i<8;i++)
			couList[i]=new OneDaySch();
		this.GetCouFromDB();
		this.GetTestFromDB();
	}
	public void GetCouFromDB()
	{
		
		
		this.Add(1, new Course("����ϵͳ", "��ѵ��", "1308", "8:00~9:50 a.m."));
		this.Add(1, new Course("�������", "���", "3305", "10:00~11:50 a.m."));
		this.Add(2, new Course("ë��", "����", "3J201", "8:00~9:50 a.m."));
		this.Add(3, new Course("����", "T1", "1305", "10:00~11:50 a.m."));
		this.Add(3, new Course("����", "T1", "1305", "10:00~11:50 a.m."));
		this.Add(2, new Course("�������", "���", "3305", "10:00~11:50 a.m."));
	}
	public void WriteCouTODB()
	{
		
	}
	public void GetTestFromDB()
	{
		this.Add(0,new Course("3��5��","��ѵ��", "1308", "8:00~9:50 a.m."));
		this.Add(0,new Course("3��9��", "���", "3305", "10:00~11:50 a.m."));
		this.Add(0,new Course("4��1��", "����","3J201", "8:00~9:50 a.m."));
		this.Add(0,new Course("4��5��","T1",  "1305", "10:00~11:50 a.m."));
	}
	public void WriteTestTODB()
	{
		
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
		return couList[Day].course.elementAt(index);
	}
}

public class MainActivity extends Activity {
	Button btn_Class;
	Button btn_Test;
	Spinner spn_Day;
	TableLayout tbl_Course;
	TextView txt_Title;
	Boolean status;
	int day,Num;
	CurSch curSch;
	
	private ArrayAdapter<String> adapter;

	void Show()
	{
		tbl_Course.removeAllViews();
        tbl_Course.setStretchAllColumns(true);
        Num=0;
        TableRow row=new TableRow(this);
        TextView tv=new TextView(this);
        String []context={"   ","��ʦ ","    ʱ��            ","��Ŀ        ","����"};
        if (day==0)
        		context[1]="���� ";
        for (int i=0;i<=4;i++)
        {
        	tv=new TextView(this);
        	tv.setText(context[i]);
        	row.addView(tv);
        }
        tbl_Course.addView(row);
        for (int j=0;j<curSch.GetNum(day);j++)
        {
        	Course course=curSch.GetCourse(day, j);
        	context[0]=String.valueOf(j+1);
        	context[1]=course.teacher;
        	context[2]=course.time;
        	context[3]=course.name;
        	context[4]=course.classroom;
        	row=new TableRow(this);
        	Num++;
        	for (int i=0;i<=4;i++)
            {
            	tv=new TextView(this);
            	tv.setText(context[i]);
            	row.addView(tv);
            }
        	row.setOnClickListener(new MyOnclickListaner(row));
            tbl_Course.addView(row);
        }
        
        row=new TableRow(this);
        
        row.addView(new TextView(this));
        row.addView(new TextView(this));
        tv=new TextView(this);
        if (day==0)
        	tv.setText("         +��ӿ���");
        else tv.setText("         +��ӿγ�");
        row.addView(tv);
        row.addView(new TextView(this));
        row.addView(new TextView(this));
        row.setOnClickListener(new MyOnclickListaner(null));
        tbl_Course.addView(row);
        //MainActivity.this.btn_Class.setText("AAAA");
	}

	class MyOnclickListaner implements OnClickListener
	{

		TableRow row;
		public MyOnclickListaner(TableRow row) {
			this.row=row;
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//MainActivity.this.btn_Class.setText(String.valueOf(row.getChildCount()));
			
			LittleDialog dialog = new LittleDialog(MainActivity.this,R.style.LittleDialog);
			dialog.show();
			dialog.AddRow(row,MainActivity.this.day,MainActivity.this,Num);
			
		}
		
	}
    class SpinnerSelectedListener implements OnItemSelectedListener
    {   
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
        {   
        	day=arg2+1;   
        	Show();
        }   
  
        public void onNothingSelected(AdapterView<?> arg0) {   
        }   
    }   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        status=true;
        day=1;
        //��ȡ�ؼ�
    	btn_Class=(Button) findViewById(R.id.btn_Class);
    	btn_Test=(Button) findViewById(R.id.btn_Test);
    	spn_Day=(Spinner)findViewById(R.id.spn_Day);
    	tbl_Course=(TableLayout)findViewById(R.id.tbl_Class);
    	txt_Title=(TextView)findViewById(R.id.txt_Title);
    	String []Days={"������","����һ","���ڶ�","������","������","������","������",};
    	adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Days);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spn_Day.setAdapter(adapter);
    	spn_Day.setSelection(1);
    	curSch=new CurSch();
    	Show();
    	
    	
    	//�γ̱����
    	btn_Class.setOnClickListener(new Button.OnClickListener() 
    	{    	
			public void onClick(View v)
			{
				spn_Day.setVisibility(View.VISIBLE);
				txt_Title.setText("�γ̱�");
				if (!status)
				{
					Show();
					status=true;
				}
			}
        });
    	//���ڼ���
    	spn_Day.setOnItemSelectedListener(new SpinnerSelectedListener()); 
    	
    	//���Ա����
    	btn_Test.setOnClickListener(new Button.OnClickListener() 
    	{    
    		
			public void onClick(View v)
			{
				spn_Day.setVisibility(View.INVISIBLE);
				txt_Title.setText("���Ա�");
				if (status)
				{
					day=0;
					Show();
					status=false;
				}
					
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
