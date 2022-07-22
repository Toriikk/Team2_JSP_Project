package com.wmember.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class WQnaDAO<WQna> {
	  private Connection conn;
	  private ResultSet rs;

	  public WQnaDAO() {
	  	try {
	  		String dbURL = "jdbc:oracle:thin:@localhost:1521:XE";
	  		String dbID = "TEAM2";
	  		String dbPassword = "1234";
	  		Class.forName("oracle.jdbc.OracleDriver");
	  		conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
	  	} catch (Exception e) {
	  		e.printStackTrace();
	  	}
	  }

	  public String getDate() {
	  	String SQL = "SELECT NOW()"; // 현재 시간 가져오기
	  	try {
	  		PreparedStatement pstmt = conn.prepareStatement(SQL);
	  		rs = pstmt.executeQuery();
	  		if (rs.next()) {
	  			return rs.getString(1);
	  		}
	  	} catch(Exception e) {
	  		e.printStackTrace();
	  	}
	  	return "";
	  }

	  public int getNext() { // 다음 글 가지고 오기.
	  	String SQL = "SELECT QNUM FROM WQna ORDER BY QNUM DESC";
	  	try {
	  		PreparedStatement pstmt = conn.prepareStatement(SQL);
	  		rs = pstmt.executeQuery();
	  		if (rs.next()) {
	  			return rs.getInt(1) + 1;
	  		}
	  		return 1; // 첫 번째 게시물인 경우
	  	} catch(Exception e) {
	  		e.printStackTrace();
	  	}
	  	return -1; // 데이터베이스 오류
	  }

	  public int write(String qtitle, String useId, String content) {
	  	String SQL = "INSERT INTO WQna VALUES (?, ?, ?, ?, ?, ?)";
	  	try {
	  		PreparedStatement pstmt = conn.prepareStatement(SQL);
	  		pstmt.setInt(1, getNext());
	  		pstmt.setString(2, qtitle);
	  		pstmt.setString(3, useId);
	  		pstmt.setString(4, getDate());
	  		pstmt.setString(5, content);
	  		pstmt.setInt(6, 1);

	  		return pstmt.executeUpdate();
	  	} catch(Exception e) {
	  		e.printStackTrace();
	  	}
	  	return -1; // 데이터베이스 오류
	  }
	  public ArrayList<WQnaDTO> getList(int pageNumber) {
			String SQL = "SELECT * FROM WQna WHERE QNUM < ? AND QAvailable = 1 ORDER BY QNUM DESC LIMIT 10";
			ArrayList<WQnaDTO> list = new ArrayList<WQnaDTO>();
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					WQnaDTO wqna = new WQnaDTO();
					wqna.setQnum(rs.getInt(1));
					wqna.setQtitle(rs.getString(2));
					wqna.setUserId(rs.getString(3));
					wqna.setQreg_date(rs.getString(4));
					wqna.setContent(rs.getString(5));
					wqna.setQavailable(rs.getInt(1));
					list.add(wqna);
				}			
			} catch(Exception e) {
				e.printStackTrace();
			}
			return list;
		}

		
		public boolean nextPage(int pageNumber) {
			String SQL = "SELECT * FROM WQNA WHERE < ? AND QAvailable = 1";

			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					return true;
				}			
			} catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		public WQnaDTO getWQnaDTO(int QNUM) {
			String SQL = "SELECT * FROM Wqna WHERE QNUM = ?";
			
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, QNUM);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					WQnaDTO wqna = new WQnaDTO();
					wqna.setQnum(rs.getInt(1));
					wqna.setQtitle(rs.getString(2));
					wqna.setUserId(rs.getString(3));
					wqna.setQreg_date(rs.getString(4));
					wqna.setContent(rs.getString(5));
					wqna.setQavailable(rs.getInt(1));
					return wqna;
				}			
			} catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public int update(int userid, String qtitle, String content) {
			String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent = ? WHERE bbsID =?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, qtitle);
				pstmt.setString(2, content);
				pstmt.setInt(3, userid);
				
				return pstmt.executeUpdate(); 
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; // 데이터베이스 오류
		}
		
		public int delete(int qnum) {
			String SQL = "UPDATE WQna SET qavailable = 0 WHERE QNUM = ?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, qnum);
				
				return pstmt.executeUpdate(); 
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; // 데이터베이스 오류
		}
	}
