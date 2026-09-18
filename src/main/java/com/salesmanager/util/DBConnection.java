package com.salesmanager.util;

import com.salesmanager.exception.DatabaseException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConnection {
    private static volatile DBConnection instance;

    private final String url;
    private final String username;
    private final String password;

    private DBConnection() {
        Properties props = loadProperties();
        this.url = props.getProperty("db.url");
        this.username = props.getProperty("db.username");
        this.password = props.getProperty("db.password");

        if (url == null || url.isBlank()) {
            throw new DatabaseException(
                    "Thiếu cấu hình db.url. Kiểm tra file db.properties trong classpath.", null);
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    private Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (is != null) {
                props.load(is);
            } else {
                AppLogger.get(DBConnection.class).warn("Không tìm thấy db.properties trong classpath.");
            }
        } catch (IOException e) {
            AppLogger.get(DBConnection.class).error("Lỗi đọc db.properties", e);
        }
        return props;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new DatabaseException("Không thể kết nối tới cơ sở dữ liệu: " + e.getMessage(), e);
        }
    }
}