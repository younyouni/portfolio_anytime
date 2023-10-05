package com.naver.anytime.task;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class SendMail {
	private static final Logger logger = LoggerFactory.getLogger(SendMail.class);

	private JavaMailSenderImpl mailSender;

	@Autowired
	public SendMail(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	// 회원가입시 메일 본인 인증
	public void sendAuthEmail(String to, String subject, String authCode) {
		MimeMessagePreparator mp = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom("chaji281@naver.com"); // 보내는 사람의 이메일 주소
				helper.setTo(to);
				helper.setSubject(subject);

				String content = "애니타임 인증 코드: " + authCode;
				helper.setText(content, true);

			}
		};
		mailSender.send(mp);
		logger.info("메일 전송했습니다.");
	}

	// 아이디 찾기 이메일
	public void sendFindIdEmail(String to, String subject, String foundId) {
		MimeMessagePreparator mp = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom("chaji281@naver.com"); // 보내는 사람 이메일 주소
				helper.setTo(to);
				helper.setSubject(subject);

				String content = "애니타임 가입 아이디 " + foundId;
				helper.setText(content, true);

			}
		};
		try {
			mailSender.send(mp);
			logger.info("아이디 찾기 메일 전송했습니다. ");
		} catch (MailException e) {
			logger.error("메일 전송 실패", e);
		}
	}

}