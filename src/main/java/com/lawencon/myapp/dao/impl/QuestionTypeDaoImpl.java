package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.QuestionTypeDao;
import com.lawencon.myapp.model.QuestionType;
import com.lawencon.myapp.model.User;

public class QuestionTypeDaoImpl implements QuestionTypeDao{

	private final Connection conn;

	public QuestionTypeDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}
	
	@Override
	public List<QuestionType> getAll() throws SQLException {
		final String sql = "SELECT * FROM t_question_type INNER JOIN t_user ON t_question_type.superadmin_id = t_user.id";
		final PreparedStatement ps = conn.prepareStatement(sql);
		final ResultSet rs = ps.executeQuery();
		final List<QuestionType> questionTypes= new ArrayList<>();
		
		while(rs.next()) {
			final QuestionType questionType = new QuestionType();
			questionType.setId(rs.getLong("id"));
			final User superadmin = new User();
			superadmin.setId(rs.getLong("superadmin_id"));
			questionType.setSuperadmin(superadmin);
			
			questionType.setTypeCode(rs.getString("type_code"));
			questionType.setQuestionType(rs.getString("question_type"));
			questionType.setCreatedBy(rs.getLong("created_by"));
			questionType.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			questionType.setIsActive(rs.getBoolean("is_active"));
			questionType.setVer(rs.getInt("ver"));
			questionTypes.add(questionType);
		}
		return questionTypes;
	}

	
}
