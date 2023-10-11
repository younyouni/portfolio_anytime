package com.naver.anytime.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Message;
import com.naver.anytime.mybatis.mapper.MessageMapper;

@Service
public class MessageServiceImpl implements MessageService{
	
	private MessageMapper dao;
	
	public MessageServiceImpl(MessageMapper dao) {
		this.dao = dao;
	}

	public int getUserIdConversion(int num) {
		return dao.getUserIdConversion(num);
	}

	public int insertMessage(int receiver_user_id) {
		return dao.insertMessage(receiver_user_id);
	}

	@Override
	public List<Message> getMessageList(int user_id) {
		return dao.getMessageList(user_id);
	}
}
