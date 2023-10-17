package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Photo;

public interface PostPhotoService {
    
	public void insertPhoto(Photo photo);
	
	public List<Photo> getPhotosByPostId(int post_id);
    
}
