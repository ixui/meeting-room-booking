$(function() {
	$('#confirm-return').dialog({
		autoOpen: false,
		modal: true,
		closeOnEscape: false
	});

	$('#return').click(function(){
		$('#confirm-return').dialog('open');
	});
});

$(function() {
	$('.return').click(function() {
		$('.btn').attr('disabled', true);

		var ref = document.referrer;

		if ("" == ref) {
			window.location = '/calendar';
			return;
		}

		if (ref == 'https://meeting-room-booking.herokuapp.com/reservationList'){
			var rsvDate = $('#rsvDate').attr('value');
			var calendarDate = rsvDate.replace(/-/g, '');
			$('#calendarDate').attr('value', calendarDate);
			$('#returnForm').submit();
			return;
		}

		window.location = ref;
	});
});