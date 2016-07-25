$(function() {
	$('.confirm').click(function(){
		$(this).next().attr('name', 'empNo');
		$(this).next().next().attr('name', 'rsvId');
	});
});

$(function() {
	$('#newRegistration').click(function(){
		var rsvDate = $('#rsvDate').attr('value');
		$('#reservationDate').val('rsvDate');
		$('#registrationForm').submit();
	})
})