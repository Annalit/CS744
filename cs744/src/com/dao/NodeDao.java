package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.entity.Edge;
import com.entity.Node;
import com.utils.ConnUtils;

public class NodeDao {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private int ri, row, rc;
	
	public ArrayList<Integer> getAllC() {
		ArrayList<Integer> res = new ArrayList<Integer>();
		try {
			conn = ConnUtils.getConnection();//
			pstmt = conn.prepareStatement("SELECT * FROM Node WHERE type = ?");
			pstmt.setString(1, "c");
			rs = pstmt.executeQuery();					
			while (rs.next()){
				res.add(rs.getInt("nID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return res;
	}
	public ArrayList<Integer> getAllN() {
		ArrayList<Integer> res = new ArrayList<Integer>();
		try {
			conn = ConnUtils.getConnection();//
			pstmt = conn.prepareStatement("SELECT * FROM Node WHERE type = ?");
			pstmt.setString(1, "n");
			rs = pstmt.executeQuery();					
			while (rs.next()){
				res.add(rs.getInt("nID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return res;
	}
	public boolean deletePattern(int nid){
		boolean result = false;
		try {
			conn = ConnUtils.getConnection();
			String deleteNode = "DELETE FROM Node WHERE cID= ?";
			pstmt = conn.prepareStatement(deleteNode);
			pstmt.setInt(1, nid);
			ri = pstmt.executeUpdate();
			if (ri > 0) {
				result = true;
			}} catch (SQLException e) {
				result = false; // 
			} finally {
				ConnUtils.releaseConn(rs, pstmt, conn);
			}
			return result; //
	}
	public boolean whetherC(int nid) {
		try {
			conn = ConnUtils.getConnection();//
			pstmt = conn.prepareStatement("SELECT * FROM Node WHERE nID = ? AND type = ?");
			pstmt.setInt(1, nid);
			pstmt.setString(2, "c");
			rs = pstmt.executeQuery();					
			while (rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return false;
	}
	public boolean deleteInNode(int nid){
		boolean result = false;

		try {
			conn = ConnUtils.getConnection();

			String deleteNode = "DELETE FROM Node WHERE nID= ?";

			pstmt = conn.prepareStatement(deleteNode);
			// 
			pstmt.setInt(1, nid);
			//
			ri = pstmt.executeUpdate();
			if (ri > 0) {
				result = true;
			}} catch (SQLException e) {
				result = false; // 
			} finally {
				ConnUtils.releaseConn(rs, pstmt, conn);
			}
			return result; //
			// 
		}
	public boolean deleteInNodeEdge(int nid){
		boolean result = false;

		try {
			conn = ConnUtils.getConnection();

			String deleteNode = "DELETE FROM NodeEdge WHERE nID1 = ? or nID2 = ?";

			pstmt = conn.prepareStatement(deleteNode);
			// 
			pstmt.setInt(1, nid);
			pstmt.setInt(2, nid);
			//
			ri = pstmt.executeUpdate();
			if (ri > 0) {
				result = true;
			}} catch (SQLException e) {
				result = false; // 
			} finally {
				ConnUtils.releaseConn(rs, pstmt, conn);
			}
			return result; //
			// 
		}
	public ArrayList<Edge> searchNeighbors(int nid) {
		ArrayList<Edge> result = new ArrayList<Edge>();
		try {
			conn = ConnUtils.getConnection();//
			pstmt = conn.prepareStatement("SELECT * FROM NodeEdge Where nID1 = ? or nID2 = ?");
			pstmt.setInt(1, nid);
			pstmt.setInt(2, nid);
			rs = pstmt.executeQuery();					
			while (rs.next()){
				Edge e = new Edge(rs.getInt("nID1"), rs.getInt("nID2"));
				result.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return result;
	}
	public int countConnector(){
		int count = 0;
		try {
			conn = ConnUtils.getConnection();//
			pstmt = conn.prepareStatement("SELECT COUNT(DISTINCT cID) FROM Node");
			rs = pstmt.executeQuery();					
			while (rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return count;
	}
	
	public ArrayList<Edge> getNEdges(){
		ArrayList<Edge> result = new ArrayList<Edge>();
		try {
			conn = ConnUtils.getConnection();//
			//rs = pstmt.executeQuery();
			pstmt = conn.prepareStatement("SELECT * FROM NodeEdge");
			rs = pstmt.executeQuery();					
			while (rs.next()){
				Edge e = new Edge(rs.getInt("nID1"), rs.getInt("nID2"));
				result.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return result;
	}
	
	public ArrayList<Edge> getEdgesForC(){
		ArrayList<Edge> result = new ArrayList<Edge>();
		try {
			conn = ConnUtils.getConnection();//
			ArrayList<Integer> nodes = this.getAllC();
			for (int i = 0; i < nodes.size(); i++) {
				conn = ConnUtils.getConnection();
				pstmt = conn.prepareStatement("SELECT * FROM NodeEdge WHERE nID1 = ?");
				pstmt.setInt(1, nodes.get(i));
				rs = pstmt.executeQuery();					
					while (rs.next()){
						Edge e = new Edge(rs.getInt("nID1"), rs.getInt("nID2"));
						result.add(e);
					}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return result;
	}
	public ArrayList<Edge> getEdgesForN(){
		ArrayList<Edge> result = new ArrayList<Edge>();
		try {
			conn = ConnUtils.getConnection();//
			ArrayList<Integer> nodes = this.getAllN();
			for (int i = 0; i < nodes.size(); i++) {
				conn = ConnUtils.getConnection();
				pstmt = conn.prepareStatement("SELECT * FROM NodeEdge WHERE nID1 = ?");
				pstmt.setInt(1, nodes.get(i));
				rs = pstmt.executeQuery();					
					while (rs.next()){
						Edge e = new Edge(rs.getInt("nID1"), rs.getInt("nID2"));
						result.add(e);
					}
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return result;
	}
	
	public ArrayList<Node> getAllNodes() {
		ArrayList<Node> result = new ArrayList<Node>();
		try {
			conn = ConnUtils.getConnection();//
			//rs = pstmt.executeQuery();
			pstmt = conn.prepareStatement("SELECT * FROM Node");
			rs = pstmt.executeQuery();					
			while (rs.next()){
				Node n = new Node(rs.getInt("nID"), rs.getInt("status"), rs.getString("type"), rs.getInt("cID"), rs.getInt("flag"));
				result.add(n);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return result;
	}
	
	
	public ArrayList<Edge> getCEdges(){
		ArrayList<Edge> result = new ArrayList<Edge>();
		try {
			conn = ConnUtils.getConnection();//
			//rs = pstmt.executeQuery();
			pstmt = conn.prepareStatement("SELECT * FROM Node Where flag = ?");
			pstmt.setInt(1, 0);
			rs = pstmt.executeQuery();					
			while (rs.next()){
				Edge e = new Edge(rs.getInt("nID"), rs.getInt("cID"));
				result.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return result;
	}
	
	public boolean addCNode() {
		boolean result = false;
		try {
			conn = ConnUtils.getConnection();
			String sql = "INSERT INTO Node(status,type,flag)"
					+ "VALUES(?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.setString(2, "c");
			pstmt.setInt(3, -1);
			ri = pstmt.executeUpdate();
			if (ri > 0) {
				result = true;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return result;
	}		
	public int getLast() {
		int result = 0;
		try {
			conn = ConnUtils.getConnection();
			String sql = "SELECT max(nID) from Node";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();					
			while (rs.next()){
				result = rs.getInt(1);
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return result;
	}		
	public boolean updateCNode(int nid) {
		boolean result = false;
		try {
			conn = ConnUtils.getConnection();
			String sql = "UPDATE Node set cID = ? WHERE nID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nid);
			pstmt.setInt(2, nid);
			row = pstmt.executeUpdate();
			ri=pstmt.executeUpdate();
			if(ri>0){
				result=true;
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return result;
	}	
	
	public boolean addNNode(int cID, int flag){
		boolean result = false;
		try {
			conn = ConnUtils.getConnection();
			String sql = "INSERT INTO Node(status,type,cID, flag)"
					+ "VALUES(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.setString(2, "n");
			pstmt.setInt(3, cID);
			pstmt.setInt(4, flag);
			row = pstmt.executeUpdate();
			if (row > 0){
			    result =true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return result;
	}
	
	public boolean deleteEdge(int n1, int n2) {
		boolean result = false;

		try {
			conn = ConnUtils.getConnection();

			String deleteCat = "DELETE FROM NodeEdge WHERE nID1= ? AND nID2 = ?";

			pstmt = conn.prepareStatement(deleteCat);
			// 
			pstmt.setInt(1, n1);
			pstmt.setInt(2, n2);
			//
			ri = pstmt.executeUpdate();
			if (ri > 0) {
				result = true;
			}} catch (SQLException e) {
				result = false; // 
			} finally {
				ConnUtils.releaseConn(rs, pstmt, conn);
			}
			return result; //
			// 
	}
	
	public boolean addEdge(int n1, int n2) {
		boolean result = false;
		try {
			conn = ConnUtils.getConnection();
			String sql = "INSERT INTO NodeEdge(nID1,nID2)"
					+ "VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, n1);
			pstmt.setInt(2, n2);
			row = pstmt.executeUpdate();
			if (row > 0){
			    return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return result;
	}
	
	public List<Integer> getCNode(){
		List<Integer> result = new ArrayList<Integer>();
		try {
			conn = ConnUtils.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM Node WHERE type = 'c'");
			rs = pstmt.executeQuery();					
			while (rs.next()){
				result.add(rs.getInt("nID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return result;
	}
	
	public ArrayList<Integer> getNNodesByGroup(int groupId){
		ArrayList<Integer> result = new ArrayList<Integer>();
		try {
			conn = ConnUtils.getConnection();//
			//rs = pstmt.executeQuery();
			pstmt = conn.prepareStatement("SELECT * FROM Node WHERE groupId = ? AND type = ?");
			pstmt.setInt(1, groupId);
			pstmt.setString(2, "n");
			rs = pstmt.executeQuery();					
			while (rs.next()){
				result.add(rs.getInt("nID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return result;
	}
	
	public int countN(int nId) {
		int count = 0;
		try {
			conn = ConnUtils.getConnection();//
			//rs = pstmt.executeQuery();
			pstmt = conn.prepareStatement("SELECT * FROM NodeEdge WHERE nID1 = ? or nID2 = ?");
			pstmt.setInt(1, nId);
			pstmt.setInt(2, nId);
			rs = pstmt.executeQuery();					
			while (rs.next()){
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return count;
	}
	
	public int countG(int cId) {
		int count = 0;
		try {
			conn = ConnUtils.getConnection();//
			//rs = pstmt.executeQuery();
			pstmt = conn.prepareStatement("SELECT * FROM Node WHERE cID = ? AND flag = ? ");
			pstmt.setInt(1, cId);
			pstmt.setInt(2, 0);
			rs = pstmt.executeQuery();					
			while (rs.next()){
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return count;
	}
	
	public boolean belong(int nId, int cId) {
		int count = 0;
		try {
			conn = ConnUtils.getConnection();//
			//rs = pstmt.executeQuery();
			pstmt = conn.prepareStatement("SELECT * FROM Node WHERE nID = ? AND cID = ? ");
			pstmt.setInt(1, nId);
			pstmt.setInt(2, cId);
			rs = pstmt.executeQuery();					
			if (rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return false;
	}
	public int countTotalNodes(int cId) {
		int count = 0;
		try {
			conn = ConnUtils.getConnection();//
			//rs = pstmt.executeQuery();
			pstmt = conn.prepareStatement("SELECT * FROM Node WHERE cID = ? ");
			pstmt.setInt(1, cId);
			rs = pstmt.executeQuery();					
			while (rs.next()){
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return count;
	}
	
	public boolean edge(int n1, int n2) {
		try {
			conn = ConnUtils.getConnection();//
			//rs = pstmt.executeQuery();
			pstmt = conn.prepareStatement("SELECT * FROM NodeEdge WHERE nID1 = ? AND nID2 = ?");
			pstmt.setInt(1, n1);
			pstmt.setInt(2, n2);
			rs = pstmt.executeQuery();					
			if (rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return false;
	}
	public boolean contains(int nid) {
		try {
			conn = ConnUtils.getConnection();//
			//rs = pstmt.executeQuery();
			pstmt = conn.prepareStatement("SELECT * FROM Node WHERE nID = ? ");
			pstmt.setInt(1, nid);
			rs = pstmt.executeQuery();					
			if (rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnUtils.releaseConn(rs, pstmt, conn);
		}
		return false;
	}
	}