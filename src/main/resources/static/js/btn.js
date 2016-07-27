$(function() {
	$('.btn').click(function(){
		$('.btn').attr('disabled', true);
		$('a').css('pointer-events', 'none');
	});
});

$(function() {
	$('a').click(function(){
		$('.btn').attr('disabled', true);
		$('a').css('pointer-events', 'none');
	});
});