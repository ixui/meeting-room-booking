package jp.co.ixui.tamura;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tamura
 *
 */
@Service
@Getter
@Setter
public class MakeCalendar {
	private int[] calendarDay;
	private int[] calendarParts;
	private Map<String, String> reservationMap;
}
