package com.atlas.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atlas.entity.Title;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TitleDAOTest {

	@Mock
	private SessionFactory sessionFactory;
	
	@Mock
	private Session session;
	
	@Mock
	private Query query;
	
	@InjectMocks
	private TitleDAO dao = new TitleDAOImpl();
	
	private Title title;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		title = new Title();
		title.setId(1);
		title.setActors("Actor-1,Actor-2");
		title.setAwards("Award-1,Award-2");
		title.setCountry("India");
		title.setDirector("Director-1");
		title.setGenre("Action");
		title.setImdbId("tt884455rt");
		title.setImdbRating(4.2);
		title.setImdbVotes(11223344);
		title.setLanguage("Hindi");
		title.setMetaScore(6);
		title.setPlot("This is plot");
		title.setPoster("This is poster");
		title.setRated("R");
		title.setReleased(new Date());
		title.setRuntime(22);
		title.setTitle("Movie title");
		title.setType("Movie");
		title.setWriter("Writer-1");
		title.setYear(2002);
	}
	
	@Test
	public void testAddTitle () {
		dao.addTitle(title);
		Mockito.verify(session).persist(title);
	}
	
	@Test
	public void testupdateTitle () {
		dao.updateTitle(title);
		Mockito.verify(session).update(title);
	}
	
	@Test
	public void testremoveTitle () {
		Mockito.when(session.get(Title.class, new Integer(title.getId()))).thenReturn(title);
		dao.removeTitle(title.getId());
		Mockito.verify(session).delete(title);
	}
	
	@Test
	public void testlistTitles() {
		List<Title> expected = Arrays.asList(title);
		Mockito.when(session.createQuery("from Title")).thenReturn(query);
		Mockito.when(query.list()).thenReturn(expected);
		List<Title> actual = dao.listTitles();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testgetTitleById () {
		Mockito.when(session.get(Title.class, new Integer(title.getId()))).thenReturn(title);
		Title actual = dao.getTitleById(title.getId());
		Assert.assertEquals(title, actual);
	}
	
	@Test
	public void testlistTitlesBySearch() {
		List<Title> expected = Arrays.asList(title);
		Mockito.when(session.createQuery("from Title t where str(t.title) like :searchTerm")).thenReturn(query);
		Mockito.when(query.setParameter("searchTerm","%" + title.getTitle() + "%")).thenReturn(query);
		Mockito.when(query.list()).thenReturn(expected);
		List<Title> actual = dao.getTitleBySearchTerm(title.getTitle());
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testlistTitlesByYear() {
		List<Title> expected = Arrays.asList(title);
		Mockito.when(session.createQuery("from Title where year="+title.getYear()+"")).thenReturn(query);
		Mockito.when(query.list()).thenReturn(expected);
		List<Title> actual = dao.getTitleByYear(title.getYear());
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testlistTitlesByType() {
		List<Title> expected = Arrays.asList(title);
		Mockito.when(session.createQuery("from Title t where str(t.type) = :searchTerm")).thenReturn(query);
		Mockito.when(query.setParameter("searchTerm",title.getType())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(expected);
		List<Title> actual = dao.getTitleByType(title.getType());
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testlistTitlesByGenre() {
		List<Title> expected = Arrays.asList(title);
		Mockito.when(session.createQuery("from Title t where str(t.genre) like :searchTerm")).thenReturn(query);
		Mockito.when(query.setParameter("searchTerm","%" + title.getGenre() + "%")).thenReturn(query);
		Mockito.when(query.list()).thenReturn(expected);
		List<Title> actual = dao.getTitleByGenre(title.getGenre());
		Assert.assertEquals(expected, actual);
	}
	
	@Configuration
	public static class TestConfig {
		
	}
	
}
