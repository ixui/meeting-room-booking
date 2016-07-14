package jp.co.ixui.tamura.controller.reservation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ixui.tamura.controller.reservation.ReservationForm;
import jp.co.ixui.tamura.controller.reservation.validator.annotation.EndTime;

/**
 * @author tamura
 *
 */
public class EndTimeValidator implements ConstraintValidator<EndTime, ReservationForm> {

	@Override
	public void initialize(EndTime endTime) {
		// 初期化処理
	}

	@Override
	public boolean isValid(ReservationForm value, ConstraintValidatorContext cxt) {
		// 入力されてない場合チェックしない
		if ("".equals(value.getStartTime()) || "".equals(value.getEndTime())) {
			return true;
		}

		int startTime = Integer.parseInt(value.getStartTime());
		int endTime = Integer.parseInt(value.getEndTime());

		if (startTime >= endTime) {
			return false;
		}

		return true;
	}
}
