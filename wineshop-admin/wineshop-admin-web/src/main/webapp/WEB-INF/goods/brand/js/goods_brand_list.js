
var dataUrl = $("#baseUrl").attr("data-url") + "/goodsBrand/";
var brandPageTable;
var brandAction={
  mainBrandAdd:'marinBrandAdd',//主品牌添加操作
  subBrandAdd:'subBrandAdd',//子品牌添加操作
  mainBrandEdit:'mainBrandEdit',//主品牌编辑操作
  subBrandEdit:'subBrandEdit',//子品牌编辑操作，
  mainBrandDelete:'mainBrandDelete',//主品牌删除
  subBrandDelete:'subBrandDelete',//子品牌删除
};
//设置当前操作，添加，编辑
var currentActions;
//未查询到匹配信息
var searchNoDataMessage='';
//参数名称
var obj ={
    brandName:'',
    stateTag:'',
    updateFrom:'',
    updateTo:'',
    orderTag:0
};
var subBrandList = {};
$(function () {
    pageSetUp();
    //获取品牌管理
    getBrandsList();
    //查询开始时间初始化
    initDate("#searchStartDate");
    //查询结束时间初始化
    initDate("#searchEndDate");
    //查询列表启用禁用事件
    $(".brandAble input[type=checkbox]").change(function () {
        var checkBoxFlag = $(this).is(':checked');
        if(checkBoxFlag){
            $(this).attr("checked","false");
        }else{
            $(this).removeAttr("checked");
        }
        $(".brandAble input[type=checkbox]").not(this).removeAttr("checked");
    });
    $("#brandListTable").delegate("tbody tr td.details-control",'click',function () {
        var tr = $(this).closest('tr');
        var row = brandPageTable.row( tr );
        var trData = row.data();
        if ( row.child.isShown()){
            tr.removeClass( 'details' );
            row.child.hide();
        }else{
            tr.addClass( 'details' );
            row.child( format(trData,row) ).show();
        }
    })
})
function format ( data,row ) {
    var subTable = '<table id="subBrandTable" class="subBrandTable" width="100%">';
    //获取子品牌数据
    var subBrandData = data[data.length-1];
    if(subBrandData && subBrandData.length>0){
       for(var i = 0;i<subBrandData.length;i++){
        //设置图片Imageid
        var imgId = data[data.length-2].toString()+subBrandData[i].brandId;
        var tr = "<tr>";
        //设置主品牌为空
        var mainTd = '<td></td>';
        //设置子品牌名称
        var subTd = '<td>'+subBrandData[i].brandName+'</td>';
        //设置子品牌更新使劲
        var updateTd = '<td>'+subBrandData[i].brandUpdateTime+'</td>';
        //设置子品牌状态
        if(subBrandData[i].brandStateTag==1){
            var img='<img src="Content/Home/image/switch_open.png"  data-brandId="'+subBrandData[i].brandId+'"  data-statusId="1" id="'+imgId+'" class="subBrandStatus" onclick="setSubBrandDisable()"/>'
        }else{
            var img='<img src="Content/Home/image/switch_close.png" data-brandId="'+subBrandData[i].brandId+'" data-statusId="0" id="'+imgId+'" class="subBrandStatus" onclick="setSubBrandDisable()"/>'
        }
        var statusTd = '<td class="subBrandStatus">禁用'+img+'开启</td>';
        //设置子品牌操作按钮
       var actionTD = '<td><a class="tableBtn hidden-mobile" href="javascript:void(0)"></a>' +
           '   <a class="tableBtn hidden-mobile" href="javascript:void(0)" onclick="updateBrand('+subBrandData[i].brandId+",\'"+brandAction.subBrandEdit+'\')"><img class="hidden-md hidden-sm hidden-xs icon_img" src="Content/Home/image/xiugai.png" />编辑</a>' +
           '   <a class="tableBtn hidden-mobile" href="javascript:void(0)" onclick="deleteBrandById('+subBrandData[i].brandId+",\'"+brandAction.subBrandDelete+'\')"><img class="hidden-md hidden-sm hidden-xs icon_img" src="Content/Home/image/del.png" />删除</a></td>';
       tr+=mainTd+subTd+updateTd+statusTd+actionTD+'</tr>';
       subTable+=tr;
       }
       subTable+='</table>';
       return subTable;
    }else{
        return '<p class="nosubBrandData">暂无子品牌数据</p>';
    }

}
//获取品牌列表数据
function getBrandsList() {
//ajax请求数据
    $.ajax({
        type:'POST',
        url:dataUrl+ "listBrandInfo",
        dataType: 'json',
        async:false,
        data: obj,
        success: function (result) {
            if(result.data && result.data.length>0){
                var dataSet = [];
                var list = result.data;
                for(var i = 0;i<list.length;i++){
                    var listItem =[];
                    //设置设置主品牌名称
                    listItem.push(list[i].mainBrandName);
                    //设置子品牌名称
                    listItem.push('');
                    //设置更新时间
                    listItem.push(list[i].mainBrandUpdateTime);
                    //设置状态为空
                    listItem.push('');
                    //设置操作
                    var actionBtn = '<td><a class="tableBtn hidden-mobile" href="javascript:void(0)" onclick="updateBrand('+list[i].mainBrandId+",\'"+brandAction.subBrandAdd+'\')"><img class="hidden-md hidden-sm hidden-xs icon_img" src="Content/Home/image/edit.png" />添加</a>' +
                        '   <a class="tableBtn hidden-mobile" href="javascript:void(0)" onclick="updateBrand('+list[i].mainBrandId+",\'"+brandAction.mainBrandEdit+'\')"><img class="hidden-md hidden-sm hidden-xs icon_img" src="Content/Home/image/xiugai.png" />编辑</a>' +
                        '   <a class="tableBtn hidden-mobile" href="javascript:void(0)" onclick="deleteBrandById('+list[i].mainBrandId+",\'"+brandAction.mainBrandDelete+'\')"><img class="hidden-md hidden-sm hidden-xs icon_img" src="Content/Home/image/del.png" />删除</a></td>';
                    listItem.push(actionBtn);
                    //设置主品牌ID
                    listItem.push(list[i].mainBrandId);
                    //设置子品牌信息
                    listItem.push(list[i].brandVOList);
                    dataSet.push(listItem);
                }
                fullBrandData(dataSet);
            }else{
                fullBrandData([]);
            }
        }
    });
}
//填充表格数据
function fullBrandData(dataList) {
    //设置未查询到结果的提示信息
    if(searchNoDataMessage){
        window.gjhDataTable_CN_Lang.sEmptyTable=searchNoDataMessage;
    }
    //表格填充
    brandPageTable = $("#brandListTable").DataTable({
        data:dataList,
        bAutoWidth:false,
        searching:false,
        bLengthChange:false,
        destroy:true,
        oLanguage: window.gjhDataTable_CN_Lang,
        order: [[ 3, "desc" ]],
        columns:[
            {
                class:'details-control',
                title:'主品牌'
            },
            {title:'子品牌'},
            {title:'更新时间'},
            {title:'状态'},
            {title:'操作'}
        ]
    })
}

