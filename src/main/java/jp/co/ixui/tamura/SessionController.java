package jp.co.ixui.tamura;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public static ModelAndView index(
			@ModelAttribute("formModel") EmpMst empMst,
			ModelAndView mav) {
		mav.addObject("formModel", empMst);
		mav.setViewName("/index");
		return mav;
	}

	/**
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value="/register-user", method = RequestMethod.POST)
	public static ModelAndView registerUser(ModelAndView mav) {
		mav.setViewName("/usr/register");
		return mav;
	}
}
