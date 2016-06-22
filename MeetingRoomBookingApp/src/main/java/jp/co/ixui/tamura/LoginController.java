package jp.co.ixui.tamura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ixui.tamura.domain.EmpMst;
import jp.co.ixui.tamura.mapper.EmpMstMapper;

/**
 * @author tamura
 *　ログイン処理
 */
@Controller
@SessionAttributes("EmpMst")
public class LoginController {
	
	/**
	 *
	 */
	@Autowired
	EmpMstMapper empMstMapper;
	
	/**
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public static ModelAndView index(ModelAndView mav) {
		mav.setViewName("index");
		return mav;
	}
	
	/**
	 * @param id
	 * @param pass
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value = "/refer-all.html", method = RequestMethod.POST)
	public ModelAndView referAll(
			@RequestParam(value = "empNo") String id,
			@RequestParam(value = "pass") String pass,
			ModelAndView mav) {
		
		if ("".equals(id) || "".equals(pass)) {
			mav.setViewName("index");
			if ("".equals(id))
				mav.addObject("errMsg1", "社員番号を入力してください");
			if ("".equals(pass))
				mav.addObject("errMsg2", "パスワードを入力してください");
			return mav;
		}
		
		int empNo = Integer.parseInt(id);
		EmpMst empMst = this.empMstMapper.selectUser(empNo);
		if (null == empMst || !empMst.getPass().equals(pass)) {
			mav.setViewName("index");
			mav.addObject("errMsg1", "社員番号かパスワードが違います");
			return mav;
		}
		
		mav.setViewName("refer-all");
		return mav;
		
	}
}
