package com.dao;

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

import com.entity.Question;
import com.utils.ConnUtils;

	public class QuestionDao {
		private Connection conn;
		private Statement stmt;
		private PreparedStatement pstmt;
		private ResultSet rs;
		private int ri,row,rc;
		public ArrayList<Question> getQuestions() {
			ArrayList<Question> result = new ArrayList<Question>();
				try {
					conn = ConnUtils.getConnection();//
					pstmt = conn.prepareStatement("SELECT * FROM Question");
					rs = pstmt.executeQuery();					
					while (rs.next()){
						Question question = new Question();
						question.setId(rs.getInt("qID"));
					    question.setContent(rs.getString("qContent"));
						result.add(question);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					ConnUtils.releaseConn(rs, pstmt, conn);
				}
				return result;
			}
	public Question getQuestionById(int id){
		Question question = new Question();
		try{
			conn = ConnUtils.getConnection();//
			String getQuestion = "SELECT * FROM Question WHERE qID = ?";
			pstmt = conn.prepareStatement(getQuestion);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();	
			if (rs.next()) {
					question = new Question();
					question.setId(id);
					question.setContent(rs.getString("qContent"));
			}
			} catch (SQLException e) {
				e.printStackTrace(); 
			} finally {
				ConnUtils.releaseConn(rs, pstmt, conn);
			}
			return question;
		}
	}