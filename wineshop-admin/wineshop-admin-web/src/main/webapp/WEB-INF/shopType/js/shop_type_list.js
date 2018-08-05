var treeNode;
var setting = {
    view: {
        dblClickExpand: false,
        showLine: false
    },
    check: {
        enable: true,
        chkStyle: "radio",
        radioType: "all"
    },
    data: {
        key: {
            name: "text"
        },
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeExpand: beforeExpand,
        onExpand: onExpand,
        onCheck: zTreeOnCheck
    }
};
var zTreeNodes;
var curExpandNode = null;

var pageTable;
var pageTables;
$(function () {
    pageSetUp();
    var dataUrl = $("#dataURL").val() + "/shopType/listByAttributeShopType";
    $.get(dataUrl, function (res) {
        treeNode = res.data;
        zTreeNodes = $.fn.zTree.init($("#treeAttribute"), setting, treeNode);
        var treeObj = $.fn.zTree.getZTreeObj("treeAttribute");

        var sNodes = treeObj.getCheckedNodes(true);
        if (sNodes.length > 0) {
            listDictionaryData(sNodes[0].id);
            $("#attribute_name").html(sNodes[0].text);
        }
    })


    $("#updateAttribute").click(function () {
        $("#dictionary_list_add").text("修改匹配类型");
        $('#addInfoDictionary').modal();
        $("#dictionary_name").val("")
        listDictionary();
        CommonBase.hideLoading();
        $("#selectDictionary").unbind("click");
        $("#selectDictionary").bind("click", selectDictionaryClick);
        $("#addDictionarySubmit").unbind("click");
        $("#addDictionarySubmit").bind("click", addDictionarySubmitClick);
    });
});
var selectDictionaryClick = function () {
    listDictionary();
    CommonBase.hideLoading();
}
var addDictionarySubmitClick = function () {
    var id = $("input:radio[name='dictionary_id']:checked").attr("id");
    if (id == null || id == "") {
        simpleNotify("请选择一条数据")
        return false;
    }
    var treeObj = $.fn.zTree.getZTreeObj("treeAttribute");
    var sNodes = treeObj.getCheckedNodes(true);
    if (sNodes.length < 1) {
        simpleNotify("请选择一条属性");
        return false;
    }
    CommonBase.hideLoading();
    var params = {
        dictionaryId: parseInt(id),
        goodsAttributeId: parseInt(sNodes[0].id),
    };
    CommonBase.showLoading();
    $.post($("#baseUrl").attr("data-url") + "/shopType/updateByDictionaryAttribute", params, function (res) {
        CommonBase.hideLoading();
        if (res.success) {
            listDictionaryData(sNodes[0].id);
            $("#attribute_name").html(sNodes[0].text);
            $("#addInfoDictionary").modal('hide');
            simpleNotify("修改字典成功。");
        } else {
            simpleNotify(res.message);
        }
    })
}

/**** 保持展开单一路径 start***/
function beforeExpand(treeId, treeNode) {
    var pNode = curExpandNode ? curExpandNode.getParentNode() : null;
    var treeNodeP = treeNode.parentTId ? treeNode.getParentNode() : null;
    var zTree = $.fn.zTree.getZTreeObj("treeAttribute");
    for (var i = 0, l = !treeNodeP ? 0 : treeNodeP.children.length; i < l; i++) {
        if (treeNode !== treeNodeP.children[i]) {
            zTree.expandNode(treeNodeP.children[i], false);
        }
    }
    while (pNode) {
        if (pNode === treeNode) {
            break;
        }
        pNode = pNode.getParentNode();
    }
    if (!pNode) {
        singlePath(treeNode);
    }

}

function singlePath(newNode) {
    if (newNode === curExpandNode) return;

    var zTree = $.fn.zTree.getZTreeObj("treeAttribute"),
        rootNodes, tmpRoot, tmpTId, i, j, n;

    if (!curExpandNode) {
        tmpRoot = newNode;
        while (tmpRoot) {
            tmpTId = tmpRoot.tId;
            tmpRoot = tmpRoot.getParentNode();
        }
        rootNodes = zTree.getNodes();
        for (i = 0, j = rootNodes.length; i < j; i++) {
            n = rootNodes[i];
            if (n.tId != tmpTId) {
                zTree.expandNode(n, false);
            }
        }
    } else if (curExpandNode && curExpandNode.open) {
        if (newNode.parentTId === curExpandNode.parentTId) {
            zTree.expandNode(curExpandNode, false);
        } else {
            var newParents = [];
            while (newNode) {
                newNode = newNode.getParentNode();
                if (newNode === curExpandNode) {
                    newParents = null;
                    break;
                } else if (newNode) {
                    newParents.push(newNode);
                }
            }
            if (newParents != null) {
                var oldNode = curExpandNode;
                var oldParents = [];
                while (oldNode) {
                    oldNode = oldNode.getParentNode();
                    if (oldNode) {
                        oldParents.push(oldNode);
                    }
                }
                if (newParents.length > 0) {
                    zTree.expandNode(oldParents[Math.abs(oldParents.length - newParents.length) - 1], false);
                } else {
                    zTree.expandNode(oldParents[oldParents.length - 1], false);
                }
            }
        }
    }
    curExpandNode = newNode;
}

