var pageTable;
var vue;
$(function () {
    vue = new Vue({
        el: '#dictionary',
        data: {
            message: '',
        },
        methods: {
            addDictionary: function () {
                $("#dictionary_list_add").text("添加基础类型");
                $('#addInfoDictionary').modal();
                $("#dictionary_name").val("");
                $("#addDictionarySubmit").unbind("click");
                $("#addDictionarySubmit").bind("click", dictionaryNameClick);
            },
            deleteDictionary: function () {
                $.confirm({
                    msg: "是否确认删除？",
                    confirm: function () {
                        var id = $("#dictionary_list td[class='info']").attr("id");
                        $.post($("#baseUrl").attr("data-url") + "/dictionary/removeDictionary", {id: id}, function (res) {
                            CommonBase.hideLoading();
                            if (res.success) {
                                listDictionary();
                                simpleNotify("删除类型成功。");
                            } else {
                                simpleNotify(res.message);
                            }
                        })
                    },
                    cancel: function () {
                        return false;
                    }
                });
            },
            selectDictionary: function (event) {
                event = event || window.event;
                var target = event.srcElement || event.target;
                $("#dictionary_list td").removeClass("info");
                $(target).addClass("info");
                $("#dictionary_data #exactValue").val("");
                var dictionaryId = $("#dictionary_list td[class='info']").attr("id");
                listDictionaryData(dictionaryId);
            }
        }
    });
    listDictionary();
    pageSetUp();

});
var deleteDictionaryDataClick = function () {
    $.confirm({
        msg: "是否确认删除？",
        confirm: function () {
            var id = $("input:radio[name='dictionary_id']:checked").attr("id");
            if (id == null || id == "") {
                simpleNotify("请选择一条数据")
                return false;
            }
            var dictionaryId = $("#dictionary_list td[class='info']").attr("id");
            $.post($("#baseUrl").attr("data-url") + "/dictionary/removeDictionaryData", {
                dictionaryId: dictionaryId,
                id: parseInt(id)
            }, function (res) {
                CommonBase.hideLoading();
                if (res.success) {
                    var dictionaryId = $("#dictionary_list td[class='info']").attr("id");
                    listDictionaryData(dictionaryId);
                    simpleNotify("删除类型成功。");
                } else {
                    simpleNotify(res.message);
                }
            })
        },
        cancel: function () {
            return false;
        }
    });

}
var addDictionaryDataClick = function () {
    $("#add_dictionnay_data_lable").text("添加字典详情");
    $('#add_dictionnay_data').modal();
    var dictionaryName = $("#dictionary_list td[class='info']").attr("data-name");
    $("#dictionary_data_type").html(dictionaryName);
    $("#dictionary_data_value_2").addClass("active");
    $("#dictionary_data_value_1").removeClass("active");
    $(".inputDiv input").val("");
    $(".checkbox-inline input:radio[name='optionsRadiosinline']").eq(0).attr("checked", "checked");
    $(".checkbox-inline input:radio[name='optionsRadiosinline']").eq(1).removeAttr("checked");
    $(".checkbox-inline input:radio[name='optionsRadiosinline']").eq(0).click();
    addClick();
    $("#addDictionaryDataSubmit").unbind("click");
    $("#addDictionaryDataSubmit").bind("click", addDictionaryDataSubmitClick);
}
var addDictionaryDataSubmitClick = function () {
    var dictionaryId = $("#dictionary_list td[class='info']").attr("id");
    var dictionaryCode = $("#dictionary_list td[class='info']").attr("data-code");
    var exactValue = "";
    var intervalValue = "";
    var sort;
    var type = $(".checkbox-inline input:radio[name='optionsRadiosinline']:checked").attr("data-vlaue");
    type = parseInt(type);
    switch (type) {
        case 1:
            exactValue = $("#dictionary_data_exactValue").val();
            break;
        case 2:
            exactValue = $("#dictionary_data_activeinfo_exactValue").val();
            intervalValue = $("#dictionary_data_activeinfo_intervalValue").val();
            break;
    }
    if (exactValue == null || exactValue == "") {
        simpleNotify("请输入字典详情")
        return false;
    }
    if ((intervalValue == null || intervalValue == "") && type == 2) {
        simpleNotify("请完整输入字典详情")
        return false;
    }
    sort = $("#dictionary_data_sort").val();
    if (sort == null || sort == "") {
        simpleNotify("请输入显示顺序")
        return false;
    }
    var params = {
        dictionaryId: parseInt(dictionaryId),
        type: type,
        dictionaryCode: dictionaryCode,
        exactValue: exactValue,
        intervalValue: intervalValue,
        sort: parseInt(sort),
    };
    CommonBase.showLoading();
    $.post($("#baseUrl").attr("data-url") + "/dictionary/saveDictionaryData", params, function (res) {
        CommonBase.hideLoading();
        if (res.success) {
            listDictionaryData(dictionaryId);
            $("#add_dictionnay_data").modal('hide');
            simpleNotify("添加字典详情成功。");
        } else {
            simpleNotify(res.message);
        }
    })
}
var btnSearchClick = function () {
    // if (pageTable) {
    //     pageTable.container.fnDraw();
    // }
    var dictionaryId = $("#dictionary_list td[class='info']").attr("id");
    listDictionaryData(dictionaryId);
}

