package jp.co.ixui.tamura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.ixui.tamura.domain.EmpMst;
import jp.co.ixui.tamura.mapper.EmpMstMapper;

@Service
public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	EmpMstMapper empMstMapper;

	@Override
	public UserDetails loadUserByUsername(String empNo) throws UsernameNotFoundException {

		 if (empNo == null || "".equals(empNo)) {
			 throw new UsernameNotFoundException("Username is empty");
		 }

		 EmpMst empMst = empMstMapper.selectUser(empNo);
		 if (empMst == null) {
			 throw new UsernameNotFoundException("User not found for name: " + empNo);
		 }

		 return new LoginUserDetails(empMst);
	}
}
