package jp.co.ixui.tamura.controller.reservation;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.ixui.tamura.service.ReservationService;

@Configuration
public class TestContext {

	@SuppressWarnings("static-method")
	@Bean
	public ReservationService reservationService() {
		return Mockito.spy(ReservationService.class);
	}
}