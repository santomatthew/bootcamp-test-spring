package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.QuestionPackageDao;
import com.lawencon.myapp.model.QuestionPackage;

public class QuestionPackageDaoImpl implements QuestionPackageDao{

	private final Connection conn;

	public QuestionPackageDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}
	
	@Override
	public QuestionPackage insert(QuestionPackage questionPackage) throws SQLException {
		final String sql = "INSERT INTO t_question_package (hr_id,package_code,package_name,created_by,created_at,is_active,ver)VALUES(?,?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, questionPackage.getHr().getId());
		ps.setString(2, questionPackage.getPackageCode());
		ps.setString(3, questionPackage.getPackageName());
		ps.setLong(4,questionPackage.getCreatedBy());
		ps.setTimestamp(5, Timestamp.valueOf(questionPackage.getCreatedAt()));
		ps.setBoolean(6, questionPackage.getIsActive());
		ps.setInt(7, questionPackage.getVer());
		
		final ResultSet rs= ps.executeQuery();
		if(rs.next()) {
			questionPackage.setId(rs.getLong("id"));
		}
		
		return questionPackage;
	}

	@Override
	public List<QuestionPackage> getAll() throws SQLException {
		final String sql = "SELECT * FROM t_question_package";
		final PreparedStatement ps = conn.prepareStatement(sql);
		final ResultSet rs = ps.executeQuery();
		final List<QuestionPackage> questionPackages= new ArrayList<>();
		
		while(rs.next()) {
			final QuestionPackage questionPackage = new QuestionPackage();
			questionPackage.setId(rs.getLong("id"));
			questionPackage.setPackageCode(rs.getString("package_code"));
			questionPackage.setPackageName(rs.getString("package_name"));
			questionPackages.add(questionPackage);
		}
		return questionPackages;
	}

}
