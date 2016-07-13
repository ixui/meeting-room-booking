$(function() {
	$('.confirm').click(function(){
		$(this).next().attr('name', 'empNo');
		$(this).next().next().attr('name', 'rsvId');
	});
});