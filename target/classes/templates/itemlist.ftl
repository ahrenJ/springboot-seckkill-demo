<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="/static/bootstrap-4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="/static/javascript/jquery-3.3.1.min.js" type="text/javascript"></script>
    <meta charset="UTF-8">
    <title>商品列表</title>
</head>
<body style="background-color: gainsboro">
<div class="container" style="width: 900px;height: 820px;background-color: white">
    <div class="table-responsive">
        <h3>商品列表</h3>
        <table class="table">
            <thead>
                <tr>
                    <th>商品名称</th>
                    <th>商品图片</th>
                    <th>商品描述</th>
                    <th>商品价格</th>
                    <th>商品库存</th>
                    <th>商品销量</th>
                </tr>
            </thead>
            <tbody id="items-list">

            </tbody>
        </table>
    </div>
</div>
</body>
<script src="/static/bootstrap-4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script>
    $(function () {
        var items=[];
            $.ajax({
                type:"GET",
                contentType:"application/x-www-form-urlencoded",
                url:"http://localhost:8080/item/list",
                xhrFields:{withCredentials:true},
                success:function(data) {
                        if (data.status=="success"){
                            items=data.data;
                            reloadDOM();
                        }else {
                            alert("获取商品信息失败，原因:"+data.data.errorMsg);
                        }
                },
                error:function(data){
                        alert("获取商品信息失败，原因:"+data.responseText);
                }
            });

            function reloadDOM() {
                for (var i=0;i<items.length;i++){
                    var item=items[i];
                    var dom="<tr data-id='"+item.id+"' id='item-detail-"+item.id+"'>" +
                                "<td>"+item.title+"</td>" +
                                "<td><img style='width:100px;height:auto;' src='"+item.imgUrl+"' ></td>" +
                                "<td>"+item.description+"</td>"+
                                "<td>"+item.price+"</td>"+
                                "<td>"+item.stock+"</td>"+
                                "<td>"+item.sales+"</td>"+
                            "</tr>";
                    $("#items-list").append($(dom));

                    $("#item-detail-"+item.id).on("click",function (e) {
                       window.location.href="http://localhost:8080/static/public/itemdetail.html?id="+$(this).data("id");
                    });
                }
            }
    })
</script>
</html>