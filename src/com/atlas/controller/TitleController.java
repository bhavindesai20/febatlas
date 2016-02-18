package com.atlas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atlas.entity.Title;
import com.atlas.service.TitleService;


@Controller
public class TitleController {

	@Autowired
	private TitleService titleService;

	@RequestMapping(value = "/api/titles", method = RequestMethod.GET)
	public @ResponseBody List<Title> getAllTitles() {
		return this.titleService.listTitles();
	}

	@RequestMapping(value = "/api/titles", method = RequestMethod.POST)
	public @ResponseBody Title addTitle(@RequestBody Title t) {
		this.titleService.addTitle(t);
		return t;
	}

}
