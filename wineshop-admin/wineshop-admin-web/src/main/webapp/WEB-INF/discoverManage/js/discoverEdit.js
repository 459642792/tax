//实例化编辑器
var ue = UE.getEditor('container');
// UE.Editor.prototype._bkGetActionUrl=UE.Editor.prototype.getActionUrl;
// UE.Editor.prototype.getActionUrl=function(action){
//     if (action == 'uploadimage'){
//         console.log("1");
//     }else{
//         return this._bkGetActionUrl.call(this, action);
//     }
// };
var ImageList = [];
var groomList = []

function getProvinceList(dom) {
    //初始化调用省份
    $.ajax({
        type: "get",
        url: $("#baseUrl").attr("data-url") + "/couponMain/ProvinceList",
        success: function (result) {
            var str = '';
            for (var i = 0; i < result.data.length; i++) {
                str += "<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>";
            }
            dom.append(str)
        }
    })
};

/*审核*/
function getShenHeId(id, stuts) {
    if (stuts == 2) {
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
    } else if (stuts == 3) {
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
    }

}

$(function () {
    CommonBase.showLoading();
    init();
    /*调用初始化省份*/
    ;
    if ($("#baseUrl").attr("data-admin") == "admin") {
        getProvinceList($('#Province'));
    }
    //省市联动数据调用
    $('#allAddr').on("change", "#Province", function () {
        var that = $(this);
        var val = $(this).find("option:selected").val();
        that.next(".city").empty();
        that.next(".county").empty();
        $.ajax({
            type: "get",
            url: $("#baseUrl").attr("data-url") + "/couponMain/cityList",
            data: {
                parent: val
            },
            success: function (result) {
                var str = '';
                for (var i = 0; i < result.data.length; i++) {
                    str += "<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>";
                }
                that.next(".city").append(str);
            }
        })
    })

    //区域联动数据调用
    $('#allAddr').on("change", ".city", function () {
        var that = $(this);
        var val = $(this).find("option:selected").val();
        that.next(".county").empty();
        $.ajax({
            type: "get",
            url: $("#baseUrl").attr("data-url") + "/couponMain/countyList",
            data: {
                parent: val
            },
            success: function (result) {
                var str = '';
                for (var i = 0; i < result.data.length; i++) {
                    str += "<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>";
                }
                that.next('.county').append(str)
            }
        })
    })
    /*添加地址*/
    $("#allAddr").on("click", "#addAddr", function () {
        if ($("#allAddr").find(".row").length > 4) {
        } else {
            var str = '<section class="row"><div class="address">'
            str += '<div class="label col col-2"></div>'
            str += '<div class="col col-9" class="getNewAddr">'
            str += '<select class="input-sm"  id="Province" name="Province"  style="width:150px">'
            str += '</select> '
            str += '<select class="input-sm Town city"  name="city" style="width:150px">'
            str += '</select> '
            str += '<select class="input-sm Town county"  name="county" style="width:150px">'
            str += '</select> '
            str += '</div>'
            str += '<label class="col col-1 removeAddr glyphicon glyphicon-minus" style="height: 24px;line-height: 24px"></label>'
            str += '</div></section>'
            $("#allAddr").append(str);
            getProvinceList($("#allAddr .row:last-child").find("#Province"));
        }
    })
    /*删除地址*/
    $("#allAddr").on("click", ".removeAddr", function () {
        $(this).parent().parent().remove();
    })
    /*推荐店铺*/
    if ($("#baseUrl").attr("data-admin") == "admin") {
        $("#isGroom").on("change", function () {
            if ($("#isGroom").is(":checked")) {
                $.ajax({
                    url: $("#baseUrl").attr("data-url") + "/discover/GroomCount ",
                    type: "get",
                    success: function (data) {
                        if (data.success) {
                            if (data.count > 3) {
                                $("#groomModal").modal({backdrop: false});
                                $.ajax({
                                    url: $("#baseUrl").attr("data-url") + "/discover/LstGroom",
                                    type: "get",
                                    success: function (result) {
                                        if (result.success) {
                                            $("#groomTable tbody").empty();
                                            if (result.data.length != 0) {
                                                var str = ''
                                                for (var i = 0; i < result.data.length; i++) {
                                                    str += '<tr><td style="text-align:center"><input type="checkbox" checked="true" data-id="' + result.data[i].id + '" class="groomCheckBox"></td><td   style="text-align:center">' + result.data[i].title + '</td><td  style="text-align:center">' + result.data[i].type + '</td></tr>'
                                                }
                                                $("#groomTable tbody").append(str);
                                            } else {
                                                $("#groomTable tbody").append("<tr><td colspan='3' style='text-align: center'>暂无数据</td></tr>");
                                            }
                                        }

                                    }
                                })
                            }
                        }
                    }
                })
            } else {
                var id = $("#baseUrl").attr("data-id");
                for (var i = 0; i < groomList.length; i++) {
                    if (groomList[i] == id) {
                        groomList.splice(i, 1);
                    }
                }
            }
        });
        $("#subGroom").unbind("click")
        $("#subGroom").on("click", function () {
            var tr = $("#groomTable tbody tr");
            groomList = []
            var _groomList = []
            for (var i = 0; i < tr.length; i++) {
                if (tr.eq(i).find(".groomCheckBox").is(":checked")) {
                    _groomList.push(tr.eq(i).find(".groomCheckBox").attr('data-id'));
                }
            }
            if (_groomList.length >= 3) {
                simpleNotify("您最多能勾选2两个推荐活动。");
                return false;
            } else {
                groomList = _groomList;
                $('#groomModal').modal('hide')
            }

        })
        $('#reSubGroom').on('click', function () {
            $('#remoteModal').css({'overflow': 'scroll'});
            $("#isGroom").attr("checked", false);
            $("body").addClass("modal-open");
        });
    }
    /*图片上传*/
    $("#coverImg li").on("change", function (e) {
        var that = this;
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
                            $(that).append("<span class='imgDelete' style=' z-index:999;position: absolute;width: 20px;border-radius:50%;right: -10px;top: -10px;height: 20px;line-height: 20px;text-align: center;line-height: 20px;background-color: red;color: #ffffff'>X</span>");
                            ImageList[$(that).index()] = data.Data;
                        }
                    },
                    error: function (data) {
                        console.log(data.message)
                    }
                })
            }

        }
    });
    /*图片删除*/
    $("#coverImg").on("click", ".imgDelete", function (e) {
        e.preventDefault();
        e.stopPropagation();
        var index = $(this).parent().index();
        $(this).prev().prev().attr("src", "Content/themes/smart/img/loadImg.png");
        ImageList[index] = '';
        $(this).remove();
        return false;
    })
    /*为数组添加克隆方法*/
    Array.prototype.clone = function () {
        return this.slice(0);
    }
    //保存事件操作
    $('#subBtnItem').on("click", "#submit", function () {
        $.ajax({
            url: $("#baseUrl").attr("data-url") + "/discover/GroomCount ",
            type: "get",
            success: function (data) {
                if (data.count >= 3) {
                    $.ajax({
                        url: $("#baseUrl").attr("data-url") + "/discover/UpdateGroom",
                        type: "Post",
                        data: {LstId: JSON.stringify(groomList)},
                        success: function (data) {
                            if (data.isSuccessed) {
                                simpleNotify("推荐成功")
                            }
                        }
                    })
                }
            }
        });
        var subListImg = [];
        subListImg = ImageList.clone();
        if ($('#title').val().trim() == '') {
            simpleNotify("标题不能为空");
            return false;
        }
        if (subListImg.length <= 3) {
            for (var i = 0; i < subListImg.length; i++) {
                if (subListImg[i] == '' || subListImg[i] == null || subListImg[i] == undefined) {
                    subListImg.splice(i, 1);
                    i = i - 1;
                }
            }
            if (subListImg.length == 2) {
                simpleNotify("图片不能上传两张");
                return false;
            } else if (subListImg.length == 0) {
                if ($("#isGroom").is(":checked") == true) {
                    simpleNotify("推荐活动封面图不能为空")
                    return false;
                } else {
                    ImageList = [];
                }
            }
        } else {
            return false;
        }
        if ($('#coverType option:selected').val() == '') {
            simpleNotify("请选择类型");
            return false;
        }
        if (ue.getContent() == "") {
            simpleNotify("请输入内容")
            return false;
        }
        CommonBase.showLoading();
        /*获取地址Json数组*/
        var addrList = [];
        var allAddr = $("#allAddr").find(".address");
        for (var i = 0; i < allAddr.length; i++) {
            if (allAddr.eq(i).find(".county option:selected").val() != undefined && allAddr.eq(i).find(".county option:selected").val() != 0) {
                addrList.push(allAddr.eq(i).find(".county option:selected").val() + "/" + allAddr.eq(i).find("#Province option:selected").text() + "_" + allAddr.eq(i).find(".city option:selected").text() + "_" + allAddr.eq(i).find(".county option:selected").text())
            } else {
                if (allAddr.eq(i).find(".city option:selected").val() != undefined && allAddr.eq(i).find(".city option:selected").val() != 0) {
                    addrList.push(allAddr.eq(i).find(".city option:selected").val() + "/" + allAddr.eq(i).find("#Province option:selected").text() + "_" + allAddr.eq(i).find(".city option:selected").text())
                } else {
                    addrList.push(allAddr.eq(i).find("#Province option:selected").val() + "/" + allAddr.eq(i).find("#Province option:selected").text())

                }
            }
        }
        /*获标签名组*/
        if ($("#tagMenu").val() != '') {
            var tagList = $("#tagMenu").val().split(",").join("; ");
        } else {
            var tagList = [];
        }
        /*获取店铺ID组*/
        if ($("#baseUrl").attr("data-admin") != "admin") {
            var obj = {
                Id: $("#baseUrl").attr("data-id"),
                Title: $('#title').val().trim(),                             //标题
                Type: $('#coverType option:selected').text(),      //类型
                HandLine: $('#isShowTitle').is(":checked") == true ? "Y" : "N",    //头条显示
                Label: tagList,                                              //标签合集
                LstVendor: JSON.stringify($("#shopLink").val() == null ? [] : $("#shopLink").val()),                                             //店铺链接合集
                Detail: ue.getContent(),                                       //富文本详情
                LstFaceImage: JSON.stringify(ImageList),                                       //图片集合
                Groom: "N"
            };
        } else {
            var obj = {
                Id: $("#baseUrl").attr("data-id"),
                Title: $('#title').val().trim(),                             //标题
                Type: $('#coverType option:selected').text(),      //类型
                HandLine: $('#isShowTitle').is(":checked") == true ? "Y" : "N",    //头条显示
                LstCitys: JSON.stringify(addrList),                                          //地址集合
                Label: tagList,                                              //标签合集
                LstVendor: JSON.stringify($("#shopLink").val() == null ? [] : $("#shopLink").val()),                                             //店铺链接合集
                Detail: ue.getContent(),                                       //富文本详情
                LstFaceImage: JSON.stringify(ImageList),                                        //图片集合
                Groom: $("#isGroom").is(":checked") == true ? "Y" : "N"
            };
        }
        $.post($("#baseUrl").attr("data-url") + "/discover/Editdiscover", obj, function (data) {
            CommonBase.hideLoading();
            if (data.success) {
                window.simpleNotify("保存成功", '提示', "success");
                $("#remoteModal").modal("hide");
                window.location.reload();
            }
            else {
                window.simpleNotify("保存失败。", "提示", "error");
                return false;
            }
        })
    })

});

