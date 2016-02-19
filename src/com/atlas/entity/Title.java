package com.atlas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

@Component
@Entity
@Table(name="Title")
public class Title {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="year")
	private int year;
	
	@Column(name="rated")
	private String rated;
	
	@Column(name="released")
	private Date released;
	
	@Column(name="runtime")
	private int runtime;
	
	@Column(name="genre")
	private String genre;
	
	@Column(name="director")
	private String director;
	
	@Column(name="actor")
	private String actor;
	
	@Column(name="writer")
	private String writer;
	
	@Column(name="plot")
	private String plot;
	
	@Column(name="language")
	private String language;
	
	@Column(name="country")
	private String country;
	
	@Column(name="award")
	private String award;
	
	@Column(name="poster")
	private String poster;
	
	@Column(name="metascore")
	private int metaScore;
	
	@Column(name="imdbrating")
	private double imdbRating;
	
	@Column(name="imdbvotes")
	private long imdbVotes;
	
	@Column(name="imdbid")
	private String imdbId;
	
	@Column(name="titletype")
	private String titleType;
	 
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getId() {
		return id;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRated() {
		return rated;
	}

	public void setRated(String rated) {
		this.rated = rated;
	}
	
	@JsonSerialize(using=DateSerializer.class)
	public Date getReleased() {
		return released;
	}

	public void setReleased(Date released) {
		this.released = released;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public int getMetaScore() {
		return metaScore;
	}

	public void setMetaScore(int metaScore) {
		this.metaScore = metaScore;
	}

	public double getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(double imdbRating) {
		this.imdbRating = imdbRating;
	}

	public long getImdbVotes() {
		return imdbVotes;
	}

	public void setImdbVotes(long imdbVotes) {
		this.imdbVotes = imdbVotes;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getTitleType() {
		return titleType;
	}

	public void setTitleType(String titleType) {
		this.titleType = titleType;
	}

}
