package jp.co.ixui.tamura.controller.reservation.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import jp.co.ixui.tamura.controller.reservation.validator.DuplicationValidator;

/**
 * 予約時間がかぶっていないか確認する
 *
 * @author tamura
 *
 */
@Documented
@Constraint(validatedBy = DuplicationValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@SuppressWarnings("javadoc")
public @interface Duplication {

	String message() default "他の予約と時間がかぶっています";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ ElementType.TYPE, ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		Duplication[] value();
	}
}