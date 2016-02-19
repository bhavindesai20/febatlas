package com.atlas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.atlas.entity.Title;

@Repository
public class TitleDAOImpl implements TitleDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public TitleDAOImpl() {
	}
	
	@Override
	@Transactional
	public void addTitle(Title t) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(t);
	}
	
	@Override
	@Transactional
	public List<Title> listTitles() {
		Session session = sessionFactory.getCurrentSession();
		List<Title> titleList = session.createQuery("from Title").list();
		return titleList;
	}

	@Override
	public void updateTitle(Title t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTitle(int id) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public Title getTitleById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Title> getTitleBySearchTerm(String title) {
		// TODO Auto-generated method stub
		return null;
	}

}
