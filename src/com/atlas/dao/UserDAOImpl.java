package com.atlas.dao;

import java.util.List;

import org.hibernate.Query;
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
	@Transactional
	public User login(String email, String password) {
		User user = getUserByEmail(email);
		if (user != null && BCrypt.checkpw(password, user.getPassword())) {
			return user;
		}
		return null;
	}
	
	@Override
	@Transactional
	public User getUserByEmail(String email) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from User u where str(u.email) = :searchTerm");
		@SuppressWarnings("unchecked")
		List<User> user = query.setParameter("searchTerm",email).list();
		if(user.size()!= 0){
		  return user.get(0);
		}
		return null;
	}




}
