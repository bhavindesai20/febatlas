package com.atlas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public Title addTitle(@RequestBody Title t) {
		this.titleService.addTitle(t);
		return t;
	}

}
