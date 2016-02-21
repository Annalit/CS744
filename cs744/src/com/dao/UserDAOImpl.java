package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.*;
import com.entity.User;

public class UserDAOImpl implements UserDAO{

	@Override
	public void save(User user) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(user);
			t.commit();
		} catch(Exception e){
			e.printStackTrace();
			t.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}	
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUser() {
		List<User> users = new ArrayList<User>();
		String hql = "from User u order by u.uid asc";
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			users = query.list();
			t.commit();
		} catch(Exception e){
			e.printStackTrace();
			t.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return users;
	}

	@Override
	public void delete(User user) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(user);
			t.commit();
		} catch(Exception e){
			e.printStackTrace();
			t.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}	
	}

	@Override
	public User findUserByID(String uID) {
		User user = null;
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		try {
			user = (User)session.get(User.class,uID);
			t.commit();
		} catch(Exception e){
			e.printStackTrace();
			t.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}	
		return user;
	}

}
