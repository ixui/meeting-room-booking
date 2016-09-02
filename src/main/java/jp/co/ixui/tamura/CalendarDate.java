package jp.co.ixui.tamura;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jp.co.ixui.tamura.domain.Reservation;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tamura
 *
 */
@Getter
@Setter
public class CalendarDate {

	private String year;
	private String month;
	private int day;
	private int dayOfWeek;
	private List<Reservation> reservationList;

	/**
	 * @param month
	 */
	public void setMonth(String month){
		// monthが一ケタの場合"0"をたして"08"の形にする
		if (month.length() == 1) {
			this.month = "0" + month;
		} else {
			this.month = month;
		}
	}

	/**
	 * @return 'yyyyMMdd'形式の日付文字列
	 *
	 */
	public String makeSelectCalendarDate() {
		String currentDay = String.valueOf(this.day);
		if (10 > this.day) {
			currentDay = 0 + currentDay;
		}
		return this.year + this.month + currentDay;
	}

	/**
	 * @return 次月
	 */
	public String getNextMonth() {
		String nextMonth = YearMonth.parse(this.year + "-" + this.month).plusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMM"));
		return nextMonth;
	}

	/**
	 * @return 前月
	 */
	public String getLastMonth() {
		String lastMonth = YearMonth.parse(this.year + "-" + this.month).minusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMM"));
		return lastMonth;
	}
}
