$(function() {

    $(".leftnav ul li .navbtn").click(function() {
        var flag = $(this).attr("flag");
        if(flag == "off") {
            $(".leftnav ul li .navbtn").find("span").text("+");
            $(".leftnav ul li .navbtn").removeClass("on").attr("flag", "off");
            $(".leftnav ul li .hidebtn").hide();
            $(this).find("span").text("-");
            $(this).addClass("on").attr("flag", "on");
            $(this).parent().find(".hidebtn").fadeIn(350);

        } else if(flag == "on") {
            $(".leftnav ul li .navbtn").find("span").text("+");
            $(this).removeClass("on").attr("flag", "off");
            $(this).parent().find(".hidebtn").fadeOut(350);
        }
        /*
        $(this).addClass("on");
        $(this).parent().find(".hidebtn").fadeIn(500);
        */
    });

    $(".outin").click(function(){
        if($(this).attr("flag") == 'off'){
            $(".logout").show();
            $(this).attr("flag","on");
        }
        else if($(this).attr("flag") == 'on'){
            $(".logout").hide();
            $(this).attr("flag","off");
        }
    })

    $(".outin .logout").click(function(){
        alert("注销成功");
        $.removeCookie('token');
        window.location.href = "/login"
    })
/*
    $(".hidebtn a").click(function(){
        if($(this).attr('tmp')==1){
            return;
        }
        $(".hidebtn a").css("color","#aeb2b7");
        $(this).css("color","#41cac0");
        var flag = $(this).attr("flag");
        $(".screen").empty();
        $(".screen").append("<iframe src="+flag+".html' scrolling='no' frameborder='0' width='100%' height='100%'></iframe>");
        $(".hidebtn a").attr("tmp","0");
        $(this).attr("tmp","1");
    });
*/



})