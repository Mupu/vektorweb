package me.mupu.vektorweb;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class JOOQConfiguration {

    @Value("${spring.datasource.url}")
    private String URL;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.flyway.schemas}")
    private String databaseName;

    @Bean
    @Primary
    public DSLContext getContext() {
        return DSL.using(getMySQLDataSource(), SQLDialect.MYSQL_8_0);
    }

    private DataSource getMySQLDataSource() {
        MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL(URL);
        mysqlDS.setUser(user);
        mysqlDS.setPassword(password);
        mysqlDS.setDatabaseName(databaseName);
        return mysqlDS;
    }

}
