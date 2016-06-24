package jp.co.ixui.tamura;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ixui.tamura.domain.EmpMst;
import jp.co.ixui.tamura.domain.Reservation;
import jp.co.ixui.tamura.mapper.EmpMstMapper;
import jp.co.ixui.tamura.mapper.ReservationMapper;

/**
 * @author tamura
 *
 */
@Controller
public class ReservastionController {


	/**
	 * カレンダーのセル数
	 */
	private static final int CELL_COUNT = 42;

	/**
	 * DB操作を行うMapperインタフェースを関連付ける
	 */
	@Autowired
	EmpMstMapper empMstMapper;

	/**
	 * Reservastionテーブルの操作を行うMapperインタフェースを関連付ける
	 */
	@Autowired
	ReservationMapper reservationMapper;

	/**
	 * @param id
	 * @param pass
	 * @param mav
	 * @return mav
	 * カレンダーを表示
	 */
	@RequestMapping(value = "/refer-all.html", method = RequestMethod.POST)
	public ModelAndView referAll(
			@RequestParam(value = "empNo") String id,
			@RequestParam(value = "pass") String pass,
			ModelAndView mav) {

		// 入力チェック
		if ("".equals(id) || "".equals(pass)) {
			mav.setViewName("index");
			if ("".equals(id)) mav.addObject("errMsg1", "社員番号を入力してください");
			if ("".equals(pass)) mav.addObject("errMsg2", "パスワードを入力してください");
			return mav;
		}

		int empNo = Integer.parseInt(id);
		EmpMst empMst = this.empMstMapper.selectUser(empNo);
		if (null == empMst || !empMst.getPass().equals(pass)) {
			mav.setViewName("index");
			mav.addObject("errMsg1", "社員番号かパスワードが違います");
			return mav;
		}

		// カレンダーの日付作成
		int[] calendarDay = makeCalendar();

		// カレンダーに表示する予約情報の取得
		Map<String, String> reservationMap = makeReservationMap();

		System.out.println(reservationMap.get("23"));

		// カレンダーを表示するための値をmavにつめる
		mav.setViewName("refer-all");
		mav.addObject("reservationMap", reservationMap);
		mav.addObject("calendarDay", calendarDay);
		return mav;
	}


	/**
	 * @return reservationMap
	 */
	private Map<String, String> makeReservationMap() {
		// 今日の日付を取得しString型に変換
		Date currentDate = new Date();
		String datePattern = "yyyyMM";
		String currentMonth = new SimpleDateFormat(datePattern).format(currentDate) + "%";
		// reservationテーブルから今月の予約情報を取得
		List<Reservation> reservationList = this.reservationMapper.selectReservationByCurrentMonth(currentMonth);
		// カレンダーに表示する予定を渡すために日付と開始時間をMapにつめる
		Map<String, String> reservationMap = new HashMap<>();
		for (Reservation rsv : reservationList) {
			reservationMap.put(new SimpleDateFormat("dd").format(rsv.getRsvDate()), rsv.getStartTime());
		}
		return reservationMap;
	}


	/**
	 * @return calendarDay
	 */
	private static int[] makeCalendar() {

		Calendar calendar = Calendar.getInstance();
		// 現在の年・月・日を取得
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		// 今月が何曜日からか
		calendar.set(year, month, 1);
		int startWeek = calendar.get(Calendar.DAY_OF_WEEK);

		// 今月の日数
		calendar.set(year, month + 1, 0);
		int thisMonthlastDay = calendar.get(Calendar.DATE);
		// 先月の日数
		calendar.set(year, month, 0);
		int beforeMonthlastDay = calendar.get(Calendar.DATE);

		// カレンダーの日付の格納
		int[] calendarDay = new int[CELL_COUNT];
		int count = 0;
		// TODO
		for (int i = startWeek - 2; i >= 0; i--) {
			calendarDay[count++] = beforeMonthlastDay - i;
		}
		for (int i = 1 ; i <= thisMonthlastDay ; i++){
			calendarDay[count++] = i;
		}
		int nextMonthDay = 1;
		while (count < CELL_COUNT){
			calendarDay[count++] = nextMonthDay++;
		}
		return calendarDay;
	}
}
