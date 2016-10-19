package jp.co.ixui.tamura.controller.signup;

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
	 * ユーザ登録を行う
	 */
	@RequestMapping(value="/admin/signup", method = RequestMethod.POST)
	public ModelAndView signup(
			@ModelAttribute("formModel") @Validated SignupForm signupForm,
			BindingResult result,
			ModelAndView mav) {

		// 入力チェック
		if (result.hasErrors()) {
			mav.setViewName("show-all-user :: frag_registerForm");
			return mav;
		}

		this.userService.createUser(signupForm);

		mav.setViewName("show-all-user :: frag_registerForm");
		return mav;
	}


}
