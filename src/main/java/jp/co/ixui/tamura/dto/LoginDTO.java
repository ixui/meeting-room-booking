package jp.co.ixui.tamura.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tamura
 *
 */
@Getter
@Setter
public class LoginDTO {

	@NotNull(message="社員番号を入力してください")
	private int empNo;
	@NotNull(message="パスワードを入力してください")
	private String pass;

}
