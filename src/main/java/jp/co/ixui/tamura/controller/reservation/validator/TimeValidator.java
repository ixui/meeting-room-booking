package jp.co.ixui.tamura.controller.reservation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ixui.tamura.controller.reservation.validator.annotation.Time;

/**
 * @author tamura
 *
 */
public class TimeValidator implements ConstraintValidator<Time, String> {

	@Override
	public void initialize(Time time) {
		// 初期化処理
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		// 入力されてない場合チェックしない
		if ("".equals(value)) return true;

		// 入力された文字列を hour と minute に分割
		int hour = Integer.parseInt(value.substring(0, 2));
		int minute = Integer.parseInt(value.substring(2));

		// hour, minute それぞれ時間としてありえない数字の場合は false
		if (hour > 24 && minute > 59) return false;

		return true;
	}

}
