$(function() {


	
  
  setTimeout(function(){
    $('#empNum').focus();
  }, 0);







  $('header a').click(function() {
    var id = $(this).attr('href');
    var position = $(id).offset().top;

    $('html, body').animate({'scrollTop': position}, 500);
  });




    $(".menu-icon").on("click", function() {
      $(this).next().toggle();
    });
    
  
  
  
  
  
  
 
 
 /*---------モーダルの表示、非表示--------------*/ 

  $(" #login-show").click(function() {
    $("#login-modal").fadeIn();
  }); 

  
  
  $(".close-modal").click(function() {
    $("#login-modal").fadeOut();
  }); 

  
  
  
  
  
  
  
  
	
    /*
 
	$(".login-modal-wrapper").click(function() { 
		$("#login-modal").fadeOut();
		
	});

*/






$(".login-modal-wrapper").on('click touchend', function(event) {
	if (!$(event.target).closest('.modal1').length) {
		$("#login-modal").fadeOut();
	}
});

  
  
  
  
  
  
  
	
    
 /*
  $(document).click(function(event) { 
  	if ($('#login-modal').is(':visible')) {
    	if (!$.contains($("#login-modal")[0], event.target)&& $('#login-modal').is(':visible')) { 
        	$("#login-modal").hide();
    	}
    
  });
*/

  
 /* -------------------------------------------*/ 

  
  
  
  




  /*お問い合わせフォーム部分*/
    //$('.contact-form  > form').submit(function() {
    $('#sendMail').click(function() {
      var isAllInupt = true;

      $('.contact-form').find('input, textarea').each(function() {
        if ($(this).val() == '') {
          $(this).prev('.error-message').text('入力してください');
          isAllInupt = false;
        }	else {
          $(this).prev('.error-message').text('');
        }
      });
      
      /*alert($("#subject").val());
      alert($("#message").val()); */
      
      if (isAllInupt) {
        //$('.contact-form').html('<h4>お問い合わせありがとうございます。</h4>');
        var title = $("#subject").val();
        var section = $("#message").val();
        
        $('#sendMail').attr('href','mailto:tamura-shunsuke@ixui.co.jp?subject=' + title + '&body=' + section);
      }	else {
        return false;
      }
    });



  /*スライダー*/

    $('.carousel').carousel({
      interval: 2500
    });

  var pagetop = $('.page-top');
  $(window).scroll(function () {
    if ($(this).scrollTop() > 700) {
      pagetop.fadeIn();
    } else {
      pagetop.fadeOut();
    }
  });
  pagetop.click(function () {
    $('body, html').animate({ scrollTop: 0 }, 500);
    return false;
  });
});





  