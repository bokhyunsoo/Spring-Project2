package com.example.spring02.controller.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller// 컨트롤러 빈으로 등록
@RequestMapping("member/*") // 공통적인 url 매핑
public class MemberController {
	
	//로깅을 위한 변수
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@RequestMapping("login.do")  // 세부적인 url 매핑
	public String login() {
		return "member/login"; // login.jsp로 이동
	}
}
