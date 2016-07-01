package jp.co.ixui.tamura.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ixui.tamura.MakeCalendarBean;
import jp.co.ixui.tamura.domain.EmpMst;
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
	MakeCalendarBean makeCalendar;

	/**
	 * カレンダーのセル数
	 */
	private static final int CELL_COUNT = 42;

	/**
	 * カレンダーで使う日付を配列に格納する
	 * @return calendarDay
	 */
	public static int[] makeCalendar() {
		int[] calendarParts = makeCalendarParts();
		int startDayOfWeek = calendarParts[0];
		int beforeMonthLastDay = calendarParts[1];
		int thisMonthLastDay = calendarParts[2];

		// カレンダーの日付を格納するための配列
		int[] calendarDay = new int[CELL_COUNT];
		int count = 0;
		// カレンダーの頭の先月の日付を格納
		for (int i = startDayOfWeek - 2; i >= 0; i--) {
			calendarDay[count++] = beforeMonthLastDay - i;
		}
		// 今月の日付を格納
		for (int i = 1 ; i <= thisMonthLastDay ; i++){
			calendarDay[count++] = i;
		}
		// カレンダーの終わりの来月の日付を格納
		int nextMonthDay = 1;
		while (count < CELL_COUNT){
			calendarDay[count++] = nextMonthDay++;
		}
		return calendarDay;
	}

	/**
	 * カレンダー表示に使う値を配列につめる
	 * @return calendarParts
	 */
	public static int[] makeCalendarParts() {
		// 現在の年・月を取得
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		// 今月が何曜日からか
		calendar.set(year, month, 1);
		int startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		// 今月が何日までか
		calendar.set(year, month + 1, 0);
		int thisMonthLastDay = calendar.get(Calendar.DATE);
		// 先月が何日までか
		calendar.set(year, month, 0);
		int beforeMonthLastDay = calendar.get(Calendar.DATE);

		int[] calendarParts = {startDayOfWeek, thisMonthLastDay, beforeMonthLastDay};
		return calendarParts;
	}

	/**
	 * カレンダーに表示する予約情報(開始時間)を日付をkeyにしてmapにつめる
	 * @return reservationMap
	 */
	public MakeCalendarBean makeReservationMap() {
		// 今日の日付を取得しString型に変換
		Date currentDate = new Date();
		String currentMonth = new SimpleDateFormat("yyyyMM").format(currentDate) + "%";
		// reservationテーブルから今月の予約情報を取得
		List<Reservation> reservationList = this.reservationMapper.selectReservationByCurrentMonth(currentMonth);
		// カレンダーに表示する予定を渡すために日付と開始時間をMapにつめる
		Map<String, String> reservationMap = new HashMap<>();
		for (Reservation rsv : reservationList) {
			reservationMap.put(new SimpleDateFormat("dd").format(rsv.getRsvDate()), rsv.getStartTime());
		}
		int[] calendarDay = ReservationService.makeCalendar();
		int[] calendarParts = ReservationService.makeCalendarParts();

		this.makeCalendar.setCalendarDay(calendarDay);
		this.makeCalendar.setCalendarParts(calendarParts);
		this.makeCalendar.setReservationMap(reservationMap);

		return this.makeCalendar;
	}

	/**
	 * 選択日の予約情報を取得する
	 * @param reservationDate
	 * @return reservationList
	 */
	public List<Reservation> getReservationByDate(String reservationDate) {
		List<Reservation> reservationList = this.reservationMapper.selectReservationByCurrentDay(reservationDate);
		return reservationList;
	}

	/**
	 * 送られてきたidの予約情報を取得する
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
	 * @param empNo
	 * @param request
	 * @return principal
	 */
	public static boolean booleanPrincipal(String empNo, HttpServletRequest request) {
		// セッション情報から社員番号を取得
		HttpSession session = request.getSession();
		EmpMst empMst = (EmpMst)session.getAttribute("empMst");
		// 予約者かどうか調べる
		boolean principal = false;
		if (empMst.getEmpNo() == empNo) {
			principal = true;
		}
		return principal;
	}

	/**
	 * 予約情報を削除する
	 * @param id
	 */
	public void deleteReservation(String id) {
		this.reservationMapper.deleteReservationById(Integer.parseInt(id));
	}
}
