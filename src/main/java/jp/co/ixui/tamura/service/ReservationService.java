package jp.co.ixui.tamura.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ixui.tamura.CalendarDate;
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

	/**
	 * カレンダーのセル数
	 */
	private static final int CELL_COUNT = 42;

	/**
	 * 日曜日を示す数字
	 */
	private static final int SUNDAY_NUMBER = 7;

	/**
	 * カレンダー作成メソッド
	 *
	 * @return calendarDateList
	 */
	public List<CalendarDate> makeCalendarDateList() {
		// 現在の年月を取得
		YearMonth yearMonth = YearMonth.now();
		// 年月を文字列に変換
		DateTimeFormatter formatCurrentYearMonth = DateTimeFormatter.ofPattern("yyyyMM");
		String currentYearMonth = formatCurrentYearMonth.format(yearMonth);
		// 年と月をそれぞれ切り取る
		String year = currentYearMonth.substring(0, 4);
		String month = currentYearMonth.substring(4, 6);
		// 取得した月の1日の曜日をintで取得
		int startDayOfWeek = yearMonth.atDay(1).getDayOfWeek().getValue();
		// 取得した月の日数を取得
		int currrentMonthLastDay = yearMonth.atEndOfMonth().lengthOfMonth();

		int count = 0;
		List<CalendarDate> calendarDateList = new ArrayList<>();
		// カレンダーの頭の空白部分にnullを格納
		if (SUNDAY_NUMBER != startDayOfWeek) {
			for (int i = 1; i <= startDayOfWeek; i++) {
				calendarDateList.add(null);
				count++;
			}
		}
		// 今月の日付の情報を格納
		for (int i = 1; i <= currrentMonthLastDay; i++) {
			CalendarDate calendarDate = new CalendarDate();
			calendarDate.setYear(year);
			calendarDate.setMonth(month);
			calendarDate.setDay(i);
			calendarDate.setDayOfWeek(YearMonth.now().atDay(i).getDayOfWeek().getValue());
			String currentDay = String.valueOf(i);
			// iが一桁のとき dd の形にする
			if (10 > i) {
				currentDay = 0 + currentDay;
			}
			List<Reservation> reservationList = this.reservationMapper.selectReservationByCurrentDay(currentYearMonth + currentDay);
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
	 * @param rsvDate
	 * @return reservationList
	 */
	public List<Reservation> getReservationListByDay(Date rsvDate) {
		String reservationDate = new SimpleDateFormat("yyyyMMdd").format(rsvDate);
		List<Reservation> reservationList = this.getReservationByDate(reservationDate);
		return reservationList;
	}

	/**
	 * セッション情報.empNo と 予約.empNo が同じか調べる
	 *
	 * @param empNo
	 * @param request
	 * @return principal
	 */
	public static boolean booleanPrincipal(String empNo, HttpServletRequest request) {
		// セッション情報から社員番号を取得
		HttpSession session = request.getSession();
		String sessionEmpNo = (String)session.getAttribute("empNo");
		// 予約者かどうか調べる
		boolean principal = false;
		if (sessionEmpNo == empNo) {
			principal = true;
		}
		return principal;
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
	 * @param request
	 */
	public void registerReservation(String rsvDate, Reservation reservation, HttpServletRequest request) {
		// フォームから受け取った日付をDate型に変換する
		Date reservationDay = null;
		try {
			reservationDay = new SimpleDateFormat("yyyy-MM-dd").parse(rsvDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 変換した日付をインスタンスに追加
		reservation.setRsvDate(reservationDay);
		// セッションに保存されているempNoをreservation.empNoに追加
		HttpSession session = request.getSession();
		String empNo = (String) session.getAttribute("empNo");
		reservation.setEmpNo(empNo);
		// 予約を登録する
		this.reservationMapper.insertReservation(reservation);
	}
}
