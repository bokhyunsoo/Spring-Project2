package com.example.spring02.controller.shop;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("shop/cart/*") // 공통적인 url mapping
public class CartController {
	@RequestMapping("insert.do") // 세부적인 url mapping
	public String insert(HttpSession session) {
		//세션에 userid 변수가 존재하는지 확인
		String userid = (String) session.getAttribute("userid");
		if(userid == null) { // 로그인 하지 않은 상태
			return "redirect:/member/login.do"; // 로그인 페이지로
		}
		//장바구니에 insert 처리 후 장바구니 목록으로 이동
		return "redirect:/shop/cart/list.do";
	}
}
