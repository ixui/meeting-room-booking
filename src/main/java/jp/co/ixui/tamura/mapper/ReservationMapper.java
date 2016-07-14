package jp.co.ixui.tamura.mapper;

import java.time.LocalDate;
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

	/**
	 * @param reservationDate
	 * @return 選択された日付の予約情報
	 */
	List<Reservation> selectReservationByCurrentDay(String reservationDate);

	/**
	 * @param id
	 * @return ID = id の予約情報
	 */
	Reservation selectReservationById(int id);

	/**
	 * @param id
	 */
	void deleteReservationById(int id);

	/**
	 * @param reservation
	 */
	void insertReservation(Reservation reservation);

	/**
	 * @param reservation
	 */
	void updateReservation(Reservation reservation);

	/**
	 * @param rsvDate
	 * @return 開始時間と終了時間
	 */
	List<Reservation> selectTimeByRsvDate(LocalDate rsvDate);
}
