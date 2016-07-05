package jp.co.ixui.tamura;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ixui.tamura.domain.EmpMst;


/**
 * @author tamura
 *　ログイン処理
 */
@Controller
public class SessionController {

	/**
	 * @param empMst
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public static ModelAndView index(
			ModelAndView mav) {
		mav.addObject("formModel",new EmpMst());
		mav.setViewName("/index");
		return mav;
	}

	/**
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value="/usr/register", method = RequestMethod.POST)
	public static ModelAndView registerUser(ModelAndView mav) {
		mav.setViewName("/register-user");
		return mav;
	}
}
