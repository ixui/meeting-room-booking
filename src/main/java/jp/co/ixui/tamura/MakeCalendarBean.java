package jp.co.ixui.tamura;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jp.co.ixui.tamura.domain.Reservation;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tamura
 *
 */
@Service
@Getter
@Setter
public class MakeCalendarBean {
	// 現在の年
	private String currentYear;
	// 現在の年月
	private String currentMonth;
	// 月の初めの曜日
	private int startDayOfWeek;
	// 今月の日数
	private int currrentMonthLastDay;
	// 今月のカレンダーの日にち
	private String[] calendarDay;
	// 日付がキー、予約の有無がバリュー
	private Map<String, String> reservationMap;
	// 選択日の予約リスト
	private List<Reservation> reservationList;
	/**
	 * コンストラクタ
	 */
	public MakeCalendarBean() {
		setCurrentYear(String.valueOf(YearMonth.now().getYear()));
		setCurrentMonth(String.valueOf(YearMonth.now().getMonthValue()));
		setCurrrentMonthLastDay(YearMonth.now().atEndOfMonth().lengthOfMonth());
		setStartDayOfWeek(YearMonth.now().atDay(1).getDayOfWeek().getValue());
	}
}
