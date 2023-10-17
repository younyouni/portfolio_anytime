package com.naver.anytime.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.naver.anytime.domain.Photo;

@Mapper
public interface PostPhotoMapper {
    
	public void insertPhoto(Photo photo);
    
	public List<Photo> getPhotosByPostId(int post_id);

}
