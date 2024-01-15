package com.anant.SpringSecurity_2X.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anant.SpringSecurity_2X.entity.Post;
import com.anant.SpringSecurity_2X.entity.PostStatus;
import com.anant.SpringSecurity_2X.repository.PostRepository;
import com.anant.SpringSecurity_2X.repository.PostService;


@Service
public class PostServiceImpl implements PostService{

	
	@Autowired
	private PostRepository postRepo;

	@Override
	public Post createPost(Post post) {
		// TODO Auto-generated method stub
		
		post.setStatus(PostStatus.PENDING);
		
		Post post_saved=postRepo.save(post);
		
		
		return post_saved;
	}

	@Override
	public void approvePost(int postId) {
		// TODO Auto-generated method stub
		
		Post post=postRepo.findById(postId).get();
		post.setStatus(PostStatus.APPROVED);
		postRepo.save(post);
		
	}

	@Override
	public void approveAll() {
		// TODO Auto-generated method stub
	 postRepo.findAll().stream().filter(post->post.getStatus().equals(PostStatus.PENDING))
	 .forEach(post->{
		 
		 post.setStatus(PostStatus.APPROVED);
		 postRepo.save(post);
	 });
	}

	@Override
	public void removePost(int postId) {
		// TODO Auto-generated method stub
		
		Post post=postRepo.findById(postId).get();
		post.setStatus(PostStatus.REJECTED);
		postRepo.save(post);
		
		
	}

	@Override
	public void removeAll() {
		// TODO Auto-generated method stub
		
		postRepo.findAll().stream().filter(post-> post.getStatus().equals(PostStatus.PENDING))
		.forEach(post->{
			
			post.setStatus(PostStatus.REJECTED);
			postRepo.save(post);
		});
		
	}

	@Override
	public List<Post> viewAllApprovedPost() {
		// TODO Auto-generated method stub
		
	List<Post> posts=postRepo.findAll().stream().filter(post->post.getStatus().equals(PostStatus.APPROVED))
		.collect(Collectors.toList());
		return posts;
	}
}