//查询品牌列表数据
function searchBrandList() {
    //表单验证
    if(!checkForm()){
        return false
    }
    obj = {
        brandName:$("#searchBrandName").val(),
        stateTag:$('input:checkbox:checked').val(),
        updateFrom:$("#searchStartDate").val(),
        updateTo:$("#searchEndDate").val(),
        orderTag:0
    }
    //设置未查询到数据结果的信息
    searchNoDataMessage='未查询到匹配结果';
    getBrandsList();
}
//添加品牌
function updateBrand(brandID,brandType){
    $("#remoteModal").modal({backdrop: 'static', keyboard: false});
    //表单的清空
    clearFrom("actionBrandForm");
    $("#brandTypeId").val(brandID);
    if(brandType==brandAction.mainBrandAdd){
        //主品牌添加，隐藏商品类型
        $(".subBrandType").css({"display":"none"});
    }else if(brandType == brandAction.mainBrandEdit){
        //主品牌添加，隐藏商品类型
        $(".subBrandType").css({"display":"none"});
        //主品牌编辑展示
        getBrandEditData(brandID,brandType);
    }else if(brandType == brandAction.subBrandAdd){
        //子品牌添加
        $(".subBrandType").css({"display":"block"});
    }else if(brandType == brandAction.subBrandEdit){
        //子品牌编辑展示
        $(".subBrandType").css({"display":"block"});
        getBrandEditData(brandID,brandType);
    }
    //当前操作内容
    currentActions = brandType;
}

