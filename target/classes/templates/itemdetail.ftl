<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="/static/bootstrap-4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="/static/javascript/jquery-3.3.1.min.js" type="text/javascript"></script>
    <meta charset="UTF-8">
    <title>商品详情</title>
</head>
<body>
<div class="container" style="width: 550px">
    <h3>商品详情</h3>
    <div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">商品名称</label>
            <div class="col-sm-10">
                <label id="title" name="title" class="custom-control-label">
            </div>
        </div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">商品描述</label>
            <div class="col-sm-10">
                <label id="description" name="description" class="custom-control-label">
            </div>
        </div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">价格</label>
            <div class="col-sm-10">
                <label id="price" name="price" class="custom-control-label">
            </div>
        </div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">销量</label>
            <div class="col-sm-10">
                <label id="sales" name="sales" class="custom-control-label">
            </div>
        </div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">库存</label>
            <div class="col-sm-10">
                <label id="stock" name="stock" class="custom-control-label">
            </div>
        </div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">图片</label>
            <div class="col-sm-10">
                <img style="width:200px;height: auto" id="imgUrl">
            </div>
        </div>
    </div>
</div>
</body>
<script src="/static/bootstrap-4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script>
    $(function () {
            var itemVo={}
            $.ajax({
                type:"POST",
                contentType:"application/x-www-form-urlencoded",
                url:"http://localhost:8080/item/create",
                data:{"id":getParam("id")}
                xhrFields:{withCredentials:true},
                success:function(data) {
                        if (data.status=="success"){
                            itemVo=data.data;
                        }else {
                            alert("获取商品详情失败，原因:"+data.data.errorMsg);
                        }
                },
                error:function(data){
                        alert("获取商品详情失败，原因:"+data.responseText);
                }
            });
            
            function getParam(param) {
                var reg=new RegExp("(^|&)"+param+"=([^&]*)(&|$)");
                var r=window.location.search.substr(1).match(reg);
                if(r!=null){
                    return decodeURI(r);
                }
                return null;
            }
    })
</script>
</html>