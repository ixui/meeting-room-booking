package jp.co.ixui.tamura.controller.reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ixui.tamura.CalendarDate;
import jp.co.ixui.tamura.domain.Reservation;
import jp.co.ixui.tamura.service.ReservationService;
import jp.co.ixui.tamura.service.UserService;

/**
 * @author tamura
 *
 */
@Controller
public class ReservationController {

	@Autowired
	ReservationService reservationService;

	@Autowired
	UserService userService;

	/**
	 * 現在の月のカレンダーを表示する
	 *
	 * @param mav カレンダー表示するために使う値
	 * @return mav カレンダーを表示するために使う値
	 */
	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public ModelAndView referAll(ModelAndView mav) {

		// カレンダーに表示する日付インスタンスを取得
		List<CalendarDate> calendarDateList = this.reservationService.makeCurrentMonthCalendar();

		mav.addObject("calendarDateList", calendarDateList);
		mav.setViewName("refer-all");
		return mav;
	}

	/**
	 * URLで指定した月のカレンダーを表示
	 *
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value = "/calendar/{designatedMonth}", method = RequestMethod.GET)
	public ModelAndView referDesignatedMonthCalendar(
			@PathVariable String designatedMonth,
			ModelAndView mav) {
		// カレンダーに表示する日付インスタンスを取得
		List<CalendarDate> calendarDateList = this.reservationService.makeDesignatedMonthCalendar(designatedMonth);

		mav.addObject("calendarDateList", calendarDateList);
		mav.setViewName("refer-all");
		return mav;
	}

	/**
	 * カレンダー画面から遷移して、日付ごとの予約情報表示画面を表示する
	 *
	 * @param selectCalendarDate
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value = "/reservationList", method = RequestMethod.POST)
	public ModelAndView referDate(
			@RequestParam(value="calendarDate") String selectCalendarDate,
			ModelAndView mav) {
		// 選択日の予約情報を取得する
		List<Reservation> reservationList = this.reservationService.getReservationByDate(selectCalendarDate);

		mav.setViewName("refer-date");
		mav.addObject("reservationList", reservationList);
		mav.addObject("selectCalendarDate", selectCalendarDate);
		return mav;
	}

	/**
	 * 予約を削除して予約情報表示画面へ遷移する
	 *
	 * @param id
	 * @param rsvDate
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value = "/reservation/delete", method = RequestMethod.POST)
	public ModelAndView deleteReservation(
			@RequestParam(value="deleteId") String id,
			@DateTimeFormat(pattern = "yyyy-MM-dd")@RequestParam(value="reservationDate") LocalDate rsvDate,
			ModelAndView mav) {
		// 予約を削除する
		this.reservationService.deleteReservation(id);
		// 予約情報を取得
		String reservationDate = rsvDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		List<Reservation> reservationList = this.reservationService.getReservationByDate(reservationDate);

		if (reservationList.size() == 0) {
			return new ModelAndView("redirect:/reservationList/empty");
		}

		mav.addObject("reservationList", reservationList);
		mav.addObject("selectCalendarDate", reservationDate);
		mav.setViewName("refer-date");
		return mav;
	}

	/**
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value = "/reservationList/empty", method = RequestMethod.GET)
	public static ModelAndView emptyData(ModelAndView mav) {
		mav.setViewName("empty-data");
		return mav;
	}

	/**
	 * 確認･修正画面を表示する
	 *
	 * @param reservationForm
	 * @param result
	 * @param id 予約ID
	 * @param empNo 社員番号
	 * @param request
	 * @param mav
	 * @return mav
	 */
	@SuppressWarnings("boxing")
	@RequestMapping(value = "/reservation/confirm", method = RequestMethod.POST)
	public ModelAndView modify(
			@ModelAttribute("formModel") ReservationForm reservationForm,
			BindingResult result,
			@RequestParam(value="rsvId") int id,
			@RequestParam(value="empNo") String empNo,
			HttpServletRequest request,
			ModelAndView mav) {
		// 予約者本人かどうか確認する
		boolean principal = ReservationService.booleanPrincipal(empNo, request);
		// 選択日の予約情報をidから取得する
		Reservation reservation = this.reservationService.getReservsationById(id);

		// フォームに初期値を格納
		reservationForm.setRsvDate(String.valueOf(reservation.getRsvDate()));
		reservationForm.setTitle(reservation.getTitle());
		reservationForm.setStartTime(reservation.getStartTime());
		reservationForm.setEndTime(reservation.getEndTime());
		reservationForm.setDetail(reservation.getDetail());
		reservationForm.setMemo(reservation.getMemo());
		reservationForm.setId(id);

		mav.addObject("reservation", reservation);
		mav.addObject("principal", principal);
		mav.setViewName("modify");
		return mav;
	}

	/**
	 * 予約更新処理
	 *
	 * @param id
	 * @param empNo
	 * @param principal
	 * @param reservationForm
	 * @param result
	 * @param request
	 * @param mav
	 * @return mav
	 */
	@SuppressWarnings("boxing")
	@RequestMapping(value = "/reservation/modify", method = RequestMethod.POST)
	public ModelAndView update(
			@RequestParam(value="id") int id,
			@RequestParam(value="empNo") String empNo,
			@RequestParam(value="principal") String principal,
			@ModelAttribute("formModel")@Validated ReservationForm reservationForm,
			BindingResult result,
			HttpServletRequest request,
			ModelAndView mav) {
		// エラーがある場合予約修正画面へ戻す
		if (result.hasErrors()) {
			mav.addObject("empNo", empNo);
			mav.addObject("id", id);
			mav.addObject("principal", principal);
			mav.setViewName("modify");
			return mav;
		}

		// 受け取った日付をLocalDateに変換
		LocalDate rsvDate = LocalDate.parse(reservationForm.getRsvDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		// 入力フォームから受け取った値をreservationに格納
		Reservation reservation = this.reservationService.storeReservation(rsvDate, reservationForm, request);
		reservation.setId(id);
		// 予約情報を更新
		this.reservationService.updateReservation(reservation, request);

		// 予約情報を取得
		String reservationDate = rsvDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		List<Reservation> reservationList = this.reservationService.getReservationByDate(reservationDate);

		mav.addObject("selectCalendarDate", reservationDate);
		mav.addObject("reservationList", reservationList);
		mav.setViewName("refer-date");
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
	 * @param reservationForm
	 * @param request
	 * @param result バリデーションの結果
	 * @param mav
	 * @return mav
	 */
	@RequestMapping(value="/reservation/register", method = RequestMethod.POST)
	public ModelAndView register(
			@ModelAttribute("formModel") @Validated ReservationForm reservationForm,
			BindingResult result,
			HttpServletRequest request,
			ModelAndView mav) {
		// エラーがある場合新規予約画面へ戻す
		if (result.hasErrors()) {
			mav.setViewName("register-reserve");
			return mav;
		}

		// 受け取った日付をLocalDateに変換
		LocalDate rsvDate = LocalDate.parse(reservationForm.getRsvDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		// 入力フォームから受け取った値をreservationに格納
		Reservation reservation = this.reservationService.storeReservation(rsvDate, reservationForm, request);
		// 予約を登録する
		this.reservationService.registerReservation(reservation, request);

		// 予約情報を取得
		String reservationDate = rsvDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		List<Reservation> reservationList = this.reservationService.getReservationByDate(reservationDate);

		mav.addObject("selectCalendarDate", reservationDate);
		mav.addObject("reservationList", reservationList);
		mav.setViewName("refer-date");
		return mav;
	}
}
