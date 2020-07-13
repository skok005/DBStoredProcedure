package net.tis.day06;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DBTest  {
//  Connection CN ; //DB서버정보및 user/pwd기억, CN참조해서 명령어생성
//  Statement ST ;  //정적인명령어 ST=CN.createStatement(X);
//  ResultSet RS ;  //조회한결과기억 RS=ST.executeQuery(select~만 가능),RS.next()
//  String msg="insert" ;  //쿼리문 기억하는 문자열 
  
  

	public static void main(String[] args) {
		Connection CN; //DB서버정보및 user/pwd기억, CN참조해서 명령어생성
		Statement ST;  //정적인명령어 ST=CN.createStatement(X);
		ResultSet RS;
		 try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이브로드
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection(url,"system","1234");
	     System.out.println("오라클연결성공success 월요일");
	     
	   Scanner sc = new Scanner(System.in);
//	   System.out.print("코드입력>> ");
//	   String code=sc.nextLine();
//	   System.out.print("이름입력>> ");
//	   String name=sc.nextLine();
//	   System.out.print("제목입력>> ");
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
			  System.out.println("test테이블 저장성공success");
      	
			  //저장성공후 전체출력---------------------------------------------------------
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
		 }catch(Exception ex){System.out.println("에러: "+ ex.toString() );   }
	}//main end
}// class END