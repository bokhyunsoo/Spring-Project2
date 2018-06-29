package com.example.spring02.controller.shop;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.service.shop.ProductService;

@Controller
@RequestMapping("shop/product/*")
public class ProductController {
	
	@Inject // 의존관계 주입
	ProductService productService;
	
	@RequestMapping("list.do") // 세부적인 url pattern
	public ModelAndView list(ModelAndView mav) {
		// 포워딩할 뷰의 경로
		mav.setViewName("/shop/product_list");
		// 전달할 데이터
		mav.addObject("list", productService.listProduct());
		return mav;
	}
	
	// 상품코드가 {product_id}에 전달됨
	@RequestMapping("detail/{product_id}")
	public ModelAndView detail(@PathVariable int product_id, ModelAndView mav) {
		// 포워딩할 뷰의 경로
		mav.setViewName("/shop/product_detail");
		// 전달할 데이터
		mav.addObject("dto", productService.detailProduct(product_id));
		return mav;
	}
	
}
