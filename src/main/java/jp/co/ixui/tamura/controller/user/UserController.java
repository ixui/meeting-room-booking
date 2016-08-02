package jp.co.ixui.tamura.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ixui.tamura.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	/**
	 * ユーザー情報更新画面を表示する
	 *
	 * @param request
	 * @param mav
	 * @return mav
	 */
	@SuppressWarnings("static-method")
	@RequestMapping(value="/update", method = RequestMethod.GET)
	public ModelAndView update(ModelAndView mav) {
		// バリデーション用に空の画面用DTOを設定する
		mav.addObject("formModel",new UserForm());
		mav.setViewName("update-user");
		return mav;
	}

	/**
	 * ユーザー情報を更新する
	 *
	 * @param request
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public ModelAndView update(
			@ModelAttribute("formModel") @Validated UserForm userForm,
			BindingResult result,
			HttpServletRequest request,
			ModelAndView mav) {
		// 入力チェック
		if (result.hasErrors()) {
			mav.setViewName("update-user");
			return mav;
		}
		// ユーザー情報更新処理
		this.userService.updateUser(request, userForm);

		// ユーザー名に変更がある場合sessionに変更後のユーザー名を保存
		HttpSession session = request.getSession();
		String sessionUserName = (String)session.getAttribute("userName");
		if (!sessionUserName.equals(userForm.getName())) {
			this.userService.setUserNameSession(request, userForm);
		}

		mav.setViewName("redirect:/calendar");
		return mav;
	}

}