function init() {
    //初始化详情数据
    $.ajax({
        type: "get",
        data: {Id: $("#baseUrl").attr("data-id")},
        url: $("#baseUrl").attr("data-url") + "/discover/GetDiscover",
        success: function (result) {
            var btnStr = ''
            if (result.data.status == 1 && $("#baseUrl").attr("data-admin") == "admin") {
                $("#dialogTitle").text("查看/审核")
                btnStr += '<button type="button" onclick="getShenHeId(' + $("#baseUrl").attr("data-id") + ',2)" class="btn btn-primary" id="submit">'
                btnStr += '<i class="fa fa-save"></i>'
                btnStr += '审核通过'
                btnStr += '</button>'
                btnStr += '<button type="button" onclick="getShenHeId(' + $("#baseUrl").attr("data-id") + ',3)"  class="btn btn-default" data-dismiss="modal">'
                btnStr += '审核拒绝'
                btnStr += '</button>'
            } else {
                btnStr += '<button type="button" class="btn btn-primary" id="submit">'
                btnStr += '<i class="fa fa-save"></i>'
                btnStr += '保存'
                btnStr += '</button>'
                btnStr += '<button type="button" class="btn btn-default" data-dismiss="modal">'
                btnStr += '取消'
                btnStr += '</button>'
            }
            $("#subBtnItem").append(btnStr);
            /*select搜索店铺地址*/
            $.ajax({
                type: "get",
                url: $("#baseUrl").attr("data-url") + "/discover/DisvendorList",
                data: {Id: $("#baseUrl").attr("data-id")},
                success: function (data) {
                    var str = '';
                    var addr = ''
                    for (var i = 0; i < data.data.length; i++) {
                        addr = data.data[i].tradingArea == null ? "无" : data.data[i].tradingArea;
                        str += "<option value='" + data.data[i].id + "'>" + data.data[i].name + "(" + addr + ")</option>"
                    }
                    $("#shopLink").append(str);

                    /*
                     *
                     * 初始化链接店铺
                     */
                    $("#shopLink").select2({
                        maximumSelectionSize: 10,
                        formatSelectionTooBig: "最多可以选十个哦"
                    }).val(result.data.lstVendor).trigger("change");
                }
            })
            /*判断是够能够修改*/
            if (result.data.status == 1) {
                if ($("#baseUrl").attr("data-admin") != "admin") {
                    $("#submit").remove()
                    $("#dialogTitle").append("<span style='color:red'>（该状态仅能查看）</span>")
                }
            }
            if (result.data.status == 2) {
                if (result.data.isShow == "Y") {
                    $("#submit").remove()
                    $("#dialogTitle").append("<span style='color:red'>（该状态仅能查看）</span>")
                }
            }
            ;
            $("#title").val(result.data.title);
            $("#coverType").find("option:contains('" + result.data.type + "')").attr("selected", "selected");
            result.data.groom == "Y" ? $("#isGroom").attr("checked", "checked") : $("#isGroom").removeAttr("checked")
            result.data.handLine == "Y" ? $("#isShowTitle").attr("checked", "checked") : $("#isShowTitle").removeAttr("checked");
            if (result.data.detail) {
                ue.ready(function () {
                    //设置编辑器的内容
                    ue.setContent(result.data.detail);
                });
            }
            ImageList = result.data.lstFaceImage;
            if (result.data.lstFaceImage.length != 0) {
                for (var i = 0; i < result.data.lstFaceImage.length; i++) {
                    if (result.data.lstFaceImage[i] != '') {
                        $("#coverImg").find("li").eq(i).find("img").attr("src", result.data.lstFaceImage[i]);
                        $("#coverImg").find("li").eq(i).append("<span class='imgDelete' style=' z-index:999;position: absolute;width: 20px;border-radius:50%;right: -10px;top: -10px;height: 20px;line-height: 20px;text-align: center;line-height: 20px;background-color: red;color: #ffffff'>X</span>");
                    }
                }
            }

            /*选择标签*/
            if (result.data.label != "" && result.data.label != null) {
                var label = result.data.label.split("; ");
                $("#tagMenu").select2({
                    placeholder: "请输入标签",
                    containerCss: {border: "0 none"},
                    maximumSelectionSize: 4,
                    minimumResultsForSearch: Infinity,
                    formatSelectionTooBig: "最多可以选四个哦",
                    tags: [],
                    allowClear: true
                }).val(label).trigger("change");
            } else {
                $("#tagMenu").select2({
                    placeholder: "请输入标签",
                    minimumResultsForSearch: Infinity,
                    containerCss: {border: "0 none"},
                    maximumSelectionSize: 4,
                    formatSelectionTooBig: "最多可以选四个哦",
                    tags: [],
                    allowClear: true
                });
            }
            /*推荐专区*/
            $.ajax({
                url: $("#baseUrl").attr("data-url") + "/discover/GroomCount ",
                type: "get",
                success: function (data) {
                    if (data.success) {
                        if (data.count >= 3) {
                            $.ajax({
                                url: $("#baseUrl").attr("data-url") + "/discover/LstGroom",
                                type: "get",
                                success: function (results) {
                                    if (result.success) {
                                        $("#groomTable tbody").empty();
                                        if (result.data.length != 0) {
                                            for (var i = 0; i < results.data.length; i++) {
                                                groomList.push(results.data[i].id)
                                            }
                                        }
                                    }

                                }
                            })
                        }
                    }
                }
            })
            /*获取所有省份*/
            if ($("#baseUrl").attr("data-admin") == "admin") {
                $.ajax({
                    type: "get",
                    url: $("#baseUrl").attr("data-url") + "/couponMain/ProvinceList",
                    success: function (province) {
                        $("#allAddr").empty();
                        var str;
                        for (var i = 0; i < result.data.lstCitys.length; i++) {
                            var addr = result.data.lstCitys[i].split("/");
                            var city = addr[0].split("_");
                            var code = addr[1].split("_");
                            str = '';
                            str += '<section class="row"><div class="address">'
                            if (i == 0) {
                                str += '<div class="label col col-2">发布地区</div>'
                            } else {
                                str += '<div class="label col col-2"></div>'
                            }
                            str += '<div class="col col-9" class="getNewAddr">'
                            str += '<select class="input-sm"  id="Province" name="Province"  style="width:150px">'
                            for (var j = 0; j < province.data.length; j++) {
                                if (province.data[j].code == code[0]) {
                                    str += '<option value="' + province.data[j].code + '" selected="selected">' + province.data[j].name + '</option>'
                                } else {
                                    str += '<option value="' + province.data[j].code + '">' + province.data[j].name + '</option>'
                                }

                            }
                            str += '</select> '
                            str += '<select class="input-sm Town city"  name="city" style="width:150px">'
                            $.ajax({
                                type: "get",
                                url: $("#baseUrl").attr("data-url") + "/couponMain/cityList",
                                data: {
                                    parent: code[0]
                                },
                                async: false,
                                success: function (city) {
                                    for (var k = 0; k < city.data.length; k++) {
                                        if (code[0] + "_" + code[1] == city.data[k].code) {
                                            str += '<option value="' + city.data[k].code + '" selected="selected">' + city.data[k].name + '</option>'
                                        } else {
                                            str += '<option value="' + city.data[k].code + '">' + city.data[k].name + '</option>'
                                        }
                                    }
                                }
                            })
                            str += '</select> '
                            str += '<select class="input-sm Town county"  name="county" style="width:150px">'
                            $.ajax({
                                type: "get",
                                async: false,
                                url: $("#baseUrl").attr("data-url") + "/couponMain/countyList",
                                data: {
                                    parent: code[0] + "_" + code[1]
                                },
                                success: function (county) {
                                    for (var l = 0; l < county.data.length; l++) {
                                        if (county.data[l].code == code[0] + "_" + code[1] + "_" + code[2]) {
                                            str += '<option value="' + county.data[l].code + '" selected="selected">' + county.data[l].name + '</option>'
                                        } else {
                                            str += '<option value="' + county.data[l].code + '">' + county.data[l].name + '</option>'
                                        }
                                    }
                                }
                            })
                            str += '</select> '
                            str += '</div>'
                            if (i == 0) {
                                str += '<label id="addAddr" class="col col-1 glyphicon glyphicon-plus" style="height: 24px;line-height: 24px"></label>'
                            } else {
                                str += '<label class="col col-1 removeAddr glyphicon glyphicon-minus" style="height: 24px;line-height: 24px"></label>'
                            }
                            str += '</div></section>'
                            $("#allAddr").append(str);
                        }
                    }
                })
            }

            CommonBase.hideLoading();
        }
    });
}