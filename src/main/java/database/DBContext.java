package database;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBContext {

    // --- CẤU HÌNH THÔNG TIN KẾT NỐI ---
    private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    // SỬA LẠI DB_URL: Thêm integratedSecurity=true để dùng quyền đăng nhập Windows
    private static final String DB_URL =
            "jdbc:sqlserver://localhost:1433;"
          + "databaseName=OldBookStoreDB;"
          + "encrypt=true;"
          + "trustServerCertificate=true;"
          + "integratedSecurity=true;";

    // ĐÃ COMMENT: Không dùng tài khoản sa nữa
    // private static final String DB_USER = "sa";
    // private static final String DB_PASSWORD = "123"; 

    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();
            
            // 1. Cấu hình cơ bản
            config.setDriverClassName(DB_DRIVER);
            config.setJdbcUrl(DB_URL);
            
            // ĐÃ COMMENT: Không nạp username và password vào config nữa
            // config.setUsername(DB_USER);
            // config.setPassword(DB_PASSWORD);
            
            // 2. Cấu hình tối ưu cho HikariCP
            config.setMinimumIdle(5);
            config.setMaximumPoolSize(20);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);

            // 3. Tạo DataSource
            dataSource = new HikariDataSource(config);
            
        } catch (Exception e) {
            System.err.println("Lỗi khởi tạo HikariCP: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("HikariDataSource chưa được khởi tạo!");
        }
        return dataSource.getConnection();
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Kết nối SQL Server (Windows Authentication) thành công!");
                System.out.println("Tên DB: " + conn.getCatalog());
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối:");
            e.printStackTrace();
        }
    }
}