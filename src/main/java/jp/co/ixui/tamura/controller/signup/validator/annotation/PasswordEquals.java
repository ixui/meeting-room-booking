package jp.co.ixui.tamura.controller.signup.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import jp.co.ixui.tamura.controller.signup.validator.PasswordEqualsValidator;

/**
 * パスワード一致確認用のアノテーション
 *
 * @author kawasaki
 *
 */
@Documented
@Constraint(validatedBy = PasswordEqualsValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@SuppressWarnings("javadoc")
public @interface PasswordEquals {

	String message() default "パスワードが一致していません";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ ElementType.TYPE, ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		PasswordEquals[] value();
	}
}
