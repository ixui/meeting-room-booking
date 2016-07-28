$(function() {
	$('#delete').click(function(){
		var reservationId = $('#id').attr('value');
		var reservationDate = $('#rsvDate').attr('value');
		$('#deleteId').attr('value', reservationId);
		$('#reservationDate').attr('value', reservationDate);
	});
});

$(function() {
	$('#delete-reservation').click(function(){
		$('.btn').attr('disabled', true);
		$('#deleteForm').submit();
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
		$('#calendarDate').attr('value', reservationDate);

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

$(function() {
	$('.return').click(function() {
		$('.btn').attr('disabled', true);
		$('#returnForm').submit();
	});
});