package com.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.NodeDao;
import com.entity.Edge;
import com.entity.Node;

public class addNode extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Edge> res = new ArrayList<Edge>();
		List<Node> str = new ArrayList<Node>();
		String s1 = request.getParameter("n1");
		String s2 = request.getParameter("n2");
		NodeDao nDao = new NodeDao();
		int gid = Integer.parseInt(request.getParameter("gid"));
		int flag = 0;
		
		if (request.getParameter("flag") == null) {
			flag = -1;
		}
		if (nDao.countTotalNodes(gid) == 7) {
			request.setAttribute("error", "Nodes in a pattern has reached maximum connections");
		}
		if (flag == 0 && nDao.countG(gid) == 3) {// connector node is full
			request.setAttribute("error", "Connector node has reached maximum connections");
		} else if (s1.length() == 0 && s2.length() == 0) {
			// corner case1: only connector node
			if (flag == 0 && nDao.countTotalNodes(gid)==1){
				nDao.addNNode(gid, flag);
				int id = nDao.getLast(); // update database
			} else {
				request.setAttribute("error", " the pattern must be closed to form a ring ");
			} 
		} else if (s1.length() == 0) {
			int n2 = Integer.parseInt(s2);
			if (!nDao.belong(n2, gid)) {
				request.setAttribute("error", " Non-connector should be in the pattern you choose");
			} else if (flag == 0 &&nDao.countN(n2) < 2) {
				nDao.addNNode(gid, flag);
				int id = nDao.getLast(); // update database
				nDao.addEdge(id, n2);// update database;
			} else {
				request.setAttribute("error", " Non-connector node can only connected with up to two non-connector nodes ");
			}
		} else if (s2.length() == 0) {
			// corner case2: add one to two
			int n1 = Integer.parseInt(s1);
			// corner case2: add one to two
			if (!nDao.belong(n1, gid)) {
				request.setAttribute("error", " Non-connector should be in the pattern you choose");} 
			else if (flag == 0 &&nDao.countN(n1) <2) {
				nDao.addNNode(gid, flag);
				int id = nDao.getLast(); // update database
				nDao.addEdge(id, n1);// update database;
			} else {
				request.setAttribute("error", " Non-connector node can only connected with up to two non-connector nodes ");
			}
		} else {
			int n1 = Integer.parseInt(s1);// if null, -1 should be 
			int n2 = Integer.parseInt(s2);
			if (!nDao.belong(n1, gid) && !nDao.belong(n2, gid)) { 
				request.setAttribute("error", " Non-connector should be in the pattern you choose ");
			} else if (nDao.countN(n1) == 1 && nDao.countN(n2) == 1) {
				nDao.addNNode(gid, flag);
				int id = nDao.getLast(); // update database
				nDao.addEdge(id, n1);
				nDao.addEdge(id, n2);
			} else if (nDao.countN(n1) == 2 && nDao.countN(n2) == 2 && (nDao.edge(n1, n2)|| nDao.edge(n2, n1))) {
				nDao.deleteEdge(n1, n2);
				nDao.deleteEdge(n2, n1);
				nDao.addNNode(gid, flag);
				int id = nDao.getLast(); // update database
				nDao.addEdge(id, n1);
				nDao.addEdge(id, n2);
			} else {
				request.setAttribute("error", " Non-connector node can only connected with up to two non-connector nodes");
			}
		}
		res.addAll(nDao.getNEdges());
		res.addAll(nDao.getCEdges());
		str.addAll(nDao.getAllNodes());
		request.setAttribute("Edges", res);
		request.setAttribute("nodes", str);
		request.getRequestDispatcher("addNode.jsp").forward(request, response);
		
	}

}
