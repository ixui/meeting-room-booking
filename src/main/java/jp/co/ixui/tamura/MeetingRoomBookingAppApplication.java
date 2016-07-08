package jp.co.ixui.tamura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

/**
 * @author tamura
 *
 */
@SpringBootApplication
public class MeetingRoomBookingAppApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(MeetingRoomBookingAppApplication.class, args);
	}

	/**
	 * @return java8日時
	 */
	@SuppressWarnings("static-method")
	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}
}