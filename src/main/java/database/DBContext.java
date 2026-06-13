package database;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBContext {
    private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_URL =
            "jdbc:sqlserver://localhost:1433;"
          + "databaseName=OldBookStoreDB;"
          + "encrypt=true;"
          + "trustServerCertificate=true;"
          + "integratedSecurity=true;";
    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(DB_DRIVER);
            config.setJdbcUrl(DB_URL);
            config.setMinimumIdle(5);
            config.setMaximumPoolSize(20);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
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