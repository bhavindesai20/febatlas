package com.atlas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	public void addUser(User u) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(u);

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
		User user = (User) session.load(User.class, new Integer(id));
		if (null != user) {
			session.delete(user);
			return user;
		} else {
			return null;
		}

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
		User user = (User) session.load(User.class, new Integer(id));
		System.out.println(user);
		if (null != user) {
			return user;
		}
		return null;
	}

}
