$(function() {
	$('.btn').click(function(){
		$('.btn').attr('disabled', true);
		$('a').css('pointer-events', 'none');
	});
});

$(function() {
	$('a').click(function(){

		if ($(this).attr('class') == 'dropdown-toggle') return

		$('.btn').attr('disabled', true);
		$('a').css('pointer-events', 'none');
	});
});