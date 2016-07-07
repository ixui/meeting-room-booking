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

import jp.co.ixui.tamura.dto.LoginDTO;
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
	 * ログイン画面を表示する
	 *
	 * @param empMst
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public static ModelAndView index(
			ModelAndView mav) {
		mav.addObject("formModel",new LoginDTO());
		mav.setViewName("index");
		return mav;
	}

	/**
	 * 入力チェックをする、エラーがない場合はカレンダー画面へリダイレクト
	 *
	 * @param loginDTO
	 * @param result
	 * @param request
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value="/login/error")
	public static ModelAndView login(
			@ModelAttribute("formModel") @Validated LoginDTO loginDTO,
			BindingResult result,
			HttpServletRequest request,
			ModelAndView mav) {
		// バリデーションの結果をチェック
		if (!result.hasErrors()) {
			// セッションに社員番号を格納
			UserService.setEmpNoSession(request, loginDTO);
			// カレンダー表示画面にリダイレクト
			return new ModelAndView("redirect:/calendar");
		}
		mav.setViewName("index");
		return mav;
	}

}
