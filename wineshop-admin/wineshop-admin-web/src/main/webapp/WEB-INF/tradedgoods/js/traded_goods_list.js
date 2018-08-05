var img;
//页面初始化调用
pageSetUp();
/*render主表格*/
var pageTable = {
    dom: $('#dt_basic'),//table节点
    ajaxUrl: $("#baseUrl").attr("data-url") + "/tradedGoods/listTradedGoods",//ajax请求地址
    httpMethod: 'Get',//接口的请求方式方法分为get请求和post请求
    aoColumns: ["id", 'imageUrl', 'goodsName', 'brandName', 'goodsPrice', 'counts', 'goodsStatus'],//table要显示的列
    primaryKey: "id",
    bProcessing: false,
    diyColumn: [
        {
            aTargets: [1],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full, obj) {
                return obj.row + 1;
            }
        },
        {
            /*图片*/
            aTargets: [2],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full) {
                return "<img src='" + full.imageUrl + "' style='width: 100px;height: 100px'/>";
            }
        },
        {
            /*状态*/
            aTargets: [6],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full) {
                return full.counts == undefined ? 0 : full.counts
            }
        },
        {
            /*状态*/
            aTargets: [7],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full) {
                return full.goodsStatus == "1" ? "上架" : "下架"
            }
        },
        {
            /*操作按钮组*/
            aTargets: [8],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full) {
                var links = [];
                links.push("<a class='tableBtn hidden-mobile' href='javascript:void(0);' onclick='initForm(" + full.id + ")' data-toggle='modal'  data-backdrop='static' data-target='#remoteModal'><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/edit.png' />查看/编辑</a>");
                if (full.goodsStatus == 1) {
                    links.push("<a class='tableBtn hidden-mobile' href='javascript:void(0);' onclick='changeStatus(" + full.id + ",this)' data-status='" + full.goodsStatus + "' ><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/down.png' /><span class='statusText'>下架</span></a>");
                } else if (full.goodsStatus == 0) {
                    links.push("<a class='tableBtn hidden-mobile' href='javascript:void(0);' onclick='changeStatus(" + full.id + ",this)' data-status='" + full.goodsStatus + "' ><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/upup.png' /><span class='statusText'>上架</span></a>");
                }
                links.push("<a class='tableBtn hidden-mobile' href='javascript:void(0);' onclick='changeStatus(" + full.id + ",this)' data-status='9' ><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/del.png' />删除</a>");
                links.push("<a class='tableBtn hidden-mobile' href='javascript:void(0);    ' onclick='getCount(" + full.id + ")' data-toggle='modal' data-backdrop='static' data-target='#countModal'><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/jilu.png' />查看兑换记录</a>");

                return links.join(" ");
            }
        }

    ],
    ajxaParam: function () {
        return {
            goodsName: $("#productName").val().trim(),
            brandName: $("#brande").val().trim(),
            goodsStatus: $("#productStatus option:selected").val() == "全部" ? null : $("#productStatus option:selected").val()
        };
    },
    fnFooterCallback: function () {
        CommonBase.hideLoading();
    }
};

/*上传图片方法*/
function imgUpLoad(_this) {
    var that = _this;
    var maxsize = 2 * 1024 * 1024;//2M
    var img = event.target.files[0];
    if (!img) return false;
    if (!img.type.indexOf('image') == 0 && img.type && /\.(?:jpg|png|gif)$/.test(img.name)) return false
    if (img.size > maxsize) {
        simpleNotify('图片上传最大不能超过2M');
        return false;
    } else {
        var reader = new FileReader()
        reader.readAsDataURL(img);
        reader.onload = function (e) {
            $.ajax({
                url: $("#baseUrl").attr("data-img-url"),
                type: 'POST',
                data: {img: e.target.result, folfer: "mobile"},
                success: function (data) {
                    if (data.IsSucceed == true) {
                        $(that).find("img").attr("src", data.Data);
                        img = data.Data;
                    }
                },
                error: function (data) {
                    console.log(data.message)
                }
            })
        }

    }
}

/*初始化详情页*/
function initForm(id) {
    if (id != undefined) {
        $(".titleType").text("修改");
        $.ajax({
            type: "Get",
            url: $("#baseUrl").attr("data-url") + "/tradedGoods/getTradedGoods",
            data: {tradedGoodsId: id},
            success: function (data) {
                if (data.success) {
                    $("#addTitle").val(data.data.goodsName);
                    $("#addBrand").val(data.data.brandName);
                    $("#addGoodsPrice").val(data.data.goodsPrice);
                    $("#coverImg").find("img").attr("src", data.data.imageUrl);
                    $("#addSubmit").attr("data-id", id);
                }
            }
        })
    } else {
        $(".titleType").text("新增");
        $("#addTitle").val('');
        $("#addBrand").val('');
        $("#addGoodsPrice").val('');
        $("#coverImg").find("img").attr("src", "Content/themes/smart/img/loadImg.png");
        $("#addSubmit").attr("data-id", '');
    }
}

