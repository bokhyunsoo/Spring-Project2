package com.example.spring02.controller.memo;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.memo.dto.MemoDTO;
import com.example.spring02.service.memo.MemoService;



@Controller
@RequestMapping("memo/*")
public class MemoController {
	
	@Inject
	MemoService memoService;
// http://localhost/spring02/memo/list.do
//http://localhost/spring02/memo/insert.do
//http://localhost/spring02/memo/update.do
	
//	@RequestMapping("list.do") // 세부적인 url pattern
//	public ModelAndView list(ModelAndView mav) {
//		List<MemoDTO> items = memoService.list();
//		mav.setViewName("memo/memo_list"); // 포워딩할 뷰의 이름
//		mav.addObject("list" , items); // 전달할 데이터
//		return mav;
//	}
	
	@RequestMapping("list.do") // 세부적인 url pattern
	public ModelAndView list(ModelAndView mav) {
//		List<MemoDTO> items = memoService.list();
//		return new ModelAndView("memo/memo_list", "list" ,items);
		
		//포워딩할 뷰의 이름
		mav.setViewName("memo/memo_list");
		//전달할 데이터
		mav.addObject("list", memoService.list());
		return mav;
	}
	
	@RequestMapping("insert.do")
	public String insert(@ModelAttribute MemoDTO dto) {
		memoService.insert(dto);
		return "redirect:/memo/list.do";
	}
	
	@RequestMapping("view/{idx}")
	public ModelAndView view(@PathVariable int idx, ModelAndView mav) {
		//포워딩할 뷰의 이름
		mav.setViewName("memo/view");
		//전달할 데이터
		mav.addObject("dto", memoService.memo_view(idx));
		return mav;
	}
	
	@RequestMapping("update/{idx}")
	public String update(@PathVariable int idx,@ModelAttribute MemoDTO dto) {
		// 메모 수정
		memoService.update(dto);
		// 수정 완료 후 목록으로 이동
		return "redirect:/memo/list.do";
	}
	
	@RequestMapping("delete/{idx}")
	public String delete(@PathVariable int idx) {
		memoService.delete(idx);
		return "redirect:/memo/list.do";
		
	}
	
}
