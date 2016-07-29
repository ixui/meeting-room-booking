$(function() {
	$('a.calendar').click(function(){
		var date = $(this).children('input').attr('value');
		document.getElementById('calendarDate').value=date;
		$('form').submit();
	});
});

$(function() {
	$('#newRegistration').click(function(){
		window.location = '/reservation/new';
	});
});