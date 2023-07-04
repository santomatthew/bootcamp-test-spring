package com.lawencon.myapp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.constant.UserRole;
import com.lawencon.myapp.dao.FileDao;
import com.lawencon.myapp.dao.ProfileDao;
import com.lawencon.myapp.dao.RoleDao;
import com.lawencon.myapp.dao.UserDao;
import com.lawencon.myapp.model.File;
import com.lawencon.myapp.model.Profile;
import com.lawencon.myapp.model.Role;
import com.lawencon.myapp.model.User;
import com.lawencon.myapp.service.UserService;

public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final ProfileDao profileDao;
	private final FileDao fileDao;
	private final RoleDao roleDao;
	private final Connection conn;

	public UserServiceImpl(UserDao userDao, ProfileDao profileDao, FileDao fileDao, RoleDao roleDao,DataSource dataSource)
			throws SQLException {
		this.userDao = userDao;
		this.profileDao = profileDao;
		this.fileDao = fileDao;
		this.roleDao =roleDao;
		this.conn = dataSource.getConnection();
		this.conn.setAutoCommit(false);
	}

	@Override
	public List<User> getAll() throws SQLException {
		final List<User> users = userDao.getAll();
		return users;
	}

	@Override
	public User insert(User user, Profile profile, File file) {

		User newUser = null;
		try {
			final File photo = fileDao.insert(file);
			profile.setPhoto(photo);
			final Profile userProfile = profileDao.insert(profile);
			user.setProfile(userProfile);
			newUser = userDao.insert(user);
			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return newUser;
	}

	@Override
	public User update(User user, Profile profile) {
		User updatedUser = null;
		try {

			final Profile userProfile = profileDao.update(profile);
			user.setProfile(userProfile);

			updatedUser = userDao.update(user);
			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return updatedUser;
	}

	@Override
	public Profile changePhoto(Long profileId, File file) throws SQLException {
		Profile newProfile = null;
		try {
			final Profile currentProfile = profileDao.getById(profileId);
			final File newPhoto = fileDao.insert(file);
			currentProfile.setPhoto(newPhoto);

			newProfile = profileDao.update(currentProfile);
			fileDao.deleteById(newPhoto.getId());
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return newProfile;
	}

	@Override
	public User login(String email, String password) throws SQLException {
		User user = null;
		try {
			final User checkUser = userDao.getByUsernameAndPassword(email, password);
			user = checkUser;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public String getRoleView(String roleInput) {
		final UserRole[] userRole = UserRole.values();
		String roleCode = "";

		for (int i = 0; i < userRole.length; i++) {
			if (userRole[i].roleCode.equals(roleInput)) {
				roleCode = userRole[i].roleCode;
			}
		}
		return roleCode;
	}

	@Override
	public List<User> getByRoleCode(String roleCode) throws SQLException {
		final List<User> candidates = userDao.getByRoleCode(roleCode);
		return candidates;
	}

	@Override
	public List<Role> getRoleExceptSuperAdmin() throws SQLException {
		final List<Role> roles = roleDao.getAll();
		final List<Role> updatedRoles = new ArrayList<>();
		
		for(int i=0;i<roles.size();i++) {
			if(roles.get(i).getRoleCode().equals(UserRole.HUMANRESOURCE.roleCode) || roles.get(i).getRoleCode().equals(UserRole.REVIEWER.roleCode)) {
				updatedRoles.add(roles.get(i));
			}
		}
		
		return updatedRoles;
	}

	@Override
	public Role getByCode(String roleCode) throws SQLException {
		Role role = new Role();
		role.setRoleCode(roleCode);
		
		role = roleDao.getByRoleCode(role);
		return role;
	}

}
