package com.atlas.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
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
	public Title addTitle(Title t) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(t);
		return t;
	}

	@Override
	@Transactional
	public Title updateTitle(Title t) {
		Session session = sessionFactory.getCurrentSession();
		session.update(t);
		return t;
	}

	@Override
	@Transactional
	public Title removeTitle(int id) {
		Session session = sessionFactory.getCurrentSession();
		Title title = (Title)session.load(Title.class, new Integer(id));
		if (null != title) {
			session.delete(title);
			return title;
		}
		else{
			return null; //throw exception
		}
	}

	@Override
	@Transactional
	public List<Title> listTitles() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Title> titleList = session.createQuery("from Title").list();
		return titleList;
	}

	@Override
	@Transactional
	public Title getTitleById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Title title = (Title)session.load(Title.class, new Integer(id));
		System.out.println(title);
		if (null != title) {
			return title;
		}
		return null;
	}

	@Override
	@Transactional
	public List<Title> getTitleBySearchTerm(String title) {
		
		Query query = sessionFactory.getCurrentSession().createQuery("from Title t where str(t.title) like :searchTerm");
		@SuppressWarnings("unchecked")
		List<Title> titleListByTitle = query.setParameter("searchTerm","%"+ title + "%").list();
		return titleListByTitle;
	}
	
	@Override
	@Transactional
	public List<Title> getTitleByYear(int year) {
		String hql ="from Title where year="+year;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
	    List<Title> listTitle = (List<Title>) query.list();
		return listTitle;
	}

	@Override
	@Transactional
	public List<Title> getTitleByType(String type) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Title t where str(t.type) = :searchTerm");
		@SuppressWarnings("unchecked")
		List<Title> titleListByType = query.setParameter("searchTerm",type).list();
		return titleListByType;
	}


	@Override
	@Transactional
	public List<Title> getTitleByGenre(String genre) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Title t where str(t.genre) like :searchTerm");
		@SuppressWarnings("unchecked")
		List<Title> titleListByGenre = query.setParameter("searchTerm","%"+ genre + "%").list();
		return titleListByGenre;
	}
}
