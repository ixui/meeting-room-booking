
$(document).ready(function() {
	$('.header').load('header.html');
});

$(function() {
	$('#dialog').dialog({
		autoOpen: false,
		modal: true,
		closeOnEscape: false,
		buttons: {
			"OK": function(){
				$(this).dialog('close');
			}
		}
	});
	
	$('#confirm').click(function(){
		$('#dialog').dialog('open');
	});
});
