package com.project.CheersClub.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.project.CheersClub.pojo.Likes;
import com.project.CheersClub.pojo.Page;
import com.project.CheersClub.pojo.Post;
import com.project.CheersClub.pojo.Status;
import com.project.CheersClub.pojo.User;
import com.project.CheersClub.pojo.Userpages;

@Component
public class UserPageDAO extends DAO {
	public void save(Userpages up) {
        begin();
        
        commit();
        try {
            begin(); //inherited from super class DAO
            getSession().save(up);
            commit();
            } catch(Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
    }
    
    public void update(Userpages a) {
        try {
            begin();
            getSession().update(a);
            commit();
        } catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    
    public void delete(Userpages a) {
        begin();
        getSession().delete(a);
        commit();
    }
    
    public Userpages getUserPage(int pid,int uid) {
    	
    	CriteriaBuilder cb = getSession().getCriteriaBuilder();
    	CriteriaQuery<Userpages> query = cb.createQuery(Userpages.class);
    	Root<Userpages> root = query.from(Userpages.class);
    	Join<Userpages, User> userJoin = root.join("user");
    	Join<Userpages, Page> pageJoin = root.join("page");
    	
//    	query.select(root)
//    	     .where(cb.equal(root.get("userid"), id));
    	query.select(root).where(
    	        cb.and(
    	            cb.equal(userJoin.get("userid"), uid),
    	            cb.equal(pageJoin.get("id"),pid)
    	            
    	        )
    	    );

    	Userpages page = getSession().createQuery(query).getSingleResult();
    	
    	return page;
        
    }
    public Userpages getApprovedUserPage(int pid,int uid) {
    	
    	CriteriaBuilder cb = getSession().getCriteriaBuilder();
    	CriteriaQuery<Userpages> query = cb.createQuery(Userpages.class);
    	Root<Userpages> root = query.from(Userpages.class);
    	Join<Userpages, User> userJoin = root.join("user");
    	Join<Userpages, Page> pageJoin = root.join("page");
    	
//    	query.select(root)
//    	     .where(cb.equal(root.get("userid"), id));
    	query.select(root).where(
    	        cb.and(
    	            cb.equal(userJoin.get("userid"), uid),
    	            cb.equal(pageJoin.get("id"),pid),
    	            cb.equal(userJoin.get("status"),Status.APPROVED)
    	            
    	        )
    	    );

    	Userpages page = getSession().createQuery(query).getSingleResult();
    	
    	return page;
        
    }
   
    public Userpages getPage(int id) {
    	
    	CriteriaBuilder cb = getSession().getCriteriaBuilder();
    	CriteriaQuery<Userpages> query = cb.createQuery(Userpages.class);
    	Root<Userpages> root = query.from(Userpages.class);
    	Join<Page, User> userJoin = root.join("moderator");
    	
//    	query.select(root)
//    	     .where(cb.equal(root.get("userid"), id));
    	query.select(root).where(
    	        cb.and(
    	            cb.equal(userJoin.get("userid"), id)
    	            
    	        )
    	    );

    	Userpages page = getSession().createQuery(query).getSingleResult();
    	
    	return page;
    }
    public List<Userpages> getUserPages(int id) {
    	
    	CriteriaBuilder cb = getSession().getCriteriaBuilder();
    	CriteriaQuery<Userpages> query = cb.createQuery(Userpages.class);
    	Root<Userpages> root = query.from(Userpages.class);
    	Join<Userpages, User> userJoin = root.join("user");
    	Join<Userpages, Page> pageJoin = root.join("page");
    	
//    	query.select(root)
//    	     .where(cb.equal(root.get("userid"), id));
    	query.select(root).where(
    	        cb.and(
    	            cb.equal(userJoin.get("userid"), id)
    	            
    	            
    	        )
    	    );


    	List<Userpages> pages = getSession().createQuery(query).getResultList();
    	
    	return pages;
    }

	
}

