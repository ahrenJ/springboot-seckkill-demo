<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="/static/bootstrap-4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="/static/javascript/jquery-3.3.1.min.js" type="text/javascript"></script>
    <meta charset="UTF-8">
    <title>用户登录</title>
</head>
<body style="background-color: gainsboro">
<div class="container" style="width: 600px;height: 820px;background-color: white">
    <h3>用户登录</h3>
    <form>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">手机号</label>
            <div class="col-sm-10">
                <input id="telphone" name="telphone" type="text" class="form-control" placeholder="手机号">
            </div>
        </div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">密码</label>
            <div class="col-sm-10">
                <input id="password" name="password" type="password" class="form-control" placeholder="密码">
            </div>
        </div>
        <div>
            <button id="login" type="button" class="btn btn-primary">登录</button>
        </div>
        <div>
            <button id="register" type="button" class="btn btn-light">注册</button>
        </div>
    </form>
</div>
</body>
<script src="/static/bootstrap-4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script>
    $(function () {
        $("#login").on("click",function () {
            var telphone=$("#telphone");
            var password=$("#password");
            if (telphone==null||telphone==""){
                alert("手机不能为空");
                return false;
            }
            if (password==null||password==""){
                alert("密码不能为空");
                return false;
            }
            $.ajax({
                type:"POST",
                contentType:"application/x-www-form-urlencoded",
                url:"http://localhost:8080/user/login",
                data:$("form").serialize(),
                xhrFields:{withCredentials:true},
                success:function(data) {
                        if (data.status=="success"){
                            alert("登录成功,将跳转到商品列表");
                            window.location.href="http://localhost:8080/item/gotoitemlist";
                        }else {
                            alert("登录失败，原因:"+data.data.errorMsg);
                        }
                },
                error:function(data){
                        alert("登录失败，原因:"+data.responseText);
                }
            });
            return false;
        });

        $("#register").on("click",function () {
           window.location.href="http://localhost:8080/user/gotogetotp"
        });
    })
</script>
</html>