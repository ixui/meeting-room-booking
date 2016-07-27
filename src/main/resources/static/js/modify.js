$(function() {
	$('.delete').click(function(){
		var reservationId = $('#id').attr('value');
		var reservationDate = $('#rsvDate').attr('value');
		document.getElementById('deleteId').value = reservationId;
		document.getElementById('reservationDate').value = reservationDate;
	});
});

$(function() {
	$('#delete').click(function(){
		$(this).attr('disabled', true);
	});
});

$(function() {
	$('#confirm-return').dialog({
		autoOpen: false,
		modal: true,
		closeOnEscape: false
	});

	$('#return').click(function(){
		var rsvDate = $('#rsvDate').attr('value');
		var reservationDate = rsvDate.replace(/-/g, '');
		document.getElementById('calendarDate').value = reservationDate;

		if ($('#principal').attr('value') == "false") {
			$('#returnForm').submit();
			return;
		};

		if (isChange) {
			$('#confirm-return').dialog('open');
		} else {
			$('#returnForm').submit();
		};
	});
});