package jp.co.ixui.tamura;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import jp.co.ixui.tamura.domain.EmpMst;

public class LoginUserDetails extends User {

	private final EmpMst empMst;

	public LoginUserDetails(EmpMst empMst) {
		super(
				empMst.getEmpNo(),
				empMst.getPass(),
				AuthorityUtils.createAuthorityList(empMst.getAuth())
				);
		this.empMst = empMst;
	}

	public EmpMst getUser() {
		return this.empMst;
	}

	public Object getSalt() {
		return this.empMst.getEmpNo();
	}
}
