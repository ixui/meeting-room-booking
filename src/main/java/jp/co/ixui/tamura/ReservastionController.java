package jp.co.ixui.tamura;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import jp.co.ixui.tamura.service.UserService;

/**
 * @author tamura
 *
 */
@Controller
public class ReservastionController {

	@Autowired
	ReservationService reservationService;

	@Autowired
	UserService sessionService;

	/**
	 * カレンダー画面を表示する
	 *
	 * @param mav カレンダー表示するために使う値
	 * @return mav カレンダーを表示するために使う値
	 */
	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public ModelAndView referAll(ModelAndView mav) {

		// カレンダーに表示する予約情報の取得
		MakeCalendarBean makeCalendar = this.reservationService.makeReservationMap();

		// カレンダーを表示するための値をmavにつめる
		mav.setViewName("refer-all");
		mav.addObject("makeCalendar", makeCalendar);
		return mav;
	}

	/**
	 * ログイン画面から遷移して、カレンダー画面を表示する
	 *
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
		if (UserService.checkNotEmpty(result)) {
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
		mav.setViewName("refer-all");
		mav.addObject("makeCalendar", makeCalendar);
		return mav;
	}

	/**
	 * 日付ごとの予約情報表示画面を表示する
	 *
	 * @param rsvDate 予約日
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value = "/reservationList", method = RequestMethod.GET)
	public ModelAndView returnReferDate(
			@RequestParam(value="rsvDate") Date rsvDate,
			ModelAndView mav) {
		List<Reservation> reservationList = this.reservationService.getReservationListByDay(rsvDate);
		mav.addObject("reservationList", reservationList);
		mav.setViewName("refer-date");
		return mav;
	}

	/**
	 * カレンダー画面から遷移して、日付ごとの予約情報表示画面を表示する
	 *
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

		mav.setViewName("refer-date");
		mav.addObject("reservationList", reservationList);
		mav.addObject("calendarDay", calendarDay);
		return mav;
	}

	/**
	 * 削除後の予約情報表示画面を表示する
	 *
	 * @param id
	 * @param rsvDate
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value = "/reservation/delete", method = RequestMethod.POST)
	public ModelAndView deleteReservation(
			@RequestParam(value="deleteId") String id,
			@RequestParam(value="rsvDate") String rsvDate,
			ModelAndView mav) {
		// 予約を削除する
		this.reservationService.deleteReservation(id);
		// 受け取った日付の dd 部分を取り出す
		String calendarDay = rsvDate.substring(rsvDate.length()-2);
		// 予約情報を取得
		List<Reservation> reservationList = this.reservationService.getReservationByDate(rsvDate);

		mav.addObject("reservationList", reservationList);
		mav.addObject("calendarDay", calendarDay);
		mav.setViewName("refer-date");
		return mav;
	}

	/**
	 * 確認･修正画面を表示する
	 *
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
		mav.setViewName("modify");
		return mav;
	}

	/**
	 * 新規予約画面を表示する
	 *
	 * @param reservation
	 * @param mav
	 * @return mav
	 */
	@SuppressWarnings("static-method")
	@RequestMapping(value="/reservation/new", method = RequestMethod.GET)
	public ModelAndView newRegistration(
			@ModelAttribute("formModel") Reservation reservation,
			ModelAndView mav) {
		mav.setViewName("register-reserve");
		return mav;
	}

	/**
	 * 新規予約処理
	 *
	 * @param rsvDate 予約日
	 * @param reservation エンティティ
	 * @param request
	 * @param result バリデーションの結果
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value="/reservation/register", method = RequestMethod.POST)
	public ModelAndView register(
			@RequestParam(value="rsvDate") String rsvDate,
			@ModelAttribute("formModel") @Validated Reservation reservation,
			BindingResult result,
			HttpServletRequest request,
			ModelAndView mav) {
		// 予約を登録する
		this.reservationService.registerReservation(rsvDate, reservation, request);
		// 予約情報を取得
		String reservationDate = new SimpleDateFormat("yyyyMMdd").format(reservation.getRsvDate());
		List<Reservation> reservationList = this.reservationService.getReservationByDate(reservationDate);
		// 日付を表示するためにddを切り取る
		String calendarDay = rsvDate.substring(rsvDate.length()-2);

		mav.addObject("calendarDay", calendarDay);
		mav.addObject("reservationList", reservationList);
		mav.setViewName("refer-date");
		return mav;
	}
}
