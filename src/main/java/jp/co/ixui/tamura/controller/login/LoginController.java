package jp.co.ixui.tamura.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ixui.tamura.service.UserService;


/**
 * @author tamura
 *　ログイン処理
 */
@Controller
public class LoginController {

	@Autowired
	UserService userService;

	/**
	 * ログイン画面を表示する
	 *
	 * @param empMst
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView index(
			HttpServletRequest request,
			ModelAndView mav) {

		if (this.userService.isValidUserSession(request)) {
			return new ModelAndView("redirect:/calendar");
		}

		mav.setViewName("index");
		return mav;
	}
}
