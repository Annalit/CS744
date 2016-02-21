package com.dao;

import java.util.List;
import com.entity.User;

public interface UserDAO {
	void save(User user);
	void delete(User user);
	User findUserByID(String uID);
	List<User> findAllUser();
}
