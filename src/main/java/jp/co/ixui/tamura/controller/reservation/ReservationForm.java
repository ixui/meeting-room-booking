package jp.co.ixui.tamura.controller.reservation;

import org.hibernate.validator.constraints.NotEmpty;

import jp.co.ixui.tamura.controller.reservation.validator.annotation.Duplication;
import jp.co.ixui.tamura.controller.reservation.validator.annotation.EndTime;
import jp.co.ixui.tamura.controller.reservation.validator.annotation.Future;
import jp.co.ixui.tamura.controller.reservation.validator.annotation.MeetingTime;
import jp.co.ixui.tamura.controller.reservation.validator.annotation.Time;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tamura
 *
 */
@Getter
@Setter
@Duplication
@EndTime
public class ReservationForm {
	@NotEmpty(message="日付を入力してください")
	@Future
	private String rsvDate;

	@NotEmpty(message="タイトルを入力してください")
	private String title;

	@Time
	@MeetingTime
	private String startTime;

	@Time
	@MeetingTime
	private String endTime;

	@NotEmpty(message="詳細を入力してください")
	private String detail;

	private String memo;

	private int id;
}
