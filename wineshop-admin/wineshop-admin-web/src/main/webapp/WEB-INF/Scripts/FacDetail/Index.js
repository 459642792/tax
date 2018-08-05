var jiChuXinXi = {
    //初始化
    Init: function () {

    },
    //保存信息
    sub: function () {
        var obj = {
            FactoryName: $('#FactoryName').val(),
            FactoryShortName: $('#FactoryShortName').val(),
            FactoryLogo: '',
            FactoryOperateType: jiChuXinXi.proTypeCode(),
            Province: $('.Province').val(),
            Town: $('.Town').val(),
            District: $('.District').val(),
            FactoryAddress: $('.FactoryAddress').val(),
            FactoryTel: jiChuXinXi.factoryTel(),
            FactoryEmail: jiChuXinXi.factoryEml(),
            FactoryFax: '',
            FactoryDes: '',
            FactoryDesImg: '',
            FactoryPoint: '',
        }

        console.log(obj);

        return false;
    },
    //获取选中的经营类型
    proTypeCode: function () {
        var proCode = '';

        $('.protypeCode').each(function (i, data) {
            if ($(this).is(':checked')) {
                proCode += $(this).val() + ',';
            }
        });
        return proCode;
    },
    //获取企业电话
    factoryTel: function () {
        var tel = '';
        $('.factel').each(function (i, data) {
            if ($(this).val() != '' & $(this).val() != null) {
                tel += $(this).val() + ',';
            }
        });
        return tel;
    },
    //获取企业邮箱
    factoryEml: function () {
        var eml = '';
        var emil = $("#emil_test").val();
        var de_emil = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;

        $('.facEml').each(function (i, data) {
            if ($(this).val() != '' & $(this).val() != null & de_emil.test($(this).val())) {
                eml += $(this).val() + ',';
            }
        });
        return eml;
    },
    //获取城市信息
    cityInit: function (parentCode, dom, cityCode) {
        if (parentCode != '') {
            $.post('/FacDetail/GetArea', {parentCode: parentCode}, function (data, status) {
                if (status == 'success') {
                    jiChuXinXi.cityBind(data, dom, cityCode);
                }
            });
        }
    },
    //绑定城市数据
    cityBind: function (data, dom, cityCode) {
        $(dom).empty();
        var checked = 'selected="selected"';
        $.each(data, function (i, data) {
            var str = '';
            str += ' <option value="' + data.CityCode + '"';
            if (cityCode == data.CityCode) {
                str += checked;
            }
            str += '>' + data.CityName + '</option>';
            $(dom).append(str);
        })
    },
    //修改城市
    changeCity: function (citycode, code) {
        console.log(citycode + '--' + code);
        switch (code) {
            case 1:
                jiChuXinXi.cityInit(citycode, '.Town', '@entity.Town');
                $('.District').empty();
                break;
            case 2:
                jiChuXinXi.cityInit(citycode, '.District', '@entity.District');
                break;
            default:

                break;
        }
    },
    //删除联系人信息
    delLianXiRen: function (obj) {
        $(obj).closest('.row').remove();
    }
}


$('.facEml').on('blur', function () {
    var de_emil = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
    if (!de_emil.test($(this).val())) {
        $(this).focus();
    }
})

//动态添加联系人信息
$('.addbutton').click(function () {
    $('.lianxiren').append($('.lianxirenrow').clone().removeClass('lianxirenrow hidden'));
})