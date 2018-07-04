package com.example.spring02.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.board.dto.BoardDTO;
import com.example.spring02.service.board.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	BoardService boardService;
	
	@RequestMapping("list.do")
	public ModelAndView list(String search_option, String keyword) throws Exception {
		int start = 0;
		int end = 0;
		List<BoardDTO> list = boardService.listAll(search_option, keyword, start, end);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/list"); // 포워딩할 페이지
		Map<String,Object> map = new HashMap<>();
		map.put("list", list); // 게시물 목록
		map.put("count", list.size()); // 레코드 갯수
		mav.addObject("map", map);
//		mav.addObject("list", list);
//		mav.addObject("count", list.size());
		//list.jsp로 포워딩
		return mav;
	}
	
	@RequestMapping("write.do")
	public String write() {
		return "board/write"; // views/board/write.jsp
	}
	
	@RequestMapping("insert.do")
	public String insert(@ModelAttribute BoardDTO dto, HttpSession session) throws Exception {
		//로그인한 사용자 아이디(세션변수)
		String writer = (String)session.getAttribute("userid");
		dto.setWriter(writer);
		boardService.create(dto);
		return "redirect:/board/list.do";
	}
}