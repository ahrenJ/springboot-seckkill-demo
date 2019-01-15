<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="/static/bootstrap-4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="/static/javascript/jquery-3.3.1.min.js" type="text/javascript"></script>
    <meta charset="UTF-8">
    <title>创建商品</title>
</head>
<body style="background-color: gainsboro">
<div class="container" style="width: 600px;height: 820px;background-color: white">
    <h3>创建商品</h3>
    <form>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">商品名称</label>
            <div class="col-sm-10">
                <input id="title" name="title" type="text" class="form-control" placeholder="商品名称">
            </div>
        </div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">商品描述</label>
            <div class="col-sm-10">
                <input id="description" name="description" type="text" class="form-control" placeholder="商品描述">
            </div>
        </div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">价格</label>
            <div class="col-sm-10">
                <input id="price" name="price" type="text" class="form-control" placeholder="价格">
            </div>
        </div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">库存</label>
            <div class="col-sm-10">
                <input id="stock" name="stock" type="text" class="form-control" placeholder="库存">
            </div>
        </div>
        <div class="form-group row">
            <label for="colFormLabelSm" class="col-sm-2 col-form-label col-form-label-sm">图片</label>
            <div class="col-sm-10">
                <input id="imgUrl" name="imgUrl" type="text" class="form-control" placeholder="图片">
            </div>
        </div>
        <div>
            <button id="create" type="button" class="btn btn-primary">创建</button>
        </div>
    </form>
</div>
</body>
<script src="/static/bootstrap-4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script>
    $(function () {
        $("#create").on("click",function () {

            var title=$("#title");
            var description=$("#description");
            var price=$("#price");
            var stock=$("#stock");
            var imgUrl=$("#imgUrl");

            if (title==null||title==""){
                alert("商品名称不能为空");
                return false;
            }
            if (description==null||description==""){
                alert("商品描述不能为空");
                return false;
            }
            if (price==null||price==""){
                alert("价格不能为空");
                return false;
            }
            if (stock==null||stock==""){
                alert("库存不能为空");
                return false;
            }
            if (imgUrl==null||imgUrl==""){
                alert("图片地址不能为空");
                return false;
            }
            $.ajax({
                type:"POST",
                contentType:"application/x-www-form-urlencoded",
                url:"http://localhost:8080/item/create",
                data:$("form").serialize(),
                xhrFields:{withCredentials:true},
                success:function(data) {
                        if (data.status=="success"){
                            alert("创建成功");
                        }else {
                            alert("创建失败，原因:"+data.data.errorMsg);
                        }
                },
                error:function(data){
                        alert("创建失败，原因:"+data.responseText);
                }
            });
            return false;
        });
    })
</script>
</html>