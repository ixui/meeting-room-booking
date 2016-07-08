$(function() {
	$('#date').click(function(){
		var date = $(this).text();
		document.getElementById('calendarDate').value=date;
		document.refer.submit();
		return false;
	});
});