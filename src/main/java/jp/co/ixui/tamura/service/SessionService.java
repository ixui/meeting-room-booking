package jp.co.ixui.tamura.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import jp.co.ixui.tamura.domain.EmpMst;
import jp.co.ixui.tamura.dto.LoginDTO;
import jp.co.ixui.tamura.mapper.EmpMstMapper;

/**
 * @author tamura
 *
 */
@Service
public class SessionService {

	@Autowired
	EmpMstMapper empMstMapper;

	/**
	 * @param result
	 * @return number
	 */
	public static boolean checkNotEmpty(BindingResult result) {
		if (result.hasFieldErrors("empNo") || result.hasFieldErrors("pass")) {
			return true;
		}
		return false;
	}

	/**
	 * @param loginDTO
	 * @return checkFlg
	 */
	public boolean checkEmpNo(LoginDTO loginDTO) {
		if (null == getUserEmpNo(loginDTO) || !getUserEmpNo(loginDTO).getPass().equals(loginDTO.getPass())) {
			return true;
		}
		return false;
	}

	/**
	 * @param loginDTO
	 * @return eMst
	 */
	public EmpMst getUserEmpNo(LoginDTO loginDTO) {
		EmpMst eMst = this.empMstMapper.selectUser(loginDTO.getEmpNo());
		return eMst;
	}

	/**
	 * @param request
	 * @param loginDTO
	 */
	public void setUserSession(HttpServletRequest request,LoginDTO loginDTO) {
		HttpSession session = request.getSession();
		session.setAttribute("empMst",getUserEmpNo(loginDTO));
	}
}
