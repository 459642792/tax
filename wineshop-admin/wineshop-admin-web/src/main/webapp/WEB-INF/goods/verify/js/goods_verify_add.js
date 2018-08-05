//富文本编辑器实例化
var ue=UE.getEditor('addContainer');
var updateDataUrl = $("#baseUrl").attr("data-url") ;
var addData={
    //主品牌名称
    mainBrandInfo:{
        name:'mainBrandInfo',
        attrValueCode:'mainBrandId',
        attrValueName:'mainBrandName',
        attrSelectFlag:'selectedFlag',
        id:'addProductMainBrand'//主品牌ID
    },
    //子品牌数据名称
    subBrandInfo:{
        name:'subBrandInfo',
        attrValueCode:'brandId',
        attrValueName:'brandName',
        attrSelectFlag:'selectedFlag',
        id:'addProductSubBrand',//子品牌ID
        subBrandList:{},//子品牌数据列表
    },
    //基本属性div信息
    baseAttrInfo:{id:'addBaseAttrList'},
    //特殊属性div信息
    specialAttrInfo:{id:'addSpecialAttrList'},
    //图像添加id
    count:0,
    //编辑当前商品ID编号
    currentProductID:'',
};
//图像上传地址存执
var addUpLoadImageList = new Set();
$(function () {
    //初始化主品牌和子品牌
    getAddBrandsList();
    //品类选择
    $(".brandType").on('click',function () {
        $(this).addClass('active').siblings().removeClass('active');
        //获取基本属性列表数据
        getAttrList($(this).attr("data-value"),addData.baseAttrInfo.id);
        //获取特有属性列表数据
        getAttrList($(this).attr("data-value"),addData.specialAttrInfo.id);
        //设置主品牌[请选择]为选中状态
        $("#"+addData.mainBrandInfo.id).find("option[value='-1']").attr("selected","true");
        //设置子品牌为空
        $("#"+addData.subBrandInfo.id).empty();
        //设置子品牌errormssage为空
        $(".subBrandErrorMessage").remove();
    });
    //主品牌，子品牌二级菜单连动
    $("#addProductMainBrand").change(function () {
        //主品牌ID
        var mainBrandId = $(this).val();
        //品牌类型
        var goodsType = $("span[class='brandType active']").attr('data-value');
        //获取子品牌数据
        getAddSubBrandList(mainBrandId,goodsType);
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
});
//新增初始化
function formInit() {
    //表单的清空
    clearProductsForm();
    //设置商品条形码
    if($("#verifyBarCode").text()){
        $("#addBarCode").val($("#verifyBarCode").text());
        $("#addBarCode").attr("disabled","true");
    }

    //获取条形码信息
    $("#addBarCode").text();
    //获取基本属性列表数据
    getAttrList('1101',addData.baseAttrInfo.id);
    //获取特有属性列表数据
    getAttrList('1101',addData.specialAttrInfo.id);
}
//清空表单数据
function clearProductsForm() {
    $("#addForm")[0].reset();
    //清楚子品牌数据
    $("#"+addData.subBrandInfo.id).empty();
    //清楚子品牌提示信息
    $(".subBrandErrorMessage").remove();
    //清楚品牌种类
    $(".brandType").removeClass('active');
    $(".brandType:first-child").addClass('active');
    //清楚商品图像信息
    $(".productsImageList .imagItem").remove();
    //图像id编号为0
    count = 0;
    //清楚set图像地址数据
    addUpLoadImageList.clear();
}
//获取主品牌数据
function getAddBrandsList() {
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
                addFormatBrandList(result.data,addData.mainBrandInfo.name);
            }
        }
    });
}
//获取子品牌数据（通过主品牌ID，品牌类型ID）
function getAddSubBrandList(mainBrandId,goodsType) {
    $.ajax({
        type:'POST',
        url:updateDataUrl+"/goodsAdmin/subBrandSearch",
        dataType:'json',
        data:{mainBrandId:mainBrandId,goodsType:goodsType},
        success:function (reseult) {
            $("#"+addData.subBrandInfo.id).empty();
            //清楚errormessage
            $(".subBrandErrorMessage").remove();
            if(reseult.success && reseult.data && reseult.data.length>0){
                //存在二级子品牌的时候
                addFormatBrandList(reseult.data,addData.subBrandInfo.name);
            }else{
                //不存在二级子品牌的时候,显示提示信息
                var errorMessage = '<p class="subBrandErrorMessage">子品牌为空，需要新增子品牌</p>'
                $("#"+addData.subBrandInfo.id).after(errorMessage);
            }
        },
        fail:function (error) {
            $("#"+addData.subBrandInfo.id).empty();
            //清楚errormessage
            $(".subBrandErrorMessage").remove();
            //不存在二级子品牌的时候,显示提示信息
            var errorMessage = '<p class="subBrandErrorMessage">子品牌为空，需要新增子品牌</p>'
            $("#"+addData.subBrandInfo.id).after(errorMessage);
        }
    })
}
//获取属性列表[基本属性：酒精度，包装，规格，瓶数],[特有属性：香型，省份，酿造工艺，窖藏年份]
function getAttrList(attrTypeId,typeName) {
    if(typeName==addData.baseAttrInfo.id){
        var ajaxUrl = updateDataUrl+'/goodsAdmin/listBaseAttr';
        var elementID = addData.baseAttrInfo.id;
    }else{
        var ajaxUrl = updateDataUrl+'/goodsAdmin/listSpecialAttr';
        var elementID = addData.specialAttrInfo.id
    }
    $.ajax({
        url:ajaxUrl,
        type:'POST',
        dataType:'json',
        data:{goodsTypeId:attrTypeId},
        success:function (result) {
            if(result.success && result.data && result.data.length>0){
                addCreateAttrSelect(result.data,elementID);
            }
        }
    })
}

