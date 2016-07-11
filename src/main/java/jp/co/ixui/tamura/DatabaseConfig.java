package jp.co.ixui.tamura;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
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

	@Bean(destroyMethod="close")
	DataSource datasource() throws URISyntaxException {
		String url;
		String userName;
		String password;

		String databaseUrl = System.getenv("DATABASE_URL");
		if (null != databaseUrl) {
			//heroku
			URI dbUri = new URI(databaseUrl);
			url = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
			userName = dbUri.getUserInfo().split(":")[0];
			password = dbUri.getUserInfo().split(":")[1];
		} else {
			//localhost
			url = this.properties.getUrl();
			userName = this.properties.getUsername();
			password = this.properties.getPassword();
		}

		DataSourceBuilder factory = DataSourceBuilder
				.create(this.properties.getClassLoader())
				.url(url)
				.username(userName)
				.password(password);
		return factory.build();
	}
}
