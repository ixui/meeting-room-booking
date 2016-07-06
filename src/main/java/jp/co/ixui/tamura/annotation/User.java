package jp.co.ixui.tamura.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import jp.co.ixui.tamura.validator.UserValidator;

/**
 * ユーザー情報のアノテーション
 *
 * @author tamura
 *
 */
@Documented
@Constraint(validatedBy = UserValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@SuppressWarnings("javadoc")
public @interface User {

	String message() default "社員番号かパスワードが違います";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ ElementType.TYPE, ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		User[] value();
	}
}
