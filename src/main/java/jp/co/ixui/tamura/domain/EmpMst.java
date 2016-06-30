package jp.co.ixui.tamura.domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tamura
 * EmpMstテーブル
 */
@Getter
@Setter
public class EmpMst {

	/**
	 * 社員番号
	 */
	@NotEmpty(message="社員番号を入力してください")
	private String empNo;

	/**
	 * パスワード
	 */
	@NotEmpty(message="パスワードを入力してください")
	private String pass;

	/**
	 * 社員名
	 */
	@NotNull
	private String name;

	/**
	 * メールアドレス
	 */
	@NotNull
	private String mail;
}
