$(function() {

	$(".button").click(function() {
			var username = $("input[type='text']").val();
			if(username === "") {
				alert("请输入用户名");
				return;
			}

			var password = $("input[type='password']").val();
			if(password === "") {
				alert("请输入密码");
				return;
			}

        	$.post("/web/user/login", {username: username, password: password}, function(res){
				if(res.code!==10000) {
					alert(res.msg);
				} else if(res.code === 10000) {
                    alert("登陆成功");
					console.log(res);
					var token = res.data.token;
                    $.cookie('token', token);
                    window.location.href ="list1"

				}
        	})
		});

		$(document).keyup(function(event) {
			if(event.keyCode === 13) {
                var username = $("input[type='text']").val();
                if(username === "") {
                    alert("请输入用户名");
                    return;
                }

                var password = $("input[type='password']").val();
                if(password === "") {
                    alert("请输入密码");
                    return;
                }

                $.post("/web/user/login", { username:  username, password: password},function(res){
                    if(res.code!==10000)
                    {
                        alert(res.msg);
                    }
                    else if(res.code===10000)
                    {
                        alert("登陆成功");
                        console.log(res);
                        var token = res.data.token;
                        $.cookie('token', token);
                        window.location.href ="list1"
                    }

                })
			}
		});

});