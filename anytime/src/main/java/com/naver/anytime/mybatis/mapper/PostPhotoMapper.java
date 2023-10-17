package com.naver.anytime.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.naver.anytime.domain.Photo;

@Mapper
public interface PostPhotoMapper {
    
	public void savePhoto(Photo photo) throws Exception;

	public void insertPhoto(Photo photo);
    

}
