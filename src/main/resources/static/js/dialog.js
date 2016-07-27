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
		$(this).parents('div').dialog('close');
	});
});