package jp.co.ixui.tamura.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.ixui.tamura.domain.Reservation;

/**
 * @author tamura
 *
 */
@Mapper
public interface ReservationMapper {
	
	/**
	 * @param currentMonth 
	 * @return 予約
	 */
	List<Reservation> selectReservationByCurrentMonth(String currentMonth);
}
