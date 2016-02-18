package com.atlas.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atlas.entity.Title;

@Repository
public interface TitleDAO {
	
	public void addTitle(Title t);

	public void updateTitle(Title t);

	public void removeTitle(int id);
	
	public List<Title> listTitles();
	
	public Title getTitleById(int id);
	
	public List<Title> getTitleBySearchTerm(String title);
	
}
