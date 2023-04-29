package com.project.CheersClub.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.engine.jdbc.BlobProxy;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.project.CheersClub.pojo.User;
import com.project.CheersClub.pojo.UserType;
import com.project.CheersClub.pojo.Userpages;
import com.project.CheersClub.util.HibernateUtil;
import com.project.CheersClub.dao.CommentDAO;
import com.project.CheersClub.dao.PageDAO;
import com.project.CheersClub.dao.PostDao;
import com.project.CheersClub.dao.TopicDAO;
import com.project.CheersClub.dao.UserDAO;
import com.project.CheersClub.dao.UserPageDAO;
import com.project.CheersClub.pojo.Comment;
import com.project.CheersClub.pojo.Page;
import com.project.CheersClub.pojo.Post;
import com.project.CheersClub.pojo.Status;
import com.project.CheersClub.pojo.Topic;
import com.project.CheersClub.pojo.Userpages;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {
	
	@Autowired
	PageDAO pageDao;
	
	@Autowired
	UserPageDAO userpageDao;
	
	@Autowired
	UserDAO userDao;
	
	@Autowired
	TopicDAO topicDao;
	
	@Autowired
	CommentDAO commentDao;
	
	@Autowired
	PostDao postDao;
	
	
	@GetMapping("/user/yourPages")
	public String showPages(ModelMap model,HttpSession session) throws SQLException,IOException,Exception{

		if(session.getAttribute("user")==null) {
			return "redirect:/loginpage";
		}
		User user = (User)session.getAttribute("user");
		
		if(user.getUserType()==UserType.USER) {
			List<Userpages> userPageList = pageDao.getuserPageList(user.getUserid());
			model.addAttribute("userpageList", userPageList);
		}
		else if(user.getUserType()==UserType.MODERATOR) {
			List<Page> pageList = pageDao.getPagesList(user.getUserid());
			model.addAttribute("pageList",pageList);
			
		}
		else if(user.getUserType()==UserType.ADMIN) {
			List<Userpages> userPageList = pageDao.getuserPageList(user.getUserid());
			model.addAttribute("userpageList", userPageList);
			List<Page> pageList = pageDao.getPagesList(user.getUserid());
			model.addAttribute("pageList",pageList);
		}
		
		
		return "yourPages";
	}
	@GetMapping("/user/yourPages/addPage")
	public String addPage(@ModelAttribute("post")Page page,ModelMap model,HttpSession session) throws SQLException,IOException,Exception{
		model.addAttribute("page", page);
		if(session.getAttribute("user")==null) {
			return "redirect:/loginpage";
		}
		User user = (User)session.getAttribute("user");
		List<Page> pageList = pageDao.getPagesList(user.getUserid());
		model.addAttribute("pageList",pageList);
		return "addPage";
	}
	@PostMapping("/user/yourPages/createPage")
	//public int updateVote(ModelMap model,@RequestParam("post")Post post,@RequestParam("button")String button)
	public ModelAndView createPage(@ModelAttribute("post")Page page,ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws SQLException,IOException,Exception{
		User user = (User)session.getAttribute("user");
		if(session.getAttribute("user")==null) {
			return new ModelAndView("redirect:/loginpage");
		}
		else if(user.getUserType()!=UserType.MODERATOR) {
			return new ModelAndView("redirect:/user/homepage");
		}
		page.setModerator(user);
		pageDao.save(page);
		return new ModelAndView("redirect:/user/yourPages");
	    
	}
	
	@GetMapping("/user/yourPages/approvePageRequest")
	public String approveUserPageRequest(ModelMap model, HttpSession session) throws SQLException,IOException,Exception{
		User user = (User)session.getAttribute("user");
		if(session.getAttribute("user")==null) {
			return ("redirect:/loginpage");
		}
		else if(user.getUserType()!=UserType.MODERATOR) {
			return ("redirect:/user/homepage");
		}
		
		List<Userpages> userpages = pageDao.getmoderatortoapproveUserList(user.getUserid());
		model.addAttribute("userpages",userpages);
		
		return ("user-maintenance");
	    
	}
	@PostMapping("/user/yourPages/approveUsertoPage")
	//public int updateVote(ModelMap model,@RequestParam("post")Post post,@RequestParam("button")String button)
	public void approveUserToPage(HttpSession session,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		String status = "";
		boolean flag = pageDao.approveUserToPage(Integer.parseInt(request.getParameter("userpageid")));
		if(flag) {
			status = Status.APPROVED.toString();
		}
		JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("status", status);
	    
	    
	    // Set the content type to JSON and send the response
	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    out.print(jsonResponse.toString());
	    out.flush();
	    
	}
	@PostMapping("/user/yourPages/denyUsertoPage")
	//public int updateVote(ModelMap model,@RequestParam("post")Post post,@RequestParam("button")String button)
	public void denyUserToPage(HttpSession session,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		String status = "";
		boolean flag = pageDao.denyUserToPage(Integer.parseInt(request.getParameter("userpageid")));
		if(flag) {
			status = Status.DENIED.toString();
		}
		JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("status", status);
	    
	    
	    // Set the content type to JSON and send the response
	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    out.print(jsonResponse.toString());
	    out.flush();
	    
	}
	@GetMapping("/user/yourPages/searchForPages")
	public String searchForPages(ModelMap model,HttpSession session) throws SQLException,IOException,Exception{

		if(session.getAttribute("user")==null) {
			return "redirect:/loginpage";
		}
		User user = (User)session.getAttribute("user");
		List<Page> pageList = pageDao.getUserPagesList(user.getUserid());
		model.addAttribute("pageList",pageList);
		return "searchForPages";
	}
	@PostMapping("/user/yourPages/requestPageApproval")
	//public int updateVote(ModelMap model,@RequestParam("post")Post post,@RequestParam("button")String button)
	public void requestPageApproval(@ModelAttribute("userpage")Userpages up,ModelMap model,HttpSession session,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		String status = "";
		
		Page p = pageDao.getPage(Integer.parseInt(request.getParameter("pageid")));
		User u = userDao.getUser(Integer.parseInt(request.getParameter("userid")));
		up.setPage(p);
		up.setUser(u);
		up.setStatus(Status.PENDING);
		
		userpageDao.save(up);
		
		model.addAttribute("user-request-success", "Request Pending approval with moderator");
		JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("status", status);
	    
	    
	    // Set the content type to JSON and send the response
	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    out.print(jsonResponse.toString());
	    out.flush();
	    
	}
	@PostMapping("/user/searchAll/requestPageApproval")
	public void requestPageApprovalfromSearch(@ModelAttribute("userpage")Userpages up,ModelMap model,HttpSession session,HttpServletRequest request, HttpServletResponse response) throws Exception{
		requestPageApproval(up, model, session, request, response);
	}
	@GetMapping("user/yourPages/{itemid}")
	public String showSpecificPage(@ModelAttribute("topic")Topic topic, @PathVariable("itemid")String itemID, ModelMap model, HttpSession session,HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = (User)session.getAttribute("user");
		if(session.getAttribute("user")==null) {
			return ("redirect:/loginpage");
		}
		model.addAttribute("topic", topic);
		String a = itemID;
		
		model.addAttribute("a",a);
		Page p = pageDao.getPage(Integer.parseInt(itemID));
		model.addAttribute("page",p);
		List<Page> pageList = pageDao.getPagesList(user.getUserid());
		if(user.getUserType()==UserType.USER) {
			List<Userpages> userPageList = pageDao.getuserPageList(user.getUserid());
			model.addAttribute("userpageList", userPageList);
		}
		model.addAttribute("pageList",pageList);
		List<Topic> topics = topicDao.getTopics(itemID);
	    model.addAttribute("topics",topics);
		return ("page");
	}
	@PostMapping("/user/yourPages/{itemid}/createTopic")
	public ModelAndView createTopic(@ModelAttribute("topic")Topic topic,@PathVariable("itemid")String itemID, ModelMap model,HttpSession session) throws SQLException,IOException,Exception {
		
		if(session.getAttribute("user")==null) {
			return new ModelAndView("redirect:/loginpage");
		}
		User user = (User)session.getAttribute("user");
		
		topic.setUser(user);
		Instant instant = Instant.now();
        Timestamp currentTimestamp = Timestamp.from(instant);
	    topic.setTopicCreatedOn(currentTimestamp);
	    topic.setPage(pageDao.getPage(Integer.parseInt(itemID)));
	    topicDao.save(topic);
	    //request.setAttribute("posted", "posted");
	    List<Topic> topics = topicDao.getTopics();
	    model.addAttribute("topics",topics);
	    
		//return new ModelAndView("user-homepage","user",u);
		return new ModelAndView("redirect:/user/yourPages/"+itemID);
		//return "user-homepage";
	}
	@GetMapping("/user/yourPages/{pageId}/{topicId}")
	public ModelAndView viewTopic(@PathVariable("pageId")String pageId,@PathVariable("topicId")String topicId, ModelMap model,HttpSession session) throws SQLException,IOException,Exception{
		
		if(session.getAttribute("user")==null) {
			return new ModelAndView("redirect:/loginpage");
		}
		User user = (User)session.getAttribute("user");
		Page page = pageDao.getPage(Integer.parseInt(pageId));
		
		if(user.getUserType()==UserType.USER && userpageDao.getApprovedUserPage(Integer.parseInt(pageId), user.getUserid())==null) {
			return new ModelAndView("redirect:/user/homepage");
		}
		List<Page> pageList = pageDao.getPagesList(user.getUserid());
		if(user.getUserType()==UserType.USER) {
			List<Userpages> userPageList = pageDao.getuserPageList(user.getUserid());
			model.addAttribute("userpageList", userPageList);
		}
		model.addAttribute("pageList",pageList);
	    Topic t = topicDao.getTopic(Integer.parseInt(topicId));
	    model.addAttribute("topic",t);
	    List<Comment> comments = commentDao.getComments(Integer.parseInt(topicId));
	    model.addAttribute("comments", comments);
		//return new ModelAndView("user-homepage","user",u);
		return new ModelAndView("topic-page");
		//return "user-homepage";
	}
	@PostMapping("/user/yourPages/{pageId}/{topicId}/commentOnTopic")
	public ModelAndView commentOnTopic(@PathVariable("pageId")String pageId,@PathVariable("topicId")String topicId, ModelMap model,HttpSession session,HttpServletRequest request) throws SQLException,IOException,Exception{
		
		if(session.getAttribute("user")==null) {
			return new ModelAndView("redirect:/loginpage");
		}
		User user = (User)session.getAttribute("user");
		Page page = pageDao.getPage(Integer.parseInt(pageId));
		
		if(userpageDao.getApprovedUserPage(Integer.parseInt(pageId), user.getUserid())==null) {
			return new ModelAndView("redirect:/user/homepage");
		}
		List<Page> pageList = pageDao.getPagesList(user.getUserid());
		if(user.getUserType()==UserType.USER) {
			List<Userpages> userPageList = pageDao.getuserPageList(user.getUserid());
			model.addAttribute("userpageList", userPageList);
		}
		model.addAttribute("pageList",pageList);
	    Topic t = topicDao.getTopic(Integer.parseInt(topicId));
	    model.addAttribute("topic",t);
	    Comment c = new Comment();
	    c.setCommentText(request.getParameter("content"));
	    c.setTopic(t);
	    c.setUser(user);
	    commentDao.save(c);
	    
		//return new ModelAndView("user-homepage","user",u);
		return new ModelAndView("redirect:/user/yourPages/" + pageId + "/" + topicId);
		//return "user-homepage";
	}
	@GetMapping("/user/yourPages/logout")
	public ModelAndView logoutuserfromPages(ModelMap model, User user,HttpSession session) throws SQLException,IOException,Exception{
		session.invalidate();
		return new ModelAndView("redirect:/user/loginpage");
	}
	@GetMapping("/user/yourPages/{pageid}/logout")
	public ModelAndView logoutuserfromPageTopic(ModelMap model, User user,HttpSession session) throws SQLException,IOException,Exception{
		session.invalidate();
		return new ModelAndView("redirect:/user/loginpage");
	}
	
	@GetMapping("/user/yourPages/homepage")
	public String showuserHomepagefromYourPages(@ModelAttribute("post")Post post, BindingResult result, SessionStatus status,ModelMap model,HttpSession session) throws SQLException,IOException,Exception{
		model.addAttribute("post", post);
		if(session.getAttribute("user")==null) {
			return "redirect:/loginpage";
		}
		List<Post> posts = postDao.getPosts();
		model.addAttribute("posts",posts);
		
		return "redirect:/user/homepage";
	}
	@GetMapping("/user/yourPages/{pageid}/homepage")
	public String showuserHomepagefromPageTopic(@ModelAttribute("post")Post post, BindingResult result, SessionStatus status,ModelMap model,HttpSession session) throws SQLException,IOException,Exception{
		model.addAttribute("post", post);
		if(session.getAttribute("user")==null) {
			return "redirect:/loginpage";
		}
		List<Post> posts = postDao.getPosts();
		model.addAttribute("posts",posts);
		
		return "redirect:/user/homepage";
	}
	@GetMapping("/user/yourPages/{itemId}/searchForPages")
	public String searchForPagesfromPageTopic(ModelMap model,HttpSession session) throws SQLException,IOException,Exception{

		if(session.getAttribute("user")==null) {
			return "redirect:/loginpage";
		}
		User user = (User)session.getAttribute("user");
		List<Page> pageList = pageDao.getUserPagesList(user.getUserid());
		model.addAttribute("pageList",pageList);
		return "redirect:/user/yourPages/searchForPages";
	}
	
	
}

