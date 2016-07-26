$(function() {
	$('.confirm').click(function(){
		$(this).next().attr('name', 'empNo');
		$(this).next().next().attr('name', 'rsvId');
	});
});

$(function() {
	$('#newRegistration').click(function(){
		window.location = '/reservation/new';
	});
});

$(function() {
	$('#return').click(function(){
		window.location = '/calendar';
	});
});