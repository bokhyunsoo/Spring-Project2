package com.example.spring02.controller.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.shop.dto.CartDTO;
import com.example.spring02.service.shop.CartService;

@Controller
@RequestMapping("shop/cart/*") // 공통적인 url mapping
public class CartController {
	
	@Inject
	CartService cartService;
	
	@RequestMapping("list.do")
	public ModelAndView list(HttpSession session, ModelAndView mav) {
		Map<String,Object> map = new HashMap<>();
		// 세션변수 확인
		String userid = (String) session.getAttribute("userid");
		if(userid != null) { // 로그인한 경우
			List<CartDTO> list = cartService.listCart(userid);
			map.put("list", list); // 맵에 자료 추가
			map.put("count", list.size());
			mav.setViewName("shop/cart_list"); // jsp 페이지 이름
			mav.addObject("map", map); // jsp에 전달할 데이터
//			mav.addObject("list", list);
//			mav.addObject("count", list.size());
			return mav;
		} else { // 로그인하지 않은 경우
			return new ModelAndView("member/login","",null);
		}
	}
	
	@RequestMapping("insert.do") // 세부적인 url mapping
	public String insert(HttpSession session, @ModelAttribute CartDTO dto) {
		//세션에 userid 변수가 존재하는지 확인
		String userid = (String) session.getAttribute("userid");
		if(userid == null) { // 로그인 하지 않은 상태
			return "redirect:/member/login.do"; // 로그인 페이지로
		}
		//장바구니에 insert 처리 후 장바구니 목록으로 이동
		dto.setUserid(userid);
		cartService.insert(dto);
		return "redirect:/shop/cart/list.do";
	}
}
