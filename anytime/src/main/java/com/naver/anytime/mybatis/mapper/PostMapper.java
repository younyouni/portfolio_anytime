package com.naver.anytime.mybatis.mapper;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Member;
import com.naver.anytime.domain.Post;

@Mapper
public interface PostMapper {
	
	public int getListCount(int board_id);										//수정

	public String getPostlist(String schoolName);

	public List<Post> getPostList(int page, int limit, int board_id);			//수정 ?

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

	public ArrayList<Post> getPostSimpleList(int school_num, int board_num);

	public List<ArrayList<Post>> getPostTotalList(int school_num, int[] board_nums);

	public boolean postModify(Post modifypost, String filename);

	/*
	 * private void photoDataUpdate(Connection con, int post_num, String filename);
	 * 
	 * private boolean photoDataUpdate2(Connection con, int post_num, String
	 * filename);
	 * 
	 * private boolean postDataUpdate(Connection con, PostBean modifypost);
	 */

	public int postDelete(int post_num);
	
	public List<Post> getPostList(HashMap<String, Integer> map);
	
	// 이름 확인용
	public List<Post> getUserNickname(Map<String, String> map);
	
	public List<Post> getSearchPostList(HashMap<String, Object> map);
	
}
