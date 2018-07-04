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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.board.dto.BoardDTO;
import com.example.spring02.service.board.BoardService;
import com.example.spring02.service.board.Pager;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	BoardService boardService;
	
	@RequestMapping("list.do")
	public ModelAndView list(@RequestParam(defaultValue="1") int curPage, @RequestParam(defaultValue="all") String search_option, @RequestParam(defaultValue="") String keyword) throws Exception {
		//레코드 갯수 계산
		int count = boardService.countArticle(search_option, keyword);
		//페이지의 시작번호, 끝번호 계산
		Pager pager = new Pager(count,curPage);
		int start = pager.getPageBegin();
		int end = pager.getPageEnd();
		List<BoardDTO> list = boardService.listAll(search_option, keyword, start, end);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/list"); // 포워딩할 페이지
		Map<String,Object> map = new HashMap<>();
		map.put("list", list); // 게시물 목록
		map.put("count", list.size()); // 레코드 갯수
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		map.put("pager", pager);
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
	
	@RequestMapping("view.do")
	public ModelAndView view(@RequestParam int bno, HttpSession session) throws Exception {
		//조회수 증가처리
		boardService.increaseViewcnt(bno, session);
		//레코드를 리턴받음
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/view");
		mav.addObject("dto", boardService.read(bno));
		return mav;
	}
	
	
	@RequestMapping("update.do")
	public String update(@ModelAttribute BoardDTO dto) throws Exception {
		boardService.update(dto);
		return "redirect:/board/list.do";
	}
	
	@RequestMapping("delete.do")
	public String delete(@RequestParam int bno) throws Exception {
		boardService.delete(bno);
		return "redirect:/board/list.do";
	}
}