function onExpand(event, treeId, treeNode) {
    curExpandNode = treeNode;
}

/**** 保持展开单一路径 end ***/

/**** 事件 start***/
function zTreeOnCheck(event, treeId, treeNode) {
    listDictionaryData(treeNode.id);
    $("#attribute_name").html(treeNode.text);
};


function listDictionaryData(goodsAttributeId) {
    CommonBase.showLoading();
    $.get($("#dataURL").val() + "/shopType/listAttrebuteName", {goodsAttributeId: goodsAttributeId}, function (res) {
        if (res.data != null) {
            $("#shop_type_name").attr("data-id", res.data.id);
            $("#shop_type_name").html(res.data.dictionaryName);
        } else {
            $("#shop_type_name").attr("data-id", "");
            $("#shop_type_name").html("");
        }
        CommonBase.hideLoading();
    })
    pageTable = {
        dom: $('#dictionaryDetailTable'),//table节点
        ajaxUrl: $("#dataURL").val() + "/shopType/listShopTypeAttrebute",//ajax请求地址
        httpMethod: 'get',//接口的请求方式方法分为get请求和post请求
        aoColumns: ["sort", "dictionaryDataName", "updateDate",],//table要显示的列
        primaryKey: "id",//主键
        diyColumn: [     //自定义列
            {
                aTargets: [1],//要显示的位置
                mData: "sort",//主键
                mRender: function (data, type, full) {//返回参数
                    return full.sort == undefined ? "" : full.sort;
                },
                "bSortable": false
            },
            {
                aTargets: [2],//要显示的位置
                mData: "dictionaryDataName",//
                mRender: function (data, type, full) {
                    return full.dictionaryDataName;
                },
            },
            {
                aTargets: [3],//要显示的位置
                mData: "updateDate",//
                mRender: function (data, type, full) {
                    return getMyDate(full.createDate)
                },
            },
        ],
        ajxaParam: function () {
            return {
                goodsAttributeId: parseInt(goodsAttributeId),
            };
        },
    }
    window.initPageTab(pageTable);
}

function listDictionary() {
    CommonBase.showLoading();
    pageTables = {
        dom: $('#updateDictionaryDetailTable'),//table节点
        ajaxUrl: $("#dataURL").val() + "/dictionary/listPageDictionaryInfo",//ajax请求地址
        httpMethod: 'get',//接口的请求方式方法分为get请求和post请求
        aoColumns: ["id", "dictionaryName", "updateDate",],//table要显示的列
        primaryKey: "id",//主键
        diyColumn: [     //自定义列
            {
                aTargets: [1],//要显示的位置
                mData: "id",//主键
                mRender: function (data, type, full) {//返回参数
                    $(".checkchilds").removeAttr("checked");
                    return "<input type='radio'  class='checkchild' style='text-align: center;' name='dictionary_id' onclick='checkChild(this)' id='" + full.id + "' />";
                    ;
                },
                "bSortable": false
            },
            {
                aTargets: [2],//要显示的位置
                mData: "dictionaryName",//
                mRender: function (data, type, full) {
                    return full.dictionaryName;
                },
            },
            {
                aTargets: [3],//要显示的位置
                mData: "updateDate",//
                mRender: function (data, type, full) {
                    return getMyDates(full.updateDate)
                },
            },
        ],
        ajxaParam: function () {
            return {
                dictionaryName: $("#dictionary_name").val(),
            };
        },
    }
    window.initPageTab(pageTables);
}

function getMyDate(str) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay) + ' ' + getzf(oHour) + ':' + getzf(oMin);//最后拼接时间
    return oTime;
};

function getMyDates(str) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay);//最后拼接时间
    return oTime;
};

//补0操作
function getzf(num) {
    if (parseInt(num) < 10) {
        num = '0' + num;
    }
    return num;
}

function checkChild(obj) {
    $(".checkchild").prop("checked", false);
    $(obj).prop("checked", true);
}