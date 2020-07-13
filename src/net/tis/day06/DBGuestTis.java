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
		Connection CN=null;//DB���������� user/pwd���, CN�����ؼ� ��ɾ����
		Statement ST=null;//�����θ�ɾ� ST=CN.createStatement(X);
		PreparedStatement PST=null; //�����θ�ɾ� PST=CN.prepareStatememt(msg)
		CallableStatement CST=null; //storedprocedure����
		ResultSet RS=null;//RS=ST.executeQuery("select~") ; ��ȸ����� RS���
		String msg="" ; 
		int Gtotal=0; //��ü���ڵ尹��
		Scanner sc = new Scanner(System.in);
			
	 public DBGuestTis() {
		 try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //����̺�ε�
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
	     System.out.println("����Ŭ���Ἲ��success ������");
	     ST=CN.createStatement();
		 }catch (Exception e) {	}
	 }//������
	 
	public static void main(String[] args) {
		DBGuestTis gg = new DBGuestTis();
				
		Scanner scin = new Scanner(System.in);
		while(true) {
			System.out.print("\nsp 1���  2��ü���  3����  9����>>> ");
			int sel=scin.nextInt();
			if(sel==1){gg.guestInsert();}
			else if(sel==2){gg.guestSelectAll(); }
			else if(sel==3){gg.guestEdit(); }
			else if(sel==9){ gg.myexit(); break; }
		}
		scin.close();
	}//main end
	

	public void guestEdit( ) {//�Ѱ� ����
		try {
			System.out.print("����Է�>>"); int s=Integer.parseInt(sc.nextLine());
			System.out.print("�̸��Է�>>"); String n=sc.nextLine();
			System.out.print("�����Է�>>"); String t=sc.nextLine();
			System.out.print("�ӱ��Է�>>"); int p=Integer.parseInt(sc.nextLine());
			int h=3; //ī��Ʈ
			System.out.print("�����Է�>>"); String e=sc.nextLine();
		  CST=CN.prepareCall("{call guest_sp_update(?, ?, ?, ?, ?, ?) }");
			CST.setInt(1,s);
			CST.setString(2, n);
			CST.setString(3, t);
			CST.setInt(4, p);
			CST.setInt(5, h);
			CST.setString(6, e);
			CST.executeUpdate();
			 System.out.println("SP��������");			
		}catch (Exception e) { System.out.println("��������");}
	}
	
		
	public void guestSelectAll( ) {//��ü���
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
		}catch (Exception e) { System.out.println("��ü��ȸ����");}
	}//end--------------------
	

	
	public void myexit() {
		System.out.println("���α׷��� �����մϴ�");
		System.exit(1);
	}//end--------------------
	
	
	public void guestInsert() {
		try{
			System.out.print("����Է�>>"); int s=Integer.parseInt(sc.nextLine());
			System.out.print("�̸��Է�>>"); String n=sc.nextLine();
			System.out.print("�����Է�>>"); String t=sc.nextLine();
			System.out.print("�ӱ��Է�>>"); int p=Integer.parseInt(sc.nextLine());
			int h=3; //ī��Ʈ
			System.out.print("�����Է�>>"); String e=sc.nextLine();
		  CST=CN.prepareCall("{call guest_sp_insert(?, ?, ?, ?, ?, ?) }");
			CST.setInt(1,s);
			CST.setString(2, n);
			CST.setString(3, t);
			CST.setInt(4, p);
			CST.setInt(5, h);
			CST.setString(6, e);
			CST.executeUpdate();
			 System.out.println("SP���强��");
		}catch(Exception ex) { System.out.println(ex+"SP�������"); }
	}//end--------------------
}/////////////////////////////////////////////class END




