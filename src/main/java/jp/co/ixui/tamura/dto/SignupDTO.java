package jp.co.ixui.tamura.dto;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author kawasaki
 *
 */
@Getter
@Setter
public class SignupDTO {
	@NotEmpty(message="社員番号を入力してください")
	private String empNo;
	@NotEmpty(message="名前を入力してください")
	private String name;
	@NotEmpty(message="メールアドレスを入力してください")
	private String email;
	@NotEmpty(message="パスワードを入力してください")
	private String pass;
	@NotEmpty(message="確認用パスワードを入力してください")
	private String confirmPass;
}
