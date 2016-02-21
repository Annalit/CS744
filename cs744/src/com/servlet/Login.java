package com.servlet;

import java.io.IOException;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.annabel.UserDao;
import com.entity.User;
import com.utils.Base64Coder;

public class Login extends HttpServlet {

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			this.doPost(req, resp);
		
	}
	
	private String encrypt(String message) {
		MessageDigest md;
		byte[] buffer;
		byte[] digest = new byte[0];
		try {
			buffer = message.getBytes(); // getBytes translates String to byte[]
			md = MessageDigest.getInstance("SHA-256");
			md.update(buffer);
			digest = md.digest();
		} catch (Exception ex) {
			System.out.println("Encryption Error!");
		}
		return String.valueOf(Base64Coder.encode(digest));
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getContextPath();
		String uid = req.getParameter("uid");
		String password = req.getParameter("password");
		HttpSession session = req.getSession();
		UserDao userdao = new UserDao();
		if(session!=null) {
			session.invalidate();
			session = req.getSession();
		}
		if (uid.equals("admin") && password.equals("admin")) {
			session.setAttribute("firstname", "admin");
			session.setAttribute("role", "admin");
			req.getRequestDispatcher("addUser.jsp").forward(req, resp);
		} else if (userdao.checkUser(uid, encrypt(password))) {
			String first_name = userdao.getFristNamebyId(uid);
			session.setAttribute("firstname", first_name);
			session.setAttribute("uid", uid);
			session.setAttribute("role", "user");
			if (userdao.checkFirstTime(uid)) {
				req.getRequestDispatcher("setQuestion.jsp").forward(req, resp);
			} else {
				req.getRequestDispatcher("ansQuestion.jsp").forward(req, resp);
			}
		} else {
			req.setAttribute("currId", "error");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
	}
}