package jp.co.ixui.tamura;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ixui.tamura.domain.EmpMst;
import jp.co.ixui.tamura.dto.SignupDTO;
import jp.co.ixui.tamura.service.UserService;


/**
 * @author tamura
 *　ログイン処理
 */
@Controller
public class UserController {

	@Autowired
	UserService userService;

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
	 * ユーザの新規登録画面を表示する
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signup(ModelAndView mav) {

		// バリデーション用に空の画面用DTOを設定する
		mav.addObject("formModel",new SignupDTO());
		mav.setViewName("register-user");
		return mav;
	}

	/**
	 * ユーザ登録を行う
	 */
	@RequestMapping(value="/signup", method = RequestMethod.POST)
	public ModelAndView signup(
			@ModelAttribute("formModel") @Validated SignupDTO signupDTO,
			BindingResult result,
			HttpServletRequest request,
			ModelAndView mav) {

		// 入力チェック
		if (UserService.checkNotEmpty(result)) {
			mav.setViewName("register-user");
			return mav;
		}

		userService.createUser(signupDTO);

		mav.setViewName("redirect:login");
		return mav;
	}
}
