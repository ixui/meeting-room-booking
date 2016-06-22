package jp.co.ixui.tamura.domain;

import javax.validation.constraints.NotNull;

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
	@NotNull
	private int empNo;
	
	/**
	 * パスワード
	 */
	@NotNull
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
