var dataUrl = $("#baseUrl").attr("data-url") + "/goodsAttr/";
var pageTable;
var newPageTable;
var vue;
var typeObj;//属性
$(function () {
    pageSetUp();
    // //查询
    // $("#btnSearch").click(function () {
    //     // if (pageTable) {
    //     //     pageTable.container.fnDraw();
    //     // }
    //     listModelGoods();
    // });
    getType();
    vue = new Vue({
        el: '#unlike',
        data: {
            message: '',
        },
        methods: {
            selectAttributeValue: function (event,obj) {
                event = event || window.event;
                var target = event.srcElement || event.target;
                $("#attributeNames td").removeClass("info");
                $(target).addClass("info");
                listAttibuteData(obj);
            }
        }
    });
});	//页面初始化调用

//页面加载类型
function getType(){
    CommonBase.showLoading();
    $.get(dataUrl + "attrTypeList",function (data) {
        $("#listTypeAttr").val("");
        if (data.success == false) {
            simpleNotify(data.message);
            CommonBase.hideLoading();
            return false;
        } else {
            $.each(data.data, function (key,val) {
                if(val.attrTypeId == 1100){
                    $("#listTypeAttr").append("<div class='col-lg-2 col-md-2 col-sm-2 headline active' onclick='getAttributeValue(this)' data-id='"+val.attrTypeId+"'>"+val.attrTypeName+"</div>");
                }else{
                    $("#listTypeAttr").append("<div class='col-lg-2 col-md-2 col-sm-2 headline' onclick='getAttributeValue(this)' data-id='"+val.attrTypeId+"'>"+val.attrTypeName+"</div>");
                }
            });
            CommonBase.hideLoading();
        }
    });
}
//获取属性值
function getAttributeValue(obj){
    $(obj).addClass("active").siblings().removeClass("active");
    if ($(obj).attr("data-id") == 1100){
        $("#same").css("display","block").siblings().css("display","none");
    }else{
        $("#unlike").css("display","block").siblings().css("display","none");
        $.post(dataUrl+"attrList",{attrTypeId:$(obj).attr("data-id")},function (res) {
            vue.message = res.data;
            if (res.data[0] != undefined || res.data[0] != null) {
                listAttibuteData(res.data[0]);
                $("#attributeNames td").removeClass("info");
                $("#attributeNames td:first").addClass("info");
            }
        })
    }
}
//获取属性 的属性值列表
function listAttibuteData(obj){
    typeObj = obj;
    $("#attribute_name").text("");
    $("#attribute_name").text(obj.attrName);
    var operateFlag =     obj.operateTag == 0 ?  false:true;
    operateFlag ?  $("#add_attrubute_data").css("display","block"):$("#add_attrubute_data").css("display","none");
    var attrFlag = obj.attrType == 0 ? false : true;
    if(attrFlag){
        $("#attribute_data_input").css("display","block").siblings().css("display","none");
        //显示 input 框的值
        attributeInputShowValue(obj.attrCode);
    }else{
        //因框架封装 造成不能控制列的显示 datatable 不能支持 故使用2个table来防止列表的显示问题
        pageTable = null;
        if(operateFlag){
            pageTable = {
                dom: $('#attribute_value_update_table'),//table节点
                ajaxUrl: dataUrl+ "/attrValueList",//ajax请求地址
                httpMethod: 'POST',//接口的请求方式方法分为get请求和post请求
                aoColumns:["attrValueName","attrCode"],
                primaryKey: "attrCode",//主键
                diyColumn :[     //自定义列
                    {
                        aTargets: [1],//要显示的位置
                        mData: "attrValueName",//
                        mRender: function (data, type, full) {
                            return full.attrValueName;
                        },
                    },
                    {
                        aTargets: [2],//要显示的位置
                        mData: "attrValueName",//主键
                        mRender: function (data, type, full) {//返回参数
                           //编辑按钮
                            var editBtn = "<a class='tableBtn hidden-mobile ' onclick='updateAttributeData(\""+full.attrValueName+"\","+full.attrValueCode+" )' ><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/xiugai.png' />编辑</a>";
                            //删除按钮
                            var deleteBtn = "<a class='tableBtn hidden-mobile' onclick='deleteAttributeData("+full.attrValueCode+" )'><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/del.png' />删除</a>";
                            if(full.operateTag==0){
                                //不可编辑
                                return '';
                            }else if(full.operateTag==1){
                                //可以编辑可以修改
                                return editBtn+deleteBtn;
                            }else if(full.operateTag==2){
                                //只能修改
                                return editBtn
                            }else if(full.operateTag==3){
                                //只能删除
                                return deleteBtn;
                            }
                        }
                    },
                ],
                ajxaParam: function () {
                    return {
                        attrCode: obj.attrCode,
                    };
                },
            };
            $("#attribute_data_select_update").css("display","block").siblings().css("display","none");
            window.initPageTab(pageTable);
        }else{
            pageTable = {
                dom: $('#attribute_value_table'),//table节点
                ajaxUrl: dataUrl+ "/attrValueList",//ajax请求地址
                httpMethod: 'POST',//接口的请求方式方法分为get请求和post请求
                aoColumns:["attrValueName"],
                primaryKey: "attrCode",//主键
                diyColumn :[     //自定义列
                    {
                        aTargets: [1],//要显示的位置
                        mData: "attrValueName",//
                        mRender: function (data, type, full) {
                            return full.attrValueName;
                        },
                    }
                ],
                ajxaParam: function () {
                    return {
                        attrCode: obj.attrCode,
                    };
                },
            };
            $("#attribute_data_select").css("display","block").siblings().css("display","none");
            window.initPageTab(pageTable);
        }
    }

}
//添加属性值
function addAttributeData() {
    $("#add_attribute_data_label").text("添加新值");
    $("#add_attribute_data_model #add_attribute_data").val("");
    $('#add_attribute_data_model').modal();
}
function  addAttributeDataSubmitClick() {
    obj = typeObj;
    CommonBase.showLoading();
    var attrValueName = $("#add_attribute_data_model #add_attribute_data").val();
    if(!verifier(attrValueName,"请输入属性值")){
        return false;
    }
    $.post(dataUrl + "attrAdd", {attrCode:obj.attrCode, attrValueName: attrValueName}, function (data) {
        if (data.success == false) {
            simpleNotify(data.message);
            CommonBase.hideLoading();
            return false;
        } else {
            simpleNotify(data.message);
            listAttibuteData(obj);
            $('#add_attribute_data_model').modal('hide');
            CommonBase.hideLoading();
        }
    });
}