/*提交商品*/
function submitGoods(id) {
    var reg = /^\+?[1-9]\d{0,7}$/;
    if ($("#addTitle").val() == "") {
        simpleNotify("商品名不能为空");
        return false;
    }
    if ($("#addBrand").val() == "") {
        simpleNotify("品牌不能为空")
        return false;
    }
    if (!reg.test($("#addGoodsPrice").val())) {
        simpleNotify("酒币不能为空，且只能为大于0整数，最多为8位")
        return false;
    }
    if ($("#coverImg").find("img").attr("src") == "Content/themes/smart/img/loadImg.png") {
        simpleNotify("必须选择图片")
        return false;
    }
    var obj = {
        tradedGoodsId: id ? id : null,
        imageUrl: $("#coverImg").find("img").attr("src"),
        goodsName: $("#addTitle").val(),
        brandName: $("#addBrand").val(),
        goodsPrice: parseInt($("#addGoodsPrice").val())
    }
    $.ajax({
        type: "POST",
        url: $("#baseUrl").attr("data-url") + "/tradedGoods/saveEditTradedGoods",
        data: obj,
        success: function (data) {
            if (data.success) {
                id != null ? simpleNotify("修改成功") : simpleNotify("添加成功");
                window.location.reload();
            } else {
                id != null ? simpleNotify("修改失败") : simpleNotify("添加成功")
            }
        }
    })
}

/*改变状态*/
function changeStatus(id, _this) {
    var status = Number($(_this).attr("data-status"));
    var text = "删除";
    if (status == 0) {
        CommonBase.showLoading();
        status = 1;
        text = "上架";
        change(id, status, text, _this)
    } else if (status == 1) {
        CommonBase.showLoading();
        status = 0;
        text = "下架";
        change(id, status, text, _this)
    } else if (status == 9) {
        $.confirm({
            msg: "是否确认删除？",
            confirm: function () {
                change(id, status, text, _this)
            },
            cancel: function () {
                return false;
            }
        });
    }
}

/*改变的方法*/
function change(id, status, text, _this) {
    $.ajax({
        type: "POST",
        url: $("#baseUrl").attr("data-url") + "/tradedGoods/updateStatusTradedGoods",
        data: {tradedGoodsId: id, goodsStatus: status},
        success: function (data) {
            if (data.success) {
                var str = '';
                $(_this).parent().prev().text(text);
                $(_this).attr("data-status", status);
                if (status == 1) {
                    str = "<img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/down.png' /><span class='statusText'>下架</span>"
                } else if (status == 0) {
                    str = "<img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/upup.png' /><span class='statusText'>上架</span>"
                }
                $(_this).empty().append(str);
                if (status == 9) {
                    $(_this).parent().parent().remove();
                    pageTable.container.fnDraw();
                }
            }
            CommonBase.hideLoading();
        }
    })
}

/*获取兑换记录*/
function getCount(id) {
    initPageTab({
        dom: $('#dt_count'),//table节点
        ajaxUrl: $("#baseUrl").attr("data-url") + "/tradedGoods/listTradedGoodsGoodsCashRecord",//ajax请求地址
        httpMethod: 'Get',//接口的请求方式方法分为get请求和post请求
        aoColumns: ['orderNumber', 'vendorInfoName', 'counts', 'userInfoName', 'createDate', 'status'],//table要显示的列
        primaryKey: "id",
        autoWidth: false,
        diyColumn: [
            {
                aTargets: [4],//要显示的位置
                mData: "id",//主键
                mRender: function (data, type, full, obj) {
                    return "<p>" + full.userInfoName + " " + full.phone + "</p><p>" + full.address + "</p>";
                }
            },
            {
                aTargets: [5],//要显示的位置
                mData: "id",//主键
                mRender: function (data, type, full, obj) {
                    return full.createDate.split(" ").join("<br/>")
                }
            },
            {
                aTargets: [6],//要显示的位置
                mData: "id",//主键
                mRender: function (data, type, full, obj) {
                    return full.status == 0 ? '未发货' : '已发货'
                }
            }

        ],
        ajxaParam: function () {
            return {
                tradedGoodsId: id
            };
        }
    })
    /*if(id){
        $.ajax({
            url:$("#baseUrl").attr("data-url")+"/tradedGoods/listTradedGoodsGoodsCashRecord",
            type:"Get",
            data:{tradedGoodsId:id,
                pageSize:10,
                pageIndex:1
            },
            success:function(data){
                var str ="";
                if(data.success){
                    if(data.data ==null){
                        $("#dt_count").find("tbody").empty().append("<tr><td colspan='6' style='text-align: center'>暂无数据</td></tr>")
                    }else{
                        $("#dt_count").find("tbody").empty();
                        if(data.data.length){
                            for(var i=0;i<data.data.length;i++){
                                str +="<tr><td>"+data.data[i].orderNumber+"</td>"
                                str +="<td>"+data.data[i].vendorInfoName+"</td>"
                                str +="<td>"+data.data[i].counts+"</td>"
                                str +="<td>"+data.data[i].userInfoName+" "+data.data[i].phone+"<br/>"+data.data[i].address+"</td>"
                                str +="<td>"+data.data[i].createDate+"</td>"
                                str +="<td>"+(data.data[i].status==1?'未发货':'已发货')+"</td></tr>"
                            }
                            $("#dt_count").find("tbody").append(str);
                        }
                    }
                }
            }
        })
    }*/
}

(function () {
    var url = $("#baseUrl").attr("data-url");
    $("#btnSearch").click(function () {
        if (pageTable) {
            CommonBase.showLoading();
            pageTable.container.fnDraw();
        }
    });
    CommonBase.showLoading()
    window.initPageTab(pageTable);
    $('#dt_basic').on('page.dt', function () {
        CommonBase.showLoading();
    })
    /*新增上传图片*/
    $("#coverImg").on("change", function () {
        imgUpLoad(this);
    });
    /*新增保存*/
    $("#addSubmit").on("click", function () {
        var id = $("#addSubmit").attr("data-id");
        id == '' ? submitGoods() : submitGoods(id);
    });

})()