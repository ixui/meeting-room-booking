$(function() {
	$('#date').click(function(){
		var date = $('this #targetDate').attr('value');
		document.getElementById('calendarDate').value=date;
		document.refer.submit();
		return false;
	});
});