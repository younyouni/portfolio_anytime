package com.naver.anytime.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Update;

import com.naver.anytime.domain.Post;

public interface PostService {

	public String getPostlist(String SchoolName);

	public int getListCount(int board_id);

	public List<Post> getPostList(int page, int limit, int board_id);

	public List<Post> getPostList(int board_num, String search_field, String search_word, int page, int limit);

	public List<Post> findSchoolBoardlistcount(int school_num);

	public int getSearchListCount(List<Post> bnc, String search_word);

	public List<Post> getSearchPostList(List<Post> bnc, String search_word, int page, int limit);

	public String findNickname(int num);

	public String findBoardname(int board_num);

	public int boardAnonymousCheck(int board_num);

	public int getListCount(int board_num, String search_field, String search_word);

	public Post getDetail(int post_id);

	public int getHotListCount(int school_num);

	public List<Post> getHotList(int school_num, int page, int limit);

	public int getBestListCount(int school_num);

	public List<Post> getBestList(int school_num, int page, int limit);

	public boolean postInsert(Post postdata, String userid, String filename);

	public boolean postDataInsert(Connection con, Post postdata, String userid);

	public boolean photoDataInsert(Connection con, int post_num, String filename);

	public boolean postModify(Post modifypost, String filename);

	/*
	 * private void photoDataUpdate(Connection con, int post_num, String filename);
	 * 
	 * private boolean photoDataUpdate2(Connection con, int post_num, String
	 * filename);
	 * 
	 * private boolean postDataUpdate(Connection con, PostBean modifypost);
	 */
	
	//글삭제 => STATUS로 접근불가
	public int updatePostStatus(int post_id);

	// 포스트 닉네임 확인용
	public List<Post> getUserNickname();
	
	//글쓰기 실험용
	public void insertPost(Post post);
	
	//글수정 실험용
	public void updatePost(Post post);
	
//	int increaseLike(Integer post_id, int currentUserId);
	
	public int getPostLikes(int post_id);
	
	@Update("UPDATE POST SET LIKE_COUNT = LIKE_COUNT + 1 WHERE POST_ID = #{POST_ID}")
    public void incrementLikes(int post_id);

    @Update("UPDATE POST SET LIKE_COUNT = LIKE_COUNT - 1 WHERE POST_ID = #{POST_ID}")
    public void decrementLikes(int post_id);
    
    public void updatePostFile(int post_id, String post_file);
    public void updatePostFile(Map<String, Object> params);

	//검색용 리스트 총 수
	public int getSearchListCount(int board_id, int search_field, String search_word);
	
	//검색용 리스트 결과
	public List<Post> getSearchPostList(int page, int limit, int board_id, int search_field, String search_word);
	
	//전체 검색용 리스트 총 수
	public int getAllSearchListCount(int school_id, String search_word);
	
	//전체 검색용 리스트 결과
	public List<Post> getAllSearchPostList(int page, int limit, int school_id, String search_word);

	// ********************************= 윤희 =********************************
	public List<List<Post>> getPostListByBoard(int[] board_ids);

	public List<Post> getPostTotalList(int board_id, int page, int limit, int searchKey, String keyword);

	public int getPostTotalListCount(int board_id, int searchKey, String keyword);


}
