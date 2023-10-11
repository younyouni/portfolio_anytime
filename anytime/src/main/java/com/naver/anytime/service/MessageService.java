package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Message;

public interface MessageService {
	
	public int getUserIdConversion(int num);

	public int insertMessage(int receiver_user_id);

	public List<Message> getMessageList(int user_id);

	public List<Message> getMessageListROWNUM(int user_id);

}
