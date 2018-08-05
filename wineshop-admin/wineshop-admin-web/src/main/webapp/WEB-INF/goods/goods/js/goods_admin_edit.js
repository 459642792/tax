//富文本编辑器实例化
var ue = UE.getEditor('editContainer');
//ajax请求路劲
var updateDataUrl = $("#baseUrl").attr("data-url") ;
var data={
    //主品牌名称
    mainBrandInfo:{
        name:'mainBrandInfo',
        attrValueCode:'mainBrandId',
        attrValueName:'mainBrandName',
        attrSelectFlag:'selectedFlag',
        id:'editProductMainBrand'//主品牌ID
    },
    //子品牌数据名称
    subBrandInfo:{
        name:'subBrandInfo',
        attrValueCode:'brandId',
        attrValueName:'brandName',
        attrSelectFlag:'selectedFlag',
        id:'eidtProductSubBrand',//子品牌ID
        subBrandList:{},//子品牌数据列表
    },
    //基本属性div信息
    baseAttrInfo:{id:'eidtBaseAttrList'},
   //特殊属性div信息
    specialAttrInfo:{id:'editSpecialAttrList'},
    //图像添加id
    count:0,
    //编辑当前商品ID编号
    currentProductID:'',
};
//图像上传地址存执
var upLoadImageList = new Set();
//初始化
$(function () {
    //获取品牌数据
    getBrandsList();
    //主品牌，子品牌二级菜单连动
    $("#editProductMainBrand").change(function () {
        //主品牌ID
        var mainBrandId = $(this).val();
        //品牌类型
        var goodsType = $("span[class='brandType active']").attr('data-value');
        //获取子品牌数据
        getEditSubBrandList(mainBrandId,goodsType);
    });
    //单瓶属性值的设置
    $("select[data-parentattrcode=110]").live('change',function () {
        var text = $(this).find('option:selected').text();
        if(text=='单瓶'){
            $("input[data-parentattrcode=130]").attr("disabled","true");
            $("input[data-parentattrcode=130]").val(1);
        }else{
            $("input[data-parentattrcode=130]").removeAttr("disabled");
            $("input[data-parentattrcode=130]").val('');
        }
    })
})
//清空表单数据
function clearEditProductsForm() {
    $("#editForm")[0].reset();
    //清楚子品牌数据
    $("#eidtProductSubBrand").empty();
    //清楚子品牌提示信息
    $(".subBrandErrorMessage").remove();
    //清楚品牌种类
    $(".editBrandType").removeClass('active');
    $(".editBrandType:first-child").addClass('active');
    //清楚商品图像信息
    $(".productsImageList .imagItem").remove();
    //图像id编号为0
    data.count = 0;
    //清楚set图像地址数据
    upLoadImageList.clear();
}
//编辑商品初始化填充数据
function initUpdateProduct(productId) {
    clearEditProductsForm();
    var ajaxUlr = updateDataUrl+'/goodsAdmin/goodsEditShow';
    currentProductID = productId;
    $.ajax({
        url:ajaxUlr,
        type:'POST',
        dataType:'json',
        data:{goodsId:productId},
        success:function (result) {
            if(result.success && result.data){
                var obj = result.data;
                for(var key in obj){
                    //商品的状态
                    var goodsStatus = obj['goodsState'];
                    //品类选择
                    if(key=='goodsType'){
                        $(".editBrandType").removeClass('active');
                        $(".editBrandType[data-value="+obj[key]+"]").addClass('active');
                    }
                    //商品名称
                    if(key=='goodsName'){
                        //上架中的商品名称不可编辑
                        if(goodsStatus==1){
                            $("input[id='editGoodsName']").attr("disabled","true");
                        }else{
                            $("input[id='editGoodsName']").removeAttr("disabled");
                        }
                        $("input[id=editGoodsName]").val(obj[key]);
                    }
                    //商品条码
                    if(key=='barCode'){
                        $("input[id='editBarCode']").attr("disabled","disabled");
                        $("input[id='editBarCode']").val(obj[key]);
                    }
                    //主品牌设置
                    if(key=='mainBrandId'){
                        //设置只读属性
                        if(goodsStatus == 1){
                            $("#editProductMainBrand").attr("disabled","true");
                        }else{
                            $("#editProductMainBrand").removeAttr("disabled");
                        }
                        $("#editProductMainBrand").val(obj[key]);
                    }
                    //子品牌设置
                    if(key=='brandList' && obj[key] && obj[key].length>0){
                        //设置只读属性
                        if(goodsStatus==1){
                            $("#eidtProductSubBrand").attr("disabled","true");
                        }else{
                            $("#eidtProductSubBrand").removeAttr("disabled")
                        }
                        formatBrandList(obj[key],data.subBrandInfo.name);
                    }
                    //商品基本属性的展示
                    if(key=='baseAttrShowVOList'){
                        createAttrSelect(obj[key],data.baseAttrInfo.id,goodsStatus);
                    }
                    //商品特殊属性展示
                    if(key=='specialAttrShowVOList'){
                        createAttrSelect(obj[key],data.specialAttrInfo.id);
                    }
                    //商品描述
                    if(key=='goodsDesc'){
                        ue.setContent(obj[key]);
                    }
                    //商品图像
                    if(key=='photoList'){
                        for(var val of obj[key]){
                            var url = val.goodsPhoto;
                            creatImagElement(url);
                        }
                    }
                    //商品价格
                    if(key=='suggestPrice'){
                        $("input[id=editSuggestPrice]").val( obj[key]);
                    }
                }
            }
        }
    })
}
//获取主品牌数据
function getBrandsList() {
    //ajax请求数据
    $.ajax({
        type:'POST',
        url:updateDataUrl+ "/goodsBrand/listBrandInfo",
        dataType: 'json',
        async:false,
        data:{brandName:'', stateTag:'', updateFrom:'', updateTo:'', orderTag:0},
        success:function (result) {
            if(result.success && result.data && result.data.length>0){
                //填充下拉列表数据
                formatBrandList(result.data,data.mainBrandInfo.name);
            }
        }
    });
}
//获取子品牌数据（通过主品牌ID，品牌类型ID）
function getEditSubBrandList(mainBrandId,goodsType) {
    $.ajax({
        type:'POST',
        url:updateDataUrl+"/goodsAdmin/subBrandSearch",
        dataType:'json',
        data:{mainBrandId:mainBrandId,goodsType:goodsType},
        success:function (reseult) {
            $("#"+data.subBrandInfo.id).empty();
            //清楚errormessage
            $(".subBrandErrorMessage").remove();
            if(reseult.success && reseult.data && reseult.data.length>0){
                //存在二级子品牌的时候
                formatBrandList(reseult.data,data.subBrandInfo.name);
            }else{
                //不存在二级子品牌的时候,显示提示信息
                var errorMessage = '<p class="subBrandErrorMessage">子品牌为空，需要新增子品牌</p>'
                $("#"+data.subBrandInfo.id).after(errorMessage);
            }
        },
        fail:function (error) {
            $("#"+data.subBrandInfo.id).empty();
            //清楚errormessage
            $(".subBrandErrorMessage").remove();
            //不存在二级子品牌的时候,显示提示信息
            var errorMessage = '<p class="subBrandErrorMessage">子品牌为空，需要新增子品牌</p>'
            $("#"+data.subBrandInfo.id).after(errorMessage);
        }
    })
}

