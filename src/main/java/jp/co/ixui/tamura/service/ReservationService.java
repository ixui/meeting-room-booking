package jp.co.ixui.tamura.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ixui.tamura.CalendarDate;
import jp.co.ixui.tamura.controller.reservation.ReservationForm;
import jp.co.ixui.tamura.domain.Reservation;
import jp.co.ixui.tamura.mapper.ReservationMapper;

/**
 * @author tamura
 *
 */
@Service
public class ReservationService {

	@Autowired
	ReservationMapper reservationMapper;

	@Autowired
	UserService userService;

	/**
	 * カレンダーのセル数
	 */
	private static final int CELL_COUNT = 42;

	/**
	 * 日曜日を示す数字
	 */
	private static final int SUNDAY_NUMBER = 7;

	/**
	 * 指定した月のカレンダーを表示するための情報を設定
	 *
	 * @param calendarDate
	 * @return calendarDateList
	 */
	public List<CalendarDate> makeCalendar(String designatedMonth) {
		// 文字列から日付に変換
		YearMonth yearMonth = YearMonth.parse(designatedMonth, DateTimeFormatter.ofPattern("yyyyMM"));
		// 取得した月の1日の曜日をintで取得
		int startDayOfWeek = yearMonth.atDay(1).getDayOfWeek().getValue();
		// 取得した月の日数を取得
		int designatedMonthLastDay = yearMonth.lengthOfMonth();

		String year = String.valueOf(yearMonth.getYear());
		String month = String.valueOf(yearMonth.getMonthValue());

		List<CalendarDate> calendarDateList = makeCalendarList(designatedMonth, year, month, startDayOfWeek,
				designatedMonthLastDay);
		return calendarDateList;
	}

	/**
	 * カレンダーを表示するための日付インスタンスを格納
	 *
	 * @param yearMonth
	 * @param year
	 * @param month
	 * @param startDayOfWeek
	 * @param monthLastDay
	 * @return calendarDateList
	 */
	public List<CalendarDate> makeCalendarList(String yearMonth, String year, String month, int startDayOfWeek,
			int monthLastDay) {

		int count = 0;
		List<CalendarDate> calendarDateList = new ArrayList<>();

		// カレンダーの頭の空白部分にnullを格納
		if (SUNDAY_NUMBER != startDayOfWeek) {
			for (int i = 1; i <= startDayOfWeek; i++) {
				calendarDateList.add(null);
				count++;
			}
		}
		// 受け取った月の日付の情報を calendarDate に格納
		for (int i = 1; i <= monthLastDay; i++) {
			CalendarDate calendarDate = new CalendarDate();
			calendarDate.setYear(year);
			calendarDate.setMonth(month);
			calendarDate.setDay(i);
			calendarDate.setDayOfWeek(YearMonth.of(Integer.parseInt(year), Integer.parseInt(month)).atDay(i).getDayOfWeek().getValue());
			String currentDay = String.valueOf(i);
			// 日付ごとの予約情報を取得し calendarDate に格納
			// iが一桁のとき dd の形にする
			if (10 > i) currentDay = 0 + currentDay;
			List<Reservation> reservationList = this.reservationMapper.selectReservationByCurrentDay(yearMonth + currentDay);
			calendarDate.setReservationList(reservationList);
			calendarDateList.add(calendarDate);
			count++;
		}
		// カレンダーの終わりの空白部分にnullを格納
		while (count < CELL_COUNT){
			calendarDateList.add(null);
			count++;
		}
		return calendarDateList;
	}

	/**
	 * カレンダー月移動時のURLチェック
	 *
	 * @param designatedMonth
	 * @return エラーの有無
	 */
	@SuppressWarnings("static-method")
	public boolean urlHasErrors(String designatedMonth) {

		if (!Pattern.matches("[0-9]{6}",designatedMonth)) return true;

		int yearMonth = Integer.parseInt(designatedMonth.substring(0, 4));
		if (2015 >= yearMonth || 2100 <= yearMonth) return true;

		int day = Integer.parseInt(designatedMonth.substring(4));
		if (0 >= day || 12 < day) return true;

		return false;
	}

	/**
	 * 選択日の予約情報を取得する
	 *
	 * @param reservationDate
	 * @return reservationList
	 */
	public List<Reservation> getReservationByDate(String reservationDate) {
		List<Reservation> reservationList = this.reservationMapper.selectReservationByCurrentDay(reservationDate);
		return reservationList;
	}

	/**
	 * 送られてきたidの予約情報を取得する
	 *
	 * @param id
	 * @return reservation
	 */
	public Reservation getReservsationById(int id) {
		Reservation reservation = this.reservationMapper.selectReservationById(id);
		return reservation;
	}

	/**
	 * セッション情報.empNo と 予約.empNo が同じか調べる
	 *
	 * @param empNo
	 * @param request
	 * @return principal
	 */
	public boolean booleanPrincipal(String empNo) {
		// 認証情報から社員番号を取得
		String AuthenticationEmpNo = this.userService.getEmpNoFromAuthentication();
		// 予約者かどうか調べる
		if (AuthenticationEmpNo.equals(empNo)) return true;
		return false;
	}

	/**
	 * 予約情報を削除する
	 *
	 * @param id
	 */
	public void deleteReservation(String id) {
		this.reservationMapper.deleteReservationById(Integer.parseInt(id));
	}

	/**
	 * 新規予約処理
	 *
	 * @param rsvDate 予約日
	 * @param reservation エンティティ
	 */
	public void registerReservation(Reservation reservation) {
		// 認証情報のempNoをreservation.empNoに追加
		String empNo = this.userService.getEmpNoFromAuthentication();
		reservation.setEmpNo(empNo);
		// 予約を登録する
		this.reservationMapper.insertReservation(reservation);
	}

	/**
	 * 予約更新処理
	 *
	 * @param reservation
	 * @param request
	 *
	 */
	public void updateReservation(Reservation reservation){
		// 認証情報のempNoをreservation.empNoに追加
		String empNo = this.userService.getEmpNoFromAuthentication();
		reservation.setEmpNo(empNo);
		// 予約を更新する
		this.reservationMapper.updateReservation(reservation);
	}

	/**
	 * フォームから受け取った値をドメインに格納する
	 *
	 * @param rsvDate
	 * @param id
	 * @param reservationForm
	 * @param request
	 * @return reservation
	 */
	public Reservation storeReservation(LocalDate rsvDate, ReservationForm reservationForm) {
		// 認証情報から社員番号を取得
		String empNo = this.userService.getEmpNoFromAuthentication();
		Reservation reservation = new Reservation();
		reservation.setRsvDate(rsvDate);
		reservation.setTitle(reservationForm.getTitle());
		reservation.setEmpNo(empNo);
		reservation.setStartTime(reservationForm.getStartTime());
		reservation.setEndTime(reservationForm.getEndTime());
		reservation.setDetail(reservationForm.getDetail());
		reservation.setMemo(reservationForm.getMemo());
		return reservation;
	}
}
