package com.project.CheersClub.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;
import com.project.CheersClub.pojo.Topic;
import com.project.CheersClub.pojo.Comment;
import com.project.CheersClub.pojo.User;
import com.project.CheersClub.pojo.UserType;
import javax.persistence.*;

@Component
public class CommentDAO extends DAO {
    public void save(Comment a) {
        begin();
        getSession().save(a);
        commit();
    }
    
    public void update(Comment a) {
        try {
            begin();
            getSession().update(a);
            commit();
        } catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    
    public void delete(Comment a) {
        begin();
        getSession().delete(a);
        commit();
    }
    
    public Comment getComment(int cid) {
    	begin();
    	Comment comment = getSession().get(Comment.class, cid);
    	commit();
        return comment;
    }

	public List<Comment> getComments(int tid) {
		// TODO Auto-generated method stub
		begin();
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Comment> query = builder.createQuery(Comment.class);
		Root<Comment> root = query.from(Comment.class);
		Join<Comment, Topic> topicJoin = root.join("topic");
		//Join<UserType, User> usertypeJoin = root.join("usertype");
	    //Join<Status, User> statusjoin = root.join("status");
		query.select(root).
		where(builder.equal(topicJoin.get("topicId"), tid));
			//builder.equal(root.get("status"), Status.PENDING));

		List<Comment> comments = getSession().createQuery(query).getResultList();
		commit();
		return comments;
	}
}
