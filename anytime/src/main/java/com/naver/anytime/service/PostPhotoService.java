package com.naver.anytime.service;

import com.naver.anytime.domain.Photo;

public interface PostPhotoService {
    
	public void savePhoto(Photo photo) throws Exception;
    
	public void insertPhoto(Photo photo);
    
}
