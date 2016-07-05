package jp.co.ixui.tamura.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tamura
 *
 */
@Getter
@Setter
public class Reservation {
	/**
	* 予約ID
	*/
	private int id;
	/**
	 * 予約日
	 */
	private Date rsvDate;
	/**
	 * タイトル
	 */
	private String title;
	/**
	 * 予約者名
	 */
	private String rsvName;
	/**
	 * 社員番号
	 */
	private String empNo;
	/**
	 * 開始時間
	 */
	private String startTime;
	/**
	 * 終了時間
	 */
	private String endTime;
	/**
	 * 詳細
	 */
	private String detail;
	/**
	 * メモ
	 */
	private String memo;

}
