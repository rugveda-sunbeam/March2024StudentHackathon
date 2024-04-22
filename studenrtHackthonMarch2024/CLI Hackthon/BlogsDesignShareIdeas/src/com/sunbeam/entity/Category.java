package com.sunbeam.entity;

public class Category {
	private int id;
	private String title;
	private String description;

	// Constructors, getters, setters
	public Category() {
	}

	public Category(String title, String description) {
		this.title = title;
		this.description = description;
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
		return "Category{" + "id=" + id + ", title='" + title + '\'' + ", description='" + description + '\'' + '}';
	}
}
