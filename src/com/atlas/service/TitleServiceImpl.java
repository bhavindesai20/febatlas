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
	public void addTitle(Title t) {
		titleDAO.addTitle(t);
		
	}
	
	@Override
	public List<Title> listTitles() {
		return titleDAO.listTitles();
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
