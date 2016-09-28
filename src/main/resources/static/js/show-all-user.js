// フォームの表示
$(function() {
	// 新規登録フォーム
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

	// 更新フォーム
	$('#user-update').dialog({
		autoOpen: false,
		height: 350,
		width: 500,
		modal: true,
		closeOnEscape: false
	});

	$(document).on('click', '.select',function() {

		var auth = $(this).parent().prev();
		var name = auth.prev().text();
		var empNo = auth.prev().prev().text();
		if (auth.text() == "ROLE_USER") {
			$('#auth3').attr('checked', 'checked');
		} else {
			$('#auth4').attr('checked', 'checked');
		}
		$('#updateName').attr('value', name);
		$('#updateEmpNo').attr('value', empNo);

		$('#user-update').dialog('open');
	});
})


// Ajax通信
$(function() {

	// リクエストヘッダーに、metaタグから取得した値を設定する
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});

	// ユーザ新規登録
	$(document).on('click', '#user-register',function() {

		// ボタンの無効化
		$(".btn").prop('disabled', true);

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
			$('#userRegisterForm').find(":text").val("");
			$('#user-registration').dialog('close');
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

	// ユーザ情報更新
	$(document).on('click', '#update',function() {

		// ボタンの無効化
		$(".btn").prop('disabled', true);

		// フォーム要素を取得
		var form = $('#userUpdateForm');

		// 送信
		$.ajax({
			url: form.attr('action'),
			type: form.attr('method'),
			data: form.serialize(),
			timeout: 10000
		})
		// 通信成功時の処理
		.done(function(data) {
			$('#user-update').html(data);
			if ($('.err')[0]) return;
			// 登録に成功した場合、一覧表示を更新
			showAllUser();
			$('#userUpdateForm').find(":text").val("");
			$('#user-update').dialog('close');
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
			alert('完了');
		})
		// 通信失敗時の処理
		.fail(function() {
			alert('エラー');
		})
		// 通信終了後の処理
		.always(function() {
			$(".btn").prop('disabled', false);
		});
	}
});
