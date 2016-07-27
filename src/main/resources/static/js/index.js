$(function() {
	$('#login').click(function(){
		$('#loginForm').submit();
	});
});

$(function() {
	$('#signup').click(function(){
		window.location = '/signup';
	});
});