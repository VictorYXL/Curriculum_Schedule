package com.example.curriculumschedule3;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView txt_Show,txt1,txt2,txt3,txt4,txt5,txt6; 
	Button btn_Add,btn_Show,btn_Delete,btn_Change;
	CurSch curSch;
	Cursor c;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txt_Show=(TextView)findViewById(R.id.txt_Show);
		txt1=(TextView)findViewById(R.id.txt1);
		txt2=(TextView)findViewById(R.id.txt2);
		txt3=(TextView)findViewById(R.id.txt3);
		txt4=(TextView)findViewById(R.id.txt4);
		txt5=(TextView)findViewById(R.id.txt5);
		txt6=(TextView)findViewById(R.id.txt6);
		
		btn_Add=(Button)findViewById(R.id.btn_Add);
		btn_Change=(Button)findViewById(R.id.btn_Change);
		btn_Delete=(Button)findViewById(R.id.btn_Delete);
		
		btn_Show=(Button)findViewById(R.id.btn_Show);
		txt_Show.setText("null");
		
		
		curSch=new CurSch(this);
		curSch.Add(1,new Course("C1","·¶ÑµÀñ", "1308", "8:00~9:50 a.m."));
		curSch.Add(2,new Course("C2","Red Monkey", "2222", "11:00~1:50 a.m."));
		curSch.Add(2,new Course("Delete","Red Monkey", "2222", "11:00~1:50 a.m."));
		curSch.Add(4,new Course("C4","Red Monkey", "2222", "11:00~1:50 a.m."));
		curSch.Add(5,new Course("C5","Red Monkey", "2222", "11:00~1:50 a.m."));
		curSch.WriteToDB();
		btn_Add.setOnClickListener(new Button.OnClickListener() 
    	{    	
			public void onClick(View v)
			{
				curSch.Add(2,new Course("Add","Red Monkey", "2222", "11:00~1:50 a.m."));
				curSch.WriteToDB();
			}
        });
		btn_Delete.setOnClickListener(new Button.OnClickListener() 
    	{    	
			public void onClick(View v)
			{
				curSch.Delete(2, 0);
				curSch.WriteToDB();
			}
        });
		btn_Change.setOnClickListener(new Button.OnClickListener() 
    	{    	
			public void onClick(View v)
			{
				curSch.Change(2,0,new Course("Change","Red Monkey", "2222", "11:00~1:50 a.m."));
				curSch.WriteToDB();
			}
        });
		btn_Show.setOnClickListener(new Button.OnClickListener() 
    	{    	
			public void onClick(View v)
			{
				curSch.GetFromDB();
				txt1.setText(String.valueOf(curSch.GetNum(2))+" "+curSch.GetCourse(2,0).name);
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
