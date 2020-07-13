package net.tis.day06;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class DBTestCode  {
  Connection CN = null; //DB���������� user/pwd���, CN�����ؼ� ��ɾ����
  Statement ST = null;  //�����θ�ɾ� ST=CN.createStatement(X);
  PreparedStatement PST = null; //������ ��ɾ� PST=CN.prepareStatement(msg)
  CallableStatement CST = null; //storedProcedure����
  ResultSet RS = null;  //��ȸ�Ѱ����� RS=ST.executeQuery(select~),RS.next()
  String msg="" ;  //������ ����ϴ� ���ڿ�
  Scanner sc = new Scanner(System.in);
  
	public static void main(String[] args) {
		DBTestCode dbt = new DBTestCode();
		Scanner sc = new Scanner(System.in);
		while(true){
			try {
				System.out.print("     1.�űԵ��   2.��ü���   3.�̸�   4.����   5.������Ʈ   9.����\n�Է�>> " );
			  int sel = Integer.parseInt(sc.nextLine());	  
			  if(sel==1) {dbt.dbInsert();} 
			  else if(sel==2) {dbt.dbSelectAll();}
			  else if(sel==3) {dbt.dbSearchName();}
			  else if(sel==4) {dbt.dbDelete();}
			  else if(sel==5) {dbt.dbUpdate();}
			  else if(sel==9) {System.out.println("����"); System.exit(1);}
			  else {System.out.println("1~5, 9 ���ڸ� �Է��ϼ���");}
			  System.out.println("\n----------------------------------------------------------");
			}catch(Exception e) {System.out.println("1~5, 9 ���ڸ� �Է��ϼ���\n\n----------------------------------------------------------");}
		}//while end
	}//main end
	
	public DBTestCode() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
			CN = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
			System.out.println("����Ŭ���Ἲ��success ������");			
		}catch(Exception ex){System.out.println("����: "+ ex.toString() );}		
	}//end
	
	public void dbInsert() { //�Ѱ� ���		
		try {
			ST = CN.createStatement();
		  sc = new Scanner(System.in);
		  System.out.print("code >> ");  int cdata = Integer.parseInt(sc.nextLine());	
		  
//	  int code = 0;
//	  RS = ST.executeQuery("select code from test");
//	  while(RS.next()) {
//	  	code = RS.getInt("code");
//	  }
//	  if(code==cdata) {System.out.println("*�̹� ��ϵ� �ڵ�*"); dbInsert();}
	  
	  int code=0;
	  RS = ST.executeQuery("select count(*) as hit from test where code = "+cdata);
	  while(RS.next())
	  code = RS.getInt("hit");
	  if(code!=0) {System.out.println("*�̹� ��ϵ� �ڵ�*"); dbInsert();}
	  
		  System.out.print("name >> ");  String ndata = sc.nextLine();
		  System.out.print("title >> "); String tdata = sc.nextLine();
 		  
		  //msg = "insert into test values("+code+",'"+name+"','"+title+"',sysdate, 0)";
	    msg = "insert into test values(?,?,?,sysdate, 0)";
	    //ST = CN.createStatement();
	    PST = CN.prepareStatement(msg);
	    			PST.setInt(1, cdata);   PST.setString(2, ndata);   PST.setString(3, tdata);
			//int OK = ST.executeUpdate(msg);
		  int OK = PST.executeUpdate();
			if(OK>0) {System.out.println("test���̺� ���强��");}
			dbSelectAll();
		}catch(Exception ex) {System.out.println("����: "+ ex.toString());}		
	}//end
	
	public void dbSelectAll() { //��ü ���		
		try {
		  System.out.print("====================��ü���============");
			System.out.println("Accum.(" + dbCount() + ")");			
		  ST = CN.createStatement();
		  RS = ST.executeQuery("select rownum as rn, t.* from test t");
		  while(RS.next()) {
		  	int r = RS.getInt("rn");
		    int a = RS.getInt("code"); 
		    String b = RS.getString("name"); 
		    String c = RS.getString("title"); 
		    Date dt = RS.getDate("wdate");
		    int hit = RS.getInt("cnt");
			  System.out.println(r + "\t" + a + "\t" + b + "\t" + c + "\t" + dt + "\t" + hit);
      }//while end	 
		  System.out.print("======================END======================");
		}catch(Exception ex) {System.out.println("����: "+ ex.toString());}
	}//end
	
	public void dbSearchName() { //name �ʵ� ��ȸ
		try {
			ST = CN.createStatement();
			System.out.print("�̸� �Է�>>");
			String name = sc.nextLine();
			msg = "select * from test where name like '%"+ name +"%'";
			RS = ST.executeQuery(msg);
			while(RS.next()) {
				int a = RS.getInt("code");
				String b = RS.getString("name");
				String c = RS.getString("title");
			  Date dt = RS.getDate("wdate");
			  int hit = RS.getInt("cnt");
			  System.out.println(a + "\t" + b + "\t" + c + "\t" + dt + "\t" + hit);
			}
		}catch(Exception ex) {System.out.println("����: "+ ex.toString());}		
	}//end
	
	public void dbDelete() {
		try {
			ST = CN.createStatement();
			System.out.print("�ڵ� �Է�>> ");
			int code=Integer.parseInt(sc.nextLine());
			msg = "delete from test where code=" + code;
			int OK = ST.executeUpdate(msg);			
			if(OK>0) {System.out.println(code+"�ڵ� ���� �Ϸ�");}
			else {System.out.println("*���� �ڵ��Դϴ�*");}
			dbSelectAll();
		}catch(Exception ex) {System.out.println("����: "+ ex.toString());}		
	}//end
	
	public void dbUpdate() { //prepareStatement
		try {
//			System.out.print("setData>> ");
//			String sdata=sc.nextLine();
//			System.out.print("chData>> ");
//			String cdata=sc.nextLine();
//			msg = "update test set name= ? where code= ?";
//			PST = CN.prepareStatement(msg);
//						PST.setString(1, sdata);   PST.setString(2, cdata);
//			int OK = PST.executeUpdate();			
//			if(OK>0) {System.out.println(sdata+"������Ʈ �Ϸ�");}
			
			System.out.print("sName>> ");
			String sname=sc.nextLine();
			System.out.print("cName>> ");
			String cname=sc.nextLine();
			System.out.print("sData>> ");
			String sdata=sc.nextLine();
			System.out.print("cData>> ");
			String cdata=sc.nextLine();
			msg = "update test set " + sname + "='" + cname +"'where " + sdata + "='" + cdata + "'";
			ST = CN.createStatement();
			int OK = ST.executeUpdate(msg);			
			if(OK>0) {System.out.println(sdata+"������Ʈ �Ϸ�");}
		}catch(Exception ex) {System.out.println("����: "+ ex.toString());}	
	}//end
	
	public int dbCount() { //Statement
		int mycount=0;
		try {
			msg = "select count(*) as cnt from test";
			ST = CN.createStatement();
			RS = ST.executeQuery(msg);
			while(RS.next()) {
				mycount = RS.getInt("cnt");
			}			
		}catch(Exception ex) {System.out.println("����: "+ ex.toString());}
		return mycount;
	}//end
	
	
	public void dbInsert1234() { //�Ѱ� ���		
		try {
		  sc = new Scanner(System.in);
		  System.out.print("code >> ");  String code = sc.nextLine();		   
		  System.out.print("name >> ");  String name = sc.nextLine();		   
		  System.out.print("title >> "); String title = sc.nextLine();
		  
		  ST = CN.createStatement();
		  msg = "insert into test values("+code+",'"+name+"','"+title+"',sysdate, 0)";
		  System.out.println(msg);
			int OK = ST.executeUpdate(msg);			
			if(OK>0) {System.out.println("test���̺� ���强��");}
			dbSelectAll();
		}catch(Exception ex) {System.out.println("����: "+ ex.toString());}		
	}//end
}// class END
