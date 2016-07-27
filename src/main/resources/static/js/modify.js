$(function() {
	$('#delete').click(function(){
		var reservationId = $('#id').attr('value');
		var reservationDate = $('#rsvDate').attr('value');
		$('#deleteId').attr('value', reservationId);
		$('#reservationDate').attr('value', reservationDate);
	});
});

$(function() {
	$('#delete-reservation').click(function(){
		$('.btn').attr('disabled', true);
		$('#deleteForm').submit();
	});
});