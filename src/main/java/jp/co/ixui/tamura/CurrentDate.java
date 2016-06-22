package jp.co.ixui.tamura;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;

import lombok.Data;

/**
 * @author tamura
 * アプリケーション内の処理で用いる日時
 */
@Data
public class CurrentDate {
	
	/**
	 * 現在の年・月・日・時間
	 */
	private LocalDateTime dateTime = LocalDateTime.now();
	
	/**
	 * 
	 */
	private YearMonth yearMonth = YearMonth.now();
	
	/**
	 * 
	 */
	private Month month = this.dateTime.getMonth();
	
	/**
	 * 
	 */
	private int day = this.dateTime.getDayOfMonth();
}
