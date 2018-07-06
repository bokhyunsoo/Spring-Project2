package com.example.spring02.controller.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.board.dto.ReplyDTO;
import com.example.spring02.service.board.ReplyService;

//Controller - 뷰를 리턴
//			 데이터를 리턴하려면 @ResponseBody
//RestController - 데이터(json)를 리턴
//				 뷰를 리턴하려면 ModelAndView

@RestController // 스프링 4.0부터 사용
@RequestMapping("/reply/*")
public class ReplyController {

	@Inject
	ReplyService replyService;
	
	@RequestMapping("insert.do")
	public void insert(@ModelAttribute ReplyDTO dto, HttpSession session) {
		String userid = (String)session.getAttribute("userid");
		dto.setReplyer(userid);
		replyService.create(dto);
	}
	// RestController의 주용도는 뷰가 아닌 데이터를 리턴
	// 뷰를 리턴할 경우는 ModelAndView 사용
	@RequestMapping("list.do")
	public ModelAndView list(@RequestParam int bno, @RequestParam(defaultValue="1") int curPage, ModelAndView mav, HttpSession session) {
		List<ReplyDTO> list = replyService.list(bno, 1, 5, session);
		mav.setViewName("board/reply_list"); // 뷰의 이름
		mav.addObject("list", list); // 뷰에 전달할 데이터
		return mav;
	}
// @ResponseBody : 리턴 타입이 json
	@RequestMapping("list_json.do")
	public @ResponseBody List<ReplyDTO> list_json(@RequestParam(defaultValue="1") int curPate, @RequestParam int bno, HttpSession session){
		return replyService.list(bno, 1, 10, session);
	}
}
