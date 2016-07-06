package jp.co.ixui.tamura.dto;

import org.hibernate.validator.constraints.NotEmpty;

import jp.co.ixui.tamura.annotation.User;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tamura
 *
 */
@Getter
@Setter
@User
public class LoginDTO {
	@NotEmpty(message="社員番号を入力してください")
	private String empNo;
	@NotEmpty(message="パスワードを入力してください")
	private String pass;
}
