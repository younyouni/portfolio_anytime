package com.naver.anytime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Photo;
import com.naver.anytime.mybatis.mapper.PostPhotoMapper;

@Service
public class PostPhotoServiceImpl implements PostPhotoService {
    private PostPhotoMapper postPhotoDao;
    
    @Autowired
    public PostPhotoServiceImpl(PostPhotoMapper postPhotoDao) {
    	this.postPhotoDao = postPhotoDao;
    }
    
    @Override	
    public void insertPhoto(Photo photo) { 
    	postPhotoDao.insertPhoto(photo); 
      }

	@Override
	public List<Photo> getPhotosByPostId(int post_id) {
		return postPhotoDao.getPhotosByPostId(post_id);
	}


    
    
}
