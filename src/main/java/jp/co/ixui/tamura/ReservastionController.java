package jp.co.ixui.tamura;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ixui.tamura.domain.Reservation;
import jp.co.ixui.tamura.dto.LoginDTO;
import jp.co.ixui.tamura.service.ReservationService;
import jp.co.ixui.tamura.service.SessionService;

/**
 * @author tamura
 *
 */
@Controller
public class ReservastionController {

	@Autowired
	ReservationService reservationService;

	@Autowired
	SessionService sessionService;

	/**
	 * カレンダー表示画面に遷移する
	 * @param mav カレンダー表示するために使う値
	 * @return mav カレンダーを表示するために使う値
	 */
	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public ModelAndView referAll(ModelAndView mav) {

		// カレンダーに表示する予約情報の取得
		MakeCalendarBean makeCalendar = this.reservationService.makeReservationMap();

		// カレンダーを表示するための値をmavにつめる
		mav.setViewName("/refer-all");
		mav.addObject("makeCalendar", makeCalendar);
		return mav;
	}

	/**
	 * カレンダー表示画面に遷移する
	 * @param loginDTO
	 * @param result
	 * @param request
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value = "/calendar", method = RequestMethod.POST)
	public ModelAndView referAll(
			@ModelAttribute("formModel") @Validated LoginDTO loginDTO,
			BindingResult result,
			HttpServletRequest request,
			ModelAndView mav) {
		// 入力チェック
		if (SessionService.checkNotEmpty(result)) {
			mav.setViewName("index");
			return mav;
		}
		if (this.sessionService.checkEmpNo(loginDTO)) {
			mav.setViewName("index");
			mav.addObject("errMsg1", "社員番号かパスワードが違います");
			return mav;
		}

		// セッションにユーザー情報を格納
		this.sessionService.setUserSession(request, loginDTO);

		// カレンダーに表示する予約情報の取得
		MakeCalendarBean makeCalendar = this.reservationService.makeReservationMap();

		// カレンダーを表示するための値をmavにつめる
		mav.setViewName("/refer-all");
		mav.addObject("makeCalendar", makeCalendar);
		return mav;
	}

	/**
	 * 日付ごとの予約情報表示画面に遷移する
	 * @param rsvDate 予約日
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value = "/reservationList", method = RequestMethod.GET)
	public ModelAndView returnReferDate(
			@RequestParam(value="rsvDate") Date rsvDate,
			ModelAndView mav) {
		List<Reservation> reservationList = this.reservationService.getReservaionListByDay(rsvDate);
		mav.addObject("reservationList", reservationList);
		mav.setViewName("/refer-date");
		return mav;
	}

	/**
	 * 日付ごとの予約情報表示画面に遷移する
	 * @param mav
	 * @param calendarDay カレンダー表示画面で選択された日付
	 * @param flg
	 * @param id 予約ID
	 * @return mav
	 */
	@RequestMapping(value = "/reservationList", method = RequestMethod.POST)
	public ModelAndView referDate(
			@RequestParam(value="calendarDay") String calendarDay,
			ModelAndView mav) {
		// 選択日の予約情報を取得する
		Date currentDate = new Date();
		String currentMonth = new SimpleDateFormat("yyyyMM").format(currentDate);
		List<Reservation> reservationList = this.reservationService.getReservationByDate(currentMonth + calendarDay);

		mav.setViewName("/refer-date");
		mav.addObject("reservationList", reservationList);
		mav.addObject("calendarDay", calendarDay);
		return mav;
	}

	/**
	 * @param id
	 * @param rsvDate
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value = "/reservation/delete", method = RequestMethod.POST)
	public ModelAndView deleteReservation(
			@RequestParam(value="deleteId") String id,
			@RequestParam(value="rsvDate") Date rsvDate,
			ModelAndView mav) {
		this.reservationService.deleteReservation(id);
		List<Reservation> reservationList = this.reservationService.getReservaionListByDay(rsvDate);
		mav.addObject("reservationList", reservationList);
		mav.setViewName("/refer-date");
		return mav;
	}

	/**
	 * 確認･修正画面に遷移する
	 * @param id 予約ID
	 * @param empNo 社員番号
	 * @param request
	 * @param mav
	 * @return mav
	 */
	@SuppressWarnings("boxing")
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public ModelAndView modify(
			@RequestParam(value="id") int id,
			@RequestParam(value="empNo") String empNo,
			HttpServletRequest request,
			ModelAndView mav) {
		// 予約者本人かどうか確認する
		boolean principal = ReservationService.booleanPrincipal(empNo, request);
		// 選択日の予約情報をidから取得する
		Reservation reservation = this.reservationService.getReservsationById(id);

		mav.addObject("reservation", reservation);
		mav.addObject("id", id);
		mav.addObject("principal", principal);
		mav.setViewName("/modify");
		return mav;
	}

	/**
	 * 予約登録画面に遷移する
	 * @param reservation
	 * @param mav
	 * @return mav
	 */
	@SuppressWarnings("static-method")
	@RequestMapping(value="/reservation/new", method=RequestMethod.GET)
	public ModelAndView register(
			@ModelAttribute("formModel") Reservation reservation,
			ModelAndView mav) {
		mav.setViewName("/register-reserve");
		return mav;
	}
}
