package jp.co.ixui.tamura.controller.reservation.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ixui.tamura.controller.reservation.ReservationForm;
import jp.co.ixui.tamura.controller.reservation.validator.annotation.Duplication;
import jp.co.ixui.tamura.domain.Reservation;
import jp.co.ixui.tamura.mapper.ReservationMapper;

/**
 * @author tamura
 *
 */
public class DuplicationValidator implements ConstraintValidator<Duplication, ReservationForm> {

	@Autowired
	ReservationMapper reservationMapper;

	@Override
	public void initialize(Duplication duplication) {
		// 初期化処理
	}

	@Override
	public boolean isValid(ReservationForm value, ConstraintValidatorContext cxt) {
		// 入力されてない場合チェックしない
		if ("".equals(value.getRsvDate()) || "".equals(value.getStartTime()) || "".equals(value.getEndTime())) {
			return true;
		}

		LocalDate rsvDate = LocalDate.parse(value.getRsvDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		// 入力された予約日の開始時間、終了時間をすべて取得
		List<Reservation> reservationList = this.reservationMapper.selectTimeByRsvDate(rsvDate);

		for (Reservation reservation : reservationList) {
			int startTime = Integer.parseInt(reservation.getStartTime());
			int endTime = Integer.parseInt(reservation.getEndTime());
			// 開始時間がすでにある予約の使用時間中の場合
			if (startTime <= Integer.parseInt(value.getStartTime()) && endTime > Integer.parseInt(value.getStartTime())) {
				return false;
			}
			// 終了時間がすでにある予約の使用時間中の場合
			if (startTime > Integer.parseInt(value.getStartTime()) && startTime < Integer.parseInt(value.getEndTime())) {
				return false;
			}
		}
		return true;
	}
}
