//页面初始化调用
pageSetUp();
var pageTable = {
    dom: $('#dt_basic'),//table节点
    crateRow: function (row, data, index) {
        /* 设置表格中的内容居中 */
        if (data.handLine == "Y") {
            $(row).css({"color": "red"});
        }
    },
    ajaxUrl: $("#baseUrl").attr("data-url") + "/discover/discoverList",//ajax请求地址
    httpMethod: 'Get',//接口的请求方式方法分为get请求和post请求
    aoColumns: ["id", 'title', 'type', 'label', 'isUser', 'status', 'visits', 'updateDate'],//table要显示的列
    primaryKey: "id",
    diyColumn: [
        {
            aTargets: [1],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full, obj) {
                return obj.row + 1;
            }
        },
        {
            aTargets: [2],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full, obj) {
                return '<div id="a" style="width:100%;white-space: nowrap;height:23px;overflow: hidden;text-overflow: ellipsis;" >' + full.title + '</div>';
            }
        },
        {
            aTargets: [3],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full) {
                return full.type;
            }

        },
        {
            aTargets: [4],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full, obj) {
                var str = full.label == null ? "-" : full.label
                return '<div id="a" style="width:100%;white-space: nowrap;height:23px;overflow: hidden;text-overflow: ellipsis;" >' + str + '</div>';
            }
        },
        {
            /*状态转换*/
            aTargets: [6],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full) {
                var statusVal;
                switch (full.status) {
                    case 1:
                        statusVal = "待审核";
                        break;
                    case 2:
                        statusVal = "已通过";
                        break;
                    case 3:
                        statusVal = "未通过";
                        break;
                    default:
                        break;
                }
                return statusVal;
            }
        },
        {
            /*日前格式化*/
            aTargets: [8],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full) {
                var newDate = new Date(full.updateDate);
                return newDate.getFullYear() + "-" + ((newDate.getMonth() + 1) >= 10 ? (newDate.getMonth() + 1) : "0" + (newDate.getMonth() + 1)) + "-" + (newDate.getDate() >= 10 ? newDate.getDate() : "0" + newDate.getDate()) + " " + newDate.getHours() + ":" + (newDate.getMinutes() >= 10 ? newDate.getMinutes() : "0" + newDate.getMinutes());
            }
        },
        {
            /*操作按钮组*/
            aTargets: [9],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full, obj) {
                var url = $("#baseUrl").attr("data-url");
                var links = [];
                switch (full.status) {
                    case 1:
                        if ($("#baseUrl").attr("data-admin") == "admin") {

                            links.push("<a style='display: inline-block'  class='tableBtn hidden-mobile' href='" + url + "/discover/AddOrEdit?Id=" + full.id + "' data-toggle='modal' data-backdrop='static' data-target='#remoteModal'><img class='hidden-md hidden-sm hidden-xs' src='Content/Home/image/xiugai.png' />查看/审核</a>");
                        } else {

                            links.push("<a style='display: inline-block'  class='tableBtn hidden-mobile' href='" + url + "/discover/AddOrEdit?Id=" + full.id + "' data-toggle='modal' data-backdrop='static' data-target='#remoteModal'><img class='hidden-md hidden-sm hidden-xs' src='Content/Home/image/xiugai.png' />查看/编辑</a>");
                        }
                        break;
                    case 2:
                        links.push("<a style='display: inline-block'  class='tableBtn hidden-mobile' href='" + url + "/discover/AddOrEdit?Id=" + full.id + "' data-toggle='modal' data-backdrop='static' data-target='#remoteModal'><img class='hidden-md hidden-sm hidden-xs' src='Content/Home/image/xiugai.png' />查看/编辑</a>");
                        break;
                    case 3:
                        links.push("<a style='display: inline-block'  class='tableBtn hidden-mobile' href='" + url + "/discover/AddOrEdit?Id=" + full.id + "' data-toggle='modal' data-backdrop='static' data-target='#remoteModal'><img class='hidden-md hidden-sm hidden-xs' src='Content/Home/image/xiugai.png' />查看/编辑</a>");
                        break;
                    default:
                        break;
                }
                if (full.status == 2) {
                    if (full.isShow == 'Y') {
                        links.push("<a style='display: inline-block' class='tableBtn hidden-mobile' data-show=" + full.isShow + " onclick='isShow(this," + full.id + ")'  href='javascript:void(0);' ><img class='hidden-md hidden-sm hidden-xs showIcon' src='Content/Home/image/show.png' /><span class='isShowText'>已显示</span></a>");

                    } else {
                        links.push("<a style='display: inline-block'  class='tableBtn hidden-mobile' data-show=" + full.isShow + " onclick='isShow(this," + full.id + ")'  href='javascript:void(0);' ><img class='hidden-md hidden-sm hidden-xs showIcon' src='Content/Home/image/hidden.png' /><span class='isShowText'>已隐藏</span></a>");
                    }
                }
                return links.join(" ");
            }
        }

    ],
    ajxaParam: function () {

        return {
            Type: $('#discoverType option:selected').val() == "" ? $('#discoverType option:selected').val() : $('#discoverType option:selected').text(),
            Status: $("#discoverStatus option:selected").val(),
            Title: $('#discoverTitle').val(),
            IsUser: $('#discoverUser').val()
        };
    },
};

function isShow(that, id) {
    CommonBase.showLoading();
    $.ajax({
        type: "POST",
        data: {Id: id, IsShow: $(that).attr("data-show") == "Y" ? "N" : "Y"},
        url: $("#baseUrl").attr("data-url") + "/discover/DiscoverVisitor",
        success: function (data) {
            CommonBase.hideLoading();
            if (data.success) {
                $(that).attr("data-show", $(that).attr("data-show") == "Y" ? "N" : "Y");
                $(that).find('.isShowText').html($(that).attr("data-show") == "Y" ? "已显示" : "已隐藏");
                $(that).find('.showIcon').attr("src", $(that).attr("data-show") == "N" ? "Content/Home/image/hidden.png" : "Content/Home/image/show.png")
            } else {
                simpleNotify("操作失败");
            }
        }
    })
}

function getShenHeId(id, stuts) {
    if (stuts == 2) {
        $.confirm({
            msg: "是否确认审核通过？？",
            confirm: function () {
                $.ajax({
                    type: "POST",
                    data: {Id: id, Status: stuts},
                    url: $("#baseUrl").attr("data-url") + "/discover/AduitStatus",
                    success: function (data) {
                        if (data.success) {
                            window.location.reload();
                        } else {
                            simpleNotify("操作失败")
                        }
                    }
                })
            },
            cancel: function () {
                return false
            }
        })
    } else if (stuts == 3) {
        $.confirm({
            msg: "是否确认拒绝？",
            confirm: function () {
                $.ajax({
                    type: "POST",
                    data: {Id: id, Status: stuts},
                    url: $("#baseUrl").attr("data-url") + "/discover/AduitStatus",
                    success: function (data) {
                        if (data.success) {
                            window.location.reload();
                        } else {
                            simpleNotify("操作失败")
                        }
                    }
                })
            },
            cancel: function () {
                return false
            }
        })
    }

}

(function () {
    $("#btnSearch").click(function () {
        if (pageTable) {
            /* CommonBase.showLoading();*/
            pageTable.container.fnDraw()
        }
    });
    /*CommonBase.showLoading();*/
    window.initPageTab(pageTable);
    /*$('#dt_basic').on('page.dt',function(){
        CommonBase.showLoading();
    })*/
})()