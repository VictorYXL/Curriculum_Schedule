package com.example.curriculumschedule;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

public class LittleDialog extends Dialog {

    Context context;
    TableRow row;
    EditText edt_Time,edt_Name,edt_Teacher,edt_Classroom;
    Button btn_Save,btn_Delete,btn_Cancel;
    TextView txt_LittleTitle,txt_Teacher;
    int day,num,testNum;
    MainActivity main;
    public LittleDialog(Context context,boolean CT) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    public LittleDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.littledialog);
        //获取控件
        edt_Time=(EditText)findViewById(R.id.edt_Time);
        edt_Name=(EditText)findViewById(R.id.edt_Name);
        edt_Teacher=(EditText)findViewById(R.id.edt_Teacher);
        edt_Classroom=(EditText)findViewById(R.id.edt_Classroom);
        btn_Save=(Button)findViewById(R.id.btn_Save);
        btn_Delete=(Button)findViewById(R.id.btn_Delete);
        btn_Cancel=(Button)findViewById(R.id.btn_Cancel);
        txt_LittleTitle=(TextView)findViewById(R.id.txt_LittleTitle);
        txt_Teacher=(TextView)findViewById(R.id.txt_Teacher);
    }
    public void AddRow(TableRow row,int day,MainActivity main,int num)
    {
    	//保存传入数据
    	this.row=row;
    	this.day=day;
    	this.num=num;
    	this.main=main;
    	//课程表
    	if (day!=0)
    	{
    		String []Days={"","星期日","星期一","星期二","星期三","星期四","星期五","星期六",};
    		String Str1="课程表:";
    		String Str2=Days[day];
    		String Str3 = null;
    		if (row==null)
    			Str3="第"+String.valueOf(num+1)+"节课";
    		else Str3="第"+((TextView)(row.getChildAt(0))).getText()+"节课";
    		txt_LittleTitle.setText(Str1+Str2+Str3);//标题
    		txt_Teacher.setText("老师");
    	}else //考试表
    	{
    		if (row==null)
    			txt_LittleTitle.setText("考试表:"+"第"+String.valueOf(num+1)+"场考试");
    		else txt_LittleTitle.setText("考试表:"+"第"+((TextView)(row.getChildAt(0))).getText()+"场考试");
    		txt_LittleTitle.setText("考试表");//标题
    		txt_Teacher.setText("日期");
    		
    	}
    	if (row!=null)
    	{
    		//显示修改信息
    		edt_Teacher.setText(((TextView)(row.getChildAt(1))).getText());
    		edt_Time.setText(((TextView)(row.getChildAt(2))).getText());
    		edt_Name.setText(((TextView)(row.getChildAt(3))).getText());
    		edt_Classroom.setText(((TextView)(row.getChildAt(4))).getText());
    			
    		btn_Delete.setEnabled(true);
        }
    	else btn_Delete.setEnabled(false);//添加则不能使用删除键
        btn_Save.setOnClickListener(new Button.OnClickListener() //添加按钮
    	{    	
			public void onClick(View v)
			{
				//创建新的课程
				Course course;
				course=new Course(LittleDialog.this.edt_Name.getText().toString(),LittleDialog.this.edt_Teacher.getText().toString(),LittleDialog.this.edt_Classroom.getText().toString(),LittleDialog.this.edt_Time.getText().toString());
				if (LittleDialog.this.row==null)//添加课程
					LittleDialog.this.main.curSch.Add(LittleDialog.this.day,course);
				else 
				{
					//替换课程
					int index=Integer.parseInt((String) ((TextView)(LittleDialog.this.row.getChildAt(0))).getText());
					index--;
					LittleDialog.this.main.curSch.Change(LittleDialog.this.day,index,course);
				}
				//显示新结果
				LittleDialog.this.main.Show();
				LittleDialog.this.main.curSch.WriteToDB();//写入数据库
				LittleDialog.this.dismiss();//此窗口关闭
			}
        });
        btn_Cancel.setOnClickListener(new Button.OnClickListener()//取消按钮 
    	{    	
			public void onClick(View v)
			{
				LittleDialog.this.dismiss();
			}
        });
        btn_Delete.setOnClickListener(new Button.OnClickListener() //删除按钮
    	{    	
			public void onClick(View v)
			{
				int index=Integer.parseInt((String) ((TextView)(LittleDialog.this.row.getChildAt(0))).getText());
				index--;//找出删除项
				LittleDialog.this.main.curSch.Delete(LittleDialog.this.day,index);//删除
				LittleDialog.this.main.Show();//显示结果
				LittleDialog.this.main.curSch.WriteToDB();//写入数据库
				LittleDialog.this.dismiss();//此窗口关闭
			}
        });
    }

}