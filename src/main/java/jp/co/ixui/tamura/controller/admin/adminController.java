package jp.co.ixui.tamura.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ixui.tamura.controller.signup.SignupForm;
import jp.co.ixui.tamura.controller.user.UserUpdateForm;
import jp.co.ixui.tamura.domain.EmpMst;
import jp.co.ixui.tamura.service.UserService;

/**
 * @author tamura
 * 管理者権限用のコントローラー
 *
 */
@Controller
@Transactional
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelAndView mav) {
		return "admin";
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public List<EmpMst> getUsers() {
		return this.userService.getAllUser();
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ResponseBody
	public List<EmpMst> postUser(@RequestParam(value="user") SignupForm user) {

		this.userService.createUser(user);

		return this.userService.getAllUser();
	}

	@RequestMapping(value = "/user/{empNo}", method = RequestMethod.PUT)
	@ResponseBody
	public List<EmpMst> putUser(@RequestParam(value="user") UserUpdateForm user) {

		userService.updateUser(user);

		return this.userService.getAllUser();
	}

//	@RequestMapping(value = "/user/{empNo}", method = RequestMethod.GET)
//	@ResponseBody
//	public EmpMst getUser() {
//
//		EmpMst user = this.userService.getAllUser();
//		userList.forEach(u -> u.setAuth(u.getAuth() == "ROLE_USER" ? "ユーザー" : "管理者"));
//
//		return user;
//	}

}
