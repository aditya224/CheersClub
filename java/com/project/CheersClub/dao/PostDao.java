package com.project.CheersClub.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.*;

import org.hibernate.Criteria;
import org.springframework.stereotype.Component;

import com.project.CheersClub.pojo.Address;
import com.project.CheersClub.pojo.Comment;
import com.project.CheersClub.pojo.Post;
import com.project.CheersClub.pojo.PostType;
import com.project.CheersClub.pojo.Topic;
import com.project.CheersClub.pojo.User;
import com.project.CheersClub.pojo.Likes;
@Component
public class PostDao extends DAO {
	public void save(Post p) {
        begin();
        getSession().save(p);
        commit();
    }
    
    public void update(Post p) {
        try {
            begin();
            getSession().update(p);
            commit();
        } catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    
    public void delete(Post p) {
        begin();
        getSession().delete(p);
        commit();
    }
    
    public Post getPost(int pid) {
    	
        Post post = getSession().get(Post.class, pid);
        
        return post;
    }
    public List<Post> getPosts(){
//    	CriteriaBuilder builder = getSession().getCriteriaBuilder();
//    	CriteriaQuery<Post> query = builder.createQuery(Post.class);
//    	Root<Post> root = query.from(Post.class);
//    	Predicate predicate = builder.notEqual(root.get("userId"), userId);
//    	query.where(predicate);
//    	List<Post> posts = getSession().createQuery(query).getResultList();
//    	return posts;
    	begin();
    	List<Post> posts;
    	
    		CriteriaBuilder builder = getSession().getCriteriaBuilder();
        	CriteriaQuery<Post> query = builder.createQuery(Post.class);
        	Root<Post> root = query.from(Post.class);
        	query.select(root);
        	query.orderBy(builder.desc(root.get("createdOn")));
        	posts = getSession().createQuery(query).getResultList();
        	
    	
    	commit();
    	return posts;
    	
    }

	public int upvotePost(int postid,int userid) {
		// TODO Auto-generated method stub
		
		Post p = getPost(postid);
		if(getLikes(postid, userid)) {
			return p.getLikeCount();
		}
		
		
		Likes l = new Likes();
		l.setPost(getPost(postid));
		l.setUser(getUser(userid));
		save(l);
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

	    // Create the update query and specify the entity class to update
	    CriteriaUpdate<Post> update = criteriaBuilder.createCriteriaUpdate(Post.class);

	    // Set the property to update
	    
	    Root<Post> root = update.from(Post.class);
	    update.set(root.get("likeCount"), p.getLikeCount()+1);

	    // Set the WHERE clause to specify the entity to update
	    update.where(criteriaBuilder.equal(root.get("postid"), p.getPostid()));
	    begin();
	    // Execute the update query
	    getSession().createQuery(update).executeUpdate();
	    p.setLikeCount(p.getLikeCount()+1);
	    commit();
	    
		return getPost(postid).getLikeCount();
		
	}

	private User getUser(int userid) {
		// TODO Auto-generated method stub
		begin();
		User user = getSession().get(User.class, userid);
		commit();
        return user;
	}

	private void save(Likes l) {
		// TODO Auto-generated method stub
		begin();
        getSession().save(l);
        commit();
        
	}

	public int downvotePost(int postid,int userid) {
		// TODO Auto-generated method stub
		
		Post p = getPost(postid);
		if(getLikes(postid, userid)) {
			return p.getLikeCount();
		}
		
		
		Likes l = new Likes();
		l.setPost(getPost(postid));
		l.setUser(getUser(userid));
		save(l);
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

	    // Create the update query and specify the entity class to update
	    CriteriaUpdate<Post> update = criteriaBuilder.createCriteriaUpdate(Post.class);

	    // Set the property to update
	    Root<Post> root = update.from(Post.class);
	    update.set(root.get("likeCount"), p.getLikeCount()-1);

	    // Set the WHERE clause to specify the entity to update
	    update.where(criteriaBuilder.equal(root.get("postid"), p.getPostid()));
	    begin();
	    // Execute the update query
	    getSession().createQuery(update).executeUpdate();
	    commit();
		return getPost(postid).getLikeCount();
		
	}
	public boolean getLikes(int postid,int userid) {
		begin();
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
	    
	    // create a CriteriaQuery to specify the query parameters
	    CriteriaQuery<Likes> query = builder.createQuery(Likes.class);
	    Root<Likes> root = query.from(Likes.class);
	    Join<Likes, User> userJoin = root.join("user");
	    Join<Likes, Post> postJoin = root.join("post");
	    query.select(root).where(
	        builder.and(
	            builder.equal(userJoin.get("userid"), userid),
	            builder.equal(postJoin.get("postid"), postid)
	        )
	    );
	    
	    // execute the query and return true if data is found
	    List<Likes> results = getSession().createQuery(query).getResultList();
	    commit();
	    return (results.size() > 0);
	}

	public List<Post> getPosts(PostType postType) {
		// TODO Auto-generated method stub
		begin();
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Post> query = builder.createQuery(Post.class);
		Root<Post> root = query.from(Post.class);
		
		//Join<UserType, User> usertypeJoin = root.join("usertype");
	    //Join<Status, User> statusjoin = root.join("status");
		query.select(root).
		where(builder.equal(root.get("postType"), postType));
		query.orderBy(builder.desc(root.get("createdOn")));
			//builder.equal(root.get("status"), Status.PENDING));

		List<Post> posts = getSession().createQuery(query).getResultList();
		commit();
		return posts;
	}

	public List<Post> getPostsbasedOnSearchContent(String searchContent) {
		// TODO Auto-generated method stub
		begin();
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Post> query = builder.createQuery(Post.class);
		Root<Post> root = query.from(Post.class);
		
		//Join<UserType, User> usertypeJoin = root.join("usertype");
	    //Join<Status, User> statusjoin = root.join("status");
		query.select(root).
		where(builder.or(
		        builder.like(root.get("postTitle"), "%" + searchContent + "%"),
		        builder.like(root.get("postContent"), "%" + searchContent + "%")
		    )
		);
		query.orderBy(builder.desc(root.get("createdOn")));
			//builder.equal(root.get("status"), Status.PENDING));

		List<Post> posts = getSession().createQuery(query).getResultList();
		commit();
		return posts;
	}

	public List<Post> getUserPosts(int userid) {
		// TODO Auto-generated method stub
CriteriaBuilder builder = getSession().getCriteriaBuilder();
	    
	    // create a CriteriaQuery to specify the query parameters
	    CriteriaQuery<Post> query = builder.createQuery(Post.class);
	    Root<Post> root = query.from(Post.class);
	    Join<Post, User> userJoin = root.join("user");
	    
	    query.select(root).where(
	        builder.and(
	            builder.equal(userJoin.get("userid"), userid)
	           
	        )
	    );
	    
	    // execute the query and return true if data is found
	    List<Post> posts = getSession().createQuery(query).getResultList();
	    return posts;
	}

	
}
