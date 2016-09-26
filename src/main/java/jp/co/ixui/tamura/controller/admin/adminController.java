package jp.co.ixui.tamura.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ixui.tamura.domain.EmpMst;
import jp.co.ixui.tamura.service.UserService;

/**
 * @author tamura
 * 管理者権限用のコントローラー
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class adminController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/showAllUser", method = RequestMethod.GET)
	public ModelAndView show(
			ModelAndView mav) {

		// ユーザ情報をすべて取得する
		List<EmpMst> userList = this.userService.getAllUser();

		// ユーザ情報が取得できなかった場合、一覧の代わりにエラーメッセージを表示する
		if (userList == null)
			return new ModelAndView("show-all-user", "errorMessage", "ユーザー情報の取得に失敗しました");

		mav.addObject("userList", userList);
		mav.setViewName("show-all-user");
		return mav;
	}
}
