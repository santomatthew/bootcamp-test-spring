package com.lawencon.myapp;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lawencon.myapp.view.UserView;

public class App {

	public static void main(String[] args) throws SQLException {
		final ApplicationContext context = new ClassPathXmlApplicationContext("main.xml");	
		final UserView userView =  context.getBean("userView", UserView.class);
		userView.show();
		

		
	}
	
}
