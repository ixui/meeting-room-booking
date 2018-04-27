package jp.co.ixui.tamura.controller.reservation;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import jp.co.ixui.tamura.MeetingRoomBookingAppApplication;
import jp.co.ixui.tamura.domain.Reservation;
import jp.co.ixui.tamura.service.ReservationService;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {TestContext.class,MeetingRoomBookingAppApplication.class})
@Transactional
public class ReservationControllerTest {

	@Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    ReservationService reservationService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
        		.defaultRequest(get("/").with(csrf()).with(user("user").password("pass")))
        		.apply(springSecurity())
        		.build();
    }

    @Test
    public void カレンダー画面の正常表示() throws Exception{

    	this.mockMvc.perform(get("/calendar")
    			.accept(MediaType.parseMediaType("application/html;charset=UTF-8")))
        		.andExpect(status().isOk());
    }

    @Test
    public void カレンダー表示月遷移の確認() throws Exception {

    	// 正常
    	this.mockMvc.perform(get("/calendar/201610")
    			.accept(MediaType.parseMediaType("application/html;charset=UTF-8")))
        		.andExpect(status().isOk());

    	// 異常(表示範囲外を指定した場合当月のカレンダーへ)
    	this.mockMvc.perform(get("/calendar/210010")
    			.accept(MediaType.parseMediaType("application/html;charset=UTF-8")))
        		.andExpect(status().isFound());
    }

    @Test
    public void 新規予約画面の正常表示() throws Exception {

    	// カレンダー画面からの遷移
    	this.mockMvc.perform(get("/reservation/new")
    			.accept(MediaType.parseMediaType("application/html;charset=UTF-8")))
        		.andExpect(status().isOk());

    	// 日付ごとの予約確認画面からの遷移
    	this.mockMvc.perform(post("/reservation/new")
    			.accept(MediaType.parseMediaType("application/html;charset=UTF-8"))
    			.param("calendarDate", "2099-10-28"))
    			.andExpect(status().isOk());
    }

    @Test
    public void 日付ごとの予約確認画面の正常表示() throws Exception {

    	this.mockMvc.perform(post("/reservationList")
    			.accept(MediaType.parseMediaType("application/html;charset=UTF-8"))
    			.param("calendarDate", "20991028"))
    			.andExpect(status().isOk());
    }

    @Test
    public void 予約確認画面の正常表示() throws Exception {

    	LocalDate rsvDate = LocalDate.parse("2099-10-28", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	Reservation reservation = new Reservation();
    	reservation.setRsvDate(rsvDate);
    	reservation.setTitle("Test");
    	reservation.setStartTime("1200");
    	reservation.setEndTime("1300");
    	reservation.setDetail("test");
    	reservation.setMemo("memo");
    	reservation.setId(1);

    	// メソッドの戻り値を設定
    	when(this.reservationService.getReservsationById(1)).thenReturn(reservation);

    	this.mockMvc.perform(post("/reservation/confirm")
    			.accept(MediaType.parseMediaType("application/html;charset=UTF-8"))
    			.param("rsvId", "1")
    			.param("empNo", "5008"))
    			.andExpect(status().isOk());
    }

    @Test
    public void 新規予約登録の確認() throws Exception {

    	doNothing().when(this.reservationService).registerReservation(anyObject());

    	this.mockMvc.perform(post("/reservation/register")
    			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
    			.accept(MediaType.parseMediaType("application/html;charset=UTF-8"))
    			.param("rsvDate", "2099-01-01")
    			.param("title", "Test")
    			.param("startTime", "1200")
    			.param("endTime", "1300")
    			.param("detail", "test")
    			.param("memo", "memo"))
    			.andExpect(status().isOk())
    			.andExpect(model().hasNoErrors());

    	// 登録が行われていることを確認
    	verify(this.reservationService).registerReservation(anyObject());
    }

    @Test
    public void 予約修正登録の確認() throws Exception {

    	this.mockMvc.perform(post("/reservation/modify")
    			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
    			.accept(MediaType.parseMediaType("application/html;charset=UTF-8"))
    			.param("id", "1")
    			.param("empNo", "5008")
    			.param("principal", "true")
    			.param("rsvDate", "2099-10-28")
    			.param("title", "Test")
    			.param("startTime", "2200")
    			.param("endTime", "2300")
    			.param("detail", "test"))
    			.andExpect(status().isOk())
    			.andExpect(model().hasNoErrors());

    	// 登録が行われていることを確認
    	verify(this.reservationService).updateReservation(anyObject());
    }

    @Test
    public void 予約の削除の確認() throws Exception {

    	// 削除後に表示する予約がある場合
    	this.mockMvc.perform(post("/reservation/delete")
    			.accept(MediaType.parseMediaType("application/html;charset=UTF-8"))
    			.param("deleteId", "1")
    			.param("reservationDate", "2099-10-28"))
    			.andExpect(status().isOk())
    			.andExpect(model().hasNoErrors())
    			.andExpect(view().name("refer-date"));

    	// 削除が行われていることを確認
    	verify(this.reservationService).deleteReservation(anyString());
    }

    @Test
    public void ReservationFormのバリデーションテスト() throws Exception {

    	// @Futureの確認
    	this.mockMvc.perform(post("/reservation/register")
    			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
    			.param("rsvDate", "2016-09-03")
    			.param("title", "Test")
    			.param("startTime", "1200")
    			.param("endTime", "1300")
    			.param("detail", "test"))
    			.andExpect(status().isOk())
    			.andExpect(model().hasErrors())
    			.andExpect(model().errorCount(1))
    			.andExpect(model().attributeHasFieldErrors("formModel", "rsvDate"));

    	// @EndTimeの確認
    	this.mockMvc.perform(post("/reservation/register")
    			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
    			.param("rsvDate", "2099-09-03")
    			.param("title", "Test")
    			.param("startTime", "1300")
    			.param("endTime", "1300")
    			.param("detail", "test"))
    			.andExpect(status().isOk())
    			.andExpect(model().hasErrors())
    			.andExpect(model().errorCount(1))
    			.andExpect(model().attributeHasErrors("formModel"));

    	// @MeetingTimeの確認
    	this.mockMvc.perform(post("/reservation/register").with(user("admin").password("pass"))
    			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
    			.param("rsvDate", "2099-09-03")
    			.param("title", "Test")
    			.param("startTime", "333")
    			.param("endTime", "55555")
    			.param("detail", "test"))
    			.andExpect(status().isOk())
    			.andExpect(model().hasErrors())
    			.andExpect(model().errorCount(4))
    			.andExpect(model().attributeHasFieldErrors("formModel", "startTime", "endTime"));

    	// @Duplicationの確認
    	this.mockMvc.perform(post("/reservation/register").with(user("admin").password("pass"))
    			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
    			.param("rsvDate", "2099-10-28")
    			.param("title", "Test")
    			.param("startTime", "1100")
    			.param("endTime", "1201")
    			.param("detail", "test")
    			.param("memo", "memo"))
    			.andExpect(status().isOk())
    			.andExpect(model().hasErrors())
    			.andExpect(model().errorCount(1))
    			.andExpect(model().attributeHasErrors("formModel"));

    	// @NotEmptyの確認
    	this.mockMvc.perform(post("/reservation/register").with(user("admin").password("pass"))
    			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
    			.param("rsvDate", "")
    			.param("title", "")
    			.param("startTime", "1200")
    			.param("endTime", "1300")
    			.param("detail", ""))
    			.andExpect(status().isOk())
    			.andExpect(model().hasErrors())
    			.andExpect(model().errorCount(3))
    			.andExpect(model().attributeHasFieldErrors("formModel", "rsvDate", "title", "detail"));

    	// @Timeの確認
    	this.mockMvc.perform(post("/reservation/register").with(user("admin").password("pass"))
    			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
    			.param("rsvDate", "2099-12-31")
    			.param("title", "Test")
    			.param("startTime", "2360")
    			.param("endTime", "2400")
    			.param("detail", "test"))
    			.andExpect(status().isOk())
    			.andExpect(model().hasErrors())
    			.andExpect(model().errorCount(2))
    			.andExpect(model().attributeHasFieldErrors("formModel", "startTime", "endTime"));
    }
}
