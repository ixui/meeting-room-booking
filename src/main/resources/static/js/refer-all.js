$(function() {
	$('a.calendar').click(function(){
		var date = $(this).children('input').attr('value');
		document.getElementById('calendarDate').value=date;
		$('form').submit();
	});
});

$(function() {
	$('a.moveMonth').click(function(){
		var date = $('input#currentYearMonth').attr('value');
		document.getElementById('calendarDate').value=date;
		if ($(this).parent('div').attr('id') == 'next') {
			$('form').attr('action', '/calendar/next');
		} else {
			$('form').attr('action', '/calendar/before');
		};
		$('form').submit();
	});
});