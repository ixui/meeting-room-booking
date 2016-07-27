$(function() {

	isChange = false;

	$('#rsvDate').change(function() {
		isChange = true;
	});
	$('#title').change(function() {
		isChange = true;
	});
	$('#startTime').change(function() {
		isChange = true;
	});
	$('#endTime').change(function() {
		isChange = true;
	});
	$('#detail').change(function() {
		isChange = true;
	});
	$('#memo').change(function() {
		isChange = true;
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

$(function() {
	$('#confirm-register').dialog({
		autoOpen: false,
		modal: true,
		closeOnEscape: false
	});

	$('#register').click(function(){
		$('#confirm-register').dialog('open');
	});
});

$(function() {
	$('#confirm-delete').dialog({
		autoOpen: false,
		modal: true,
		closeOnEscape: false
	});

	$('.delete').click(function(){
		$('#confirm-delete').dialog('open');
	});
});

$(function() {
	$('#register-reservation').click(function() {
		registerForm.submit();
	});
});

$(function() {
	$('#modify-reservation').click(function() {
		modifyForm.submit();
	});
});

$(function() {
	$('.close-dialog').click(function() {
		$(this).parents('div').dialog('close');
	});
});