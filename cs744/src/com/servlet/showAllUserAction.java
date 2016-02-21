package com.servlet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.dao.UserDAO;
import com.dao.UserDAOImpl;
import com.entity.User;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class showAllUserAction extends ActionSupport {
	private UserDAO userDAO = new UserDAOImpl();
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<User> users = this.userDAO.findAllUser();  
		request.setAttribute("ulist", users);
		return SUCCESS;
	}
}