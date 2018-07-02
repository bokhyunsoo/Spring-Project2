package com.example.spring02.controller.chart;

import javax.inject.Inject;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.service.chart.GoogleChartService;
// RestController : 뷰가 아닌 객체를 리턴할 수 있음
// ajax에서 백그라운드로 호출하는 코드에 많이 사용
@RestController // 스프링 4.0부터 지원
@RequestMapping("/chart/*") // 공통적인 url pattern
public class GoogleChartController {
	
	@Inject
	GoogleChartService googleChartService;
	
	@RequestMapping("chart1.do")
	public ModelAndView chart1() {
		// views/chart/chart01.jsp로 포워딩
		return new ModelAndView("chart/chart01");
	}
	
	@RequestMapping("chart2.do")
	public ModelAndView chart2() {
		return new ModelAndView("chart/chart02");
	}
//ArrayList를 JSON으로 가공한 후 ajax로 리턴됨
//뷰(jsp)로 포워딩되는 것이 아닌 json data를 리턴함
	@RequestMapping("cart_money_list.do")
//	@ResponseBody
	public JSONObject cart_money_list() {
		return googleChartService.getChartData();
	}
	
}
