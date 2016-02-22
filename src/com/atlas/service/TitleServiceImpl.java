package com.atlas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atlas.dao.TitleDAO;
import com.atlas.entity.Title;

@Service
public class TitleServiceImpl implements TitleService{

	@Autowired
	private TitleDAO titleDAO;
	
	public TitleServiceImpl(){}
	
	@Override
	public Title addTitle(Title t) {
		return titleDAO.addTitle(t);
	}

	@Override
	public Title updateTitle(Title t) {
		return titleDAO.updateTitle(t);
		
	}

	@Override
	public Title removeTitle(int id) {
		return titleDAO.removeTitle(id);
		
	}

	@Override
	public List<Title> listTitles() {
		return titleDAO.listTitles();
	}

	@Override
	public Title getTitleById(int id) {
		return titleDAO.getTitleById(id);
	}

	@Override
	public List<Title> getTitleBySearchTerm(String title) {
		return titleDAO.getTitleBySearchTerm(title);
	}

	@Override
	public List<Title> getTitleByYear(int year) {
		return titleDAO.getTitleByYear(year);
	}

	@Override
	public List<Title> getTitleByType(String type) {
		return titleDAO.getTitleByType(type);
	}

	@Override
	public List<Title> getTitleByGenre(String genre) {
		return titleDAO.getTitleByGenre(genre);
	}


}
