package com.atlas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.atlas.dao.CommentsDAO;
import com.atlas.dao.RatingDAO;
import com.atlas.dao.TitleDAO;
import com.atlas.entity.Comments;
import com.atlas.entity.Rating;
import com.atlas.entity.Title;
import com.atlas.exception.MovieBadRequest;
import com.atlas.exception.MovieNotFound;

@Service
public class TitleServiceImpl implements TitleService{

	@Autowired
	@Qualifier("titleDAOImpl")
	private TitleDAO titleDAO;
	
	@Autowired
	@Qualifier("commentsDAOImpl")
	private CommentsDAO commentsDAO;

	@Autowired
	@Qualifier("ratingDAOImpl")
	private RatingDAO ratingDAO;
	
	public TitleServiceImpl(){}
	
	@Override
	public Title addTitle(Title t) throws MovieBadRequest {
		if (t.getTitle() == null) {
			throw new MovieBadRequest();
		}
		return titleDAO.addTitle(t);
	}

	@Override
	public Title updateTitle(Title t) throws MovieNotFound {
		Title existing = titleDAO.getTitleById(t.getId());
		if (existing == null) {
			throw new MovieNotFound();
		} else {
			return titleDAO.updateTitle(t);
		}
	}

	@Override
	public Title removeTitle(int id) throws MovieNotFound {
		Title existing = titleDAO.getTitleById(id);
		if (existing == null) {
			throw new MovieNotFound();
		} else {
			List<Comments> commentlist= commentsDAO.getCommentsForTitle(id);
			List<Rating> ratinglist= ratingDAO.getRatingByTitle(id);
			if(commentlist!=null){
				commentsDAO.removeCommentsForTitle(id);			}
			if(ratinglist!=null){
				ratingDAO.removeRatingForTitle(id);
			}
			return titleDAO.removeTitle(id);
		}
	}

	@Override
	public List<Title> listTitles() {
		return titleDAO.listTitles();
	}

	@Override
	public Title getTitleById(int id) throws MovieNotFound {
		Title title =  titleDAO.getTitleById(id);
		if(title == null) {
			throw new MovieNotFound();
		}
		else {
			return title;
		}
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
