$(function() {
	$('a.calendar').click(function(){
		var date = $(this).children('input').attr('value');
		document.getElementById('calendarDate').value=date;
		document.refer.submit();
	});
});

$(function() {
	$('#newRegistration').click(function(){
		window.location = document.getElementById('new').action;
	});
});

$(function() {
	$('#userRegistration').click(function(){
		window.location = document.getElementById('update').action;
	});
});