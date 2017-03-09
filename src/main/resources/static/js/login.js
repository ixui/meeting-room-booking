$(function() {

  $('header a').click(function() {
    var id = $(this).attr('href');
    var position = $(id).offset().top;

    $('html, body').animate({'scrollTop': position}, 500);
  });


/*
  $("menu-icon").click(function() {
    $("header-menu").show();
  });
*/                       
    

    $(".menu-icon").on("click", function() {
      $(this).next().toggle();
    });
    
  
  
  
  
   /* $(".menu-close").on("click", function() {
      $(this).next().fadeout();
    });
  */
  
  
  
  
  
  

  $(" #login-show").click(function() {
    $("#login-modal").fadeIn();
  }); 

  //モーダルを閉じる
  $(".close-modal").click(function() {
    $("#login-modal").fadeOut();
  }); 

  
  

  
  
  
  




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





  