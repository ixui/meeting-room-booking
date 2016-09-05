$(function() {
	var dateFormat = 'yy-mm-dd'
	$( "#rsvDate" ).datepicker({
		dateFormat: dateFormat
	});
});

$(function() {
    var options = { step: 30, timeFormat: 'Hi' };
    $('#startTime').timepicker(options);
    $('#endTime').timepicker(options);
});