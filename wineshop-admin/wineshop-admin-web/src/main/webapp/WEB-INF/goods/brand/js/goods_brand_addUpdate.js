//实例化编辑器
var ue = UE.getEditor('container');
//logo图标信息
var upLoadImage;
$(function () {

})
//设置二次密码登录验证
var checkPassWordFlag=$.cookie('checkPassWordFlag');
//图片上传事件
function upLoadChange(e) {
    var fileName = "fjjh_admin_brandList_logo";
    window.upLoadOssImage(event,fileName,function (result) {
        //图片信息地址
        url = window.ossImageUrl+result.name;
        $("#brandLogo").attr("src",url);
    })
}
//添加子品牌提交功能
function addSubBrandObj() {
    //表单验证
    if(!addEditCheckForm()){
        return false;
    }
    CommonBase.showLoading();
    //请求条件
    var ajaxUrl = '';
    //品牌ID
    var brandId = $("#brandTypeId").val();
    //品牌名称
    var brandName = $("#brandName").val();
    //品牌类型
    var brandType = $('input:radio[name="subBrandType"]:checked').val();
    //图片tul地址
    var brandLogoUrl = $("#brandLogo").attr("src");
    //品牌描述
    var brandDesc = ue.getContent();
    //参数设置
    var ajaxData = {};
    switch(currentActions){
        case brandAction.mainBrandAdd:
            //主品牌添加
            ajaxUrl=dataUrl+'addMainBrand';
            ajaxData = {
                mainBrandName:brandName,
                mainBrandPhoto:brandLogoUrl,
                mainBrandDesc:brandDesc,
            };
            break;
        case brandAction.mainBrandEdit:
            //主品牌编辑
            ajaxUrl=dataUrl+'updateMainBrand';
            ajaxData={
                mainBrandId:brandId,
                mainBrandName:brandName,
                mainBrandPhoto:brandLogoUrl,
                mainBrandDesc:brandDesc
            }
            break
        case brandAction.subBrandAdd:
            //子品牌添加
            ajaxUrl=dataUrl+'addSubBrand';
            ajaxData = {
                mainBrandId:brandId,
                brandName:brandName,
                brandPhoto:brandLogoUrl,
                brandDesc:brandDesc,
                brandGoodsType: brandType
            };
            break;
        case brandAction.subBrandEdit:
            //子品牌编辑
            ajaxUrl=dataUrl+'updateSubBrand';
            ajaxData={
                brandId:brandId,
                brandName:brandName,
                brandPhoto:brandLogoUrl,
                brandDesc:brandDesc,
                brandGoodsType: brandType
            }
            break;
    }
    $.ajax({
        url:ajaxUrl,
        type:'POST',
        data:ajaxData,
        success:function (result) {
            if(result.success){
                getBrandsList();
                //信息提示
                window.simpleNotify('操作成功','提示','success');
                $("#remoteModal").modal('hide');
                CommonBase.hideLoading();
            }else{
                window.simpleNotify(result.message);
                $("#remoteModal").modal('hide');
                CommonBase.hideLoading();
            }
        },
        fail:function (result) {
            window.simpleNotify(result.message);
            $("#remoteModal").modal('hide');
            CommonBase.hideLoading();
        }
    })

}

//品牌编辑展示页面
function getBrandEditData(brandID,brandType) {
    var url = '';
    var dataObj = {};
    if(brandType==brandAction.mainBrandEdit){
        //主品牌编辑展示
        url = dataUrl+'editMainBrandShow';
        dataObj.mainBrandId = brandID;
    }else if(brandType == brandAction.subBrandEdit){
        //子品牌编辑展示
        url = dataUrl+'editBrandShow';
        dataObj.brandId = brandID;
    }
    $.ajax({
        url:url,
        dataType:'json',
        type:'post',
        data:dataObj,
        success:function (result) {
            if(result.success){
                if(brandType==brandAction.mainBrandEdit){
                    //主品牌名称
                    $("#brandName").val(result.data.mainBrandName);
                    //主品牌图像
                    $("#brandLogo").attr("src",result.data.mainBrandPhoto);
                    //主品牌描述
                    //设置编辑器的内容
                    ue.setContent(result.data.mainBrandDesc);
                }else if(brandType == brandAction.subBrandEdit){
                    //子品牌名称
                    $("#brandName").val(result.data.brandName);
                    //子品牌类型选择
                    $("input:radio[name='subBrandType'][value='" + result.data.brandGoodsType+ "']").prop("checked", "checked");
                    //子品牌图像
                    $("#brandLogo").attr("src",result.data.brandPhoto);
                    //子品牌描述
                    ue.setContent(result.data.brandDesc);

                }
            }
        }
    })
}
//品牌名称的二次密码验证模态窗口的打开
function checkSecondPassword() {
    if(currentActions==brandAction.mainBrandAdd || currentActions==brandAction.subBrandAdd || $.cookie('checkPassWordFlag')){
        //主品牌添加和子品牌添加:不做二次密码验证
        return true;
    }
    if(currentActions==brandAction.mainBrandAdd || currentActions == brandAction.subBrandAdd){
        $("#brandName").focus();
    }else{
        $("#brandName").blur();
    }
    //设置警告信息
    $("#checkPasswordForm .waringInfo").text('(注:禁用或删除品牌后，该品牌所有商品将下架。请谨慎操作)');
    $("#checkPasswordFormInput").val('');
    $(".errorMessage").empty();
    $("#checkPasswordForm").modal();
}
//提交密码验证
function confirmCheckSecondPassword(){
    $.ajax({
        url:dataUrl+'passwordConfirm',
        type:'POST',
        dataType:'json',
        data:{password:$("#checkPasswordFormInput").val()},
        success:function (result){
            if(result.success){
                //设置过期时间为30分钟
                var date=new Date();
                date.setTime(date.getTime()+30*60*1000);
                $.cookie('checkPassWordFlag',true,{expires:date});
                $("#checkPasswordForm").modal('hide');
            }else{
                var errorMessage = '<p class="errorMessage">'+result.message+'</p>';
                $(".errorMessage").append(errorMessage);
            }
        }
    })
}
//品牌名称二次密码验证取消按钮
function cancleCheckSecondPassword() {
    $("#checkPasswordForm").modal('hide');
}
//清空表单信息
function clearFrom(formID) {
    var formElement =document.getElementById(formID);
    formElement.reset();
    formElement.brandLogo.src="Content/themes/smart/img/loadImg.png";
    ue.setContent('');
}
//添加编辑表单验证
function addEditCheckForm() {
    //品牌明后才能
    var brandName = $("#brandName").val();
    //品牌名称的验证
    if(!brandName){
        simpleNotify('品牌名称不能为空');
        return false;
    }
    //酒类名称
    var brandType = $('input:radio[name="subBrandType"]:checked').val();
    if(currentActions==brandAction.subBrandAdd || currentActions==brandAction.subBrandEdit){
        //品牌类型的验证
        if(!brandType){
            simpleNotify('请选择子品牌名称');
            return false;
        }
    }
    //获取品牌图标
    var brandLogo = $("#brandLogo").attr("src");
    var defaultLogo = "Content/themes/smart/img/loadImg.png";
    if(brandLogo==defaultLogo){
        simpleNotify('请上传品牌图标');
        return false;
    }
    //获取品牌介绍
    var brandDesc = ue.getContent();
    if(!brandDesc){
        simpleNotify('请填写品牌介绍');
        return false;
    }
    return true;
}