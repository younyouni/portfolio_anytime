package com.naver.anytime.service;

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


    
    
}
