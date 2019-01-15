<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="/static/bootstrap-4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="/static/javascript/jquery-3.3.1.min.js" type="text/javascript"></script>
    <meta charset="UTF-8">
    <title>获取OTP验证码</title>
</head>
<body style="background-color: gainsboro">
    <div class="container" style="width: 600px;height: 820px;background-color: white">
        <h3 class="h3">获取OTP信息</h3>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">手机号</label>
            <div>
                <input id="telphone" name="telphone" type="text" class="form-control" placeholder="手机号">
            </div>
            <div>
                <button id="getotp" type="submit" class="btn btn-primary">获取OTP短信</button>
            </div>
        </div>
    </div>
</body>
<script src="/static/bootstrap-4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script>
    $(function () {
        $("#getotp").on("click",function () {
            var telphone=$("#telphone").val();
            if (telphone==""||telphone==null){
                return false;
            }
            $.ajax({
                type:"POST",
                contentType:"application/x-www-form-urlencoded",
                url:"http://localhost:8080/user/getotp",
                data:{"telphone":$("#telphone").val()},
                success:function (data) {
                    if(data.status=="success"){
                        alert("OTP验证码已发送，请注意查收短信");
                        //window.location.href="http://localhost:8080/user/regpage"
                    }else {
                        alert("OTP发送失败，原因是"+data.data.errorMsg);
                    }
                },
                error:function (data) {
                    alert("OTP发送失败，原因是"+data.responseText);
                }
            });
            return false;
        });
    })
</script>
</html>