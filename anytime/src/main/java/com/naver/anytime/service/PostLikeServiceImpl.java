package com.naver.anytime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.PostLike;
import com.naver.anytime.mybatis.mapper.PostLikeMapper;

@Service
public class PostLikeServiceImpl implements PostLikeService{
    private PostLikeMapper postLikeDao;
    
    @Autowired
    public PostLikeServiceImpl(PostLikeMapper postLikeDao) {
    	this.postLikeDao = postLikeDao;
    }
    
    @Override
    public int addLike(PostLike postlike) {
        return postLikeDao.addLike(postlike);
    }

    @Override
    public int removeLike(int post_like_id) {
        return postLikeDao.removeLike(post_like_id);
   }

	@Override
	public PostLike findExistingLike(int post_id, int user_id) {
		return postLikeDao.findExistingLike(post_id, user_id);
	}
	
}
