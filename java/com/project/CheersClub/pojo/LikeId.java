package com.project.CheersClub.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LikeId implements Serializable {

    private static final long serialVersionUID = 1L;

	@Column(name = "postid")
    private int postId;

    @Column(name = "userid")
    private int userId;

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
    
    

    // Constructors, getters, and setters

    // Override equals and hashCode methods
}
