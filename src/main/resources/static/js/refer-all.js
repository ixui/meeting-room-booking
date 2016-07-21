$(function() {
	$('a.calendar').click(function(){
		var date = $(this).children().attr('value');
		document.getElementById('calendarDate').value=date;
		document.refer.submit();
		return false;
	});
});

$(function() {
	$('a.nextMonth').click(function(){
		var date = $('input#currentYearMonth').attr('value');
		document.getElementById('calendarDate').value=date;
		$('form').attr('action', '/calendar/next');
		document.refer.submit();
		return false;
	});
});

$(function() {
	$('a.beforeMonth').click(function(){
		var date = $('input#currentYearMonth').attr('value');
		document.getElementById('calendarDate').value=date;
		$('form').attr('action', '/calendar/before');
		document.refer.submit();
		return false;
	});
});