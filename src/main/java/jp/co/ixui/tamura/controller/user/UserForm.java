package jp.co.ixui.tamura.controller.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import jp.co.ixui.tamura.controller.signup.validator.annotation.PasswordEquals;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordEquals(fieldPass="pass", fieldConfirmPass="confirmPass")
public class UserForm {

	@NotEmpty(message="名前を入力してください")
	private String name;

	@NotEmpty(message="メールアドレスを入力してください")
	@Email(message="メールアドレスを確認してください")
	private String email;

	@NotEmpty(message="パスワードを入力してください")
	@Size(min = 8, message="パスワードは8文字以上で入力してください")
	private String pass;

	@NotEmpty(message="確認用パスワードを入力してください")
	private String confirmPass;

	@NotNull
	private String auth;
}
