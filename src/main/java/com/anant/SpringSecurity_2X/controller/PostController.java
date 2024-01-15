package com.anant.SpringSecurity_2X.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anant.SpringSecurity_2X.entity.Post;
import com.anant.SpringSecurity_2X.repository.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
	
	
    @Autowired
	private PostService postService;
    
    @PostMapping("/create")
    public String createPost(@RequestBody Post post,Principal principal)
    {
    
    post.setUserName(principal.getName());
    postService.createPost(post);
    
    return "Post Successfully created by "+principal.getName()+" now required ADMIN/MODERATOR Action";
    }
    
    @GetMapping("/approvePost/{postId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public String approvePost(@PathVariable int postId,Principal principal)
    {
    	postService.approvePost(postId);
    	
    	return "Post Successfully Approved"+" "+principal.getName();
    }
    
    @GetMapping("/approveAll")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public String approveAll(Principal principal)
    {
    	postService.approveAll();
    	
    	return "All the pending posts are approved by Admin/Moderator"+" "+principal.getName();
    }
    
    
    @GetMapping("/removePost/{postId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public String removePost(@PathVariable int postId,Principal principal)
    {
    	postService.removePost(postId);
    	
    	return "Pending post removed by Admin/Moderator "+ principal.getName();
    }
    
    
    
    @GetMapping("/removeAll")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public String removeAll(Principal principal)
    {
    	postService.removeAll();
    	
    	return "All pending post successfully removed by ADMIN/Moderator" +" "+principal.getName();
    }
    
    
    @GetMapping("/viewAll")
    public List<Post> viewAllApprovedPost()
    {
    	List<Post> posts=postService.viewAllApprovedPost();
    	
    	return posts;
    }
}
