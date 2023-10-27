package com.naver.anytime.mybatis.mapper;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.naver.anytime.domain.Post;

@Mapper
public interface PostMapper {

	public int getListCount(int board_id); // 수정

	public String getPostlist(String schoolName);

	public List<Post> getPostList(int page, int limit, int board_id); // 수정 ?

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

	public List<Post> getPostList(HashMap<String, Integer> map);

	// 이름 확인용
	public List<Post> getUserNickname(Map<String, String> map);
	
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
    
    public void deletePhoto(int photo_id);
	
	// 검색용
	public List<Post> getSearchPostList(HashMap<String, Object> map);

	// 검색용
	public int getSearchListCount(int board_id, int search_field, String search_word);
	
	//전체 검색용 리스트 결과
	public List<Post> getAllSearchPostList(HashMap<String, Object> map);
	
	//전체 검색용 리스트 총 수
	public int getAllSearchListCount(int school_id, String search_word);
	
	//게시물 신고수
	public int getPostReportCount(int post_id);
	
	//게시물 스크랩 리스트
	public List<Post> getMyScrapList(HashMap<String, Integer> map);
	
	//내가 쓴 글 리스트 총 수
	public int getMyArticlesListCount(int user_id);
	
	//내가 쓴 글 리스트
	public List<Post> getMyArticlesList(HashMap<String, Integer> map);
	
	//내가 댓글 단 글 리스트 총 수
	public int getMyCommentArticlesListCount(int user_id);
	
	//내가 댓글 단 글 리스트
	public List<Post> getMyCommentArticlesList(HashMap<String, Integer> map);
	// ********************************= 윤희 =********************************
	
	// 메인 커뮤니티 페이지 게시물 리스트 출력 사용X
	public List<Post> getMainPostList(int school_id);

	public List<Post> getPostListByBoard(int board_id);

	public List<Post> getPostTotalList(HashMap<String, Object> map);

	public int getPostTotalListCount(HashMap<String, Object> map);

	public int getPost(int content_id);

	//핫 게시물 리스트 총 수
	public int getHotPostListCount(int school_id);

	//베스트 게시물 리스트 총 수
	public int getBestPostListCount(int school_id);
	
	//핫 게시물 리스트
	public List<Post> getHotPostList(HashMap<String, Object> map);
	
	//베스트 게시물 리스트
	public List<Post> getBestPostList(HashMap<String, Object> map);
	
	//라이트 사이드 핫 게시물 리스트
	public List<Post> getHotPostList4(int school_id);


}
