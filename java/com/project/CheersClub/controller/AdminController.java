package com.project.CheersClub.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

import com.project.CheersClub.dao.AddressDAO;
import com.project.CheersClub.dao.PostDao;
import com.project.CheersClub.dao.UserDAO;
import com.project.CheersClub.pojo.Address;
import com.project.CheersClub.pojo.Post;
import com.project.CheersClub.pojo.PostType;
import com.project.CheersClub.pojo.Status;
import com.project.CheersClub.pojo.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminController {
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	UserDAO userDao;
	
	@Autowired
	AddressDAO addDao;
	
	
	@GetMapping("/admin/homepage")
	public String showadminHomepageget(ModelMap model,HttpSession session) {
		//model.addAttribute("post", post);
		
		List<Post> posts = postDao.getPosts();
		model.addAttribute("posts",posts);
		if(session.getAttribute("user")==null) {
			return "redirect:/loginpage";
		}
		return "admin-homepage";
	}
	
	@GetMapping("/admin/{type}")
	public String showPostsBasedOnPostTypeAdmin( ModelMap model,HttpSession session,@PathVariable("type")String type) {
		
		if(session.getAttribute("user")==null) {
			return "redirect:/loginpage";
		}
		User user = (User)session.getAttribute("user");
		
		List<Post> posts;
		if(type.equals("entertainment")) { 
			posts = postDao.getPosts(PostType.ENTERTAINMENT);
		}
		else if(type.equals("sports")) {
			posts = postDao.getPosts(PostType.SPORTS);
		}
		else if(type.equals("music")){
			posts = postDao.getPosts(PostType.MUSIC);
		}
		else{
			posts = postDao.getPosts(PostType.GAMING);
		}
		
		model.addAttribute("posts",posts);
		return "admin-homepage";
	}
	
	@GetMapping("/admin/logout")
	public ModelAndView logoutuser(ModelMap model, User user,HttpSession session) {
		session.invalidate();
		return new ModelAndView("redirect:/user/loginpage");
	}
	@GetMapping("/admin/moderators")
	public String showmoderatorFunctions(ModelMap model,HttpSession session) {

		if(session.getAttribute("user")==null) {
			return "redirect:/loginpage";
		}
		List<User> moderatorList = userDao.getModeratorsList();
		model.addAttribute("moderatorList",moderatorList);
		return "moderator-maintenance";
	}
	@GetMapping("/admin/users")
	public String showuserFunctions(ModelMap model,HttpSession session) {

		if(session.getAttribute("user")==null) {
			return "redirect:/loginpage";
		}
		List<User> userList = userDao.getUsersList();
		model.addAttribute("userList",userList);
		return "user-maintenance-admin";
	}
	@PostMapping("/admin/deleteUser")
	//public int updateVote(ModelMap model,@RequestParam("post")Post post,@RequestParam("button")String button)
	public void deleteUser(ModelMap model, HttpSession session,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		String status = "";
		boolean flag = userDao.deleteUser(Integer.parseInt(request.getParameter("id")));
		
		if(flag) {
			status = Status.APPROVED.toString();
			model.addAttribute("message","Approved");
		}
		JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("status", status);
	    
	    
	    // Set the content type to JSON and send the response
	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    out.print(jsonResponse.toString());
	    out.flush();
	    
	}
	@PostMapping("/admin/displayModeratorDetails")
	//public int updateVote(ModelMap model,@RequestParam("post")Post post,@RequestParam("button")String button)
	public void displayModeratorDetails(HttpSession session,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		
		Address add = addDao.address(Integer.parseInt(request.getParameter("addressId")));
		
		JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("updateVotes", add);
	    List<Post> posts = postDao.getPosts();
	    
	    // Set the content type to JSON and send the response
	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    out.print(jsonResponse.toString());
	    out.flush();
	    
	}
	@PostMapping("/admin/approveModerator")
	//public int updateVote(ModelMap model,@RequestParam("post")Post post,@RequestParam("button")String button)
	public void approveModerator(ModelMap model, HttpSession session,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		String status = "";
		boolean flag = userDao.approveModerator(Integer.parseInt(request.getParameter("id")));
		if(flag) {
			status = Status.APPROVED.toString();
			model.addAttribute("message","Approved");
		}
		JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("status", status);
	    
	    
	    // Set the content type to JSON and send the response
	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    out.print(jsonResponse.toString());
	    out.flush();
	    
	}
	@PostMapping("/admin/denyModerator")
	//public int updateVote(ModelMap model,@RequestParam("post")Post post,@RequestParam("button")String button)
	public void denyModerator(ModelMap model, HttpSession session,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		String status = "";
		boolean flag = userDao.denyModerator(Integer.parseInt(request.getParameter("id")));
		if(flag) {
			status = Status.PENDING.toString();
			model.addAttribute("message","Denied");
		}
		JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("status", status);
	    
	    
	    // Set the content type to JSON and send the response
	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    out.print(jsonResponse.toString());
	    out.flush();
	    
	}
}
