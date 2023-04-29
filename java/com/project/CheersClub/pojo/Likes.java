package com.project.CheersClub.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "likes")
public class Likes {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int likeid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postid", referencedColumnName = "postid")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    private User user;

	public int getId() {
		return likeid;
	}

	public void setId(int likeid) {
		this.likeid = likeid;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
}


