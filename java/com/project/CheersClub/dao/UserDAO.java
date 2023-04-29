package com.project.CheersClub.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.hibernate.Criteria;

import com.project.CheersClub.pojo.*;

@Component
public class UserDAO extends DAO {
    public void saveUser(User user) {
        try {
        begin(); //inherited from super class DAO
        getSession().save(user);
        commit();
        } catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    
    public void deleteUser(User user) {
        begin();
        getSession().delete(user);
        commit();
    }
    
    public void deleteUserById(int userid) {
        begin();
        getSession().delete(getSession().get(User.class, userid));
        commit();
    }
    
    public User getUser(int userid) {
        User user = getSession().get(User.class, userid);
        return user;
    }
    public User getUser(String emailId, String password) {
    	
    	String hql = "FROM User ud WHERE ud.email =:email and ud.password=:password";
    	
    	Query query = getSession().createQuery(hql);
    	query.setParameter("email", emailId);
    	query.setParameter("password", password);
    	
    	List<User> users = query.getResultList();
    	if(users.isEmpty()) {
    		return null;
    	}
    	else {
    		User user = (User)users.get(0);
    		return user;
    	}
    		
    		
        	
    	
    	
    	
    	
    }

	public List<User> getModeratorsList() {
		// TODO Auto-generated method stub
		begin();
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		//Join<UserType, User> usertypeJoin = root.join("usertype");
	    //Join<Status, User> statusjoin = root.join("status");
		query.select(root).
		where(builder.equal(root.get("userType"), UserType.MODERATOR));
			//builder.equal(root.get("status"), Status.PENDING));

		List<User> moderators = getSession().createQuery(query).getResultList();
		commit();
		return moderators;
	}
	public List<User> getUsersList() {
		// TODO Auto-generated method stub
		begin();
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		//Join<UserType, User> usertypeJoin = root.join("usertype");
	    //Join<Status, User> statusjoin = root.join("status");
		query.select(root).
		where(builder.equal(root.get("userType"), UserType.USER));
			//builder.equal(root.get("status"), Status.PENDING));

		List<User> moderators = getSession().createQuery(query).getResultList();
		commit();
		return moderators;
	}
	public boolean deleteUser(int userid) {
		// TODO Auto-generated method stub
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
//		//delete comments
		CriteriaDelete<Comment> commentDelete = cb.createCriteriaDelete(Comment.class);
	    Root<Comment> commentRoot = commentDelete.from(Comment.class);
	    commentDelete.where(cb.equal(commentRoot.get("user").get("userid"), userid));
	    int row1 = getSession().createQuery(commentDelete).executeUpdate();
//	    if(row1==0) {
//	    	return false;
//	    }
	    
	    //delete likes
	    CriteriaDelete<Likes> likesDelete = cb.createCriteriaDelete(Likes.class);
	    Root<Likes> likesRoot = likesDelete.from(Likes.class);
	    likesDelete.where(cb.equal(likesRoot.get("user").get("userid"), userid));
	    int row2 = getSession().createQuery(likesDelete).executeUpdate();
////	    if(row2==0) {
////	    	return false;
////	    }
	    
	    //delete topic
	    
		CriteriaDelete<Topic> topicDelete = cb.createCriteriaDelete(Topic.class);
	    Root<Topic> topicRoot = topicDelete.from(Topic.class);
	    topicDelete.where(cb.equal(topicRoot.get("user").get("userid"), userid));
	    int row3 = getSession().createQuery(topicDelete).executeUpdate();
//	    if(row3==0) {
//	    	return false;
//	    }
	    
	    //delete post
	    CriteriaDelete<Post> postDelete = cb.createCriteriaDelete(Post.class);
	    Root<Post> postRoot = postDelete.from(Post.class);
	    postDelete.where(cb.equal(postRoot.get("user").get("userid"), userid));
	    int row4 = getSession().createQuery(postDelete).executeUpdate();
////	    if(row4==0) {
////	    	return false;
////	    }
	    //delete userpage
	    CriteriaDelete<Userpages> userpageDelete = cb.createCriteriaDelete(Userpages.class);
	    Root<Userpages> userpageRoot = userpageDelete.from(Userpages.class);
	    userpageDelete.where(cb.equal(userpageRoot.get("user").get("userid"), userid));
	    int row5 = getSession().createQuery(userpageDelete).executeUpdate();
////	    if(row5==0) {
////	    	return false;
////	    }
//	    
//	    //delete page
	    CriteriaDelete<Page> pageDelete = cb.createCriteriaDelete(Page.class);
	    Root<Page> pageRoot = pageDelete.from(Page.class);
	    
	    pageDelete.where(cb.equal(pageRoot.get("moderator").get("userid"), userid));
	    int row6 = getSession().createQuery(pageDelete).executeUpdate();
////	    if(row6==0) {
////	    	return false;
////	    }
	    
	    
	    
	    
	    
	    
		//delete user
	    
	    CriteriaDelete<User> delete = cb.createCriteriaDelete(User.class);
	    Root<User> root = delete.from(User.class);
	    delete.where(cb.equal(root.get("userid"), userid));
	    int rows = getSession().createQuery(delete).executeUpdate();
	    commit();
	    return rows > 0;
	}
	public boolean approveModerator(int userid) {
		// TODO Auto-generated method stub
		begin();
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
	    CriteriaUpdate<User> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(User.class);
	    Root<User> userRoot = criteriaUpdate.from(User.class);
	    criteriaUpdate.set("status", Status.APPROVED)
	                  .where(criteriaBuilder.equal(userRoot.get("userid"), userid));
	    int rowsUpdated = getSession().createQuery(criteriaUpdate).executeUpdate();
	    commit();
	    return rowsUpdated > 0;
	}
	public boolean denyModerator(int userid) {
		// TODO Auto-generated method stub
		begin();
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
	    CriteriaUpdate<User> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(User.class);
	    Root<User> userRoot = criteriaUpdate.from(User.class);
	    criteriaUpdate.set("status", Status.PENDING)
	                  .where(criteriaBuilder.equal(userRoot.get("userid"), userid));
	    int rowsUpdated = getSession().createQuery(criteriaUpdate).executeUpdate();
	    commit();
	    return rowsUpdated > 0;
	}

	
}