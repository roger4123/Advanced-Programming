package HomeworkL8.db;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionHikariCP {
    private static final HikariDataSource ds = new HikariDataSource();

    static {
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/world_cities");
        ds.setUsername("root");
        ds.setPassword("");
        ds.setMaximumPoolSize(10);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
