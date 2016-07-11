package jp.co.ixui.tamura;

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
}
