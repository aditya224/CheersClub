package com.project.CheersClub.pojo;

import javax.persistence.Entity;
import javax.persistence.Table;
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
@Table(name = "userpages")
public class Userpages {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userpageid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private Page page;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	private Status status;

	public Userpages() {
	}

	

	

	public int getUserpageid() {
		return userpageid;
	}





	public void setUserpageid(int userpageid) {
		this.userpageid = userpageid;
	}





	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	   
}
