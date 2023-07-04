package com.lawencon.myapp.service;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.QuestionTopic;

public interface QuestionTopicService {

	QuestionTopic createQuestionTopic(QuestionTopic questionTopic) throws SQLException;
	List<QuestionTopic> getAll()throws SQLException;
}
