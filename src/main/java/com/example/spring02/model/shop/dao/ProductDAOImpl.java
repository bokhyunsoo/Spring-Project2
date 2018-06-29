package com.example.spring02.model.shop.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.spring02.model.shop.dto.ProductDTO;

@Repository
public class ProductDAOImpl implements ProductDAO {
	
	@Inject // 의존관계 주입
	SqlSession sqlSession;
	
	@Override
	public List<ProductDTO> listProduct() {
		return sqlSession.selectList("product.product_list");
	}

	@Override
	public ProductDTO detailProduct(int product_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateProduct(ProductDTO dto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteProduct(int product_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertProduct(ProductDTO dto) {
		// TODO Auto-generated method stub

	}

	@Override
	public String fileInfo(int product_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