//修改属性值
function updateAttributeData(name,code) {
    $("#update_attribute_data_label").text("修改属性值");
    $("#update_attribute_data_model #update_attribute_data").val(name);
    $("#update_attribute_data_model #update_attribute_data").attr("data-code",code);
    $("#update_attribute_data_model #update_attribute_data_password").val("");
    $('#update_attribute_data_model').modal();
}
function  updateAttributeDataSubmitClick() {
    obj = typeObj;
    CommonBase.showLoading();
    var attrValueName = $("#update_attribute_data_model #update_attribute_data").val();
    if(!verifier(attrValueName,"请输入属性值")){
        return false;
    }
    var password = $("#update_attribute_data_model #update_attribute_data_password").val();
    if(!verifier(password,"请输入密码")){
        return false;
    }
    $.post(dataUrl + "attrValueUpdate", {attrCode:obj.attrCode,attrValueCode:$("#update_attribute_data_model #update_attribute_data").attr("data-code"), attrValueName: attrValueName,password:password}, function (data) {
        if (data.success == false) {
            simpleNotify(data.message);
            CommonBase.hideLoading();
            return false;
        } else {
            simpleNotify(data.message);
            listAttibuteData(obj);
            $('#update_attribute_data_model').modal('hide');
            CommonBase.hideLoading();
        }
    });
}

//删除属性值
function deleteAttributeData(code) {
    $("#delete_attribute_data_label").text("删除属性值");
    $("#delete_attribute_data_model #delete_attribute_data_password").attr("data-code",code);
    $("#delete_attribute_data_model #delete_attribute_data_password").val("");
    $('#delete_attribute_data_model').modal();
}
function  deleteAttributeDataSubmitClick() {
    obj = typeObj;
    CommonBase.showLoading();
    var password = $("#delete_attribute_data_model #delete_attribute_data_password").val();
    if(!verifier(password,"请输入密码")){
        return false;
    }
    $.ajax({
        url: dataUrl+ "attrValueRemove",
        type: "POST",
        data: {attrCode: obj.attrCode,attrValueCode:$("#delete_attribute_data_model #delete_attribute_data_password").attr("data-code"),password:password},
        success: function (data) {
            if (data.success == true) {
                simpleNotify("删除成功");
                listAttibuteData(obj);
                $('#delete_attribute_data_model').modal('hide');
                CommonBase.hideLoading();
            }else{
                simpleNotify(data.message);
                CommonBase.hideLoading();
            }
        }
    })
}

//显示 input 框的值
function attributeInputShowValue(code){
    console.log(code);
    switch (code){
        case "207":
            $("#attribute_data_input div div:first-child").text("窖藏年份");
            $("#attribute_data_input div div:last-child").text("999-至今");
            break;
        case "301":
            $("#attribute_data_input div div:first-child").text("采摘年份");
            $("#attribute_data_input div div:last-child").text("999-至今");
            break;
        case "507":
            $("#attribute_data_input div div:first-child").text("麦芽度");
            $("#attribute_data_input div div:last-child").text("0-100");
            break;
    }
}
//验证器
function verifier(obj,message){
    if (obj == undefined || obj == null || obj.length == 0) {
        simpleNotify(message);
        CommonBase.hideLoading();
        return false;
    }else{
        return true;
    }
}

