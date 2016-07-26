$(function() {
	$('#confirm-return').dialog({
		autoOpen: false,
		modal: true,
		closeOnEscape: false
	});

	$('#return').click(function(){
		$('#confirm-return').dialog('open');
		var rsvDate = $('#rsvDate').attr('value');
		var reservationDate = rsvDate.replace(/-/g, '');
		document.getElementById('calendarDate').value = reservationDate;
	});
});

$(function() {
	$('.return').click(function() {
		$(this).attr('disabled', true);
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
		$(this).attr('disabled', true);
		registerForm.submit();
	});
});

$(function() {
	$('.close-dialog').click(function() {
		$('#confirm-return').dialog('close');
		$('#confirm-register').dialog('close');
		$('#confirm-delete').dialog('close');
	});
});