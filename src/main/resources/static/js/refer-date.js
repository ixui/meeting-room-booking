$(function() {
	$('.confirm').click(function(){
		$(this).next().attr('name', 'empNo');
		$(this).next().next().attr('name', 'rsvId');
		$('#confirmForm').submit();
	});
});

$(function() {
	$('#newRegistration').click(function(){
		var rsvDate = $('#rsvDate').attr('value');
		$('input#calendarDate').val(rsvDate);
		$('#registrationForm').submit();
	});
});

$(function() {
	$('#return').click(function(){
		window.location = '/calendar';
	});
});
