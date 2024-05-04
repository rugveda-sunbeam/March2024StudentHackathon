package com.sunbeam.entity;

import java.util.Date;

public class Blog {
	private int id;
	private String title;
	private String contents;
	private Date created_time;
	private int user_id;
	private int category_id;

	// Constructors, getters, setters
	public Blog() {
	}

	public Blog(String title, String contents, int user_id, int category_id) {
		this.title = title;
		this.contents = contents;
		this.created_time = new Date();
		this.user_id = user_id;
		this.category_id = category_id;
	}



	

	public Blog(int id, String title, String contents, Date created_time, int user_id, int category_id) {
		super();
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.created_time = created_time;
		this.user_id = user_id;
		this.category_id = category_id;
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

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	@Override
	public String toString() {
		return "Blog{" + "id=" + id + ", title='" + title + '\'' + ", contents='" + contents + '\'' + ", created_time="
				+ created_time + ", user_id=" + user_id + ", category_id=" + category_id + '}';
	}
}