package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.CandidateInformationDao;
import com.lawencon.myapp.model.CandidateInformation;

public class CandidateInformationDaoImpl implements CandidateInformationDao{

	private final Connection conn;
	
	
	public CandidateInformationDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}
	
	@Override
	public CandidateInformation insert(CandidateInformation candidateInformation) throws SQLException {
		final String sql = "INSERT INTO t_candidate_information (candidate_id,file_type_id,file_id,created_by,created_at,is_active,ver) VALUES (?,?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, candidateInformation.getCandidate().getId());
		ps.setLong(2, candidateInformation.getFileType().getId());
		ps.setLong(3, candidateInformation.getFile().getId());
		ps.setLong(4, candidateInformation.getCreatedBy());
		ps.setTimestamp(5, Timestamp.valueOf(candidateInformation.getCreatedAt()));
		ps.setBoolean(6, candidateInformation.getIsActive());
		ps.setInt(7,candidateInformation.getVer());	
		
		final ResultSet rs= ps.executeQuery();
		
		if(rs.next()) {
			candidateInformation.setId(rs.getLong("id"));
		}
		
		return candidateInformation;
	}

}
