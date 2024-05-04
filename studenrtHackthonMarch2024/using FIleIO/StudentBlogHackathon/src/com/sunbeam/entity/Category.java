package com.sunbeam.entity;

import java.util.Date;

public class Category {
	private int id;
	private String title;
	private String description;
	private Date created_time;

	// Constructors, getters, setters
	public Category() {
	}

	
	
	public Category(int id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.created_time = new Date();
	}



	public Category(int id, String title, String description, Date created_time) {
	
		this.id = id;
		this.title = title;
		this.description = description;
		this.created_time = created_time;
	}


	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}

	public int getId() {
		return id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Category{" + "id=" + id + ", title='" + title + '\'' + ", description='" + description + created_time+ '\'' + '}';
	}
}
