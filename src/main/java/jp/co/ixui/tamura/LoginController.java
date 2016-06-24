package jp.co.ixui.tamura;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author tamura
 *　ログイン処理
 */
@Controller
@SessionAttributes("EmpMst")
public class LoginController {
	
	/**
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public static ModelAndView index(ModelAndView mav) {
		mav.setViewName("index");
		return mav;
	}
	
	/**
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value="/register-user.html", method = RequestMethod.POST)
	public static ModelAndView registerUser(ModelAndView mav) {
		mav.setViewName("register-user");
		return mav;
	}
}
