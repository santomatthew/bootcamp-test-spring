package com.lawencon.myapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.Review;

public interface ReviewDao {

	Review insert(Review review) throws SQLException;
	Review update(Review review) throws SQLException;
	List<Review> getAll()  throws SQLException;
}
