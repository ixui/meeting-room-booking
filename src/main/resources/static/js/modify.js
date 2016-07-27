$(function() {
	$('.delete').click(function(){
		var reservationId = $('#id').attr('value');
		var reservationDate = $('#rsvDate').attr('value');
		document.getElementById('deleteId').value = reservationId;
		document.getElementById('reservationDate').value = reservationDate;
	});
});

$(function() {
	$('#delete').click(function(){
		$(this).attr('disabled', true);
	});
});