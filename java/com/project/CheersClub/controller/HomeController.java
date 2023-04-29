package com.project.CheersClub.controller;



import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.engine.jdbc.BlobProxy;
import org.json.simple.JSONObject;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.project.CheersClub.dao.PageDAO;
import com.project.CheersClub.dao.PostDao;
import com.project.CheersClub.dao.UserDAO;
import com.project.CheersClub.pojo.Post;
import com.project.CheersClub.pojo.User;
import com.project.CheersClub.pojo.UserType;
import com.project.CheersClub.pojo.Userpages;
import com.project.CheersClub.util.HibernateUtil;
import com.project.CheersClub.validator.PostValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
//@RequestMapping("/user/homepage")
public class HomeController {
	@Autowired
	UserDAO userDao;
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	PostValidator validator;
	
	@Autowired
	PageDAO pageDao;
	
//	@PostMapping("/user/homepage")
//	public String showhomePage(ModelMap model) {
//		//model.addAttribute("post", post);
//		return "user-homepage";
//	}
	public User validateUser(HttpSession session)  {
		if(session.getAttribute("user")==null) {
			return null;
		}
		User user = (User)session.getAttribute("user");
		
		return user;
	}
	
	@PostMapping("/user/createPost")
	public ModelAndView createPost(@RequestParam("myParagraph") String myParagraph, @ModelAttribute("post")Post post, BindingResult result, SessionStatus status,ModelMap model,@RequestParam("file")String filePath,HttpServletRequest request){
		model.addAttribute("post", post);

		 validator.validate(post, result);
	        
	     if(result.hasErrors())
	    	 //return "user-homepage";
			 return new ModelAndView("redirect:/user/homepage");
	        
	        
	    status.setComplete();
	    //post.setUser((User)session.getAttribute("user"));
	    System.out.println();
	    
	    User u = userDao.getUser(Integer.parseInt(myParagraph));
	    post.setUser(u);
	    Instant instant = Instant.now();
        Timestamp currentTimestamp = Timestamp.from(instant);
	    post.setCreatedOn(currentTimestamp);
	    String fullPath = request.getServletContext().getRealPath(filePath);
	    try(Session session = HibernateUtil.getSessionFactory().openSession()) {
	    	session.doWork(conn -> {
				try {
					post.setData(BlobProxy.generateProxy(getImage(fullPath)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
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
				catch(Exception e) {
					e.printStackTrace();
				}
				
			});
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	    
	    postDao.save(post);
	    //request.setAttribute("posted", "posted");
	    List<Post> posts = postDao.getPosts();
	    model.addAttribute("posts",posts);
	    
		//return new ModelAndView("user-homepage","user",u);
		return new ModelAndView("redirect:/user/homepage");
		//return "user-homepage";
	}
	private static byte[] getImage(String filePath) throws SQLException,IOException,Exception{
		// TODO Auto-generated method stub
		Path path = Paths.get(filePath);
		byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
//	@GetMapping("/user/createPost")
//	public String createPostget(ModelMap model, Post post) {
//		return "user-homepage";
//	}
	
	@PostMapping("/user/updateVote")
	//public int updateVote(ModelMap model,@RequestParam("post")Post post,@RequestParam("button")String button)
	public void updateVote(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws SQLException,IOException,Exception{
		int updateVotes=0;
		if(request.getParameter("button").equals("upvote")) {
			updateVotes =  postDao.upvotePost(Integer.parseInt(request.getParameter("postid")),Integer.parseInt(request.getParameter("userid")));
			
		}
		else {
			updateVotes = postDao.downvotePost(Integer.parseInt(request.getParameter("postid")),Integer.parseInt(request.getParameter("userid")));
		}
		
		JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("updateVotes", updateVotes);
	    List<Post> posts = postDao.getPosts();
	    model.addAttribute("posts",posts);
	    // Set the content type to JSON and send the response
	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    out.print(jsonResponse.toString());
	    out.flush();
	    
	}
	@PostMapping("/user/searchAll")
	public String showSearchResult(@RequestParam("searchContent")String searchContent,ModelMap model,HttpSession session) {
		User user = validateUser(session);
		if(user==null) {
			return "redirect:/loginpage";
		}
		List<Post> posts = postDao.getPostsbasedOnSearchContent(searchContent);
		model.addAttribute("posts",posts);
		List<Userpages> searchPagesList = pageDao.getsearchPagesList(searchContent,user.getUserid());
		model.addAttribute("searchPagesList",searchPagesList);
		
		return "searchResult";
	}
	
	@GetMapping("/user/userProfile")
	public String showuserHomepageget(ModelMap model,HttpSession session) throws SQLException,IOException,Exception{
		
		if(session.getAttribute("user")==null) {
			return "redirect:/user/loginpage";
		}
		User u = (User)session.getAttribute("user");
		List<Post> posts = postDao.getUserPosts(u.getUserid());
		model.addAttribute("posts",posts);
		
		return "user-profile";
	}
	
	@PostMapping("/user/deletePost")
	public void deletePost(ModelMap model,HttpSession session,HttpServletRequest request,HttpServletResponse response) throws SQLException,IOException,Exception{
		
		int updateVotes=0;
		
		Post p =postDao.getPost(Integer.parseInt(request.getParameter("postid")));
		postDao.delete(p);
		
		
		JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("updateVotes", updateVotes);
	    List<Post> posts = postDao.getPosts();
	    model.addAttribute("posts",posts);
	    // Set the content type to JSON and send the response
	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    out.print(jsonResponse.toString());
	    out.flush();
	}
	

}
