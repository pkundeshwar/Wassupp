package com.greenfurniture.onlineorder.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JOB_POSTING")
public class JobPosting implements Serializable {

	private static final long serialVersionUID = 4539878308786075188L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "REQUIREMENTS")
	private String requirements;

	@Column(name = "TITLE")
	private String title;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "JobPosting [id=" + id + ", description=" + description + ", location=" + location + ", requirements="
				+ requirements + ", title=" + title + "]";
	}

}
