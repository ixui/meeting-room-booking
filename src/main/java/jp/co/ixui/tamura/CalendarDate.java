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
	 * @return カレンダーで選択された日付
	 *
	 */
	public String makeSelectCalendarDate() {
		return this.year + this.month + this.day;
	}
}
