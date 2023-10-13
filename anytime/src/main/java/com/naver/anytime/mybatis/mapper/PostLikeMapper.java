package com.naver.anytime.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.PostLike;

@Mapper
public interface PostLikeMapper {
	
	int addLike(PostLike postlike);
	
    int removeLike(int post_like_id);
    
    PostLike findExistingLike(int post_id, int user_id);

}
