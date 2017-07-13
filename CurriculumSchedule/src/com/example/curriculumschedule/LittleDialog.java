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
        //��ȡ�ؼ�
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
    	//���洫������
    	this.row=row;
    	this.day=day;
    	this.num=num;
    	this.main=main;
    	//�γ̱�
    	if (day!=0)
    	{
    		String []Days={"","������","����һ","���ڶ�","������","������","������","������",};
    		String Str1="�γ̱�:";
    		String Str2=Days[day];
    		String Str3 = null;
    		if (row==null)
    			Str3="��"+String.valueOf(num+1)+"�ڿ�";
    		else Str3="��"+((TextView)(row.getChildAt(0))).getText()+"�ڿ�";
    		txt_LittleTitle.setText(Str1+Str2+Str3);//����
    		txt_Teacher.setText("��ʦ");
    	}else //���Ա�
    	{
    		if (row==null)
    			txt_LittleTitle.setText("���Ա�:"+"��"+String.valueOf(num+1)+"������");
    		else txt_LittleTitle.setText("���Ա�:"+"��"+((TextView)(row.getChildAt(0))).getText()+"������");
    		txt_LittleTitle.setText("���Ա�");//����
    		txt_Teacher.setText("����");
    		
    	}
    	if (row!=null)
    	{
    		//��ʾ�޸���Ϣ
    		edt_Teacher.setText(((TextView)(row.getChildAt(1))).getText());
    		edt_Time.setText(((TextView)(row.getChildAt(2))).getText());
    		edt_Name.setText(((TextView)(row.getChildAt(3))).getText());
    		edt_Classroom.setText(((TextView)(row.getChildAt(4))).getText());
    			
    		btn_Delete.setEnabled(true);
        }
    	else btn_Delete.setEnabled(false);//�������ʹ��ɾ����
        btn_Save.setOnClickListener(new Button.OnClickListener() //��Ӱ�ť
    	{    	
			public void onClick(View v)
			{
				//�����µĿγ�
				Course course;
				course=new Course(LittleDialog.this.edt_Name.getText().toString(),LittleDialog.this.edt_Teacher.getText().toString(),LittleDialog.this.edt_Classroom.getText().toString(),LittleDialog.this.edt_Time.getText().toString());
				if (LittleDialog.this.row==null)//��ӿγ�
					LittleDialog.this.main.curSch.Add(LittleDialog.this.day,course);
				else 
				{
					//�滻�γ�
					int index=Integer.parseInt((String) ((TextView)(LittleDialog.this.row.getChildAt(0))).getText());
					index--;
					LittleDialog.this.main.curSch.Change(LittleDialog.this.day,index,course);
				}
				//��ʾ�½��
				LittleDialog.this.main.Show();
				LittleDialog.this.main.curSch.WriteToDB();//д�����ݿ�
				LittleDialog.this.dismiss();//�˴��ڹر�
			}
        });
        btn_Cancel.setOnClickListener(new Button.OnClickListener()//ȡ����ť 
    	{    	
			public void onClick(View v)
			{
				LittleDialog.this.dismiss();
			}
        });
        btn_Delete.setOnClickListener(new Button.OnClickListener() //ɾ����ť
    	{    	
			public void onClick(View v)
			{
				int index=Integer.parseInt((String) ((TextView)(LittleDialog.this.row.getChildAt(0))).getText());
				index--;//�ҳ�ɾ����
				LittleDialog.this.main.curSch.Delete(LittleDialog.this.day,index);//ɾ��
				LittleDialog.this.main.Show();//��ʾ���
				LittleDialog.this.main.curSch.WriteToDB();//д�����ݿ�
				LittleDialog.this.dismiss();//�˴��ڹر�
			}
        });
    }

}