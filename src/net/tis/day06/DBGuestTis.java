package net.tis.day06;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;


public class DBGuestTis { 
		Connection CN=null;//DB서버정보및 user/pwd기억, CN참조해서 명령어생성
		Statement ST=null;//정적인명령어 ST=CN.createStatement(X);
		PreparedStatement PST=null; //동적인명령어 PST=CN.prepareStatememt(msg)
		CallableStatement CST=null; //storedprocedure연결
		ResultSet RS=null;//RS=ST.executeQuery("select~") ; 조회결과를 RS기억
		String msg="" ; 
		int Gtotal=0; //전체레코드갯수
		Scanner sc = new Scanner(System.in);
			
	 public DBGuestTis() {
		 try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이브로드
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
	     System.out.println("오라클연결성공success 월요일");
	     ST=CN.createStatement();
		 }catch (Exception e) {	}
	 }//생성자
	 
	public static void main(String[] args) {
		DBGuestTis gg = new DBGuestTis();
				
		Scanner scin = new Scanner(System.in);
		while(true) {
			System.out.print("\nsp 1등록  2전체출력  3수정  9종료>>> ");
			int sel=scin.nextInt();
			if(sel==1){gg.guestInsert();}
			else if(sel==2){gg.guestSelectAll(); }
			else if(sel==3){gg.guestEdit(); }
			else if(sel==9){ gg.myexit(); break; }
		}
		scin.close();
	}//main end
	

	public void guestEdit( ) {//한건 수정
		try {
			System.out.print("사번입력>>"); int s=Integer.parseInt(sc.nextLine());
			System.out.print("이름입력>>"); String n=sc.nextLine();
			System.out.print("제목입력>>"); String t=sc.nextLine();
			System.out.print("임금입력>>"); int p=Integer.parseInt(sc.nextLine());
			int h=3; //카운트
			System.out.print("메일입력>>"); String e=sc.nextLine();
		  CST=CN.prepareCall("{call guest_sp_update(?, ?, ?, ?, ?, ?) }");
			CST.setInt(1,s);
			CST.setString(2, n);
			CST.setString(3, t);
			CST.setInt(4, p);
			CST.setInt(5, h);
			CST.setString(6, e);
			CST.executeUpdate();
			 System.out.println("SP수정성공");			
		}catch (Exception e) { System.out.println("수정에러");}
	}
	
		
	public void guestSelectAll( ) {//전체출력
		try {
		msg="select * from guest  order by sabun" ;
		RS = ST.executeQuery(msg);
		while(RS.next()==true) {
			 int s = RS.getInt("sabun");
       String n = RS.getString("name");
       String t = RS.getString("title");
       int p = RS.getInt("pay");
       String e = RS.getString("email");
      System.out.println(s+"\t"+n+"\t"+t+"\t"+p+"\t"+e);
		}
		System.out.println("=============================================");
		}catch (Exception e) { System.out.println("전체조회에러");}
	}//end--------------------
	

	
	public void myexit() {
		System.out.println("프로그램을 종료합니다");
		System.exit(1);
	}//end--------------------
	
	
	public void guestInsert() {
		try{
			System.out.print("사번입력>>"); int s=Integer.parseInt(sc.nextLine());
			System.out.print("이름입력>>"); String n=sc.nextLine();
			System.out.print("제목입력>>"); String t=sc.nextLine();
			System.out.print("임금입력>>"); int p=Integer.parseInt(sc.nextLine());
			int h=3; //카운트
			System.out.print("메일입력>>"); String e=sc.nextLine();
		  CST=CN.prepareCall("{call guest_sp_insert(?, ?, ?, ?, ?, ?) }");
			CST.setInt(1,s);
			CST.setString(2, n);
			CST.setString(3, t);
			CST.setInt(4, p);
			CST.setInt(5, h);
			CST.setString(6, e);
			CST.executeUpdate();
			 System.out.println("SP저장성공");
		}catch(Exception ex) { System.out.println(ex+"SP저장실패"); }
	}//end--------------------
}/////////////////////////////////////////////class END




