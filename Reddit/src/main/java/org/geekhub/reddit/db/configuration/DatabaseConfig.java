package org.geekhub.reddit.db.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    @Profile("production")
    public DataSource hikariProdConfig(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.driver-class-name}") String driver,
            @Value("${spring.datasource.username}") String user,
            @Value("${spring.datasource.password}") String password,
            @Value("${spring.datasource.hikari.schema}") String schema
    ) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);
        hikariConfig.setSchema(schema);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    @Profile("dev")
    public DataSource hikariDevConfig(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.driver-class-name}") String driver,
            @Value("${spring.datasource.username}") String user,
            @Value("${spring.datasource.password}") String password
    ) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    @Profile("test")
    public DataSource hikariTestConfig(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.driver-class-name}") String driver,
            @Value("${spring.datasource.username}") String user,
            @Value("${spring.datasource.password}") String password
    ) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);
        return new HikariDataSource(hikariConfig);
    }
}
