package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.NodeDao;
import com.entity.Edge;

public class deleteNode extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Edge> res = new ArrayList<Edge>();
		NodeDao nDao = new NodeDao();
		int nid = Integer.parseInt(request.getParameter("nid"));
		// the node to be deleted
		if (!nDao.contains(nid)) {
			request.setAttribute("error", " the node doesn't exist ");
		} else if (nDao.whetherC(nid)) {
			ArrayList<Edge> neighbors = nDao.searchNeighbors(nid);	
			ArrayList<Integer> patterns = new ArrayList<Integer>();
			for (int i = 0; i < neighbors.size(); i++) {
				int node1 = neighbors.get(i).getNode1();
				int node2 = neighbors.get(i).getNode2();
				if (node1 != nid) {
					patterns.add(node1);
				}
				if (node2 != nid) {
					patterns.add(node2);
				}
			}
			nDao.deleteInNodeEdge(nid); // delete connections with other connectors
			nDao.deletePattern(nid);// delete connector and its pattern;
			ArrayList<Integer> good = new ArrayList<Integer>();
			ArrayList<Integer> bad = new ArrayList<Integer>();
			// get all the patterns that would be effected
			for (int i = 0; i < patterns.size(); i++) {
				if (nDao.countN(patterns.get(i)) == 0) {
					 bad.add(patterns.get(i));
				} else {
					good.add(patterns.get(i));
				}
			}
			for (int i = 0; i < bad.size() - 1; i++) {
				nDao.addEdge(bad.get(i), bad.get(i + 1));
			}
			if (bad.size() > 2) {
				nDao.addEdge(bad.get(bad.size() - 1), bad.get(0));
			}
			if (good.size() != 0 && bad.size()!= 0) {
				nDao.addEdge(good.get(0), bad.get(0));
			}		
		} else {	
		ArrayList<Edge> neighbors = nDao.searchNeighbors(nid);
		
		if (neighbors.size() == 0) {
			nDao.deleteInNode(nid);
		} else if(neighbors.size() == 1) {
			nDao.deleteInNode(nid);
			nDao.deleteInNodeEdge(nid);
		} else {
			ArrayList<Integer> effected = new ArrayList<Integer>();
			for (int i = 0; i < neighbors.size(); i++) {
				int node1 = neighbors.get(i).getNode1();
				int node2 = neighbors.get(i).getNode2();
				if (node1 != nid) {
					effected.add(node1);
				}
				if (node2 != nid) {
					effected.add(node2);
				}	
			}
			if (effected.size() ==2) {
				nDao.addEdge(effected.get(0), effected.get(1));
			}
			nDao.deleteInNode(nid);
			nDao.deleteInNodeEdge(nid);
		}
		}
		res.addAll(nDao.getNEdges());
		res.addAll(nDao.getCEdges());
		request.setAttribute("newEdges", res);
		request.getRequestDispatcher("deleteNode.jsp").forward(request, response);		
	}

}
