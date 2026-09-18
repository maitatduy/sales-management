package com.salesmanager.service;

import com.salesmanager.dao.UserDao;
import com.salesmanager.dao.impl.UserDaoImpl;
import com.salesmanager.enums.Role;
import com.salesmanager.exception.AuthenticationException;
import com.salesmanager.model.User;
import com.salesmanager.util.AppLogger;
import com.salesmanager.util.PasswordUtil;
import com.salesmanager.util.Validator;

public class AuthService {
    private static final AppLogger log = AppLogger.get(AuthService.class);
    private final UserDao userDao;

    public AuthService() {
        this.userDao = new UserDaoImpl();
    }

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User login(String username, String rawPassword) {
        Validator.notBlank(username, "Tên đăng nhập");
        Validator.notBlank(rawPassword, "Mật khẩu");

        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException("Sai tên đăng nhập hoặc mật khẩu"));

        if (!user.isActive()) {
            throw new AuthenticationException("Tài khoản đã bị khoá");
        }

        if (!PasswordUtil.matches(rawPassword, user.getPasswordHash())) {
            throw new AuthenticationException("Sai tên đăng nhập hoặc mật khẩu");
        }

        log.info("Đăng nhập thành công: " + username + " (role=" + user.getRole() + ")");
        return user;
    }

    public void requireAdmin(User currentUser) {
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            throw new AuthenticationException("Chỉ quản trị viên mới có quyền thực hiện thao tác này");
        }
    }
}