//品牌删除
function deleteBrandById(brandId,brandType) {
    var ajaxUrl = '';
    var ajaxObj = {};
    if(brandType==brandAction.mainBrandDelete){
        //主品牌删除
        ajaxUrl = dataUrl+'removeMainBrand';
        ajaxObj.mainBrandId=brandId;
    }else if(brandType==brandAction.subBrandDelete){
        //子品牌删除
        ajaxUrl= dataUrl+'removeSubBrand';
        ajaxObj.brandId=brandId;
    }
    if(!$.cookie('checkPassWordFlag')){
        checkSecondPassword();
        return false;
    }
    $.confirm({
        msg:'是否确认删除该品牌',
        confirm:function () {
            CommonBase.showLoading();
            $.ajax({
                url: ajaxUrl,
                type:'POST',
                data:ajaxObj,
                success:function (result) {
                    if (result.success == true) {
                       simpleNotify('删除成功','提示','success');
                        getBrandsList();
                        CommonBase.hideLoading();
                    }else{
                        CommonBase.hideLoading();
                        simpleNotify(result.message);
                    }
                },
                fail:function (result) {
                    simpleNotify(result.message);
                }
            })
        }
    })

}
//品牌的禁用与启用
function setSubBrandDisable() {
    //子品牌ID
    var subBrandId = event.target.dataset.brandid;
    //品牌禁用启用成功
    var imgId = event.target.id;
    var imgStatus = event.target.dataset.statusid;
    var ajaxUrl = '';
    if(imgStatus==1){
        ajaxUrl=dataUrl+'brandDisable';
    }else{
        ajaxUrl=dataUrl+'brandEnable';
    }
    if(!$.cookie('checkPassWordFlag')){
        checkSecondPassword();
        return false;
    }
    $.ajax({
        url:ajaxUrl,
        type:'POST',
        datatype:'json',
        data:{brandId:subBrandId},
        success:function (result) {
            if(result.success){
                if(imgStatus==1){
                    document.getElementById(imgId).src="Content/Home/image/switch_close.png";
                    document.getElementById(imgId).setAttribute('data-statusId',2);
                }else{
                    document.getElementById(imgId).src="Content/Home/image/switch_open.png";
                    document.getElementById(imgId).setAttribute('data-statusId',1);
                }
            }
        }
    })
}


//日期选择初始化
function initDate(element,fn) {
    $(element).datepicker({
        dateFormat: "yy-mm-dd",
        changeMonth: true,
        changeYear: true,
        dayNames: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
        dayNamesMin: ["日", "一", "二", "三", "四", "五", "六",],
        monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        nextText: '<i class="fa fa-chevron-right"></i>',
        prevText: '<i class="fa fa-chevron-left"></i>',
        onClose: function (dateText, inst) {
        },
    });
}

//表单验证
function checkForm() {
   //开始日期，结束日期
    var searchStartDate = $("#searchStartDate").val();
    var searchEndDate = $("#searchEndDate").val();
    if(searchStartDate || searchEndDate){
       if(!(searchStartDate && searchEndDate)){
           simpleNotify('请输入开始日期和结束日期');
           return false;
       }else if(searchStartDate>searchEndDate){
           simpleNotify('开始日期不能大于结束日期');
           return false;
       }
    }
    return true;
}
//清楚时间日期格式
function clearDate(element) {
   event.preventDefault();
    $("#"+element).val('');
}
