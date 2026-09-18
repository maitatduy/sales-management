package com.salesmanager.console;

import com.salesmanager.exception.AppException;
import com.salesmanager.model.User;
import com.salesmanager.service.AuthService;
import com.salesmanager.util.InputHelper;

import java.util.Scanner;

public class ConsoleApp {
    private final Scanner scanner = new Scanner(System.in);
    private final InputHelper input = new InputHelper(scanner);
    private final AuthService authService = new AuthService();

    public void run() {
        System.out.println("=========================================");
        System.out.println("| Hệ thống quản lý bán hàng - Đăng nhập |");
        System.out.println("=========================================");

        User user = login();
        if (user != null) {
            System.out.println("Đăng nhập thành công. Xin chào, " + user.getFullName()
                    + " (" + user.getRole() + ")");
        }
    }

    private User login() {
        String username = input.readLine("Tên đăng nhập (Để trống nếu muốn thoát): ");
        if (username.isBlank()) {
            return null;
        }
        String password = input.readLine("Mật khẩu: ");
        try {
            return authService.login(username, password);
        } catch (AppException e) {
            System.out.println("Lỗi: " + e.getMessage());
            return login();
        }
    }
}