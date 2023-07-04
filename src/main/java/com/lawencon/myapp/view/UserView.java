package com.lawencon.myapp.view;

import java.sql.SQLException;


import com.lawencon.myapp.constant.UserRole;
import com.lawencon.myapp.model.User;
import com.lawencon.myapp.service.AnswerService;
import com.lawencon.myapp.service.ReviewService;
import com.lawencon.myapp.service.UserService;
import com.lawencon.myapp.util.ScannerUtil;

public class UserView {

	private User user;
	private final UserService userService;
	private final SuperAdminView superAdminView;
	private final HumanResourceView humanResourceView;
	private final CandidateView candidateView;
	private final ReviewerView reviewerView;

	public UserView(User user,UserService userService, ReviewService reviewService, AnswerService answerService,
			SuperAdminView superAdminView, HumanResourceView humanResourceView, CandidateView candidateView,
			 ReviewerView reviewerView) {
		this.user = user;
		this.userService = userService;
		this.superAdminView = superAdminView;
		this.humanResourceView = humanResourceView;
		this.candidateView = candidateView;
		this.reviewerView = reviewerView;
	}

	public void show() throws SQLException {
		System.out.println("==== Welcome to Bootcamp Test ====");

		System.out.println("1. Login");
		System.out.println("2. Exit");
		final int option = ScannerUtil.scannerInt("Pilih menu : ", 1, 2);
		optionMenu(option);
	}

	private void optionMenu(int option) throws SQLException {
		if (option == 1) {
			showLogin();
		} else if (option == 2) {
			System.out.println("Anda keluar aplikasi");
		}
	}

	private void showLogin() throws SQLException {
		final String inputEmail = ScannerUtil.scannerStr("Email : ");
		final String inputPassword = ScannerUtil.scannerStr("Password : ");
		final User userDb = userService.login(inputEmail, inputPassword);
		if(userDb != null) {
			user.setId(userDb.getId());
			showUserByView(userDb.getRole().getRoleCode(), userDb);
		}
		else {
			System.out.println("Email atau password salah");
			showLogin();
		}
			

	}

	private void showUserByView(String roleCode, User user) throws SQLException {
		System.out.println("Halo " + user.getProfile().getFullName());
		if (roleCode.equals(UserRole.SUPERADMIN.roleCode)) {
			superAdminView.show();
		} else if (roleCode.equals(UserRole.HUMANRESOURCE.roleCode)) {
			humanResourceView.show();
		} else if (roleCode.equals(UserRole.REVIEWER.roleCode)) {
			reviewerView.show();
		} else if (roleCode.equals(UserRole.CANDIDATE.roleCode) && user.getIsActive()) {
			candidateView.show();
		}
		else {
			System.out.println("Maaf anda sudah mengerjakan soal, silahkan menunggu hasil review");
			show();
		}
		
		show();
	}



}
