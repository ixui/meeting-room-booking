package jp.co.ixui.tamura.controller.reservation.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.ixui.tamura.controller.reservation.validator.annotation.Future;

/**
 * @author tamura
 *
 */
public class FutureValidator implements ConstraintValidator<Future, String> {

	@Override
	public void initialize(Future future) {
		// 初期化処理
	}

	@Override
	public boolean isValid(String input, ConstraintValidatorContext cxt) {
		// 入力されてない場合チェックしない
		if ("".equals(input)) {
			return true;
		}

		// 現在時刻より前の場合はfalse
		LocalDate rsvDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate localDateNow = LocalDate.now();
		if (rsvDate.compareTo(localDateNow) < 0) {
			return false;
		}

		return true;
	}
}
