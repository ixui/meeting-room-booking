package jp.co.ixui.tamura.controller.signup.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ixui.tamura.controller.signup.SignupForm;
import jp.co.ixui.tamura.controller.signup.validator.annotation.UserExists;
import jp.co.ixui.tamura.domain.EmpMst;
import jp.co.ixui.tamura.mapper.EmpMstMapper;

/**
 * ユーザー登録時の重複確認用のバリデータ
 *
 * @author kawasaki
 *
 */
public class UserExistsValidator implements ConstraintValidator<UserExists, SignupForm> {

	@Autowired
	EmpMstMapper empMstMapper;

	@Override
	public void initialize(UserExists userExists) {
	}

	/**
	 * チェック
	 */
	@Override
	public boolean isValid(SignupForm value, ConstraintValidatorContext cxt) {

    	String empNo = value.getEmpNo();

        if (empNo == null) {
        	// FormDTOにて@NotNullを使用するべき項目
            return true;
        }

		// 社員情報を取得し存在すればエラー
		EmpMst eMst = this.empMstMapper.selectUser(empNo);
		if (null != eMst) {
			return false;
		}

		return true;
	}

}
