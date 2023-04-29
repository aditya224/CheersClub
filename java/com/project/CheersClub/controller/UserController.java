package com.project.CheersClub.controller;

import java.util.List;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.project.CheersClub.validator.LoginValidator;
import com.project.CheersClub.validator.UserValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.project.CheersClub.dao.PostDao;
import com.project.CheersClub.dao.UserDAO;
import com.project.CheersClub.pojo.User;
import com.project.CheersClub.pojo.Address;
import com.project.CheersClub.pojo.Page;
import com.project.CheersClub.pojo.Post;
import com.project.CheersClub.pojo.PostType;
import com.project.CheersClub.pojo.Status;
import com.project.CheersClub.pojo.UserType;
import com.project.CheersClub.pojo.Userpages;

@Controller
public class UserController {
	
	@Autowired
	UserValidator validator;
	
	@Autowired
	LoginValidator loginValidator;
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	UserDAO userDao;
	
	@GetMapping("/")
	public ModelAndView showhome(ModelMap model, User user,HttpSession session) throws SQLException,IOException,Exception {
		if(session.getAttribute("user")!=null) {
			List<Post> posts = postDao.getPosts();
			model.addAttribute(posts);
			User u = (User)session.getAttribute("user");
			if(u.getUserType().equals(UserType.ADMIN)) {
				return new ModelAndView("redirect:/admin/homepage","user",u);
			}
			return new ModelAndView("redirect:/user/homepage","user",u);
			
		}
		model.addAttribute("user", user);
		return new ModelAndView("index");
	}
	
	@GetMapping("/user/loginpage")
	public ModelAndView showloginForm(ModelMap model, User user,HttpSession session) throws SQLException,IOException,Exception {
		if(session.getAttribute("user")!=null) {
			List<Post> posts = postDao.getPosts();
			model.addAttribute(posts);
			User u = (User)session.getAttribute("user");
			if(u.getUserType().equals(UserType.ADMIN)) {
				return new ModelAndView("redirect:/admin/homepage","user",u);
			}
			return new ModelAndView("redirect:/user/homepage","user",u);
			
		}
		model.addAttribute("user", user);
		return new ModelAndView("loginpage");
	}
	@GetMapping("/user/registerpage")
	public String showregisterForm(ModelMap model, User user) {
		model.addAttribute("user", user);
		return "registerpage";
	}
	@PostMapping("/user/register")
	public String showSuccess(@ModelAttribute("user")User user, BindingResult result, SessionStatus status, HttpServletRequest request,ModelMap model) throws SQLException,IOException,Exception {
		validator.validate(user, result);
        
	     if(result.hasErrors())
	    	 //return "user-homepage";
			 return ("registerpage");
		if(user.getUserType().equals(UserType.ADMIN)) {
			user.setStatus(Status.APPROVED);
		}
		else if(user.getUserType().equals(UserType.MODERATOR)){
			user.setStatus(Status.PENDING);
		}
		else {
			user.setStatus(Status.APPROVED);
		}
		userDao.saveUser(user);
		model.addAttribute("registersuccess-message","registered successfully,Login Again");
		return "loginpage";
	}
	@PostMapping("/user/login")
	public ModelAndView login(ModelMap model,@ModelAttribute("user")User user, BindingResult result, SessionStatus status,HttpSession session) throws SQLException,IOException,Exception{
		
		loginValidator.validate(user, result);
        
        if(result.hasErrors())
            return new ModelAndView("user-form");
        
        status.setComplete();
        
		User u = userDao.getUser(user.getEmail(), user.getPassword());
		if(u!=null) {
			model.addAttribute("user", u);
			model.addAttribute("post",new Post());
			session.setAttribute("user", u);
			List<Post> posts = postDao.getPosts();
			try {
				
			
			for(Post post: posts) {
				if(post.getData()==null) {
					continue;
				}
				Blob blob = post.getData();
                
                InputStream inputStream = blob.getBinaryStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                 
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);                  
                }
                 
                byte[] imageBytes = outputStream.toByteArray();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                 
                 
                inputStream.close();
                outputStream.close();
                post.setBase64Image(base64Image);
			}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("posts",posts);
			//return new ModelAndView("user-homepage","user",u);
			if(u.getUserType().equals(UserType.ADMIN)) {
				return new ModelAndView("redirect:/admin/homepage","user",u);
			}
			return new ModelAndView("redirect:/user/homepage","user",u);
		}
		else {
			return new ModelAndView("loginpage");
		}
	}
	
	
	
	@GetMapping("/user/homepage")
	public String showuserHomepageget(@ModelAttribute("post")Post post, BindingResult result, SessionStatus status,ModelMap model,HttpSession session) throws SQLException,IOException,Exception{
		model.addAttribute("post", post);
		if(session.getAttribute("user")==null) {
			return "redirect:/user/loginpage";
		}
		List<Post> posts = postDao.getPosts();
		model.addAttribute("posts",posts);
		
		return "user-homepage";
	}
	
	
	
	
	@GetMapping("/user/logout")
	public ModelAndView logoutuser(ModelMap model, User user,HttpSession session) throws SQLException,IOException,Exception{
		session.invalidate();
		return new ModelAndView("redirect:/user/loginpage");
	}
	
	@GetMapping("/user/{type}")
	public String showPostsBasedOnPostType(@ModelAttribute("post")Post post, ModelMap model,HttpSession session,@PathVariable("type")String type) throws SQLException,IOException,Exception{
		model.addAttribute("post", post);
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
		return "user-homepage";
	}
	
	@GetMapping("/aboutWebsite")
	public String aboutWebsite() throws SQLException,IOException,Exception{
		
		return "aboutWebsite";
	}
	
	
	
	
}
