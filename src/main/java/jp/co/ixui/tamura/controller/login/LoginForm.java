package jp.co.ixui.tamura.controller.login;

import org.hibernate.validator.constraints.NotEmpty;

import jp.co.ixui.tamura.controller.login.validator.annotation.Login;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tamura
 *
 */
@Getter
@Setter
@Login
public class LoginForm {
	@NotEmpty(message="社員番号を入力してください")
	private String empNo;
	@NotEmpty(message="パスワードを入力してください")
	private String pass;
}
