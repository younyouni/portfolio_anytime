package com.naver.anytime.service;

import com.naver.anytime.domain.PostLike;

public interface PostLikeService {
	
	int addLike(PostLike postlike);
	
    int removeLike(int post_like_id);
    
    PostLike findExistingLike(int post_id, int user_id);
    
}
