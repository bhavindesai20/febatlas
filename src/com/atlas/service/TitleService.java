package com.atlas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atlas.entity.Title;
import com.atlas.exception.MovieBadRequest;
import com.atlas.exception.MovieNotFound;

@Service
public interface TitleService {

	public Title addTitle(Title t) throws MovieBadRequest;

	public Title updateTitle(Title t) throws MovieNotFound;

	public Title removeTitle(int id) throws MovieNotFound;

	public List<Title> listTitles();

	public Title getTitleById(int id) throws MovieNotFound;

	public List<Title> getTitleBySearchTerm(String title);

	public List<Title> getTitleByYear(int year);

	public List<Title> getTitleByType(String type);

	public List<Title> getTitleByGenre(String genre);

	
}
