package com.project.CheersClub.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.project.CheersClub.pojo.Comment;
import com.project.CheersClub.pojo.Post;
import com.project.CheersClub.pojo.Topic;
import com.project.CheersClub.pojo.Page;


@Component
public class TopicDAO extends DAO {
    public void save(Topic a) {
        begin();
        getSession().save(a);
        commit();
    }
    
    public void update(Topic a) {
        try {
            begin();
            getSession().update(a);
            commit();
        } catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    
    public void delete(Topic a) {
        begin();
        getSession().delete(a);
        commit();
    }
    
    public Topic getTopic(int aid) {
    	begin();
    	Topic topic = getSession().get(Topic.class, aid);
    	commit();
        return topic;
    }

	public List<Topic> getTopics() {
		// TODO Auto-generated method stub
		begin();
		List<Topic> topics;
    	
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
    	CriteriaQuery<Topic> query = builder.createQuery(Topic.class);
    	Root<Topic> root = query.from(Topic.class);
    	query.select(root);

    	topics = getSession().createQuery(query).getResultList();
    	commit();
    	return topics;
    	
	}

	public List<Topic> getTopics(String pageId) {
		// TODO Auto-generated method stub
		begin();
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Topic> query = builder.createQuery(Topic.class);
		Root<Topic> root = query.from(Topic.class);
		Join<Topic, Page> topicJoin = root.join("page");
		//Join<UserType, User> usertypeJoin = root.join("usertype");
	    //Join<Status, User> statusjoin = root.join("status");
		query.select(root).
		where(builder.equal(topicJoin.get("id"), pageId));
			//builder.equal(root.get("status"), Status.PENDING));

		List<Topic> topics = getSession().createQuery(query).getResultList();
		commit();
		return topics;
	}
}