package jp.co.ixui.tamura.controller.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jp.co.ixui.tamura.controller.signup.SignupForm;
import jp.co.ixui.tamura.controller.user.UserForm;
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

	@GetMapping(value = "/index")
	public String index(ModelAndView mav) {
		return "admin";
	}

	@GetMapping(value = "/user")
	@ResponseBody
	public List<EmpMst> getUsers() {
		return this.userService.getAllUser();
	}

	@PostMapping(value = "/user")
	@ResponseBody
	public List<EmpMst> postUser(@RequestBody SignupForm user) throws JsonParseException, JsonMappingException, IOException {

		this.userService.createUser(user);

		return this.userService.getAllUser();
	}

	@PutMapping(value = "/user/{empNo}")
	@ResponseBody
	public List<EmpMst> putUser(@RequestBody UserForm user) throws JsonParseException, JsonMappingException, IOException {

		userService.updateUser(user);

		return this.userService.getAllUser();
	}

	@DeleteMapping(value = "/user/{empNoList}")
	@ResponseBody
	public List<EmpMst> deleteUser(@RequestBody List<String> empNoList) {

		this.userService.deleteUser(empNoList);

		return this.userService.getAllUser();
	}

}
