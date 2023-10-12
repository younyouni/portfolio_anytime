package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Message;

public interface MessageService {
	
	public int getUserIdConversion(int num);
	
	public int getUserIdConversion2(int messageall_id);

	public List<Message> getMessageList(int user_id, int messageall_id);

	public List<Message> getMessageListROWNUM(int user_id);

	public int insertMessage(int check, int messageall_id, int sender_user_id, int receiver_user_id, String content);

	public int isMessageAllIdPresent(int sender_user_id, int receiver_user_id);

}
