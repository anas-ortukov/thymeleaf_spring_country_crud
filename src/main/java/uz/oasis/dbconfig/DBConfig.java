package uz.oasis.dbconfig;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Service;

@Service
public class DBConfig {

    public HikariDataSource dataSource;

    public DBConfig() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/thymeleaf_country_db");
        config.setUsername("postgres");
        config.setPassword("root123");
        config.setMaximumPoolSize(30);
        dataSource = new HikariDataSource(config);
    }

}
