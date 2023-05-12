package jdbc_test__0512;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import common.DBConnection;

class MemberDTO {
	private int age;
	private String name, id;
	public int getAge() {return age;}
	public void setAge(int age) {this.age = age;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getId() {return id;}
	public void setId(String id) {this.id = id;}
}

class DB {
	// 데이터베이스 연결 객체
	Connection con;
	// DB명령어 전송 객체
	PreparedStatement ps;
	// DB 명령어 후 결과 얻어오는 객체
	ResultSet rs;
	public DB() {
		try {
			con = DBConnection.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	/*
	// DB연결
	public DB() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("성공");
			// 오라클 연결된 연결 객체를 얻어오는 기능
			String id = "C##java", pwd = "1234", url = "jdbc:oracle:thin:@localhost:1521/xe";
			// 오라클 데이터베이스 연결
			con = DriverManager.getConnection(url, id, pwd);
			System.out.println("연결 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	// select
	public void select() {
		// 명령문구
		String sql = "select * from newst";
		try {
			ps = con.prepareStatement(sql);
			// select는 무조건 executeQuery를 사용한다
			rs = ps.executeQuery();
//			System.out.println(rs.next());
			while (rs.next()) {
				System.out.print(rs.getString("id") + " ");
				System.out.print(rs.getString("name") + " ");
				System.out.println(rs.getInt("age"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// select2
	public ArrayList<MemberDTO> select_2() {
		String sql = "select * from newst";
		ArrayList<MemberDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
				list.add(dto);
//				System.out.print(rs.getString("id") + " ");
//				System.out.print(rs.getString("name") + " ");
//				System.out.println(rs.getInt("age"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} // try
		return list;
	}
	//검색
	public MemberDTO search(String id) {
		String sql = "select * from newst where id = '"+id+"'";
		MemberDTO dto = null;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
//				System.out.print(rs.getString("id") + " ");
//				System.out.print(rs.getString("name") + " ");
//				System.out.println(rs.getInt("age"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}//try
		return dto;
	}
	//회원갑입
	public int register(MemberDTO dto) {
		int result = 0;
		String sql = "insert into newst values(?,?,?)";
		try {
			ps = con.prepareStatement(sql);
			//값지정
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getName());
			ps.setInt(3, dto.getAge());
			//실행
			//ps.executeQuery();
			//select : executeQuery사용
			//select를 제외한 나머지 : update 사용
			//실행및 성공시 1반환 실패시 0반환
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}//try
		return result;
	}
	//삭제
	public int delete(String id) {
		int result = 0;
		String sql = "delete from newst where id=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}//try
		return result;
	}
	//커밋
	public void commit() {
		String sql = "commit";
		try {
			ps = con.prepareStatement(sql);
			ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}//try
	}
	
	
}

public class MainClass01 {
	public static void main(String[] args) {
		DB db = new DB();
		// db.select();
		Scanner scan = new Scanner(System.in);
		int num, age = 0;
		String id = null, name = null;
		while (true) {
			System.out.println("=====================");
			System.out.println("1. 모든 사용자 보기");
			System.out.println("2. 검색");
			System.out.println("3. 회원가입");
			System.out.println("4. 삭제");
			System.out.println("0. 종료");
			num = scan.nextInt();
			switch (num) {
			case 1:
				ArrayList<MemberDTO> list = db.select_2();
				System.out.println("=====================");
				System.out.println("id\tname\tage");
				System.out.println("=====================");
				for (MemberDTO dto_All : list) {
					System.out.print(dto_All.getId() + "\t");
					System.out.print(dto_All.getName() + "\t");
					System.out.println(dto_All.getAge());
				}
//				for(int i=0;i<list.size();i++) {
//					System.out.print(list.get(i).getId() + " ");
//					System.out.print(list.get(i).getName() + " ");
//					System.out.println(list.get(i).getAge());
//				}
				break;
			case 2:
				System.out.println("검색 id 입력");
				id = scan.next();
				MemberDTO dto_Search = db.search(id);
				if(dto_Search == null) {
					System.out.println("존재하지 않는 아이디입니다");
				}else {
					System.out.print("id : "+dto_Search.getId());
					System.out.print(" name : "+dto_Search.getName());
					System.out.println(" age : "+dto_Search.getAge());
				}//if
				break;
			case 3:
				System.out.println("id 입력");
				id = scan.next();
				System.out.println("이름 입력");
				name = scan.next();
				System.out.println("나이 입력");
				age = scan.nextInt();
				
				MemberDTO dto_Register = new MemberDTO();
				dto_Register.setId(id);
				dto_Register.setName(name);
				dto_Register.setAge(age);
				
				int register_result = db.register(dto_Register);
				
				if(register_result == 0) {
					System.out.println("중복된 아이디 입니다");
				}else {
					System.out.println("회원가입 되었습니다");
				}//if
				break;
			case 4:
				System.out.println("검색 id 입력");
				id = scan.next();
				int delete_result = db.delete(id);
		
				if(delete_result == 0) {
					System.out.println("존재하지 않는 아이디입니다");
				}else {
					System.out.println("삭제되었습니다");
				}//if
				break;
			case 0:
				System.exit(0);
				break;
			default:
				break;
			}// switch
		} // while
	}
}
