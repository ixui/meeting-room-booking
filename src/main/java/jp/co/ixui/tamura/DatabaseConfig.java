package jp.co.ixui.tamura;

import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tamura
 *
 */
@Configuration
public class DatabaseConfig {
	@Autowired
	DataSourceProperties properties;
	DataSource dataSource;

//	@Bean(destroyMethod="close")
//	DataSource realDatasource() throws URISyntaxException {
//		String url;
//		String userName;
//		String password;
//
//		String databaseUrl = System.getenv("DATABASE_URL");
//	}
}
