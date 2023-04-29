package com.project.CheersClub.pojo;


import java.util.ArrayList;
import java.util.List;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import org.hibernate.action.internal.OrphanRemovalAction;
import org.springframework.stereotype.Component;


@Component
@Entity
@Table(name="userDirectory")
public class User {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int userid;
	@Column(name = "userType")
	private UserType userType;
    private String firstName;
    private String lastName;
    @Column(name = "email")
    private String email;
    private String password;
    @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="addressID")
    private Address address;
    @OneToMany(mappedBy = "user", cascade = javax.persistence.CascadeType.ALL, fetch=FetchType.LAZY,orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = javax.persistence.CascadeType.ALL, fetch=FetchType.LAZY,orphanRemoval = true)
    private Set<Likes> likes = new HashSet<>();
    
    @OneToMany(mappedBy = "moderator", cascade = javax.persistence.CascadeType.ALL, fetch=FetchType.LAZY,orphanRemoval = true)
    private Set<Page> moderatorpages = new HashSet<>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_page"
            ,joinColumns = {@JoinColumn(name = "userid")},
            inverseJoinColumns = {@JoinColumn(name = "id")})
    private List<Page> userpages = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Topic> topics = new ArrayList<>();
    
    private Status status;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    
    public User() {
        
    }

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Set<Likes> getLikes() {
		return likes;
	}

	public void setLikes(Set<Likes> likes) {
		this.likes = likes;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<Page> getModeratorpages() {
		return moderatorpages;
	}

	public void setModeratorpages(Set<Page> moderatorpages) {
		this.moderatorpages = moderatorpages;
	}

	public List<Page> getUserpages() {
		return userpages;
	}

	public void setUserpages(List<Page> userpages) {
		this.userpages = userpages;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	

	
	
    
}
