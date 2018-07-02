package com.example.spring02.controller.chart;

import java.io.FileOutputStream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.service.chart.JFreeChartService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@RequestMapping("/jchart/*")
public class JFreeChartController {
	@Inject
	JFreeChartService chartService;
	
	@RequestMapping("chart1.do")
	public void createChart1(HttpServletResponse response) {
		try { // 차트 생성 후 png 이미지로 출력
			JFreeChart chart = chartService.createChart();
// writeChartAsPNG (스트림, 차트, 가로, 세로)
			ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 900, 550);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("chart2.do")
	public ModelAndView createChart2(HttpServletResponse response) {
		String message = "";
		try {
			//차트 생성
			JFreeChart chart = chartService.createChart();
			//itextpdf 객체
			Document document = new Document();
			//pdf 파일 생성
			PdfWriter.getInstance(document, new FileOutputStream("d:/Spring/test.pdf"));
			document.open(); // pdf 오픈
		// 차트를 이미지 객체로 변환
			Image png = Image.getInstance(ChartUtilities.encodeAsPNG(chart.createBufferedImage(500, 500)));
			document.add(png); // pdf에 이미지 저장
			document.close(); // pdf가 저장됨
			message="pdf 파일이 생성되었습니다.";
		}catch(Exception e) {
			e.printStackTrace();
			message="pdf 파일 생성 실패...";
		}
		return new ModelAndView("chart/jchart02","message",message);
	}
}
