package com.atlas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atlas.entity.Title;
import com.atlas.service.TitleService;


@RestController
@RequestMapping("/titles")
public class TitleController {

	@Autowired
	private TitleService titleService;

	@RequestMapping(method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Title> getAllTitles() {
		return this.titleService.listTitles();
	}
	
	@RequestMapping(value="{id}",method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Title getTitleById(@PathVariable("id") int id) {
		return this.titleService.getTitleById(id);
	}

	@RequestMapping(method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public Title addTitle(@RequestBody Title t) {
		return this.titleService.addTitle(t);	
	}
	
	@RequestMapping(method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public Title updateTitle(@RequestBody Title t) {
		return this.titleService.updateTitle(t);
	}
	
	@RequestMapping(value="{id}",method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public Title deleteTitleById(@PathVariable("id") int id) {
		return this.titleService.removeTitle(id);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Title> getTitleByTitle(@RequestParam(required=true, value="q") String search) {
		return this.titleService.getTitleBySearchTerm(search);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Title> getTitleByYear(@RequestParam(required=true, value="year") int year) {
		return this.titleService.getTitleByYear(year);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Title> getTitleByType(@RequestParam(required=true, value="type") String type) {
		return this.titleService.getTitleByType(type);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Title> getTitleByGenre(@RequestParam(required=true, value="genre") String genre) {
		return this.titleService.getTitleByGenre(genre);
	}

}
