package jp.co.ixui.tamura.controller.reservation;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tamura
 *
 */
@Getter
@Setter
public class ReservationForm {
	private LocalDate rsvDate;
	private String title;
	private String empNo;
	private String startTime;
	private String endTime;
	private String detail;
	private String memo;
}
