package com.example.spring02.service.pdf;

import java.io.FileOutputStream;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.example.spring02.model.shop.dto.CartDTO;
import com.example.spring02.service.shop.CartService;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfServiceImpl implements PdfService {
	
	@Inject
	CartService cartService;
	
	@Override
	public String createPdf() {
		String result="";
		try {
			// pdf 문서 객체
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("d:/Spring/sample.pdf"));
			document.open();
			BaseFont baseFont = BaseFont.createFont("c:/windows/Fonts/malgun.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			// 폰트 설정
			Font font = new Font(baseFont,12);
			// 테이블 생성(컬럼수)
			PdfPTable table = new PdfPTable(4);
			// 텍스트
			Chunk chunk = new Chunk("장바구니",font);
			// 문단
			Paragraph ph = new Paragraph(chunk);
			// 가운데 정렬
			ph.setAlignment(Element.ALIGN_CENTER);
			document.add(ph);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			//document.newPage(); // 페이지 나누기
			
			PdfPCell cell1 = new PdfPCell(new Phrase("상품명",font));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell2 = new PdfPCell(new Phrase("단가",font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell3 = new PdfPCell(new Phrase("수량",font));
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell4 = new PdfPCell(new Phrase("금액",font));
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			// 테이블에 셀을 추가
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			//for
			List<CartDTO> items = cartService.listCart("bok");
			for(int i=0; i<items.size(); i++) {
				CartDTO dto = items.get(i);
				PdfPCell cellProductName = new PdfPCell(new Phrase(dto.getProduct_name(), font)); // 상품명
				table.addCell(cellProductName);
				PdfPCell cellPrice= new PdfPCell(new Phrase(""+dto.getPrice(), font)); // 단가
				table.addCell(cellPrice);
				PdfPCell cellAmount = new PdfPCell(new Phrase(""+dto.getAmount(), font)); // 수량
				table.addCell(cellAmount);
				PdfPCell cellMoney = new PdfPCell(new Phrase(""+dto.getMoney(), font)); // 금액
				table.addCell(cellMoney);
				// 날짜 자료를 처리할 경우 참조 : 
//				Date date = dto.getRegdate(); // java.util.Date
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				String strDate = sdf/format(date);
			}
			//pdf에 테이블 추가
			document.add(table);
			document.close(); // pdf 파일 저장
			result="pdf 파일이 생성되었습니다.";
			
		}catch (Exception e) {
			e.printStackTrace();
			result="pdf 파일 생성 실패...";
		}
		return result;
	}

}
