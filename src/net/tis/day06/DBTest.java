package net.tis.day06;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DBTest  {
//  Connection CN ; //DB���������� user/pwd���, CN�����ؼ� ��ɾ����
//  Statement ST ;  //�����θ�ɾ� ST=CN.createStatement(X);
//  ResultSet RS ;  //��ȸ�Ѱ����� RS=ST.executeQuery(select~�� ����),RS.next()
//  String msg="insert" ;  //������ ����ϴ� ���ڿ� 
  
  

	public static void main(String[] args) {
		Connection CN; //DB���������� user/pwd���, CN�����ؼ� ��ɾ����
		Statement ST;  //�����θ�ɾ� ST=CN.createStatement(X);
		ResultSet RS;
		 try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //����̺�ε�
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection(url,"system","1234");
	     System.out.println("����Ŭ���Ἲ��success ������");
	     
	   Scanner sc = new Scanner(System.in);
//	   System.out.print("�ڵ��Է�>> ");
//	   String code=sc.nextLine();
//	   System.out.print("�̸��Է�>> ");
//	   String name=sc.nextLine();
//	   System.out.print("�����Է�>> ");
//	   String title=sc.nextLine();
	   System.out.print("SQL>> ");
	   String msg = sc.nextLine();	    
	   
	   //SQL> delete from test where code > 6600 ; 
	   //SQL> commit ;
		 ST=CN.createStatement(); 
		 //String msg= "insert into test values(9900, 'TJ', 'AB', sysdate, 7)";
		 //String msg= "insert into test values(" + code + ",'" + name +"','"+ title+ "',sysdate, 7)";
		 //String msg1= "delete from test where code=8800";
		 int OK = ST.executeUpdate(msg);
		 if(OK>0) { 
			  System.out.println("test���̺� ���强��success");
      	
			  //���强���� ��ü���---------------------------------------------------------
     	  System.out.println("=============================");
     	  RS = ST.executeQuery("select * from test order by code"); 
     	  System.out.println("CODE\t" + "NAME\t" + "TITLE\t" + "WDATE\t\t" + "CNT");
     	  System.out.println("----\t" + "----\t" + "----\t" + "----\t\t" +"----");
     	  while(RS.next()==true) {
     	  	System.out.print(RS.getInt("code"));
          System.out.print("\t");
          System.out.print(RS.getString("name"));
          System.out.print("\t");
          System.out.print(RS.getString("title"));
          System.out.print("\t");
          System.out.print(RS.getDate("wdate"));
          System.out.print("\t");
          System.out.print(RS.getInt("cnt"));
          System.out.print("\n");
     	  }
			}		 
		 }catch(Exception ex){System.out.println("����: "+ ex.toString() );   }
	}//main end
}// class END