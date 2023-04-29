package com.project.CheersClub.pojo;

import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "page")
public class Page {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String pageTitle;
    
    private String pageDescription;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderatorid", referencedColumnName = "userid")
    private User moderator;
    
    @ManyToMany(mappedBy = "userpages")
    private List<User> users = new ArrayList<>();
    
    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL)
    private List<Topic> topics = new ArrayList<>();
    
    

	public Page() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public User getModerator() {
		return moderator;
	}

	public void setModerator(User user) {
		this.moderator = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getPageDescription() {
		return pageDescription;
	}

	public void setPageDescription(String pageDescription) {
		this.pageDescription = pageDescription;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	

    // getters and setters
    

}
