package jp.co.ixui.tamura;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ixui.tamura.controller.login.LoginForm;
import jp.co.ixui.tamura.service.UserService;

/**
 * アプリケーションコントローラ
 * ルートや404など全般での利用やタイムアウトなどのような特別な処理などに利用する
 * @author t-kawasaki
 *
 */
@Controller
public class ApplicationController {

	/**
	 * URL ルートでの処理
	 *
	 * @param request
	 * @param mav
	 * @return mav
	 *
	 */
	@RequestMapping(value="/")
	public static ModelAndView root(HttpServletRequest request, ModelAndView mav) {

		if (UserService.isValidUserSession(request)) {
			return new ModelAndView("redirect:/calendar");
		}

		return new ModelAndView("redirect:/login");

	}

	/**
	 * セッションタイムアウト時の表示
	 * @param loginDTO
	 * @param result
	 * @param request
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/timeout")
	public static ModelAndView sessionTimeout(
			@ModelAttribute("formModel") LoginForm loginDTO,
			BindingResult result,
			HttpServletRequest request,
			ModelAndView mav) {

		if (UserService.isValidUserSession(request)) {
			// 手でURLを打ってきた場合の処理
			return new ModelAndView("redirect:/calendar");
		} else {
			// 基本的にはすべてこちらに流れる
			result.reject("jp.co.ixui.tamura.timeout", "お手数をおかけしますが、改めてログインをお願いします");
			mav.setViewName("index");
			return mav;
		}

	}

}
