package com.servlet;

import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.dao.UserDAO;
import com.dao.UserDAOImpl;
import com.entity.User;
import com.opensymphony.xwork2.ActionSupport;
import com.utils.Base64Coder;

@SuppressWarnings("serial")
public class AddUserAction extends ActionSupport {
	private UserDAO userDAO = new UserDAOImpl();
	private String uid;
	private String password;
	private String firstname;
	private String lastname;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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
	
	public void validate(){
		
	}

	public String execute() {
		this.clearErrorsAndMessages();
		String [] num = {"1","2","3","4","5","6","7","8","9","0"};
		String [] str = {"(",")","{","}","[","]",":","'",";","<",">","/",",",".","_","-","+","|","`"};
		for (int i=0;i<num.length;i++){
			if (uid.startsWith(num[i])){
				this.addFieldError("addUserError","The first character of user ID can't be number!");
				return INPUT;
			}
		}
		for (int i=0;i<str.length;i++){
			if (password.contains(str[i])){
				this.addFieldError("addUserError","The password can't contain the invalid character!");
				return INPUT;
			}
		}
		if ( userDAO.findUserByID(uid) != null){
			this.addFieldError("addUserError","This user ID has existed!");
			return INPUT;
		}
		User user = new User();
		user.setUid(uid);
		user.setPassword(encrypt(password));
		user.setFirstName(firstname);
		user.setLastName(lastname);
		userDAO.save(user);
		return SUCCESS;
	}

}
