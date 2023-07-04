package com.lawencon.myapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.ReviewDetail;

public interface ReviewDetailDao {

	ReviewDetail insert(ReviewDetail reviewDetail) throws SQLException;
	ReviewDetail update(ReviewDetail reviewDetail) throws SQLException;
	List<ReviewDetail> get (ReviewDetail reviewDetail)throws SQLException;
}
