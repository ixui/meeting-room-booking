$(function() {
	$('#date').click(function(){
		var date = $('input#selectCalendarDate').attr('value');
		document.getElementById('calendarDate').value=date;
		document.refer.submit();
		return false;
	});
});