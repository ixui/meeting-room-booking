
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

$(function() {
	$('#dialog-message').dialog({
		autoOpen: false,
		modal: true,
		closeOnEscape: false
	});

	$('#return').click(function(){
		$('#dialog-message').dialog('open');
	});
});

$(function() {
	$('#confirm-register').dialog({
		autoOpen: false,
		modal: true,
		closeOnEscape: false
	});

	$('#register').click(function(){
		$('#confirm-register').dialog('open');
	});
});

$(function() {
	$('#confirm-delete').dialog({
		autoOpen: false,
		modal: true,
		closeOnEscape: false
	});

	$('.delete').click(function(){
		$('#confirm-delete').dialog('open');
	});
});

$(function() {
	$('.close-dialog').click(function() {
		$('#dialog-message').dialog('close');
		$('#confirm-register').dialog('close');
		$('#confirm-delete').dialog('close');
	});
});
