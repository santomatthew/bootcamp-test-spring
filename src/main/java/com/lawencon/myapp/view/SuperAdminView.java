package com.lawencon.myapp.view;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.lawencon.myapp.model.File;
import com.lawencon.myapp.model.Profile;
import com.lawencon.myapp.model.Role;
import com.lawencon.myapp.model.User;
import com.lawencon.myapp.service.UserService;
import com.lawencon.myapp.util.ScannerUtil;

public class SuperAdminView {

	private final User superAdmin;
	private final UserService userService;
	
	public SuperAdminView(User superAdmin,UserService userService) {
		this.superAdmin = superAdmin;
		this.userService = userService;
	}

	public void show() throws SQLException {
		System.out.println("===== Super Admin Menu =====");
		System.out.println("1. Buat akun (HR / REVIEWER)");
		System.out.println("2. Logout");
		
		final Boolean chosenMenu = ScannerUtil.scannerBool("Pilih menu :");
		if(chosenMenu) {
			showCreateAcc();
		}
	}
	
	private void showCreateAcc() throws SQLException {
		final LocalDateTime timeNow = LocalDateTime.now();
		System.out.println("===== Create Account (HR/Reviewer) =====");
		final String inputEmail = ScannerUtil.scannerStr("Masukkan email : ");
		final String inputPassword = ScannerUtil.scannerStr("Masukkan password : ");
		final String fullName = ScannerUtil.scannerStr("Masukkan nama lengkap : ");
		final String address = ScannerUtil.scannerStr("Masukkan alamat : ");
		final String phone = ScannerUtil.scannerStr("Masukkan no telepon : ");
		final String photoName  = ScannerUtil.scannerStr("Masukkan nama foto : ");
		final String photoExt  = ScannerUtil.scannerStr("Masukkan ext foto : ");
		
		System.out.println("===== Choose Role =====");
		final List<Role> roles = userService.getRoleExceptSuperAdmin();
		for(int i=0;i<roles.size();i++) {
			System.out.println((i+1)+". "+ roles.get(i).getRoleName());
		}
		
		final int chosenRole = ScannerUtil.scannerInt("Pilih role : ", 1, roles.size());
		
		final File newFile = new File();
		newFile.setExt(photoExt);
		newFile.setFileName(photoName);
		newFile.setCreatedBy(superAdmin.getId());
		newFile.setCreatedAt(timeNow);
		newFile.setIsActive(true);
		newFile.setVer(1);
		
		final Profile newProfile = new Profile();
		newProfile.setPhoto(newFile);
		newProfile.setFullName(fullName);
		newProfile.setAddress(address);
		newProfile.setPhoneNo(phone);
		newProfile.setCreatedBy(superAdmin.getId());
		newProfile.setCreatedAt(timeNow);
		newProfile.setIsActive(true);
		newProfile.setVer(1);
		
		User newUser = new User();
		newUser.setUserEmail(inputEmail);
		newUser.setUserPassword(inputPassword);
		newUser.setRole(roles.get(chosenRole-1));
		newUser.setProfile(newProfile);
		newUser.setCreatedBy(superAdmin.getId());
		newUser.setCreatedAt(timeNow);
		newUser.setIsActive(true);
		newUser.setVer(1);
		
		newUser = userService.insert(newUser, newProfile, newFile);
		
		if(newUser!=null) {
			System.out.println("Berhasil membuat akun "+ roles.get(chosenRole-1).getRoleName() + " baru");
		}
		else {
			System.out.println("Gagal membuat akun");
		}
		
		
		show();
	}
	
	
}
