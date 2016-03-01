package com.atlas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.atlas.entity.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public UserDAOImpl() {
	}

	@Override
	@Transactional
	public User addUser(User u) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(u);
		return u;
	}

	@Override
	@Transactional
	public User updateUser(User u) {
		Session session = sessionFactory.getCurrentSession();
		session.update(u);
		return u;
	}

	@Override
	@Transactional
	public User removeUser(int id) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, new Integer(id));
		session.delete(user);
		return user;
	}

	@Override
	@Transactional
	public List<User> listUser() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<User> userList = session.createQuery("from User").list();
		return userList;
	}

	@Override
	@Transactional
	public User getUserById(int id) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, new Integer(id));
		return user;
	}

	@Override
	public boolean authenticate(int id, String password) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.load(User.class, new Integer(id));
		if (null != user) {
			if (BCrypt.checkpw(password, user.getPassword()))
				return true;
			else
				return false;
		}
		return false;
	}


}
