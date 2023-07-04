package com.lawencon.myapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.QuestionTopic;

public interface QuestionTopicDao{

	QuestionTopic insert (QuestionTopic questionTopic) throws SQLException;
	List<QuestionTopic> getAll()throws SQLException;
	
}
