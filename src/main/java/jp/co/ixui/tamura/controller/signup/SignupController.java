package jp.co.ixui.tamura.controller.signup;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ixui.tamura.service.UserService;

/**
 * ユーザ新規登録に関するControllerクラス
 * @author t-kawasaki
 *
 */
@Controller
public class SignupController {

	@Autowired
	UserService userService;

	/**
	 * ユーザの新規登録画面を表示する
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signup(ModelAndView mav) {

		// バリデーション用に空の画面用DTOを設定する
		mav.addObject("formModel",new SignupForm());
		mav.setViewName("register-user");
		return mav;
	}

	/**
	 * ユーザ登録を行う
	 */
	@RequestMapping(value="/signup", method = RequestMethod.POST)
	public ModelAndView signup(
			@ModelAttribute("formModel") @Validated SignupForm signupForm,
			BindingResult result,
			HttpServletRequest request,
			ModelAndView mav) {

		// 入力チェック
		if (result.hasErrors()) {
			mav.setViewName("register-user");
			return mav;
		}

		userService.createUser(signupForm);

		mav.setViewName("redirect:login");
		return mav;
	}


}
