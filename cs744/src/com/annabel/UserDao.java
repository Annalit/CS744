package com.annabel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.entity.User;
import com.utils.ConnUtils;

public class UserDao {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private int ri, row, rc;

	public ArrayList<Integer> getQuestionsByUser(String uid) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		try {
			conn = ConnUtils.getConnection();//
			pstmt = conn.prepareStatement("SELECT * FROM User WHERE uid = ?");
			pstmt.setString(1, uid);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				result.add(rs.getInt("qID1"));
				result.add(rs.getInt("qID2"));
				result.add(rs.getInt("qID3"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return result;
	}
	public String getFristNamebyId(String uid) {
		String res = null;
		try{
			conn = ConnUtils.getConnection();//
			pstmt = conn.prepareStatement("SELECT * FROM User WHERE uid = ?");
			pstmt.setString(1, uid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				res = rs.getString("first_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return res;
	}
	public void setQuestions(int ques1, String ans1, int ques2, String ans2,
			int ques3, String ans3, String uid) {
		try {
			conn = ConnUtils.getConnection();
			String modifyUser = "UPDATE User SET qID1=?,qID2=?,qID3=?, ans1=?, ans2=?, ans3=? WHERE uid=?";
			pstmt = conn.prepareStatement(modifyUser);
			pstmt.setInt(1, ques1);
			pstmt.setInt(2, ques2);
			pstmt.setInt(3, ques3);
			pstmt.setString(4, ans1);
			pstmt.setString(5, ans2);
			pstmt.setString(6, ans3);
			pstmt.setString(7, uid);
			ri = pstmt.executeUpdate();
			if (ri > 0) {
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
	}
	
	public boolean checkUser(String uid, String password) {
		boolean result = false;
		String sql = "SELECT * FROM User WHERE uid = ? AND password =?";
		try {
			conn = ConnUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return result;
	}

	public boolean checkFirstTime(String uid) {
		boolean flag = false;
		String sql = "SELECT * FROM User WHERE uid = ?";
		try {
			conn = ConnUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("qID1") == 0) {
					flag = true;
				}
			}
		} catch (SQLException e) {
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return flag;
	}
	public void deleteUser(String uid) {
		
	}
	public boolean checkSecurity(String uid, int qID, String ans) {
		String sql1 = "SELECT * FROM User WHERE uid = ? AND qID1 = ? AND ans1 = ? ";
		String sql2 = "SELECT * FROM User WHERE uid = ? AND qID2 = ? AND ans2 = ? ";
		String sql3 = "SELECT * FROM User WHERE uid = ? AND qID3 = ? AND ans3 = ? ";
		try {
			conn = ConnUtils.getConnection();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, uid);
			pstmt.setInt(2, qID);
			pstmt.setString(3, ans);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, uid);
				pstmt.setInt(2, qID);
				pstmt.setString(3, ans);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					return true;
				} else {
					pstmt = conn.prepareStatement(sql3);
					pstmt.setString(1, uid);
					pstmt.setInt(2, qID);
					pstmt.setString(3, ans);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						return true;
					}
				}
			}
		} catch (SQLException e) {

		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return false;
	}
}