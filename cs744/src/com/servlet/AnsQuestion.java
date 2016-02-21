package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.QuestionDao;
import com.annabel.UserDao;

public class AnsQuestion extends HttpServlet {

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
		QuestionDao qDao = new QuestionDao();
		UserDao uDao = new UserDao();
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("uid");
		int qID = Integer.parseInt(request.getParameter("qId"));
		String ans = request.getParameter("ans");
		int count = 0;
		if (uDao.checkSecurity(uid, qID, ans)){
			request.setAttribute("count", count);
			request.setAttribute("answered", null);
			out.print("true") ;
		} else {
			request.setAttribute("currId", "error");
			HashSet<Integer> answered;
			if(session.getAttribute("answered") == null) {
				answered = new HashSet<Integer>();
			} else {
				answered = (HashSet<Integer>) session.getAttribute("answered");
			}
			if(session.getAttribute("count") == null) {
				count = 0;
			} else {
				count = (Integer) session.getAttribute("count");
			}
			count++;
			answered.add(qID);
			out.print("" + count) ;
			session.setAttribute("count", count);
			session.setAttribute("answered", answered);
			request.getRequestDispatcher("ansQuestion.jsp").forward(request, response);
		}
	}
}
