package com.naver.anytime.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Message;

@Mapper
public interface MessageMapper {
	
	public int getUserIdConversion(int num);

	public int insertMessage(int receiver_user_id);

	public List<Message> getMessageList(int user_id);

}