//品牌下拉列表框的添加
function addFormatBrandList(resultData,type) {
    var dataList = [];
    var brandConstInfo = addData[type];

    for(var val of resultData){
        var item = {};
        //判断是否是主品牌
        if(type==addData.mainBrandInfo.name){
            //保存子品牌信息存放在列表中
            addData.subBrandInfo.subBrandList[val.mainBrandId] = val.brandVOList;
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
function addCreateAttrSelect(dataList,elementId,goodStatus) {
    $("#"+elementId).empty();
    var divHtml = '';
    for(var val of dataList){
        //商品上架中，设置基本属性为只读属性
        if(goodStatus==1 && elementId==addData.baseAttrInfo.id){
            var disabled = 'disabled="disabled"';
        }else{
            var disabled='';
        }
        var span='';
        //设置必填星号
        if(elementId==addData.baseAttrInfo.id){
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
//图片上传事件
function addUploadImage(event) {
    var fileName= 'goodsImageList';
    if(addUpLoadImageList.size>=6){
        simpleNotify('只能上传6张图片');
        return false;
    }
    window.upLoadOssImage(event,fileName,function (result) {
        //图片信息地址
        var url = window.ossImageUrl+result.name;
        addCreatImagElement(url);
    })
}
//创建图片
function addCreatImagElement(url) {
    var deleteId = "deleteImage"+addData.count;
    if(!addUpLoadImageList.has(url)){
        var imgElement = '<div class="imagItem" id="'+deleteId+'"><img src="'+url+'" alt="图片上传" class="productImgDesc"/><img src="Content/Home/image/close.png" class="deleteImage"  onclick="addDeleteUploadImage(\''+deleteId+'\')"></div>';
        //添加图片地址
        $("#photoList").append(imgElement);
        //将图片地址添加到map中
        addUpLoadImageList.add(url);
        addData.count++;
    }
    //判断上传图片只能上传6张
    if(addUpLoadImageList.size>=6){
        $(".addImageList").css({"display":"none"});
    }
}
//图片信息的删除
function addDeleteUploadImage(elementId) {
    //将图片地址从list列表中删除
    var url = $("#"+elementId+" .productImgDesc").attr('src');
    if(addUpLoadImageList.has(url)){
        addUpLoadImageList.delete(url);
    }
    $("#photoList #"+elementId).remove();
    //判断上传图片只能上传6张
    if(addUpLoadImageList.size<6){
        $(".addImageList").css({"display":"block"});
    }
}
//提价按钮的执行
function submitForm(){
    //表单验证
    if(!checkAddValidateForm()){
        return false;
    }
    //禁止表达重复提交
    $("#addSubmit").attr("disabled","disabled");
    $("#addSubmit").addClass('disablSubmit');
    //设置提交的数据
    var postDataObj = {};
    var ajaxUrl = updateDataUrl+'/goodsAdmin/goodsAdd';
    //基本属性值
    postDataObj.baseAttrList=[];
    //特有属性值
    postDataObj.specialAttrList = [];
    //品类选择
    postDataObj.goodsType = $("span[class='brandType active']").attr('data-value');
    //商品名称
    postDataObj.goodsName=$("input[name=goodsName]").val();
    //商品条码
    postDataObj.barCode = $("input[name=barCode]").val();
    //主品牌
    postDataObj.mainBrandId = $("select[name=mainBrandId]").val();
    //子品牌
    postDataObj.brandId=$("select[name=brandId]").val();
    //商品基本信息
    var baseAttrInfo = $("#addBaseAttrList").find("input,select");
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
    var specialAttrInfo = $("#addSpecialAttrList").find("input,select");
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
    postDataObj.goodsPhotoList=Array.from(addUpLoadImageList);
    //商品描述
    postDataObj.goodsDesc=ue.getContent();
    //商品价格
    postDataObj.suggestPrice = $("input[name=suggestPrice]").val();
    CommonBase.showLoading();
    $.ajax({
        url:ajaxUrl,
        type:'POST',
        dataType:'json',
        contentType:'application/json',
        data:JSON.stringify(postDataObj),
        success:function (result) {
            CommonBase.hideLoading();
            $("#addSubmit").removeAttr("disabled");
            if(result.success){
                window.simpleNotify('操作成功','提示','success');
                $("#remoteModal").modal('hide');
                listGoods();
            }else{
                window.simpleNotify(result.message,'提示','error');
                $("#remoteModal").modal('hide');
            }
        },
        fail:function (result) {
            $("#addSubmit").removeAttr("disabled");
            CommonBase.hideLoading();
            window.simpleNotify(result.message,'提示',error);
            $("#remoteModal").modal('hide');
            listGoods();
        }
    })
}

//添加新品牌
function addNewBrand() {
    $("#addNewBrandModal").modal();
    $("#remoteModal").modal('hide');
}
//表单验证
function checkAddValidateForm() {
    //商品名称验证
    var goodsName = $("input[id='addGoodsName']").val();
    if(!goodsName){
        simpleNotify('请填写商品名称')
        return false;
    }
    //商品条码的验证
    var barCode = $("input[id='addBarCode']").val();
    if(!barCode){
        simpleNotify('请填写商品条码');
        return false;
    }
    if(barCode){
        //判断条形码只能为数字
        //var reg =  /^[0-9]*$/;
        var reg =  /^[0-9a-zA-Z]+$/;
        if(!reg.test(barCode)){
            simpleNotify('商品条码只能为数字');
            return false;
        }
    }
    //商品主品牌的验证
    var mainBrandId = $("select[id='addProductMainBrand']").val();
    if(mainBrandId<0){
        simpleNotify('请选择商品主品牌');
        return false;
    }
    //商品子品牌验证
    var subBrandId = $("select[id='addProductSubBrand']").val();
    if(subBrandId<0 || subBrandId==null){
        simpleNotify('请选择商品子品牌');
        return false;
    }
    //商品基本属性的描述
    var baseAttrCheckInfo = $("#addBaseAttrList").find("input,select");
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
    var specailAttrCheckInfo = $("#addSpecialAttrList").find("input,select");
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
    //商品图片信息
    if(addUpLoadImageList.size==0){
        simpleNotify('请上传商品图片');
        return false;
    }
    //商品价格
    var goodsPrice = $("input[id='addSuggestPrice']").val();
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
