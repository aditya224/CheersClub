package com.project.CheersClub.dao;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.project.CheersClub.pojo.Likes;
import com.project.CheersClub.pojo.Page;
import com.project.CheersClub.pojo.Post;
import com.project.CheersClub.pojo.Status;
import com.project.CheersClub.pojo.User;
import com.project.CheersClub.pojo.UserType;
import com.project.CheersClub.pojo.Userpages;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.*;
import java.util.List;
import java.util.*;

@Component
public class PageDAO extends DAO{
	
	@Autowired
	UserPageDAO userPageDao;
	
	public void save(Page a) {
        begin();
        getSession().save(a);
        commit();
    }
    
    public void update(Page a) {
        try {
            begin();
            getSession().update(a);
            commit();
        } catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    
    public void delete(Page a) {
        begin();
        getSession().delete(a);
        commit();
    }
    
    public Page getPage(int pid) {
    	begin();
        Page page = getSession().get(Page.class, pid);
        commit();
        return page;
    }
    
    public List<Page> getPagesList(int id) {
		// TODO Auto-generated method stub
    	begin();
    	CriteriaBuilder cb = getSession().getCriteriaBuilder();
    	CriteriaQuery<Page> query = cb.createQuery(Page.class);
    	Root<Page> root = query.from(Page.class);
    	Join<Page, User> userJoin = root.join("moderator");
//    	query.select(root)
//    	     .where(cb.equal(root.get("userid"), id));
    	query.select(root).where(
    	        cb.and(
    	            cb.equal(userJoin.get("userid"), id)
    	            
    	        )
    	    );

    	List<Page> pages = getSession().createQuery(query).getResultList();
    	commit();
    	return pages;
	}

	public List<Userpages> getmoderatortoapproveUserList(int userid) {
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
    	CriteriaQuery<Userpages> query = cb.createQuery(Userpages.class);
    	Root<Userpages> root = query.from(Userpages.class);
    	Join<Userpages, Page> pageJoin = root.join("page");
    	Join<Userpages, User> userJoin = pageJoin.join("moderator");
    	
    	
//    	
    	query.select(root).where(
    	        cb.and(
    	            cb.equal(userJoin.get("userid"), userid)
    	            
    	            
    	        )
    	    );

    	List<Userpages> up = getSession().createQuery(query).getResultList();
    	commit();
    	return up;
    	
    	
		
	}
	
	

	public List<Page> getUserPagesList(int id) {
		// TODO Auto-generated method stub
		begin();
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Page> query = builder.createQuery(Page.class);
		Root<Page> root = query.from(Page.class);
		Join<Page,User> userJoin = root.join("moderator");
		
		List<Page> pages = getSession().createQuery(query).getResultList();
		List<Page> ans = new ArrayList<>();
		List<Userpages> userpages = userPageDao.getUserPages(id);
		for(Page page:pages) {
			boolean flag= true;
			for(Userpages up:userpages) {
				if(page.getId() == up.getPage().getId() && (up.getStatus()==Status.APPROVED || up.getStatus()==Status.PENDING)) {
					flag=false;
					break;
				}
			}
			if(flag) {
				ans.add(page);
			}
		}
		commit();
		return ans;
		
	}

	public boolean approveUserToPage(int userpageID) {
		begin();
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
	    CriteriaUpdate<Userpages> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Userpages.class);
	    Root<Userpages> userRoot = criteriaUpdate.from(Userpages.class);
	    criteriaUpdate.set("status", Status.APPROVED)
	                  .where(criteriaBuilder.equal(userRoot.get("userpageid"), userpageID));
	    int rowsUpdated = getSession().createQuery(criteriaUpdate).executeUpdate();
	    commit();
	    return rowsUpdated > 0;
	}

	public boolean denyUserToPage(int userpageID) {
		// TODO Auto-generated method stub
		begin();
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
	    CriteriaUpdate<Userpages> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Userpages.class);
	    Root<Userpages> userRoot = criteriaUpdate.from(Userpages.class);
	    criteriaUpdate.set("status", Status.DENIED)
	                  .where(criteriaBuilder.equal(userRoot.get("userpageid"), userpageID));
	    int rowsUpdated = getSession().createQuery(criteriaUpdate).executeUpdate();
	    commit();
	    return rowsUpdated > 0;
	}

	public List<Userpages> getuserPageList(int userid) {
		// TODO Auto-generated method stub
		begin();
		String hql = "FROM Userpages";
		
    	
    	Query query = getSession().createQuery(hql);
    	List<Userpages> res = new ArrayList<>();
    	List<Userpages> userpagesList = query.getResultList();
    	for(Userpages up: userpagesList) {
    		if(up.getUser().getUserid()==userid && up.getStatus()==Status.APPROVED) {
    			res.add(up);
    		}
    	}
    	
    	commit();
    	return res;
	}

	public List<Userpages> getsearchPagesList(String searchContent,int uid) {
		// TODO Auto-generated method stub
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
    	CriteriaQuery<Userpages> query = cb.createQuery(Userpages.class);
    	Root<Userpages> root = query.from(Userpages.class);
    	Join<Userpages, Page> pageJoin = root.join("page");
    	Join<Userpages, User> userJoin = pageJoin.join("moderator");
    	
    	

    	query.select(root).
		where(cb.or(
		        cb.like(pageJoin.get("pageTitle"), "%" + searchContent + "%"),
		        cb.like(pageJoin.get("pageDescription"), "%" + searchContent + "%")
		    )
				
			
		);

    	List<Userpages> up = getSession().createQuery(query).getResultList();
    	List<Userpages> res = up;
    	for(Userpages u: up) {
    		if(u.getUser().getUserid()!=uid) {
    			res.remove(u);
    		}
    	}
    	commit();
    	return res;
	}
}

