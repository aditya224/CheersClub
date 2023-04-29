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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
@Component
@Entity
@Table(name="postDirectory")
public class Post {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int postid;
	@Column(name = "postType")
	private PostType postType;
	private String postTitle;
    private String postContent;
    
    @Column(name = "base64Image", columnDefinition = "TEXT")
    private String base64Image;
    private Blob data;
    private int likeCount;
    
    @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="userID")
    private User user;
    private Timestamp createdOn;
    
    @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Likes> likes = new HashSet<>();
    
	public int getPostid() {
		return postid;
	}
	public void setPostid(int postid) {
		this.postid = postid;
	}
	public PostType getPostType() {
		return postType;
	}
	public void setPostType(PostType postType) {
		this.postType = postType;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public Blob getData() {
		return data;
	}
	public void setData(Blob data) {
		this.data = data;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public Set<Likes> getLikes() {
		return likes;
	}
	public void setLikes(Set<Likes> likes) {
		this.likes = likes;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	public String getBase64Image() {
		byte[] blobBytes;
		try {
			//blobBytes = this.data.getBytes(1, (int) this.data.length());
			//base64Image = Base64.getEncoder().encodeToString(blobBytes);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return base64Image;
		
	}
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
    
    

}
