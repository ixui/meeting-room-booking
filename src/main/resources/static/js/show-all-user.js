// 新規ユーザ登録フォームの表示
$(function() {
	$('#user-registration').dialog({
		autoOpen: false,
		height: 400,
		width: 500,
		modal: true,
		closeOnEscape: false
	});

	$(document).on('click', '#register',function() {
		$('#user-registration').dialog('open');
	});
})


$(function() {
	// ユーザ新規登録
	$(document).on('click', '#user-register',function() {

		// ボタンの無効化
		$(".btn").prop('disabled', true);

		// リクエストヘッダーに、metaタグから取得した値を設定する
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});

		// フォーム要素を取得
		var form = $('#userRegisterForm');

		// 送信
		$.ajax({
			url: form.attr('action'),
			type: form.attr('method'),
			data: form.serialize(),
			timeout: 10000
		})
		// 通信成功時の処理
		.done(function(data) {
			$('#user-registration').html(data);
			if ($('.err')[0]) return;
			// 登録に成功した場合、一覧表示を更新
			showAllUser();
		})
		// 通信失敗時の処理
		.fail(function() {
			alert('エラー');
		})
		// 通信終了後の処理
		.always(function() {
			$(".btn").prop('disabled', false);
		});
	});

	// 登録ユーザ一覧表示
	function showAllUser() {
		// ボタンの無効化
		$(".btn").prop('disabled', true);
		// 送信
		$.ajax({
			url: '/admin/updateShowAllUser',
			type: 'GET',
			timeout: 10000
		})
		// 通信成功時の処理
		.done(function(data) {
			$('#resultTable').html(data);
			$('#user-registration').dialog('close');
			alert('完了');
		})
		// 通信失敗時の処理
		.fail(function() {
			alert('エラー');
		})
		// 通信終了後の処理
		.always(function() {
			$('#userRegisterForm').find(":text").val("");
			$(".btn").prop('disabled', false);
		});
	}
});
