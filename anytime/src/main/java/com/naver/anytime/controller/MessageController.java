package com.naver.anytime.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Message;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.MessageService;

@RestController
public class MessageController {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	private MessageService messageService;
	private MemberService memberService;
	
	@Autowired
	public MessageController(MessageService messageService, MemberService memberService) {
		this.messageService = messageService;
		this.memberService = memberService;
	}
	
	@RequestMapping(value = "/message")
	@ResponseBody
	public ModelAndView message(
			ModelAndView mv	
			) {

		mv.setViewName("message/Message");
		return mv;
	}
	
	@RequestMapping(value = "/messagelist")
	@ResponseBody
	public List<Message> MessageList(
			Principal principal
			) {
		String login_id = principal.getName();						//로그인한 유저 login_id
		int user_id = memberService.getUserId(login_id);		//로그인한 유저 user_id 구하기		
		
		System.out.println("로그인 한 유저 아이디 [" + login_id + "] 로그인 한 유저 번호 [" + user_id + "]");
		
		List<Message> messageList = messageService.getMessageList(user_id);
		
		for(Message message : messageList) {
			if(message.getRECEIVER() == user_id) {
				message.setDIRECTION(2);
			} else if (message.getRECEIVER() == user_id){
				message.setDIRECTION(1);
			}
		}
		
		if(messageList != null) {
			System.out.println("==메시지 출력 성공==");
		}else {
			System.out.println("==메시지 출력 실패==");
		}
		
		System.out.println("값 =" + messageList);
		return messageList;
	}
	
	@RequestMapping(value = "/messagelastlist")
	@ResponseBody
	public List<Message> MessageLastList(
			Principal principal
			) {
		String login_id = principal.getName();					//로그인한 유저 login_id
		int user_id = memberService.getUserId(login_id);		//로그인한 유저 user_id 구하기		
		
		System.out.println("로그인 한 유저 아이디 [" + login_id + "] 로그인 한 유저 번호 [" + user_id + "]");
		
		List<Message> messageLastList = messageService.getMessageListROWNUM(user_id);
		
		if(messageLastList != null) {
			System.out.println("==메시지 출력 성공==");
		}else {
			System.out.println("==메시지 출력 실패==");
		}
		
		System.out.println("값 =" + messageLastList);
		return messageLastList;
	}	
	
	
	@RequestMapping(value = "/sendmessage")
	@ResponseBody
	public int sendMessage(
			@RequestParam("standard_nume") int num,
			@RequestParam("content") String content,
			Principal principal
			) {
		int result = 0;
		String login_id = principal.getName();						//로그인한 유저 login_id
		int sender_user_id = memberService.getUserId(login_id);		//로그인한 유저 user_id 구하기
		int receiver_user_id = messageService.getUserIdConversion(num);	//고유 번호에 해당하는 user_id 찾기
		int messageinsert = messageService.insertMessage(receiver_user_id);
		
		
		
		return result;
	}
}
