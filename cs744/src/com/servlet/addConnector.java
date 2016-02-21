package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.NodeDao;
import com.entity.Edge;
import com.entity.Node;

public class addConnector extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Edge> res = new ArrayList<Edge>();
		ArrayList<Node> nodes = new ArrayList<Node>();
		NodeDao nDao = new NodeDao();
		String[] checked = request.getParameterValues("checkedC");
		nDao.addCNode();
		int node = nDao.getLast();
		nDao.updateCNode(node);
		if (checked == null || checked.length == 0) {
			
		} else {
			int[] checkedC = new int[checked.length];
			for (int i = 0; i < checkedC.length; i++) {
			checkedC[i] = Integer.parseInt(checked[i]);
			}
			for (int i = 0; i < checkedC.length;i++){
				nDao.addEdge(node, checkedC[i]);
				}
			}
		res.addAll(nDao.getNEdges());
		res.addAll(nDao.getCEdges());
		nodes.addAll(nDao.getAllNodes());
		request.setAttribute("newEdges", res);
		request.setAttribute("nodes", nodes);
		request.getRequestDispatcher("addConnector.jsp").forward(request, response);
	}

}
