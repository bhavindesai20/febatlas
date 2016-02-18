package com.atlas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atlas.entity.Title;

@Service
public interface TitleService {

	public void addTitle(Title t);

	public void updateTitle(Title t);

	public void removeTitle(int id);

	public List<Title> listTitles();

	public Title getTitleById(int id);

	public List<Title> getTitleBySearchTerm(String title);
	
}
