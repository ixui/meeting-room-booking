package jp.co.ixui.tamura;

import org.springframework.security.authentication.encoding.PasswordEncoder;

import jp.co.ixui.tamura.service.UserService;

public class LoginPasswordEncoder implements PasswordEncoder{

	@Override
	public String encodePassword(String rawPass, Object salt) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {

		String empNo = (String) salt;
		String encodedPassword = UserService.getSafetyPassword(rawPass, empNo);

		if (encPass.equals(encodedPassword)) return true;
		return false;
	}

}
