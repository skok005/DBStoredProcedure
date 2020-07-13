package net.tis.day06;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;


public class DBGuest { 
		Connection CN=null;//DB���������� user/pwd���, CN�����ؼ� ��ɾ����
		Statement ST=null;//�����θ�ɾ� ST=CN.createStatement(X);
		PreparedStatement PST=null; //�����θ�ɾ� PST=CN.prepareStatememt(msg)
		CallableStatement CST=null; //storedprocedure����
		ResultSet RS=null;//RS=ST.executeQuery("select~") ; ��ȸ����� RS���
		String msg="" ; 
		int Gtotal=0; //��ü���ڵ尹��
		Scanner sc = new Scanner(System.in);
			
	 public DBGuest() {
		 try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //����̺�ε�
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
	     System.out.println("����Ŭ���Ἲ��success ������");
	     ST=CN.createStatement();
		 }catch (Exception e) {	}
	 }//������
	 
	public static void main(String[] args) {
		DBGuest gg = new DBGuest();
				
		Scanner scin = new Scanner(System.in);
		while(true) {
			System.out.print("\n1��ü��� 2������ 3�̸���ȸ  9����>>> ");
			int sel=scin.nextInt();
			if(sel==1){gg.guestSelectAll();}
			else if(sel==2){ gg.guestPage();}
			else if(sel==3){ gg.guestSearchName();}
			else if(sel==9){ gg.myexit(); break; }
		}
		scin.close();
	}//main end
	
	
		
	public int dbCount(){//���ڵ尹�� Statement
		int mytotal=0;
		try {
			msg="select count(*) as cnt from guest";
			RS=ST.executeQuery(msg);
			if(RS.next()==true) {
				mytotal = RS.getInt("cnt");
			}
		}catch (Exception e) { System.out.println(e);}
		return mytotal;
	}//end--------------------
	
	
	public void guestSelectAll( ) {//��ü���
		try {
		System.out.println("=============================================");
		Gtotal=dbCount();
		msg="select rownum as rn, g.* from(select * from guest order by sabun)g";
		RS = ST.executeQuery(msg);
		while(RS.next()) {
			int r = RS.getInt("rn");
	    int a = RS.getInt("sabun"); 
	    String b = RS.getString("name"); 
	    String c = RS.getString("title"); 
	    Date dt = RS.getDate("wdate");
	    int pay = RS.getInt("pay");
	    int hit = RS.getInt("hit");
	    String d = RS.getString("email");
		  System.out.println(r+"\t"+a+"\t"+b+"\t"+c+"\t"+dt+"\t"+pay+"\t"+hit+"\t"+d);			
		}System.out.println("================================�ѷ��ڵ尹��:"+Gtotal +"��");
		}catch (Exception e) { System.out.println("��ü��ȸ����");}
	}//end--------------------
	
	
	public void guestPage() {
		try {
			System.out.print("������>> ");
			int page = Integer.parseInt(sc.nextLine());
			int start = (page-1)*10+1;
			int end = page*10;
			
			msg="select * from(select rownum as rn, g.* from(select * from guest order by sabun)"
					+"g where rownum<="+end+") where rn>="+start ;
			RS = ST.executeQuery(msg);
			while(RS.next()) {
				int r = RS.getInt("rn");
		    int a = RS.getInt("sabun");
		    String b = RS.getString("name"); 
		    String c = RS.getString("title"); 
		    Date dt = RS.getDate("wdate");
		    int pay = RS.getInt("pay");
		    int hit = RS.getInt("hit");
		    String d = RS.getString("email");
			  System.out.println(r+"\t"+a+"\t"+b+"\t"+c+"\t"+dt+"\t"+pay+"\t"+hit+"\t"+d);
			}//while end
			int mytotal=0;
			msg="select count(*) as cnt from(select rownum as rn, g.* from(select * from guest order by sabun)"
					+"g where rownum<="+end+") where rn>="+start ;
			RS=ST.executeQuery(msg);
			if(RS.next()==true) {
				mytotal = RS.getInt("cnt");
				}//
			Gtotal=mytotal;
			System.out.println("================================�ѷ��ڵ尹��:"+Gtotal +"��");					
		}catch (Exception ex) { System.out.println(ex.toString());}
	}//end--------------------
	
	
	public void guestSearchName( ) {//name�ʵ���ȸ
		System.out.print("\n��ȸ�̸��Է�>>>");
		String data = sc.nextLine();
		try {		
			msg="select rownum as rn, g.* from(select * from guest order by sabun)g where name like '%" + data + "%'" ;
			RS = ST.executeQuery(msg);
			while(RS.next()) {
				int r = RS.getInt("rn");
		    int a = RS.getInt("sabun");
		    String b = RS.getString("name"); 
		    String c = RS.getString("title"); 
		    Date dt = RS.getDate("wdate");
		    int pay = RS.getInt("pay");
		    int hit = RS.getInt("hit");
		    String d = RS.getString("email");
			  System.out.println(r+"\t"+a+"\t"+b+"\t"+c+"\t"+dt+"\t"+pay+"\t"+hit+"\t"+d);
			}//while end
			int mytotal=0;
			msg="select count(*) as cnt from guest where name like '%" + data + "%'";
			RS=ST.executeQuery(msg);
			if(RS.next()==true) {
				mytotal = RS.getInt("cnt");
				}//
			Gtotal=mytotal;
			System.out.println("================================�ѷ��ڵ尹��:"+Gtotal +"��");		
		}catch (Exception e) { System.out.println("�̸���ȸ����" + e.toString());}
	}//end--------------------
	
	
	public void myexit() {
		System.out.println("���α׷��� �����մϴ�");
		System.exit(1);
	}//end--------------------
	
	
	
}/////////////////////////////////////////////class END


