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
		List<Title> titleListByTitle = new ArrayList<Title>();
		Iterator<Title> allTitles = listTitles().iterator();
		while (allTitles.hasNext()) {
			Title getTitle = allTitles.next();
			boolean valid = getTitle.getTitle().contains(title);
			if (valid == true) {
				titleListByTitle.add(getTitle);
			}
		}
		return titleListByTitle;
	}
	
	@Override
	@Transactional
	public List<Title> getTitleByYear(int year) {
		String hql ="from title where year="+year;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
	    List<Title> listTitle = (List<Title>) query.list();
		return listTitle;
	}

	@Override
	public List<Title> getTitleByType(String type) {
		String hql ="from title where type="+type;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
	    List<Title> listTitle = (List<Title>) query.list();
		return listTitle;
	}


	@Override
	public List<Title> getTitleByGenre(String genre) {
		List<Title> titleListByGenre = new ArrayList<Title>();
		Iterator<Title> allTitles = listTitles().iterator();
		while (allTitles.hasNext()) {
			Title getTitle = allTitles.next();
			boolean valid = getTitle.getGenre().contains(genre);
			if (valid == true) {
				titleListByGenre.add(getTitle);
			}
		}
		return titleListByGenre;
	}

}
