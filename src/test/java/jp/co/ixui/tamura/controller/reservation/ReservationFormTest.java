package jp.co.ixui.tamura.controller.reservation;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ixui.tamura.MeetingRoomBookingAppApplication;
import jp.co.ixui.tamura.controller.reservation.validator.annotation.Future;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(MeetingRoomBookingAppApplication.class)
@Transactional
public class ReservationFormTest {

	private Validator validator;

	// バリデータを初期化
	@Before
	public void before() {
		this.validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	public void Futureアノテーションのテスト() throws Exception {

		ReservationForm reservationForm = new ReservationForm();
		reservationForm.setRsvDate("2016-09-03");
		reservationForm.setTitle("Test");
		reservationForm.setStartTime("1200");
		reservationForm.setEndTime("1300");
		reservationForm.setDetail("test");

		// バリデート
		Set<ConstraintViolation<ReservationForm>> violations = this.validator.validate(reservationForm);

		// 検証
		assertEquals(violations.size(), 1);
		for (ConstraintViolation<ReservationForm> v : violations) {
			assertTrue(v.getConstraintDescriptor().getAnnotation() instanceof Future);
		}
	}

//	@Test
//	public void EndTimeアノテーションのテスト() throws Exception {
//
//		ReservationForm reservationForm = new ReservationForm();
//		reservationForm.setRsvDate("2099-09-03");
//		reservationForm.setTitle("Test");
//		reservationForm.setStartTime("1300");
//		reservationForm.setEndTime("1300");
//		reservationForm.setDetail("test");
//
//		// バリデート
//		Set<ConstraintViolation<ReservationForm>> violations = this.validator.validate(reservationForm);
//
//		// 検証
//		assertEquals(violations.size(), 1);
//		for (ConstraintViolation<ReservationForm> v : violations) {
//			assertTrue(v.getConstraintDescriptor().getAnnotation() instanceof EndTime);
//		}
//	}
//
//	@Test
//	public void MeetingTimeアノテーションのテスト() throws Exception {
//
//		ReservationForm reservationForm = new ReservationForm();
//		reservationForm.setRsvDate("2099-09-03");
//		reservationForm.setTitle("Test");
//		reservationForm.setStartTime("1200");
//		reservationForm.setEndTime("55555");
//		reservationForm.setDetail("test");
//
//		// バリデート
//		Set<ConstraintViolation<ReservationForm>> violations = this.validator.validate(reservationForm);
//
//		// 検証
//		assertEquals(violations.size(), 1);
//		for (ConstraintViolation<ReservationForm> v : violations) {
//			assertTrue(v.getConstraintDescriptor().getAnnotation() instanceof MeetingTime);
//		}
//	}
}
