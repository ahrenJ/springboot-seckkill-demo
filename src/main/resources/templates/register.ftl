<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="/static/bootstrap-4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="/static/javascript/jquery-3.3.1.min.js" type="text/javascript"></script>
    <meta charset="UTF-8">
    <title>注册</title>
</head>
<body style="background-color: gainsboro">
<div class="container" style="width: 600px;height: 820px;background-color: white">
    <h3>注册</h3>
    <form>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">手机号</label>
            <div class="col-sm-10">
                <input id="telphone" name="telphone" type="text" class="form-control" placeholder="手机号">
            </div>
        </div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">验证码</label>
            <div class="col-sm-10">
                <input id="otpCode" name="otpCode" type="text" class="form-control" placeholder="验证码">
            </div>
        </div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">用户昵称</label>
            <div class="col-sm-10">
                <input id="name" name="name" type="text" class="form-control" placeholder="用户昵称">
            </div>
        </div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">密码</label>
            <div class="col-sm-10">
                <input id="password" name="password" type="password" class="form-control" placeholder="密码">
            </div>
        </div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">性别</label>
            <div class="col-sm-10">
                <input id="gender" name="gender" type="text" class="form-control" placeholder="性别">
            </div>
        </div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">年龄</label>
            <div class="col-sm-10">
                <input id="age" name="age" type="text" class="form-control" placeholder="年龄">
            </div>
        </div>
        <div>
            <button id="register" type="button" class="btn btn-primary">提交</button>
        </div>
    </form>
</div>
</body>
<script src="/static/bootstrap-4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script>
    $(function () {
        $("#register").on("click",function () {
            var telphone=$("#telphone");
            var otpCode=$("#otpCode");
            var name=$("#name");
            var password=$("#password");
            var gender=$("#gender");
            var age=$("#age");
            if (telphone==null||telphone==""){
                alert("手机不能为空");
                return false;
            }
            if (name==null||name==""){
                alert("用户昵称不能为空");
                return false;
            }
            if (password==null||password==""){
                alert("密码不能为空");
                return false;
            }
            if (gender==null||gender==""){
                alert("性别不能为空");
                return false;
            }
            if (age==null||age==""){
                alert("年龄不能为空");
                return false;
            }
            $.ajax({
                type:"POST",
                contentType:"application/x-www-form-urlencoded",
                url:"http://localhost:8080/user/register",
                data:$("form").serialize(),
                xhrFields:{withCredentials:true},
                success:function(data) {
                        if (data.status=="success"){
                            alert("注册成功");
                        }else {
                            alert("注册失败，原因:"+data.data.errorMsg);
                        }
                },
                error:function(data){
                        alert("注册失败，原因:"+data.responseText);
                }
            });
            return false;
        });
    })
</script>
</html>