//品牌下拉列表框的添加
function formatBrandList(resultData,type) {
    var dataList = [];
    var brandConstInfo = data[type];
    for(var val of resultData){
        var item = {};
        //判断是否是主品牌
        if(type==data.mainBrandInfo.name){
            //保存子品牌信息存放在列表中
            data.subBrandInfo.subBrandList[val.mainBrandId] = val.brandVOList;
        }
        //品牌ID
        item.attrValueCode = val[brandConstInfo.attrValueCode];
        //品牌名称
        item.attrValueName = val[brandConstInfo.attrValueName];
        //品牌是否被选择
        item.selectedFlag = val[brandConstInfo.attrSelectFlag];
        dataList.push(item);
    }
    var options = createAttrOptions(dataList);
    $("#"+brandConstInfo.id).append(options);
}
//创建输入框，下拉选择框
function createAttrSelect(dataList,elementId,goodStatus) {
    $("#"+elementId).empty();
    var divHtml = '';
    for(var val of dataList){
        //商品上架中，设置基本属性为只读属性
        if(goodStatus==1 && elementId==data.baseAttrInfo.id){
            var disabled = 'disabled="disabled"';
        }else{
            var disabled='';
        }
        var span='';
        //设置必填星号
        if(elementId==data.baseAttrInfo.id){
            span='<span class="mustFill">*</span>';
        }
        //属性值的设置
        var attrData='data-attrCode="'+val.attrCode+'" data-parentAttrCode="'+val.parentAttrCode+'" data-attrType="'+val.attrType+'"'+disabled;
        if(val.attrType==1){
            //input输入框
            var inputElement = '<input type="text" data-scope="'+elementId+'" '+attrData+'  placeholder="请输入'+val.attrName+'" value="'+val.attrValueName+'"/>';
            //创建input模块
            var itemHtml =' <section class="infoList"><div class="infoListItemLabel">'+span+val.attrName+'</div> ' +
                '<div class="infoListItemContent productQRCode productInput">'+inputElement+'</div></section>';
        }else if(val.attrType==0){
            //下拉选择框
            var selectElement = '<select data-scope="'+elementId+'" '+attrData+' id="productMainBrand'+val.attrCode+'">';
            //创建option选项
            var options = createAttrOptions(val.attrValVOList);
            selectElement+=options+'</select>'
            //创建select下拉输入框模块
            var itemHtml =' <section class="infoList"><div class="infoListItemLabel">'+span+val.attrName+'</div> ' +
                '<div class="infoListItemContent productQRCode productInput">'+selectElement+'</div></section>';
        }
        divHtml+=itemHtml;
    }
    $("#"+elementId).append(divHtml);
}
//创建下拉列表option
function createAttrOptions(data) {
    var options = '<option value="-1">请选择</option>';
    for(var val of data){
        var selectCheck='';
        if(val.selectedFlag==1){
            var selectCheck='selected';
        }
        var opt = '<option '+selectCheck+' value="'+val.attrValueCode+'">'+val.attrValueName+'</option>';
        options+=opt;
    }
    return options;
}
//图片上传事件
function uploadImage(event) {
    var fileName= 'goodsImageList';
    if(upLoadImageList.size>=6){
        simpleNotify('只能上传6张图片');
        return false;
    }
    window.upLoadOssImage(event,fileName,function (result) {
        //图片信息地址
        var url = window.ossImageUrl+result.name;
        creatImagElement(url);
    })
}
//创建图片
function creatImagElement(url) {
    var deleteId = "deleteImage"+data.count;
    //将图片地址添加到map中
    if(!upLoadImageList.has(url)){
        var imgElement = '<div class="imagItem" id="'+deleteId+'"><img src="'+url+'" alt="图片上传" class="productImgDesc"/><img src="Content/Home/image/close.png" class="deleteImage"  onclick="deleteUploadImage(\''+deleteId+'\')"></div>';
        //添加图片地址
        $("#eidtPhotoList").append(imgElement);
        upLoadImageList.add(url);
        data.count++;
    }
    //判断上传图片只能上传6张
    if(upLoadImageList.size>=6){
        $(".addImageList").css({"display":"none"});
    }
}
//图片信息的删除
function deleteUploadImage(elementId) {
    var url = $("#"+elementId+" .productImgDesc").attr('src');
    if(upLoadImageList.has(url)){
        upLoadImageList.delete(url);
    }
    $("#eidtPhotoList #"+elementId).remove();
    //判断上传图片只能上传6张
    if(upLoadImageList.size<6){
        $(".addImageList").css({"display":"block"});
    }
}
//提价按钮的执行
function editSubmitForm(){
    //表单验证
    if(!checkValidateForm()){
        return false;
    }
    //设置提交的数据
    var postDataObj = {};
    var ajaxUrl = updateDataUrl+'/goodsAdmin/goodsEdit';
    postDataObj.goodsId= currentProductID;
    //基本属性值
    postDataObj.baseAttrList=[];
    //特有属性值
    postDataObj.specialAttrList = [];
    var _elementForm = $("#editForm");
    //品类选择
    postDataObj.goodsType = _elementForm.find("span[class='editBrandType active']").attr('data-value');
    //商品名称
    postDataObj.goodsName= _elementForm.find("input[name=goodsName]").val();
    //商品条码
    postDataObj.barCode = _elementForm.find("input[name=barCode]").val();
    //主品牌
    postDataObj.mainBrandId = _elementForm.find("select[name=mainBrandId]").val();
    //子品牌
    postDataObj.brandId=_elementForm.find("select[name=brandId]").val();
    //商品基本信息
    var baseAttrInfo = _elementForm.find("#eidtBaseAttrList").find("input,select");
    for(var i =0;i<baseAttrInfo.length;i++){
        var val = baseAttrInfo[i];
        if($(val)[0].tagName.toLowerCase()=='select'){
            //基本属性下拉选择框
            var item = createAttrObject($(val).attr('data-attrCode'),$(val).val(),$(val).attr('data-attrType'),'',$(val).attr('data-parentAttrCode'));
        }else if($(val).attr('type')=='text'){
            //基本属性input输入框
            var item = createAttrObject($(val).attr('data-attrCode'),'',$(val).attr('data-attrType'),$(val).val(),$(val).attr('data-parentAttrCode'));
        }
        postDataObj.baseAttrList.push(item);
    }
    //商品特有属性信息
    var specialAttrInfo = _elementForm.find("#editSpecialAttrList").find("input,select");
    for(var i =0;i<specialAttrInfo.length;i++){
        var val = specialAttrInfo[i];
        if($(val)[0].tagName.toLowerCase()=='select'){
            //基本属性下拉选择框
            var item = createAttrObject($(val).attr('data-attrCode'),$(val).val(),$(val).attr('data-attrType'),'',$(val).attr('data-parentAttrCode'));
        }else if($(val).attr('type')=='text'){
            //基本属性input输入框
            var item = createAttrObject($(val).attr('data-attrCode'),'',$(val).attr('data-attrType'),$(val).val(),$(val).attr('data-parentAttrCode'));
        }
        postDataObj.specialAttrList.push(item);
    }
    //商品图像的信息
    postDataObj.goodsPhotoList=Array.from(upLoadImageList);
    //商品描述
    postDataObj.goodsDesc=ue.getContent();
    //商品价格
    postDataObj.suggestPrice = _elementForm.find("input[name=suggestPrice]").val();
    CommonBase.showLoading();
    $.ajax({
        url:ajaxUrl,
        type:'POST',
        dataType:'json',
        contentType:'application/json',
        data:JSON.stringify(postDataObj),
        success:function (result) {
            CommonBase.hideLoading();
            if(result.success){
                window.simpleNotify("保存成功", '提示', "success");
                $("#remoteEdit").modal('hide');
                listGoods();
            }else{
                window.simpleNotify(result.message,'提示','error');
            }
        },
        fail:function (result) {
            CommonBase.hideLoading();
            window.simpleNotify(result.message,'提示','error');
        }
    })
}
//创建添加品牌属性对象
function createAttrObject(attrCode,attrValueCode,attrType,attrValueName,parentAttrCode) {
    var item = {};
    item.attrCode = attrCode;
    item.attrValueCode = attrValueCode;
    item.attrType = attrType;
    item.attrValueName=attrValueName;
    item.parentAttrCode=parentAttrCode;
    return item;
}
//表单验证
function checkValidateForm() {
    //商品名称验证
    var editGoodsName = $("input[id='editGoodsName']").val();
    if(!editGoodsName){
        simpleNotify('请填写商品名称')
        return false;
    }
    //商品条码的验证
    var barCode = $("input[id='editBarCode']").val();
    if(!barCode){
        simpleNotify('请填写商品条码');
        return false;
    }
    if(barCode){
        //判断条形码只能为数字
        //var reg = /[1-9]\d*/;
        var reg =  /^[0-9a-zA-Z]+$/;
        if(!reg.test(barCode)){
            simpleNotify('请输入正确的商品条码');
            return false;
        }
    }
    //商品主品牌的验证
    var mainBrandId = $("select[name='editMainBrandId']").val();
    if(mainBrandId<0){
        simpleNotify('请选择商品主品牌');
        return false;
    }
    //商品子品牌验证
    var subBrandId = $("select[id='eidtProductSubBrand']").val();
    if(subBrandId<0 || subBrandId==null){
        simpleNotify('请选择商品子品牌');
        return false;
    }
    //商品基本属性的描述
    var baseAttrCheckInfo = $("#eidtBaseAttrList").find("input,select");
    var baseAttrFlag = true;
    for(var i = 0;i<baseAttrCheckInfo.length;i++){
        var val = baseAttrCheckInfo[i];
        if($(val)[0].tagName.toLowerCase()=='select'){
            if($(val).val()<0){
                simpleNotify('请选择商品的基本属性信息');
                baseAttrFlag=false;
            }
        }else if($(val).attr('type')=='text'){
            var checkBaseAttrFlag = inputSpecialCheck(val);
            if(!checkBaseAttrFlag){
                baseAttrFlag = checkBaseAttrFlag;
            }
        }
    }
    if(!baseAttrFlag){
        return false;
    }
    //商品特殊属性的描述
    var specailAttrCheckInfo = $("#editSpecialAttrList").find("input,select");
    var specialAttrFlag = true;
    for(var i = 0;i<specailAttrCheckInfo.length;i++){
        var val = specailAttrCheckInfo[i];
        if($(val).attr('type')=='text' &&  $(val).val()){
            specialAttrFlag = inputSpecialCheck(val);
        }
    }
    if(!specialAttrFlag){
        return false;
    }

    //商品描述
    var goodsDesc = ue.getContent();
    if(!goodsDesc){
        simpleNotify('请填写商品描述');
        return false;
    }
    //商品图片信息描述
    if(upLoadImageList.size==0){
        simpleNotify('请上传商品图片');
        return false;
    }
    //商品价格
    var goodsPrice = $("input[id='editSuggestPrice']").val();
    if(!goodsPrice){
        simpleNotify('请填写商品价格');
        return false;
    }
    if(goodsPrice){
        var reg =/(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
        if(!reg.test(goodsPrice)){
            simpleNotify('商品价格必须为合法数字(正数，最多两位小数)');
            return false;
        }
    }
    return true;
}

//特殊输入框验证
function inputSpecialCheck(val) {
    //属性值
    var attrValue = $(val).val();
    //属性编码
    var attrCode = $(val).attr("data-attrcode");
    //属性父级编码
    var parentCode = $(val).attr("data-parentattrcode");
    if(parentCode==100){
        //酒精度和瓶数的验证是在0-100之间
        var reg = /^(\+?[0-9]\d{0,2}|\+?100)$/;
        if(!reg.test(attrValue)){
            simpleNotify('商品基本信息输入的酒精度在0-100之间');
            return false;
        }
    }
    if(parentCode==130){
        //瓶数的验证是在0-100之间
        var reg = /^(\+?[1-9]\d{0,2}|\+?100)$/;
        if(!reg.test(attrValue)){
            simpleNotify('商品基本信息输入的瓶数在1-100之间');
            return false;
        }
    }
    if(parentCode==120){
        //规格的验证
        var reg = /^(\+?[1-9]\d{0,2}|\+?1000)$/;
        if(!reg.test(attrValue)){
            simpleNotify('商品基本信息输入的规格在0-1000之间');
            return false;
        }
    }
    if(attrCode==207||attrCode==301){
        //采摘年份和窖藏年份
        var message = (attrCode==207)?'特有属性窖藏年份应为999~今':'特有属性采摘年份为999年至今';
        //获取当前日期
        var currentYear = new Date().getFullYear();
        var yearsAgao99 = 999;
        if(attrValue<999 || attrValue>currentYear){
            simpleNotify(message);
            return false;
        }
    }
    if(attrCode==507){
        //麦芽度验证
        var reg = /^(0|[1-9]\d?|100)$/;
        if(!reg.test(attrValue)){
            var messageStr ='麦芽度数据信息在0-100之间';
            simpleNotify(messageStr);
            return false;
        }
    }
    return true;
}