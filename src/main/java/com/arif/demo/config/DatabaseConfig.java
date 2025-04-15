package com.arif.demo.config;


import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.time.Duration;


@Configuration
public class DatabaseConfig extends AbstractR2dbcConfiguration {

    @Value("${db.host}")
    private String host;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.name}")
    private String dbName;

    @Value("${db.max-pool-size}")
    private int maxPoolSize;

    @Value("${db.initial-pool-size}")
    private int initialPoolSize;

    @Value("${db.change-log}")
    private String changeLog;

    @Value("${spring.application.name}")
    private String applicationName;


    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        PostgresqlConnectionConfiguration configuration = PostgresqlConnectionConfiguration.builder()
                .host(host.split(":")[0])
                .port(Integer.parseInt(host.split(":")[1]))
                .username(username)
                .password(password)
                .database(dbName)
                .build();
        ConnectionFactory connectionFactory = new PostgresqlConnectionFactory(configuration);
        ConnectionPoolConfiguration connectionPoolConfiguration =
                ConnectionPoolConfiguration.builder(connectionFactory)
                        .maxLifeTime(Duration.ofHours(12))
                        .initialSize(initialPoolSize)
                        .maxSize(maxPoolSize)
                        .name(applicationName)
                        .build();
        return new ConnectionPool(connectionPoolConfiguration);
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        String url = "jdbc:postgresql://" + host + "/" + dbName;

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(changeLog);
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

}
