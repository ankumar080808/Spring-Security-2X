package com.anant.SpringSecurity_2X.repository;

import java.util.List;

import com.anant.SpringSecurity_2X.entity.Post;

public interface PostService {
	
	public Post createPost(Post post);
	
	public void approvePost(int postId);
	
	public void approveAll();
	
	public void removePost(int postId);
	
	public void removeAll();
	
	public List<Post> viewAllApprovedPost();
	
	
	
	

}
