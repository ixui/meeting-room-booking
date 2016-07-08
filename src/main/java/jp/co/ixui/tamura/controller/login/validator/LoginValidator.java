package jp.co.ixui.tamura.controller.login.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ixui.tamura.controller.login.LoginForm;
import jp.co.ixui.tamura.controller.login.validator.annotation.Login;
import jp.co.ixui.tamura.domain.EmpMst;
import jp.co.ixui.tamura.mapper.EmpMstMapper;
import jp.co.ixui.tamura.service.UserService;

/**
 * ユーザー情報のバリデータ
 *
 * @author tamura
 *
 */
public class LoginValidator implements ConstraintValidator<Login, LoginForm> {

	@Autowired
	EmpMstMapper empMstMapper;

	@Override
	public void initialize(Login login) {
		// 空でいい
	}

	/**
	 * @param input
	 * @param cxt
	 * @return boolean
	 */
	@Override
	public boolean isValid(LoginForm value, ConstraintValidatorContext cxt) {
		// 社員番号またはパスワードが入力されていない場合はそちらでエラーがでるのでバリデーションしない
		if ("".equals(value.getEmpNo()) || "".equals(value.getPass())) {
			return true;
		}
		// 入力された社員番号から、社員情報を取得
		EmpMst eMst = this.empMstMapper.selectUser(value.getEmpNo());
		// 入力されたパスワードを暗号化
		String password = UserService.getSafetyPassword(value.getPass(), value.getEmpNo());
		// 社員情報が取得できていないもしくはパスワードが一致しない場合エラー
		if (null == eMst || !eMst.getPass().equals(password)) {
			return false;
		}
		return true;
	}

}
