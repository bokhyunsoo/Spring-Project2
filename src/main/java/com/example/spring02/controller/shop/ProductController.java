package com.example.spring02.controller.shop;

import java.io.File;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.shop.dto.ProductDTO;
import com.example.spring02.service.shop.ProductService;

@Controller
@RequestMapping("shop/product/*")
public class ProductController {

	@Inject // 의존관계 주입
	ProductService productService;

	@RequestMapping("write.do")
	public String write() {
		return "shop/product_write";
	}

	@RequestMapping("edit/{product_id}")
	public ModelAndView edit(@PathVariable("product_id") int product_id, ModelAndView mav) {
		// 뷰의 이름
		mav.setViewName("/shop/product_edit");
		// 뷰에 전달할 데이터
		mav.addObject("dto", productService.detailProduct(product_id));
		// product_edit.jsp로 포워딩
		return mav;
	}

	@RequestMapping("insert.do")
	public String insert(@ModelAttribute ProductDTO dto) {

		

		String filename = "-";
		if (!dto.getFile1().isEmpty()) { // 첨부파일 존재
			filename = dto.getFile1().getOriginalFilename();
			try {
				// 개발 디렉토리
				// String path =
				// "D:\\Spring\\spring02\\src\\main\\webapp\\WEB-INF\\views\\images\\";
				// 배포 디렉토리
				String path = "D:\\Spring\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\spring02\\WEB-INF\\views\\images\\";

				new File(path).mkdir();
				// 업로드된 임시파일을 원하는 디렉토리로 복사
				dto.getFile1().transferTo(new File(path + filename));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		dto.setPicture_url(filename);
		// 상품정보를 레코드에 저장
		productService.insertProduct(dto);
		// 상품 목록 페이지로 이동
		
		System.out.println(dto);
		
		return "redirect:/shop/product/list.do";
	}

	@RequestMapping("update.do")
	public String update(@ModelAttribute ProductDTO dto) {

		System.out.println(dto);

		String filename = "-";
		if (!dto.getFile1().isEmpty()) { // 첨부파일 존재
			filename = dto.getFile1().getOriginalFilename();
			try {
				// 개발 디렉토리
				// String path =
				// "D:\\Spring\\spring02\\src\\main\\webapp\\WEB-INF\\views\\images\\";
				// 배포 디렉토리
				String path = "D:\\Spring\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\spring02\\WEB-INF\\views\\images\\";

				new File(path).mkdir();
				// 업로드된 임시파일을 원하는 디렉토리로 복사
				dto.getFile1().transferTo(new File(path + filename));
			} catch (Exception e) {
				e.printStackTrace();
			} // 새로운 파일 첨부가 없을 때
				// 기존의 첨부파일 정보가 지워지지 않도록 처리
		} else {
			ProductDTO dto2 = productService.detailProduct(dto.getProduct_id());
			dto.setPicture_url(dto2.getPicture_url());
		}
		productService.updateProduct(dto);
		// 상품 목록 페이지로 이동

		return "redirect:/shop/product/list.do";
	}
	
	@RequestMapping("delete.do")
	public String delete(@RequestParam int product_id) {
		// 상품코드에 해당하는 첨부파일 이름 조회
		String filename = productService.fileInfo(product_id);
		if(filename != null || filename.equals("-")) { // 첨부파일이 존재하면
			String path="D:\\Spring\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\spring02\\WEB-INF\\views\\images\\"; // 첨부파일의 디렉토리
			File f = new File(path+filename);
			if(f.exists()) { // 파일이 존재하면
				f.delete(); // 파일삭제
			}
		}
		productService.deleteProduct(product_id);
		return "redirect:/shop/product/list.do";
	}

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
