package com.example.curriculumschedule;

import java.util.*;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
//主界面
public class MainActivity extends Activity {
	//各个定义控件
	Button btn_Class;
	Button btn_Test;
	Spinner spn_Day;
	TableLayout tbl_Course;
	TextView txt_Title;
	Boolean status;
	//day表示星期，0为考试，1~7表示周日到周六
	int day,Num;
	CurSch curSch;
	
	private ArrayAdapter<String> adapter;

	
	//显示课程或考试表格
	void Show()
	{
		//删除之前的数据
		tbl_Course.removeAllViews();
        tbl_Course.setStretchAllColumns(true);
        Num=0;
        TableRow row=new TableRow(this);
        TextView tv=new TextView(this);
        String []context={"   ","教师 ","    时间            ","科目        ","教室"};
        if (day==0)
        		context[1]="日期 ";
        //添加表头
        for (int i=0;i<=4;i++)
        {
        	tv=new TextView(this);
        	tv.setText(context[i]);
        	row.addView(tv);
        }
        tbl_Course.addView(row);
        //一行行读入数据并显示
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
        	row.setOnClickListener(new MyOnclickListaner(row));//增加控件监听
            tbl_Course.addView(row);
        }
        
        row=new TableRow(this);
        
        //最后一行，添加控件的显示
        row.addView(new TextView(this));
        row.addView(new TextView(this));
        tv=new TextView(this);
        if (day==0)
        	tv.setText("         +添加考试");
        else tv.setText("         +添加课程");
        row.addView(tv);
        row.addView(new TextView(this));
        row.addView(new TextView(this));
        row.setOnClickListener(new MyOnclickListaner(null));
        tbl_Course.addView(row);
        //MainActivity.this.btn_Class.setText("AAAA");
	}

	//表格点击事件处理
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
			//显示小对话框，传入格式等
			LittleDialog dialog = new LittleDialog(MainActivity.this,R.style.LittleDialog);
			dialog.show();
			//将当前表的各个数据传入
			dialog.AddRow(row,MainActivity.this.day,MainActivity.this,Num);
			
		}
		
	}
	//选择框事件监听
    class SpinnerSelectedListener implements OnItemSelectedListener
    {   
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
        {   
        	//切换day
        	day=arg2+1;   
        	Show();
        }   
  
        public void onNothingSelected(AdapterView<?> arg0) {
        	day=spn_Day.getSelectedItemPosition()+1;
        }   
    }   
    //主函数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        status=true;
        day=1;
        //获取控件
    	btn_Class=(Button) findViewById(R.id.btn_Class);
    	btn_Test=(Button) findViewById(R.id.btn_Test);
    	spn_Day=(Spinner)findViewById(R.id.spn_Day);
    	tbl_Course=(TableLayout)findViewById(R.id.tbl_Class);
    	txt_Title=(TextView)findViewById(R.id.txt_Title);
    	String []Days={"星期日","星期一","星期二","星期三","星期四","星期五","星期六",};
    	adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Days);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spn_Day.setAdapter(adapter);
    	spn_Day.setSelection(1);
    	curSch=new CurSch(this);
    	//显示当前结果
    	Show();
    	
    	
    	//课程表监听
    	btn_Class.setOnClickListener(new Button.OnClickListener() 
    	{    	
			public void onClick(View v)
			{
				spn_Day.setVisibility(View.VISIBLE);
				txt_Title.setText("课程表");
				if (!status)
				{
					day=spn_Day.getSelectedItemPosition()+1;
					Show();
					status=true;//切换课表和考试表
				}
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
				txt_Title.setText("考试表");
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
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
