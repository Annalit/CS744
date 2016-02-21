package com.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.dao.UserDAO;
import com.dao.UserDAOImpl;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class DeleteUserAction extends ActionSupport{
	private UserDAO userDAO = new UserDAOImpl();
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	private String uid;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String execute(){
		userDAO.delete(this.userDAO.findUserByID(uid));
		return SUCCESS;
	}

}
