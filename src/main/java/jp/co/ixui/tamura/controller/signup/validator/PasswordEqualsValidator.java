package jp.co.ixui.tamura.controller.signup.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ixui.tamura.controller.signup.SignupForm;
import jp.co.ixui.tamura.controller.signup.validator.annotation.PasswordEquals;
import jp.co.ixui.tamura.mapper.EmpMstMapper;

/**
 * ユーザ登録時のパスワードが一致しているか確認するバリデータ
 *
 * @author kawasaki
 *
 */
public class PasswordEqualsValidator implements ConstraintValidator<PasswordEquals, SignupForm> {

	@Autowired
	EmpMstMapper empMstMapper;

	@Override
	public void initialize(PasswordEquals userPasswordConfirm) {
	}

	/**
	 * チェック
	 */
	@Override
	public boolean isValid(SignupForm value, ConstraintValidatorContext cxt) {

    	String password = value.getPass();
        String confirmPassword = value.getConfirmPass();

        if (password == null || confirmPassword == null) {
        	// FormDTOにて@NotNullを使用するべき項目
            return true;
        }

        if (!password.equals(confirmPassword)) {
			return false;
        }

        return true;
	}

}