var dictionaryNameClick = function () {
    CommonBase.showLoading();
    if ($("#dictionary_name").val() == null || $("#dictionary_name").val() == "") {
        simpleNotify("请输入基础类型名称");
        CommonBase.hideLoading();
        return false;
    }
    if ($("#dictionary_name").val().length > 10) {
        simpleNotify("基础类型名称过长");
        CommonBase.hideLoading();
        return false;
    }
    $.post($("#baseUrl").attr("data-url") + "/dictionary/saveDictionary", {dictionaryName: $("#dictionary_name").val()}, function (res) {
        CommonBase.hideLoading();
        if (res.success) {
            listDictionary();
            $("#addInfoDictionary").modal('hide');
            simpleNotify("添加基础类型成功。");
        } else {
            simpleNotify(res.message);
        }
    })
}

function listDictionary() {

    var dataUrl = $("#dataURL").val() + "/dictionary/listDictionaryInfo";
    $.get(dataUrl, function (res) {
        vue.message = res.data;
        if (res.data[0] != undefined || res.data[0] != null) {
            listDictionaryData(res.data[0].id);
            $("#dictionary_list td").removeClass("info");
            $("#dictionary_list td:first").addClass("info");
        }
    })
}

function addClick() {
    $("#optionsRadios3").click(function (e) {
        $("#dictionary_data_value_2").addClass("active");
        $("#dictionary_data_value_1").removeClass("active");
    });
    $("#optionsRadios4").click(function (e) {
        $("#dictionary_data_value_1").addClass("active");
        $("#dictionary_data_value_2").removeClass("active");
    });

}

function listDictionaryData(dictionaryId) {
    pageTable = null;
    pageTable = {
        dom: $('#dictionaryDetailTable'),//table节点
        ajaxUrl: $("#dataURL").val() + "/dictionary/listVagueByDictionaryId",//ajax请求地址
        httpMethod: 'get',//接口的请求方式方法分为get请求和post请求
        aoColumns: ["dictionaryDataName", "sort",],//table要显示的列
        primaryKey: "id",//主键
        diyColumn: [     //自定义列

            {
                aTargets: [1],//要显示的位置
                mData: "dictionaryCode",//
                mRender: function (data, type, full) {
                    $(".checkchilds").removeAttr("checked");
                    return "<input type='radio'  class='checkchild' style='text-align: center;' name='dictionary_id' onclick='checkChild(this)' id='" + full.id + "' />";
                    ;
                },
                "bSortable": false
            },
            {

                aTargets: [2],//要显示的位置
                mData: "dictionaryDataName",//
                mRender: function (data, type, full) {
                    return full.dictionaryDataNameRedundancy != null && full.dictionaryDataNameRedundancy != "" &&
                    full.dictionaryDataNameRedundancy != undefined ?
                        full.dictionaryDataName + " - " + full.dictionaryDataNameRedundancy : full.dictionaryDataName;
                },
            },
            {
                aTargets: [4],//要显示的位置
                mData: "sort",//主键
                mRender: function (data, type, full) {//返回参数
                    return full.sort == undefined ? "" : full.sort;
                }
            },
            {
                aTargets: [3],//要显示的位置
                mData: "id",//主键
                mRender: function (data, type, full) {//返回参数
                    return $("#dictionary_list td[class='info']").attr("data-name");
                }
            },
        ],
        ajxaParam: function () {
            return {
                dictionaryId: dictionaryId,
                exactValue: $("#dictionary_data #exactValue").val(),
            };
        },
    }
    window.initPageTab(pageTable);
    /****查询**/
    $("#btnSearch").unbind("click");
    $("#btnSearch").bind("click", btnSearchClick);
    $("#addDictionaryData").unbind("click");
    $("#addDictionaryData").bind("click", addDictionaryDataClick);
    $("#deleteDictionaryData").unbind("click");
    $("#deleteDictionaryData").bind("click", deleteDictionaryDataClick);
}

function checkChild(obj) {
    $(".checkchild").prop("checked", false);
    $(obj).prop("checked", true);
}