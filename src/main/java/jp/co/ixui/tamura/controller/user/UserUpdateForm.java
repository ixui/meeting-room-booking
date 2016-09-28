package jp.co.ixui.tamura.controller.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateForm {

	private String empNo;

	@NotEmpty(message="名前を入力してください")
	private String name;

	@NotNull
	private String auth;
}
